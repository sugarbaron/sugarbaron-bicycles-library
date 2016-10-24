/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 24.10.2016 */
package ru.sugarbaron_bicycles.library.state_machine.exception_state_machine;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import ru.sugarbaron_bicycles.library.state_machine.StateMachineState;
import ru.sugarbaron_bicycles.library.exceptions.ProvokedForTest;
import ru.sugarbaron_bicycles.library.exceptions.NeedFixCode;

final class ExceptionStateMachineTest{
  @Test
  public void run()
  throws Exception{
    StateMachineStructure engine = new StateMachineStructure();

    catchExpectedExceptionAtMakeStep(engine);
    StateMachineState startState = engine.getCurrentState();
    engine.makeStep(SignalsNames.TO_STATE_2);
    StateMachineState finalState = engine.getCurrentState();

    boolean isStartStateCorrect  = startState instanceof State1;
    boolean isFinalStateCorrect  = finalState instanceof State2;
    assertTrue(isStartStateCorrect &&  isFinalStateCorrect);
    return;
  }

  private void catchExpectedExceptionAtMakeStep(StateMachineStructure engine)
  throws Exception{
    boolean wasExpectedException = false;
    try{
      engine.makeStep(SignalsNames.START);
    }
    catch(ProvokedForTest exception){
      wasExpectedException = true;
    }
    ensureExpectedException(wasExpectedException);
    return;
  }

  private void ensureExpectedException(boolean wasException){
    if(false == wasException){
      throw new NeedFixCode("expected exception was not generated");
    }
    return;
  }
}