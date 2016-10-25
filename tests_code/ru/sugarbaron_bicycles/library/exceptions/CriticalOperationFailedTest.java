/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 14.09.2016 */
package ru.sugarbaron_bicycles.library.exceptions;

import org.junit.Test;

public final class CriticalOperationFailedTest{
  @Test(expected = CriticalOperationFailed.class)
  public void throwing(){
    throw new CriticalOperationFailed("testing exception throwing");
  }

  @Test(expected = CriticalOperationFailed.class)
  public void throwingBasedOnException(){
    Exception baseException = new Exception();
    throw new CriticalOperationFailed(baseException);
  }
}