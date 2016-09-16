/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 14.09.2016 */
package ru.sugarbaron_bicycles.library.exceptions;

public class ExceptionsTest{
  public ExceptionsTest(){
    return;
  }

  public void testNeedFixCode(){
    try{
      throw new NeedFixCode("test");
    }
    catch(NeedFixCode exception){
      /* here we catch expected NeedFixCode */
    }
    return;
  }

  public void testCriticalOperationFailed(){
    try{
      throw new CriticalOperationFailed("test");
    }
    catch(CriticalOperationFailed exception){
      /* here we catch expected CriticalOperation */
    }
  }

  public void testExecutionAborted(){
    try{
      throw new ExecutionAborted("test");
    }
    catch(ExecutionAborted exception){
      /* here we catch expected ExecutionAborted */
    }
  }

  public void testWhatAFuck(){
    try{
      throw new WhatAFuck("test");
    }
    catch(WhatAFuck exception){
      /* here we catch expected WhatAFuck */
    }
    return;
  }
}