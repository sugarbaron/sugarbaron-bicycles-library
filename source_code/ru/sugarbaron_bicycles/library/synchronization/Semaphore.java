package ru.sugarbaron_bicycles.library.synchronization;



/**
 * wrap for #java.util.concurrent.Semaphore with handling of interruption
 * while waiting for semaphore releasing
 * 
 * @author sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
 */
public final class Semaphore{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /** semaphore */
  private java.util.concurrent.Semaphore semaphore;
  
  //consrtucrors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /** 
   * create semaphore
   * 
   * @param semaphoreCounter  initial value of semaphore counter
   */
  public Semaphore(int semaphoreCounter){
    semaphore = new java.util.concurrent.Semaphore(semaphoreCounter);
  }
  
  //primary_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /**
   * wait for semaphore signal
   */
  public void waitSignal(){
    for( ; true ; ){
      try{
        //[waiting for semaphore signal]
        semaphore.acquire();
        //[semaphore signal received. profit!]
        break;
      }
      catch(InterruptedException x){
        //[wtf?!? we need to wait for semaphore signal]
        continue;
      }
    }
    return;
  }
   
  /**
   * give semaphore signal
   */
  public void giveSignal(){
    semaphore.release();
  }
} 