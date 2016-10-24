/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 24.10.2016 */
package ru.sugarbaron_bicycles.library.exceptions;

/**
 * this exception is for cases, when the final goal of method was
 * not reached because of different external conditions */
public final class ProvokedForTest
  extends RuntimeException{

  public ProvokedForTest(String message){
    super(message);
    return;
  }
}