/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 25.10.2016 */
package ru.sugarbaron_bicycles.library.exceptions;

import org.junit.Test;

public final class WhatAFuckTest{
  @Test(expected = WhatAFuck.class)
  public void throwing(){
    throw new WhatAFuck("testing exception throwing");
  }
}