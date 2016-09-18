/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 18.09.2016 */
package ru.sugarbaron_bicycles.library.synchronization;

import static org.junit.Assert.*;

class Data{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  private int quantityOfProduced;
  private Semaphore producingSignaler;

  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  Data(){
    quantityOfProduced  = 0;
    producingSignaler = Semaphore.createNew(0);
    return;
  }

  //methods_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  void produce(int quantity){
    changeQuantity(quantity);
    producingSignaler.receiveSignal();
    return;
  }

  void consume(){
    producingSignaler.waitSignal();
    changeQuantity(-1);
    return;
  }

  private synchronized void changeQuantity(int value){
    quantityOfProduced += value;
    checkCorrectness();
    return;
  }

  private void checkCorrectness(){
    boolean isCorrect = (quantityOfProduced >= 0);
    assertTrue("test failed: #quantityOfProduced became negative", isCorrect);
    return;
  }
}