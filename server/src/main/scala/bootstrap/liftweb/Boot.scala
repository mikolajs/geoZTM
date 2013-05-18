package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._
import mapper.{ DB, By, ConnectionManager, ConnectionIdentifier, 
    Schemifier, DefaultConnectionIdentifier }
import java.sql.{ Connection, DriverManager}

import com.scala.model._

object DBVendor extends ConnectionManager {
  def newConnection(name: ConnectionIdentifier): Box[Connection] = {
    try {
       Class.forName("org.h2.Driver")
      val dm = DriverManager.getConnection("jdbc:h2:hackaton")
      Full(dm)
    } catch {
      case e: Exception => e.printStackTrace; Empty
    }
  }
  def releaseConnection(conn: Connection) { conn.close }
}


class Boot {
  def boot {
      
      DB.defineConnectionManager(DefaultConnectionIdentifier, DBVendor)
      
     Schemifier.schemify(true, Schemifier.infoF _, VehiclePosition)
    // where to search snippet
    LiftRules.addToPackages("com.scala")

    // Build SiteMap
    val entries = List(
      Menu.i("Home") / "index", // the simple way to declare a menu

      // more complex because this menu allows anything in the
      // /static path to be visible
      Menu(Loc("Static", Link(List("static"), true, "/static/index"), 
	       "Static Content")))

    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    LiftRules.setSiteMap(SiteMap(entries:_*))

     LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    
    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
    

  }
}
