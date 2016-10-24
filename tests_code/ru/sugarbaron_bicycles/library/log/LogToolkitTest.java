//[author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)]
//[date: 30.08.2016]
package ru.sugarbaron_bicycles.library.log;

import static org.junit.Assert.*;

import org.junit.Test;
//[my bicycles]
import ru.sugarbaron_bicycles.library.time.Clock;
import ru.sugarbaron_bicycles.library.time.ClockUnit;
import ru.sugarbaron_bicycles.library.exceptions.NeedFixCode;

public class LogToolkitTest{
  @Test
  public void createAndGetLog(){
    final String LOG_FILE_NAME = "log_toolkit_test.log";
    Clock clock = new ClockUnit();
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    LogToolkit.createLog(LOG_FILE_NAME, clock);
    Log log = LogToolkit.getLog(LOG_FILE_NAME);
    assertNotNull(log);
    return;
  }

  @Test(expected = NeedFixCode.class)
  public void createLogForNullFileName(){
    Clock clock = new ClockUnit();
    LogToolkit.createLog(null, clock);
    return;
  }

  @Test(expected = NeedFixCode.class)
  public void createLogForEmptyFileName(){
    Clock clock = new ClockUnit();
    LogToolkit.createLog("", clock);
    return;
  }

  @Test(expected = NeedFixCode.class)
  public void createLogForNullClock(){
    LogToolkit.createLog("never_log", null);
    return;
  }

  @Test(expected = NeedFixCode.class)
  public void getLogForNullFileName(){
    LogToolkit.getLog(null);
    return;
  }

  @Test(expected = NeedFixCode.class)
  public void getLogForWrongFileName(){
    LogToolkit.getLog("wrong_name");
    return;
  }
}