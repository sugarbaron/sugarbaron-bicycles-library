/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 18.09.2016 */
package ru.sugarbaron_bicycles.library.synchronization;

//[my bicycles]
import ru.sugarbaron_bicycles.library.log.*;

class Producer
implements Runnable{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  private Data interthreadData;
  private int  requiredQuantity;
  private Log log;

  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  Producer(Data data, int quantity){
    interthreadData  = data;
    requiredQuantity = quantity;
    log = LogToolkit.getLog("0");
    return;
  }

  //methods_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  public void run(){
    for(int i=0; i<requiredQuantity; i++){
      synchronized(this){
        log.debug("producing");
        interthreadData.produce(1);
      }
    }
    return;
  }
}