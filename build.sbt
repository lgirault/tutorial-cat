
version in ThisBuild := "1.0"

scalaVersion in ThisBuild := "2.12.6"

libraryDependencies in ThisBuild += "org.scalacheck" %% "scalacheck" % "1.13.4" % Test

lazy val tutorialCat =
  Project("tutorial-cat", file("tutorial-cat"))

