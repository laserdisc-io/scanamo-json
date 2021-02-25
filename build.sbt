import sbt.Keys.{ credentials, publishTo }

lazy val scala212               = "2.12.10"
lazy val scala213               = "2.13.1"
lazy val supportedScalaVersions = List(scala212, scala213)

scalaVersion       in ThisBuild := scala212
crossScalaVersions in ThisBuild := supportedScalaVersions

organization := "io.laserdisc"

def commonOptions(scalaVersion: String) =
  CrossVersion.partialVersion(scalaVersion) match {
    case Some((2, 12)) =>
      Seq("-Ypartial-unification")
    case _ => Seq.empty
  }

lazy val commonSettings = Seq(
  organization := "io.laserdisc",
  developers := List(
    Developer(
      "semenodm",
      "Dmytro Semenov",
      "sdo.semenov@gmail.com",
      url("https://github.com/semenodm")
    )
  ),
  licenses           ++= Seq(("MIT", url("http://opensource.org/licenses/MIT"))),
  homepage           := Some(url("https://github.com/laserdisc-io/scanamo-json")),
  crossScalaVersions := supportedScalaVersions,
  scalaVersion       := scala213,
  fork               in Test := true,
  scalacOptions ++= Seq(
    "-encoding",
    "UTF-8",                         // source files are in UTF-8
    "-deprecation",                  // warn about use of deprecated APIs
    "-unchecked",                    // warn about unchecked type parameters
    "-feature",                      // warn about misused language features
    "-language:higherKinds",         // allow higher kinded types without `import scala.language.higherKinds`
    "-language:implicitConversions", // allow use of implicit conversions
    "-language:postfixOps",
    "-Xlint",             // enable handy linter warnings
    "-Xfatal-warnings",   // turn compiler warnings into errors
    "-Ywarn-macros:after" // allows the compiler to resolve implicit imports being flagged as unused
  ),
  addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1"),
  addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.10.3"),
  libraryDependencies += "org.scala-lang.modules" %% "scala-collection-compat" % "2.4.1"
)

lazy val root = project
  .in(file("."))
  .settings(commonSettings)
  .settings(crossScalaVersions := Nil)
  .settings(noPublishSettings)
  .aggregate(circe, tests)

lazy val CirceVersion     = "0.13.0"
lazy val ScanamoVersion   = "1.0.0-M15"
lazy val ScalaTestVersion = "3.2.3"

lazy val circe = project
  .in(file("scanamo-circe"))
  .settings(commonSettings)
  .settings(scalacOptions := commonOptions(scalaVersion.value))
  .settings(
    moduleName := "scanamo-circe",
    libraryDependencies ++=
      Seq(
        "io.circe"      %% "circe-parser" % CirceVersion,
        "org.scanamo"   %% "scanamo"      % ScanamoVersion,
        "org.scalatest" %% "scalatest"    % ScalaTestVersion % "test"
      )
  )
  .dependsOn(tests % "test")

//lazy val play = project
//  .in(file("scanamo-play-json"))
//  .settings(commonSettings)
//  .settings(scalacOptions := commonOptions(scalaVersion.value))
//  .settings(publishSettings)
//  .settings(
//    moduleName := "scanamo-play-json",
//    libraryDependencies ++=
//      Seq(
//        "com.typesafe.play" %% "play-json" % PlayVersion,
//        "org.scanamo" %% "scanamo" % ScanamoVersion,
//        "org.scalatest" %% "scalatest" % ScalaTestVersion % "test"
//      )
//  )
//  .dependsOn(tests % "test")

lazy val tests = project
  .in(file("tests"))
  .settings(commonSettings)
  .settings(scalacOptions := commonOptions(scalaVersion.value))
  .settings(
    moduleName := "scanamo-json-tests",
    libraryDependencies ++=
      Seq(
        "org.scanamo"   %% "scanamo"   % ScanamoVersion,
        "org.scalatest" %% "scalatest" % ScalaTestVersion
      )
  )

lazy val noPublishSettings = Seq(
  publish         := {},
  publishLocal    := {},
  publishArtifact := false
)
