package com.scala.lib

import scala.io.Source

case class DataVehicle(nrLine:String, direction:String, position:List[(String,String,String)])

class LoadTramData {
    
   private val allLines = getFileContentList("2013-03-08.csv")
   
   var dataConverted = Map[Int, DataVehicle]()
    
   private def getFileContentList(fileName:String) = {
       val path =  "/home/ms/Programy/Hackaton/"
       val file = Source.fromFile(path + fileName)
       file.getLines.toList
   }
   
   def getAllLines = allLines
   
   
   
   def uniqueLineNumbers = allLines.map(line =>{
       val dataInLine = line.split(";")
       val timeString = dataInLine(0).split(" ")(1)
       val idVehicle = dataInLine(1)
       val nrLine = dataInLine(2)
       val latitude = dataInLine(4)
       val longitude = dataInLine(5)
       val direction = dataInLine(6)
       
   })

}