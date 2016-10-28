package ru.sugarbaron_bicycles.library.time;

import static org.junit.Assert.*;

import org.junit.Test;

public final class ClockUnitTest {
  @Test
  public void testGetTime(){
    Clock clock = new ClockUnit();
    long timeValue = clock.getTime();
    //weak test
    assertFalse(timeValue < 0);
    return;
  }
}