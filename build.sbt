// https://github.com/earldouglas/xsbt-web-plugin#starting-from-scratch
organization := "org.lifetime"

name := "whatever"

version := "1.0"

jetty()

libraryDependencies ++= {
  val liftVersion = "2.6-RC1"
  Seq(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile",
    "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided"
  )
}