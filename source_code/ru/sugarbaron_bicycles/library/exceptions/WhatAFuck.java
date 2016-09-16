/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 12.09.2016 */
package ru.sugarbaron_bicycles.library.exceptions;

public class WhatAFuck
extends Exception{
  private static final long serialVersionUID = 1L;

  public WhatAFuck(String message){
    super(message);
    return;
  }
}