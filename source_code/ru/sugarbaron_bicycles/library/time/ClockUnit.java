package ru.sugarbaron_bicycles.library.time;

/**
 * this class describes a millisecond clock.
 * based on mechanism of #System.nanotime() 
 * 
 * @author sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
 */
final public class ClockUnit{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /** system time (in nanoseconds) when #this object was created */
  private long start_point = 0;
  
  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /**
   * create clock unit
   */
  public ClockUnit(){
    this.start_point = System.nanoTime();
  }
  
  //primary_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /**
   * get current time value. actually, this method returns a value of time
   * interval between creation of this #ClockUnit object and invocation of
   * this method.
   * 
   * @return time value (in milliseconds).
   */
  public long time(){
    long ms;
    ms = (System.nanoTime() - this.start_point)/1000000L;
    return ms;
  }
}