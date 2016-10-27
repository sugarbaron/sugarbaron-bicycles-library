/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 12.09.2016 */
package ru.sugarbaron_bicycles.library.exceptions;

/**
 * appearing of this exception must interrupt execution of a program
 * for analysis and code fixing. */
public final class NeedFixCode
extends RuntimeException{
  private static final long serialVersionUID = 1L;
  
  public NeedFixCode(String message){
    super("{NeedFixCode}" + message);
    return;
  }
}