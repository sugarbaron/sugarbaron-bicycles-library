/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 25.08.2016 */
package ru.sugarbaron_bicycles.library.time;

/**
 * this class provides a millisecond clock.
 * based on mechanism of #System.nanotime() */
public final class ClockUnit
implements Clock{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /** system time (in nanoseconds) when #this object was created */
  private long start_point = 0;
  
  //constructors_and_factories_section_________________________________________
  /////////////////////////////////////////////////////////////////////////////
  public ClockUnit(){
    this.start_point = System.nanoTime();
  }
  
  //methods_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /**
   * get current time value for this clock.
   * 
   * @return time value (in milliseconds). */
  public long getTime(){
    long ms;
    ms = (System.nanoTime() - this.start_point)/1000000L;
    return ms;
  }
}