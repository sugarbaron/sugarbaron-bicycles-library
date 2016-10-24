/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 18.09.2016 */
package ru.sugarbaron_bicycles.library.synchronization;

import java.util.List;
import java.util.ArrayList;

final class ConsumersFactory{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  private List<Consumer> consumers;
  private final int CONSUMERS_QUANTITY = 4;

  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  ConsumersFactory(Data data, int productsQuantity){
    consumers = new ArrayList<>(CONSUMERS_QUANTITY);
    createAllConsumers(data, productsQuantity);
    return;
  }

  private void createAllConsumers(Data data, int productsQuantity){
    int productsToConsumeQuantity = productsQuantity/CONSUMERS_QUANTITY;
    Consumer newConsumer;
    for(int i=0; i<CONSUMERS_QUANTITY; i++){
      newConsumer = new Consumer(data, productsToConsumeQuantity);
      consumers.add(newConsumer);
    }
    return;
  }

  //methods_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  List<Consumer> getConsumers(){
    return consumers;
  }
}