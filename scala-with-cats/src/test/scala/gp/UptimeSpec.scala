package gp

import cats.implicits._
import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen, Properties}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

case class TestUptime(hosts : Map[String, Int]) extends Uptime {
  override def getHostnameList(): Future[List[String]] =
    Future.successful(hosts.keys.toList)

  override def getUptime(hostname: String): Future[Int] =
    Future.successful(hosts(hostname))
}

class UptimeSpec extends Properties(s"UptimeProperties") {

  val genHost : Gen[(String, Int)] =
    for{
      hn <- Gen.identifier
      ut <- Gen.chooseNum(0, 100000)
    } yield (hn, ut)

  val genHosts : Gen[Map[String, Int]] =
    for {
      n <- Gen.chooseNum(3, 50)
      hs <- Gen.listOfN(n, genHost)
    } yield hs.toMap

  property("total") = forAll(genHosts) { hs: Map[String, Int] =>

    val service = TestUptime(hs)

    val expected = hs.maxBy(_._2)

    val actual = Await.result(service.getMaxUptime(), 1 second)


    expected === actual
  }

}
