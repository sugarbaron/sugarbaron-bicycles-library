//[author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)]
//[date: 25.08.2016]
package z_external_code_emulator;

//[my bicycles]
import ru.sugarbaron_bicycles.library.time.*;
import ru.sugarbaron_bicycles.library.log.*;
import ru.sugarbaron_bicycles.library.exceptions.*;
import ru.sugarbaron_bicycles.library.random.*;
import ru.sugarbaron_bicycles.library.synchronization.*;
import ru.sugarbaron_bicycles.library.useful_tools.*;

class z_ExternalCodeEmulator{
  public static void main(String[] unusableShit)
  throws Exception{
    useTimeLibraryUnit();
    useLogLibraryUnit();
    useStateMachineLibraryUnit();
    useRandom();
    useExceptions();
    useSemaphore();
    useUsefulTools();
    return;
  }

  private static void useTimeLibraryUnit(){
    Clock clock;
    clock = new ClockUnit();
    clock.getTime();
    return;
  }

  private static void useLogLibraryUnit()
  throws Exception{
    Dbg.out("sugarbaron_bicycles");
    Dbg.out("library units are running");

    final String LOG_NAME = "emulator_log_example.txt";
    Clock clock = new ClockUnit();
    LogToolkit.createLog(LOG_NAME, clock);
    Log log = LogToolkit.getLog(LOG_NAME);
    log.debug("debug() method is running");
    log.error("error() method is running");
    log.warning("warning() method is running");
    log.close();
    return;
  }

  private static void useStateMachineLibraryUnit()
  throws Exception{
    Machine machine = new Machine();

    while( machine.isStillWorking() ){
      machine.makeStep();
    }

    machine.getPreviousState();
    return;
  }

  private static void useRandom(){
    Random.roll(5);
    return;
  }

  private static void useExceptions() throws ExecutionAborted{
    int rollResult = Random.roll(5);
    if(rollResult > 5){
      throw new NeedFixCode("example of exception using");
    }
    if(rollResult > 6){
      throw new CriticalOperationFailed("example of exception using");
    }
    if(rollResult > 7){
      throw new ExecutionAborted("example of exception using");
    }
    if(rollResult > 8){
      throw new WhatAFuck("example of exception using");
    }
    return;
  }

  private static void useSemaphore(){
    Semaphore semaphore = Semaphore.createNew(0);
    semaphore.receiveSignal();
    semaphore.waitSignal();
    return;
  }

  private static void useUsefulTools(){
    String example = "example";
    UsefulTools.requireNotNull(example);
    UsefulTools.requireNotEmpty(example);
    return;
  }
}