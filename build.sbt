name := "fl"

version := "0.2"

scalaVersion := "2.11.2"

resolvers += "bintray-drdozer" at "http://dl.bintray.com/content/drdozer/maven"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

libraryDependencies += "uk.co.turingatemyhamster" %% "gv-core" % "0.3.1"