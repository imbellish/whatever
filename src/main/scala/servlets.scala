package servlets

import scala.xml.NodeSeq
import scala.xml.PrettyPrinter
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class MyServlet extends HttpServlet {

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
            <li>Figure out routing. This is currently just a catch all.</li>
            <li>create a branch for simple JSON webservices boilerplate</li>
          </ul>
        </body>
      </html>

    val printer = new PrettyPrinter(80, 2)

    response.getWriter.write(printer.formatNodes(responseBody))

  }

  override def doPost(request: HttpServletRequest, response: HttpServletResponse) {

    
    response.setContentType("application/json")
    response.setCharacterEncoding("UTF-8")


    val headers = request.getAuthType()
    val query = request.getQueryString()
    val url = request.getRequestURL()
    val authorization = request.getHeader("Authorization")

    val printer = new PrettyPrinter(80, 2)

    response.setStatus(201)
    response.getWriter.write(
      "Hello, your auth header is " +
        headers +
        " and your query string is " +
        query +
        " and you are on " +
        url  +
        ". The authorization code you have is " +
        authorization)


  }

}