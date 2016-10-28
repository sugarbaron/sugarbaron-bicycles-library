/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 24.10.2016 */
package ru.sugarbaron_bicycles.library.state_machine.unit_tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import ru.sugarbaron_bicycles.library.state_machine.StateMachine;
import ru.sugarbaron_bicycles.library.state_machine.StateMachineState;
import ru.sugarbaron_bicycles.library.state_machine.StateMachineSignal;
import ru.sugarbaron_bicycles.library.exceptions.ProvokedForTest;
import ru.sugarbaron_bicycles.library.exceptions.NeedFixCode;

public final class StateMachineUnitTest{
  @Test
  public void run()
  throws Exception{
    StateMachineStructure engine = new StateMachineStructure();

    catchExpectedExceptionAtMakeStep(engine);
    StateMachineState startState = engine.getCurrentState();
    engine.makeStep(SignalName.TO_STATE_2);
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
      engine.makeStep(SignalName.START);
    }
    catch(ProvokedForTest exception){
      wasExpectedException = true;
    }
    ensureExceptionCaught(wasExpectedException);
    return;
  }

  private void ensureExceptionCaught(boolean wasException){
    if(false == wasException){
      throw new NeedFixCode("expected exception was not generated");
    }
    return;
  }

  @Test
  public void createSignalForRandomOrderOfSignalsNames(){
    StateMachine stateMachine  = StateMachine.createNew();
    StateMachineSignal signal3 = stateMachine.createSignal(SignalName.SIGNAL_3);
    StateMachineSignal signal1 = stateMachine.createSignal(SignalName.SIGNAL_1);
    StateMachineSignal signal2 = stateMachine.createSignal(SignalName.SIGNAL_2);
    assertNotNull(signal1);
    assertNotNull(signal2);
    assertNotNull(signal3);
    return;
  }

  @Test
  public void getSignal(){
    StateMachine stateMachine = StateMachine.createNew();
    StateMachineSignal expectedSignal1 = stateMachine.createSignal(SignalName.SIGNAL_1);
    StateMachineSignal expectedSignal2 = stateMachine.createSignal(SignalName.SIGNAL_2);
    StateMachineSignal expectedSignal3 = stateMachine.createSignal(SignalName.SIGNAL_3);

    StateMachineSignal actualSignal3 = stateMachine.getSignal(SignalName.SIGNAL_3);
    StateMachineSignal actualSignal1 = stateMachine.getSignal(SignalName.SIGNAL_1);
    StateMachineSignal actualSignal2 = stateMachine.getSignal(SignalName.SIGNAL_2);

    assertTrue( actualSignal1 == expectedSignal1 );
    assertTrue( actualSignal2 == expectedSignal2 );
    assertTrue( actualSignal3 == expectedSignal3 );
    return;
  }

  @Test(expected = NeedFixCode.class)
  public void createSignalForMultipleSameNames(){
    StateMachine stateMachine = StateMachine.createNew();
    stateMachine.createSignal(SignalName.SIGNAL_1);
    stateMachine.createSignal(SignalName.SIGNAL_1);
    return;
  }

  @Test
  public void aLotOfGetSignalsForSameName(){
    final int ITERATIONS_QUANTITY = 100;
    StateMachine stateMachine = StateMachine.createNew();
    stateMachine.createSignal(SignalName.SIGNAL_1);

    StateMachineSignal currentSignal;
    StateMachineSignal previousSignal = stateMachine.getSignal(SignalName.SIGNAL_1);
    for(int i=0; i<ITERATIONS_QUANTITY; i++){
      currentSignal = stateMachine.getSignal(SignalName.SIGNAL_1);
      assertTrue( currentSignal == previousSignal );
      previousSignal = currentSignal;
    }
    return;
  }

  @Test(expected = NeedFixCode.class)
  public void createSignalForNull(){
    StateMachine stateMachine = StateMachine.createNew();
    stateMachine.createSignal(null);
    return;
  }

  @Test(expected = NeedFixCode.class)
  public void getSignalForNull(){
    StateMachine stateMachine = StateMachine.createNew();
    stateMachine.getSignal(null);
    return;
  }

  @Test
  public void getCurrentState(){
    StateMachine stateMachine = StateMachine.createNew();
    StateMachineState expectedState = new State1(stateMachine);
    stateMachine.setStart(expectedState);
    StateMachineState actualState = stateMachine.getCurrentState();
    assertTrue( actualState == expectedState );
    return;
  }

  @Test
  public void getPreviousState()
  throws Exception{
    StateMachine stateMachine = StateMachine.createNew();
    StateMachineState  state1 = new State1(stateMachine);
    StateMachineState  state2 = new State2(stateMachine);
    StateMachineSignal signal = stateMachine.createSignal(SignalName.SIGNAL_1);

    stateMachine.setJump(state1, state2, signal);
    stateMachine.setStart(state1);

    stateMachine.setNextStepSignal(signal);
    stateMachine.makeStep();
    StateMachineState actualState = stateMachine.getPreviousState();
    StateMachineState expectedState = state1;
    assertTrue( actualState == expectedState);
    return;
  }

  @Test
  public void setJump()
  throws Exception{
    StateMachine stateMachine = StateMachine.createNew();
    StateMachineState  state1 = new State1(stateMachine);
    StateMachineState  state2 = new State2(stateMachine);
    StateMachineSignal signal = stateMachine.createSignal(SignalName.SIGNAL_1);

    stateMachine.setJump(state1, state2, signal);
    stateMachine.setStart(state1);

    StateMachineState from = stateMachine.getCurrentState();
    stateMachine.setNextStepSignal(signal);
    stateMachine.makeStep();
    StateMachineState to = stateMachine.getCurrentState();
    assertTrue( from == state1 );
    assertTrue( to == state2 );
    return;
  }

  @Test(expected = NeedFixCode.class)
  public void setJumpForNullFrom(){
    StateMachine stateMachine = StateMachine.createNew();
    StateMachineState  state2 = new State2(stateMachine);
    StateMachineSignal signal = stateMachine.createSignal(SignalName.SIGNAL_1);

    stateMachine.setJump(null, state2, signal);
    return;
  }

  @Test(expected = NeedFixCode.class)
  public void setJumpForNullTo(){
    StateMachine stateMachine = StateMachine.createNew();
    StateMachineState  state1 = new State1(stateMachine);
    StateMachineSignal signal = stateMachine.createSignal(SignalName.SIGNAL_1);

    stateMachine.setJump(state1, null, signal);
    return;
  }

  @Test(expected = NeedFixCode.class)
  public void setJumpForNullSignal(){
    StateMachine stateMachine = StateMachine.createNew();
    StateMachineState  state1 = new State1(stateMachine);
    StateMachineState  state2 = new State2(stateMachine);

    stateMachine.setJump(state1, state2, null);
    return;
  }

  @Test
  public void setStart(){
    StateMachine stateMachine = StateMachine.createNew();
    StateMachineState expectedState = new State1(stateMachine);
    stateMachine.setStart(expectedState);
    StateMachineState actualState = stateMachine.getCurrentState();
    assertTrue( actualState == expectedState );
    return;
  }

  @Test(expected = NeedFixCode.class)
  public void setStartForNull(){
    StateMachine stateMachine = StateMachine.createNew();
    stateMachine.setStart(null);
    return;
  }

  @Test(expected = NeedFixCode.class)
  public void setStartForAlreadyStartedStateMachine(){
    StateMachine stateMachine = StateMachine.createNew();
    StateMachineState state1 = new State1(stateMachine);
    stateMachine.setStart(state1);
    stateMachine.setStart(state1);
    return;
  }

  @Test(expected = NeedFixCode.class)
  public void setNextStepSignalForNull(){
    StateMachine stateMachine = StateMachine.createNew();
    stateMachine.setNextStepSignal(null);
    return;
  }
}