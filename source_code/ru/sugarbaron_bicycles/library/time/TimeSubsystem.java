package ru.sugarbaron_bicycles.library.time;

import ru.sugarbaron_bicycles.library.exceptions.*;
//import ru.sugarbaron_bicycles.library.log.*;



/**
 * time subsystem
 */
public final class TimeSubsystem{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /** system clock */
  static private final ClockUnit systemClock = new ClockUnit();
  
  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /**
   * default constructor
   */
  public TimeSubsystem(){}
  
  //primary_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /**
   * get reference to system clock
   * 
   * @return  reference to system clock
   */
  static public ClockUnit getClock(){
    return systemClock;
  }
  
  /**
   * FIXME:this method works wrong. need debug.
   * pause execution for specified time interval (in milliseconds).
   * this method processes situation of interrupting while sleeping.
   * ensures sleeping for whole specified time interval.
   * base tool for pause is #Thread.sleep().
   * 
   * @param ms  pause value (in milliseconds)
   * 
   * @throws NeedFixCode  if was detected wrong work of a
   *                      programm, because of errors in code.
   */
  static public void sleep(long ms)
  throws NeedFixCode{
    //[creating locals]
    long    endTime = systemClock.time() + ms;
    boolean endWork = false;
    long    unsleeped = ms;
    /* uncomment for dbg
    LogUnit log;
    try{
      log = LogSubsystem.createLog("time_subsystem_log.txt", systemClock);
    }catch(CriticalOperationFailed x){
     Dbg.out("[x][TimeSubsystem]#CriticalOperationFailed while ceating log instance");
     Dbg.out(x.getMessage());
     return;
    }
    */
    //[loop will ensure sleeping for whole specified time interval]
    for( ; !endWork ; ){
      try{
        //dbg log.write("[v][TimeSubsystem]#sleep():begin sleep [%d]", unsleeped);
        //dbg log.write("[v][TimeSubsystem]#sleep():endTime     #%d", endTime);
        //[trying to sleep]
        Thread.sleep(unsleeped);
        //dbg log.write("[v][TimeSubsystem]#sleep():after sleep");
      }
      catch(InterruptedException x){
        //[if there was interruption - calculating, how long we need to sleep
        // for ensuring specified time interval 
        // (calculating after #catch block)]
      }
      unsleeped = endTime - systemClock.time();
      //dbg log.write("[v][TimeSubsystem]#sleep():new unsleeped #%d", unsleeped);
      if(unsleeped > 0){
        //dbg log.write("[v][TimeSubsystem]#sleep():sleep further");
        //[sleep further]
        continue;
      }else{
        //dbg log.write("[v][TimeSubsystem]#sleep():end sleep");
        //[profit!]
        endWork = true;
      }
      //dbg log.write("[v][TimeSubsystem]#sleep():last operation in #for");
    }
    //dbg log.write("[v][TimeSubsystem]#sleep():after #for");
  }
}
