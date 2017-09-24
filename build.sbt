name := "automaton"

version := "0.2"

scalaVersion := "2.11.2"

unmanagedJars in Compile += file("libs/FSAUtils_GUI.jar")

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"