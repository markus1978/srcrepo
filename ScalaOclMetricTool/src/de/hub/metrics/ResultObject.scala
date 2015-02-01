package de.hub.metrics

import scala.collection.mutable.ListBuffer

class ResultObject {
  private var values:ListBuffer[Double] = new ListBuffer[Double];
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
}