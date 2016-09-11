/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 04.09.2016 */
package ru.sugarbaron_bicycles.library.state_machine.manual_state_machine;

import static org.junit.Assert.*;

import java.util.List;
import java.util.LinkedList;
//[my bicycles]
import ru.sugarbaron_bicycles.library.state_machine.*;

final class StateMachineStructure{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  private StateMachine stateMachine;
  private StateMachineState initialisationState;
  private StateMachineState action1State;
  private StateMachineState action2State;
  private StateMachineState exitState;
  private List<StateMachineState> expectedStatesOrder;
  private List<StateMachineState> realStatesOrder;
  private List<StateHandlers> expectedInvocationsOrder;
  private List<StateHandlers> realInvocationsOrder;

  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  StateMachineStructure(){
    realStatesOrder = new LinkedList<>();
    realInvocationsOrder = new LinkedList<>();

    buildStateMachine();

    expectedStatesOrder = new LinkedList<>();
    fillExpectedStatesOrder();
    expectedInvocationsOrder = new LinkedList<>();
    fillExpectedInvocationsOrder();
    return;
  }

  private void buildStateMachine(){
    stateMachine = StateMachine.createNew();
    createStates();
    createSignals();
    constructStateMachineStructure();
    return;
  }

  private void createStates(){
    initialisationState = new InitialisationState(stateMachine, realInvocationsOrder);
    action1State = new Action1State(stateMachine, realInvocationsOrder);
    action2State = new Action2State(stateMachine, realInvocationsOrder);
    exitState = new ExitState(stateMachine, realInvocationsOrder);
    return;
  }

  private void createSignals(){
    Signals.startWork = stateMachine.createSignal(SignalsNames.START_WORK);
    Signals.initialisationFail = stateMachine.createSignal(SignalsNames.INITIALISATION_FAIL);
    Signals.initialisationComplete = stateMachine.createSignal(SignalsNames.INITIALISATION_COMPLETE);
    Signals.repeat = stateMachine.createSignal(SignalsNames.REPEAT);
    Signals.startAction2 = stateMachine.createSignal(SignalsNames.START_ACTION_2);
    Signals.endWork = stateMachine.createSignal(SignalsNames.END_WORK);
    return;
  }

  private void constructStateMachineStructure(){
    stateMachine.setJump(initialisationState, initialisationState, Signals.startWork);
    stateMachine.setJump(initialisationState, exitState, Signals.initialisationFail);
    stateMachine.setJump(initialisationState, action1State, Signals.initialisationComplete);
    stateMachine.setJump(action1State, action1State, Signals.repeat);
    stateMachine.setJump(action1State, action2State, Signals.startAction2);
    stateMachine.setJump(action2State, exitState, Signals.endWork);

    stateMachine.setStart(initialisationState);
    registerCurrentState();
    return;
  }

  private void fillExpectedStatesOrder(){
    expectedStatesOrder.add(initialisationState);
    expectedStatesOrder.add(initialisationState);
    expectedStatesOrder.add(action1State);
    expectedStatesOrder.add(action1State);
    expectedStatesOrder.add(action2State);
    expectedStatesOrder.add(exitState);
    return;
  }

  private void fillExpectedInvocationsOrder(){
    expectedInvocationsOrder.add(StateHandlers.INITIALISATION_ACTIVITY);
    expectedInvocationsOrder.add(StateHandlers.INITIALISATION_LEAVE);
    expectedInvocationsOrder.add(StateHandlers.ACTION_1_ENTER);
    expectedInvocationsOrder.add(StateHandlers.ACTION_1_ACTIVITY);
    expectedInvocationsOrder.add(StateHandlers.ACTION_1_ACTIVITY);
    expectedInvocationsOrder.add(StateHandlers.ACTION_1_LEAVE);
    expectedInvocationsOrder.add(StateHandlers.ACTION_2_ENTER);
    expectedInvocationsOrder.add(StateHandlers.ACTION_2_ACTIVITY);
    expectedInvocationsOrder.add(StateHandlers.ACTION_2_LEAVE);
    expectedInvocationsOrder.add(StateHandlers.EXIT_ENTER);
    expectedInvocationsOrder.add(StateHandlers.EXIT_ACTIVITY);
    return;
  }

  //methods_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  void makeStep(StateMachineSignal signal)
  throws Exception{
    stateMachine.setNextStepSignal(signal);
    stateMachine.makeStep();
    registerCurrentState();
    return;
  }

  private void registerCurrentState(){
    StateMachineState currentState = stateMachine.getCurrentState();
    realStatesOrder.add(currentState);
    return;
  }

  void checkSelfCorrectness(){
    checkStatesOrderCorrectness();
    checkStatesHandlersOrderCorrectness();
    return;
  }

  private void checkStatesOrderCorrectness(){
    boolean areEqual = realStatesOrder.equals(expectedStatesOrder);
    assertTrue("test fail: wrong order of states", areEqual);
    return;
  }

  private void checkStatesHandlersOrderCorrectness(){
    boolean areEqual = realInvocationsOrder.equals(expectedInvocationsOrder);
    assertTrue("test fail: wrong order of states handlers invocation", areEqual);
    return;
  }
}