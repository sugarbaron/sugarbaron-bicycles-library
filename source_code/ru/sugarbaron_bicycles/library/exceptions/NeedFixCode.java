package ru.sugarbaron_bicycles.library.exceptions;



/**
 * appearing of this exception must interrupt execution of a programm
 * for analysis and fixing (for debug).
 * 
 * this exception must not appear in debugged programm.
 * 
 * @author sugarbaron ([sugarbaron_bicycles] sugarbaron1@mail.ru)
 */
public final class NeedFixCode
extends Exception{
  //serial version uid
  private static final long serialVersionUID = 1L;
  
  public NeedFixCode(String message){
    super(message);
  }
}