/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 14.09.2016 */
package ru.sugarbaron_bicycles.library.exceptions;

import org.junit.Test;

public class ExceptionsTest{
  public ExceptionsTest(){
    return;
  }

  public void testNeedFixCode(){
    try{
      throwNeedFixCode();
    }
    catch(NeedFixCode exception){
      /* here must be catch expected NeedFixCode */
    }
    return;
  }

  @Test(expected = NeedFixCode.class)
  private void throwNeedFixCode(){
    throw new NeedFixCode("test");
  }

  public void testCriticalOperationFailed(){
    try{
      throwCriticalOperationFailed();
    }
    catch(CriticalOperationFailed exception){
      /* here must be catch expected CriticalOperation */
    }
  }

  @Test(expected = CriticalOperationFailed.class)
  private void throwCriticalOperationFailed(){
    throw new CriticalOperationFailed("test");
  }

  public void testExecutionAborted(){
    try{
      throwExecutionAborted();
    }
    catch(ExecutionAborted exception){
      /* here must be catch expected ExecutionAborted */
    }
  }

  @Test(expected = ExecutionAborted.class)
  private void throwExecutionAborted(){
    throw new ExecutionAborted("test");
  }

  public void testWhatAFuck(){
    try{
      throwWhatAFuck();
    }
    catch(WhatAFuck exception){

    }
    return;
  }
}