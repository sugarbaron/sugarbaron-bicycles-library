/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 18.09.2016 */
package ru.sugarbaron_bicycles.library.synchronization;

import java.lang.*;

class Consumer
implements Runnable{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  private Data interthreadData;
  private int requiredIterationsQuantity;

  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  Consumer(Data dataExample, int quantity){
    interthreadData = dataExample;
    requiredIterationsQuantity = quantity;
    return;
  }

  //methods_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  public void run(){
    for(int i=0; i<requiredIterationsQuantity; i++){
      synchronized(this){
        interthreadData.consume();
      }
    }
    return;
  }
}