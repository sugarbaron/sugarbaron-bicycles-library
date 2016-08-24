package ru.sugarbaron_bicycles.library.exceptions;

/**
 * this exception is using in cases, when
 * some operation is failed and this fact 
 * does not allow continue execution of a programm. 
 * 
 * @author sugabaron (sugarbaron1@mail.ru)
 */
 public class CriticalOperationFailed
 extends Exception{
   //serial version uid
   private static final long serialVersionUID = 1L;
   
   public CriticalOperationFailed(String message){
     super(message);
   }
   
   public CriticalOperationFailed(Exception x){
     super(x);
   }
 }
 