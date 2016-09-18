/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 18.09.2016 */
package ru.sugarbaron_bicycles.library.synchronization;

import java.util.List;
import java.util.ArrayList;

public class SemaphoreTest{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  private Producer producer;
  private List<Consumer> consumers;
  private List<Thread> consumersThreads;

  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  public SemaphoreTest(){
    final int PRODUCTS_QUANTITY = 100;

    Data interthreadData = new Data();
    producer  = new Producer(interthreadData, PRODUCTS_QUANTITY);
    consumers = createConsumers(interthreadData, PRODUCTS_QUANTITY);

    consumersThreads = new ArrayList<>();
    return;
  }

  private List<Consumer> createConsumers(Data data, int quantity){
    ConsumersFactory consumersFactory = new ConsumersFactory(data, quantity);
    List<Consumer> consumersList = consumersFactory.getConsumers();
    return consumersList;
  }

  //methods_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  public void run(){
    startAllConsumers();
    startProducer();
    waitForAllConsumersWillFinish();
    return;
  }

  private void startAllConsumers(){
    Thread newThread;
    for(Consumer each: consumers){
      newThread = new Thread(each);
      consumersThreads.add(newThread);
      newThread.start();
    }
    return;
  }

  private void startProducer(){
    Thread newThread = new Thread(producer);
    newThread.start();
    return;
  }

  private void waitForAllConsumersWillFinish(){
    for(Thread each: consumersThreads){
      waitForConsumerWillFinish(each);
    }
    return;
  }

  private void waitForConsumerWillFinish(Thread consumerThread){
    boolean waitingIsNotComplete = true;
    while(waitingIsNotComplete){
      try{
        consumerThread.join();
        waitingIsNotComplete = false;
      }
      catch(InterruptedException exception){
        //[if appears <code>InterruptedException</code> - continue waiting]
      }
    }
    return;
  }
}