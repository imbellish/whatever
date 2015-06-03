// https://github.com/earldouglas/xsbt-web-plugin#starting-from-scratch
organization := "org.lifetime"

name := "whatever"

version := "1.0"

jetty()

libraryDependencies ++= {
  val liftVersion = "2.6-RC1"
  Seq(
    //"net.liftweb" %% "lift-webkit" % liftVersion % "compile",
    "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided",
    "org.scalatra" %% "scalatra" % "2.3.0",
    "org.scalatra" %% "scalatra-scalate" % "2.3.0",
    "org.scalatra" %% "scalatra-json" % "2.3.0",
    "org.json4s" %% "json4s-jackson" % "3.2.0",
    "org.scalatra" %% "scalatra-scalatest" % "2.4.0.RC1" % "test"
    //"scalikejdbc
  )
}