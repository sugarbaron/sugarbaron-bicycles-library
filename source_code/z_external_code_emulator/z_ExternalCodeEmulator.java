//[author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)]
//[date: 25.08.2016]
package z_external_code_emulator;

//[my bicycles]
import ru.sugarbaron_bicycles.library.time.*;
import ru.sugarbaron_bicycles.library.log.*;

class z_ExternalCodeEmulator{
  public static void main(String[] unusableShit) throws Exception{
    useTimeLibraryUnit();
    useLogLibraryUnit();
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
    return;
  }
}
