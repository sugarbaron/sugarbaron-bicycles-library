/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 14.09.2016 */
package ru.sugarbaron_bicycles.library.exceptions;

import org.junit.Test;

public final class NeedFixCodeTest{
  @Test(expected = NeedFixCode.class)
  public void throwing(){
    throw new NeedFixCode("testing exception throwing");
  }
}