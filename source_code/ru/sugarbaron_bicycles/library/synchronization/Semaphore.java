/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 18.09.2016 */
package ru.sugarbaron_bicycles.library.synchronization;



/**
 * wrap for #java.util.concurrent.Semaphore with handling of interruption
 * while waiting for semaphore releasing */
public final class Semaphore{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  private java.util.concurrent.Semaphore semaphore;
  
  //consrtucrors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /** 
   * create semaphore
   * 
   * @param semaphoreCounter  initial value of semaphore counter */
  public Semaphore(int semaphoreCounter){
    semaphore = new java.util.concurrent.Semaphore(semaphoreCounter);
  }
  
  //primary_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /** wait for semaphore signal */
  public void waitSignal(){
    for( ; true ; ){
      try{
        semaphore.acquire();
        break;
      }
      catch(InterruptedException exception){
        //[wtf?!? we need to wait for semaphore signal]
      }
    }
    return;
  }
   
  /** receive waited semaphore signal. */
  public void receiveSignal(){
    semaphore.release();
  }
} 