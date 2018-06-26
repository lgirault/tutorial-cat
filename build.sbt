
version in ThisBuild := "1.0"

scalaVersion in ThisBuild := "2.12.6"

libraryDependencies in ThisBuild += "org.scalacheck" %% "scalacheck" % "1.13.4" % Test

lazy val tutorialCat =
  Project("tutorial-cat", file("tutorial-cat"))

lazy val scalaWithCats =
  Project("scala-with-cats", file("scala-with-cats"))
    .settings(libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "1.1.0",
      "org.typelevel" %% "cats-laws" % "1.1.0" % Test
    ))
