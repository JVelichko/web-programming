package com.homework.servlets

import java.io.{IOException, Writer}
import javax.servlet.ServletException
import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import com.google.gson.Gson
import com.homework.models.{JsonPackage, RepresentMaker}
import java.util.regex.Pattern
import scala.collection.Set

class BaseServlet extends HttpServlet {
  private var representer: RepresentMaker = _

  override def init() {
    representer = new RepresentMaker
    //TODO
    //Logger log = Logger.getLogger(BaseServlet.class.getName());
  }

  @throws(classOf[ServletException])
  @throws(classOf[IOException])
  protected override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
    doPost(request, response)
  }

  @throws(classOf[ServletException])
  @throws(classOf[IOException])
  protected override def doPost(request: HttpServletRequest, response: HttpServletResponse) {
    val ajax: Boolean = "XMLHttpRequest" == request.getHeader("X-Requested-With")
    if (ajax) {
      val searchText: String = request.getParameter("text")
      val writer: Writer = response.getWriter
      response.setContentType("text/plain")
      response.setCharacterEncoding("UTF-8")
      try {
        //val docsNames: Array[String] = representer.getDocNames(searchText)
        val docNamesSet: Set[String] = representer.getDocNames(searchText)
        val docsNames: Array[String] = docNamesSet.toList.toArray
         //

        val textInDocs: Array[String] = representer.getTextInDocs(docNamesSet, searchText)
        if (docsNames.length != 0) {
          writer.write(new Gson().toJson(new JsonPackage("found", docsNames, textInDocs)))
        }
        else {
          writer.write(new Gson().toJson(new JsonPackage("Not found")))
        }
      }
      catch {
        case e: Exception =>
          e.printStackTrace()
          //TODO: Логи
      }
    }
  }
}
