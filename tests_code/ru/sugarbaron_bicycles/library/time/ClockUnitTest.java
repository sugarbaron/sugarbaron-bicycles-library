package ru.sugarbaron_bicycles.library.time;

import static org.junit.Assert.*;

public class ClockUnitTest {
  private Clock clock;

  /* @Before */
  public void setUp() {
    clock = new ClockUnit();
    return;
  }

  /* @Test */
  public void testGetTime() {
    long timeValue = clock.getTime();
    //weak test
    assertFalse(timeValue < 0);
    return;
  }
}