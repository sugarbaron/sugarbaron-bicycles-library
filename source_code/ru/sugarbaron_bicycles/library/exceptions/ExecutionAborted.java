package ru.sugarbaron_bicycles.library.exceptions;



/**
 * this exception is for cases, when the final goal of method was
 * not reached because of different external conditions
 * 
 * @author sugarbaron ([sugarbaron_bicycles] sugarbaron1@mail.ru)
 */
public final class ExecutionAborted
extends Exception{
  //serial version uid
  private static final long serialVersionUID = 1L;
  
  public ExecutionAborted(String message){
    super(message);
  }
}