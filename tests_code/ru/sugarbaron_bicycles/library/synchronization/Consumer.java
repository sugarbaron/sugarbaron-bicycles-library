/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 18.09.2016 */
package ru.sugarbaron_bicycles.library.synchronization;

import java.lang.*;
//[my bicycles]
import ru.sugarbaron_bicycles.library.log.*;

class Consumer
implements Runnable{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  private Data interthreadData;
  private int requiredIterationsQuantity;
  private Log log;

  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  Consumer(Data dataExample, int quantity){
    interthreadData = dataExample;
    requiredIterationsQuantity = quantity;
    log = LogToolkit.getLog("0");
    return;
  }

  //methods_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  public void run(){
    for(int i=0; i<requiredIterationsQuantity; i++){
      synchronized(this){
        log.debug("consuming");
        interthreadData.consume();
      }
    }
    return;
  }
}