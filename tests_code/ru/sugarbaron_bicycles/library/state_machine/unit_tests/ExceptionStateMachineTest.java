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

public final class ExceptionStateMachineTest{
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
}