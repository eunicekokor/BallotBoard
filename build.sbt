name := """BallotBoard"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.mindrot" % "jbcrypt" % "0.3m",
  "uk.co.panaxiom" %% "play-jongo" % "0.9.0-jongo1.2",
  javaJdbc,
  cache,
  javaWs
)


// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


fork in run := false
