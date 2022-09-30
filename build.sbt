name := """urlShortner_scala"""
organization := "samit"
version := "1.0"

lazy val `urlShortner_scala` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.2"
libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test, guice,
  "org.picoworks" %% "pico-hashids"  % "4.4.141",
  "net.debasishg" %% "redisclient" % "3.7",
  "io.circe" %% "circe-core" % "0.9.3",
  "io.circe" %% "circe-generic" % "0.9.3",
  "io.circe" %% "circe-parser" % "0.9.3")

unmanagedResourceDirectories in Test += baseDirectory ( _ /"target/web/public/test" ).value