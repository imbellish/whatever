package servlets

import scala.xml.NodeSeq
import scala.xml.PrettyPrinter
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.ServletContext


import org.scalatra._

// pretty sure I don't use this class TODO
class ScalatraBootstrap extends LifeCycle

class HomePage extends HttpServlet {

  override def doGet(request: HttpServletRequest, response: HttpServletResponse) {

    response.setContentType("text/html")
    response.setCharacterEncoding("UTF-8")

    val responseBody: NodeSeq =
      <html>
        <body>
          <h1>whatever</h1>
          <p>This is an app that checks for auth headers</p>
          <p>Get PostMan for google chrome to debug it</p>
          <h2>To do list:</h2>
          <ul>
            <li>create a branch for simple JSON webservices boilerplate</li>
            <li>that's already been done with scalatra</li>
          </ul>
        </body>
      </html>

    val printer = new PrettyPrinter(80, 2)

    response.getWriter.write(printer.formatNodes(responseBody))

  }
}

class HeaderApp extends HttpServlet {
  override def doPost(request: HttpServletRequest, response: HttpServletResponse) {
    /* does not allow non-authorizeds posts */

    response.setContentType("application/json")
    response.setCharacterEncoding("UTF-8")

    val headers = request.getAuthType()
    val query = request.getQueryString()
    val url = request.getRequestURL()
    val authorization = request.getHeader("Authorization")

    // TODO make a better response

    val responseString =    "Hello, your auth header is " +
                            headers +
                            " and your query string is " +
                            query +
                            " and you are on " +
                            url  +
                            ". The authorization code you have is " +
      authorization
    // return 403 (not authorized)
    def respond() =
    request match {
      case _ if request.getHeader("Authorization") == null => response.setStatus(401); response.getWriter.write("You shall not pass")
      case _ => response.setStatus(201); response.getWriter.write(responseString); ActivityLogging.writeHeaders(url.toString, authorization.toString)
    }
    respond
  }
}

class LoggingService extends ScalatraServlet {
//training@ac-ubuntu:~/Documents/whatever$ mysql -h localhost -u root -p

  post("/:logType/:name") {
    val logType = params("logType")
    val name = params("name")
    ActivityLogging.writeService(logType, name)
    "logged"
  }

}

object ActivityLogging {
  import scalikejdbc._
  /*
  * Requires mysql db on your local system with the following table specs:
  *
  * Logging
    +-------+-------------+------+-----+---------+----------------+
    | Field | Type        | Null | Key | Default | Extra          |
    +-------+-------------+------+-----+---------+----------------+
    | id    | bigint(20)  | NO   | PRI | NULL    | auto_increment |
    | event | varchar(50) | YES  |     | NULL    |                |
    | time  | date        | YES  |     | NULL    |                |
    | name  | varchar(30) | YES  |     | NULL    |                |
    +-------+-------------+------+-----+---------+----------------+

  * Headers
    +--------+-------------+------+-----+---------+----------------+
    | Field  | Type        | Null | Key | Default | Extra          |
    +--------+-------------+------+-----+---------+----------------+
    | id     | int(11)     | NO   | PRI | NULL    | auto_increment |
    | header | varchar(50) | YES  |     | NULL    |                |
    | url    | varchar(90) | YES  |     | NULL    |                |
    +--------+-------------+------+-----+---------+----------------+

  *
   */
  ConnectionPool.singleton("jdbc:mysql://localhost/activity", "root", "training")
  implicit val session = AutoSession

  //class Event(val name: String, val created: Timestamp)

  def writeService(event: String, name: String) = {
    sql"insert into logging(event, name) values (${event}, ${name})".update.apply()
  }

  def writeHeaders(url: String, header: String) = {
    sql"insert into headers (header, url) values (${header}, ${url})".update.apply()
  }

}
