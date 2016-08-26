package z_external_code_emulator;

//[standard libraries]
//[my bicycles]
import ru.sugarbaron_bicycles.library.time.*;

class z_ExternalCodeEmulator{
  public static void main(String[] unusableShit) throws Exception{
    useTime();
    return;
  }

  private static void useTime(){
    Clock clock;
    clock = new ClockUnit();
    clock.getTime();
    return;
  }
}
