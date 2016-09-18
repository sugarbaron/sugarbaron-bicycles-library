//[author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)]
//[date: 30.08.2016]
package ru.sugarbaron_bicycles.library.log;

import static org.junit.Assert.*;
//[my bicycles]
import ru.sugarbaron_bicycles.library.exceptions.CriticalOperationFailed;
import ru.sugarbaron_bicycles.library.exceptions.NeedFixCode;
import ru.sugarbaron_bicycles.library.time.*;

public class LogToolkitTest{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  private final int MAX_LOGS_ALLOWED = 10;

  private Clock clock;
  private int currentLogIndex;

  //methods_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  public void setUp(){
    clock = new ClockUnit();
    return;
  }

  public void testCreateLog()
  throws Exception{
    boolean isOverflowCorrect = false;
    currentLogIndex = 0;
    try{
      createALotOfLogs();
    }
    catch(NeedFixCode unused){
      if(currentLogIndex == MAX_LOGS_ALLOWED){
        isOverflowCorrect = true;
      }
    }
    assertTrue("test failed: wrong control of maximum logs quantity", isOverflowCorrect);
    return;
  }

  private void createALotOfLogs()
  throws NeedFixCode, CriticalOperationFailed{
    final int TOO_MUCH_LOGS = MAX_LOGS_ALLOWED + 1;
    String logName;
    for(currentLogIndex = 0; currentLogIndex < TOO_MUCH_LOGS; currentLogIndex++){
      logName = String.valueOf(currentLogIndex);
      TestingLogsToolkit.deleteOldLog(logName);
      LogToolkit.createLog(logName, clock);
    }
    return;
  }

  public void testGetLog()
    throws Exception{
    Log log;
    boolean isOk = false;
    String logName;
    for(currentLogIndex = 0; currentLogIndex < MAX_LOGS_ALLOWED; currentLogIndex++){
      logName = String.valueOf(currentLogIndex);
      log = LogToolkit.getLog(logName);
      isOk = (log != null);
      if(isOk == false){
        break;
      }
    }
    assertTrue("test failed: createLog() or getLog() error", isOk);
    return;
  }
}