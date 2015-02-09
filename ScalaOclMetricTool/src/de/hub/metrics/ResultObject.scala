package de.hub.metrics

import scala.collection.mutable.ListBuffer
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.util.BasicEList

class ResultObject {
  private var values:ListBuffer[Double] = new ListBuffer[Double];
  private var coupledUnits:EList[String] = new BasicEList[String];
  private var fileName:String = "";
  	  
  override def toString():String = {	    
    var string = "";	    
    for (iter <- values.toList.iterator) {	    
		string = string.concat(iter.toString);			
	}
    return string;
  }
  
  def toStringOnlyGreaterZero():String = {	    
    var string = "";	    
    for (iter <- values.toList.iterator) {	
      if(iter > 0.0)
		string = string.concat(iter.toString);			
	}
    return string;
  }
	  
  
  def listContainsItem(list:EList[String], name:String):Int={
    var index : Int = -1;
    var iter = list.iterator;
    while(iter.hasNext){
      var item = iter.next();
      if(item.equalsIgnoreCase(name)){
        return index;
      } else {
        index = index+1;
      }
    }
	index;      
  }
  
  def getFileName():String = {
    return this.fileName ;
  }
  
  def setFileName(name:String) = {
    this.fileName = name;
  }
  
  def getValues() : ListBuffer[Double] = {
    return this.values;
  }
   
  def setValues(list : ListBuffer[Double]) = {
     this.values = list;
  }
  
  def getCoupledUnits : EList[String] = {
    return this.coupledUnits;
  }
   
  def setCoupledUnits(list : EList[String]) = {
     this.coupledUnits  = list;
  }
}