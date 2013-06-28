name := "MongoDb Client"

version := "1.0"

scalaVersion := "2.10.0"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")



libraryDependencies += "org.scalaz" %% "scalaz-core" % "6.0.3"

libraryDependencies += "org.mongodb" % "mongo-java-driver" % "2.9.3"

libraryDependencies +=   "org.scala-tools.testing" % "specs" % "1.6.2" % "test"


