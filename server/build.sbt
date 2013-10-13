name := "Krautmail"

version := "0.0.1"

scalaVersion in ThisBuild := "2.10.2"

libraryDependencies in ThisBuild ++= Seq( "com.typesafe.akka" %% "akka-actor" % "2.2.1",
                                          "org.clapper" % "grizzled-slf4j_2.10" % "1.0.1",
                                          "org.slf4j" % "slf4j-api" % "1.7.2",
                                          "com.typesafe.akka" % "akka-actor_2.10" % "2.2.1",
                                          "org.slf4j" % "jcl-over-slf4j" % "1.7.0" % "runtime",
                                          "ch.qos.logback" % "logback-core" % "1.0.1" % "runtime",
                                          "ch.qos.logback" % "logback-classic" % "1.0.7" % "runtime",
                                          "org.specs2" %% "specs2" % "2.2.3" % "test",
                                          "org.mockito" % "mockito-all" % "1.9.5" % "test")



scalacOptions in Test ++= Seq("-Yrangepos")

resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
                  "releases"  at "http://oss.sonatype.org/content/repositories/releases",
                  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/")
