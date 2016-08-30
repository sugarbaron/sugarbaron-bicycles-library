//[author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)]
//[date: 25.08.2016]
package ru.sugarbaron_bicycles.library;

import org.junit.Test;
import ru.sugarbaron_bicycles.library.log.LogToolkitTest;
import ru.sugarbaron_bicycles.library.log.LogUnitTest;
import ru.sugarbaron_bicycles.library.time.ClockUnitTest;

public final class TestsStarter{
  @Test
  public void runTests()
  throws Exception{
    ClockUnitTest clockUnitTest = new ClockUnitTest();
    clockUnitTest.setUp();
    clockUnitTest.testGetTime();

    LogUnitTest logUnitTest = new LogUnitTest();
    logUnitTest.setUp();
    logUnitTest.testLogUnit();

    LogToolkitTest logToolkitTest = new LogToolkitTest();
    logToolkitTest.setUp();
    logToolkitTest.testCreateLog();
    logToolkitTest.testGetLog();
    return;
  }
}
