package org.lifetime.liftfromscratch

/**
 * Created by training on 6/2/15.
 */

import org.scalatra.test.scalatest._
import org.scalatest.FunSuiteLike
import servlets.{LoggingService, HeaderApp}


class ServletsTests extends ScalatraSuite with FunSuiteLike {

  addServlet(classOf[LoggingService], "/*")

  test("simple post") {
    post("/tests/suites") {
      status should equal (200)
      body should include ("logged")
    }
  }
}
