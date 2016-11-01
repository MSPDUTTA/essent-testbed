organization := "lbl.gov"

version := "0.1"

name := "playground"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-deprecation", "-unchecked")

libraryDependencies += "com.github.scopt" %% "scopt" % "3.4.0"

lazy val chisel = (project in file("chisel3"))
lazy val firrtl = (project in file("firrtl"))
lazy val firrtl_interpreter = (project in file("firrtl-interpreter")).dependsOn(chisel, firrtl)
lazy val chisel_testers = (project in file("chisel-testers")).dependsOn(chisel, firrtl, firrtl_interpreter)

lazy val essent = (project in file("essent")).dependsOn(firrtl)

lazy val root = (project in file(".")).dependsOn(chisel, chisel_testers, essent)

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)
