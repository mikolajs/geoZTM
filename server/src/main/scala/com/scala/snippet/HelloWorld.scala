package com.scala.snippet

import scala.xml.{NodeSeq, Text}
import net.liftweb.util._
import net.liftweb.common._
import java.util.Date
import com.scala.lib._
import Helpers._

class HelloWorld {

  
  def showData() = {
      val dataList = (new LoadTramData).getFileContentList("2013-03-08.csv")
      "li" #> dataList.map(line => <li>{line}</li>)
  } 
 
}

