name := "fl"

version := "0.2"

scalaVersion := "2.11.2"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

enablePlugins(Antlr4Plugin)
antlr4Version in Antlr4 := "4.7"
antlr4PackageName in Antlr4 := Some("com.simplytyped")