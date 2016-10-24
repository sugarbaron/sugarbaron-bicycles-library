/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 18.09.2016 */
package ru.sugarbaron_bicycles.library.synchronization;

final class Producer
implements Runnable{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  private Data interthreadData;
  private int  requiredQuantity;

  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  Producer(Data data, int quantity){
    interthreadData  = data;
    requiredQuantity = quantity;
    return;
  }

  //methods_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  public void run(){
    for(int i=0; i<requiredQuantity; i++){
      synchronized(this){
        interthreadData.produce(1);
      }
    }
    return;
  }
}