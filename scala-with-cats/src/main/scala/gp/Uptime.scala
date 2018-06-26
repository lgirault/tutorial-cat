package gp

import cats.{Foldable, Monoid, Semigroup}
import cats.implicits._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import Uptime._
object Uptime {

  implicit val MaxUptimeMonoid: Monoid[(String, Int)] = new Monoid[(String, Int)]{
    override def empty: (String, Int) = ("no-host", 0)

    override def combine(x: (String, Int), y: (String, Int)): (String, Int) = (x, y) match {
      case ((_, ut1), (_, ut2)) => if (ut1 >= ut2) x else y
    }
  }
}

trait Uptime {

  def getHostnameList(): Future[List[String]]

  def getUptime(hostname: String): Future[Int]

  def getMaxUptime(): Future[(String, Int)] = {
    for {
      hosts <- getHostnameList()
      timedHosts <- hosts.traverse[Future, (String, Int)]{h =>
        getUptime(h).map((h, _))
      }
    } yield Foldable[List].fold(timedHosts)

  }
}
