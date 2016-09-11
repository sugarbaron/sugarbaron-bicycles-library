//[author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)]
//[date: 25.08.2016]
package z_external_code_emulator;

//[my bicycles]
import ru.sugarbaron_bicycles.library.time.*;
import ru.sugarbaron_bicycles.library.log.*;
import ru.sugarbaron_bicycles.library.state_machine.*;

import z_external_code_emulator.state_machine.*;

class z_ExternalCodeEmulator{
  public static void main(String[] unusableShit)
  throws Exception{
    useTimeLibraryUnit();
    useLogLibraryUnit();
    useStateMachineLibraryUnit();
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
}
