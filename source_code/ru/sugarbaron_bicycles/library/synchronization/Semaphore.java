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
  
  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /** 
   * create new semaphore
   * 
   * @param semaphoreCounter  initial value of semaphore counter */
  public static synchronized Semaphore createNew(int semaphoreCounter){
    Semaphore newSemaphore = new Semaphore(semaphoreCounter);
    return newSemaphore;
  }

  private Semaphore(int semaphoreCounter){
    semaphore = new java.util.concurrent.Semaphore(semaphoreCounter);
    return;
  }
  
  //methods_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /** wait for semaphore signal */
  public synchronized void waitSignal(){
    boolean signalIsNotReceived = true;
    while(signalIsNotReceived){
      try{
        semaphore.acquire();
        signalIsNotReceived = false;
      }
      catch(InterruptedException exception){
        //[wtf?!? we need to wait for semaphore signal]
      }
    }
    return;
  }
   
  /** receive waited semaphore signal. */
  public synchronized void receiveSignal(){
    semaphore.release();
    return;
  }
} 