/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 14.09.2016 */
package ru.sugarbaron_bicycles.library.exceptions;

import org.junit.Test;

public final class ExecutionAbortedTest{
  @Test(expected = ExecutionAborted.class)
  public void throwing() throws ExecutionAborted{
    throw new ExecutionAborted("testing exception throwing");
  }
}