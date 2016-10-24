/* authors: sugarbaron([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 24.10.2014 */
package ru.sugarbaron_bicycles.library.exceptions;

import org.junit.Test;

public final class ProvokedForTestTest{
  @Test(expected = ProvokedForTest.class)
  public void throwing(){
    throw new ProvokedForTest("testing exception throwing");
  }
}