/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 12.09.2016 */
package ru.sugarbaron_bicycles.library.exceptions;

/**
 * this exception is using in cases, when
 * some system operation is failed and this fact
 * does not allow continue execution of a program. */
 public class CriticalOperationFailed
 extends RuntimeException{
   private static final long serialVersionUID = 1L;
   
   public CriticalOperationFailed(String message){
     super(message);
     return;
   }
   
   public CriticalOperationFailed(Exception exception){
     super(exception);
     return;
   }
 }
 