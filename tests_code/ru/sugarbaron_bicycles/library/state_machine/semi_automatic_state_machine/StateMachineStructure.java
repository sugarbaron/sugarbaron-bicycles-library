/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 10.09.2016 */
package ru.sugarbaron_bicycles.library.state_machine.semi_automatic_state_machine;

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
  private List<StatesHandlers> expectedInvocationsOrder;
  private List<StatesHandlers> realInvocationsOrder;

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
    for(SignalsNames signalName: SignalsNames.values()){
      stateMachine.createSignal(signalName);
    }
    return;
  }

  private void constructStateMachineStructure(){
    StateMachineSignal startWorkSignal = stateMachine.getSignalByName(SignalsNames.START_WORK);
    StateMachineSignal initialisationFailSignal = stateMachine.getSignalByName(SignalsNames.INITIALISATION_FAIL);
    StateMachineSignal initialisationCompleteSignal = stateMachine.getSignalByName(SignalsNames.INITIALISATION_COMPLETE);
    StateMachineSignal repeatSignal = stateMachine.getSignalByName(SignalsNames.REPEAT);
    StateMachineSignal startAction2Signal = stateMachine.getSignalByName(SignalsNames.START_ACTION_2);
    StateMachineSignal endWorkSignal = stateMachine.getSignalByName(SignalsNames.END_WORK);

    stateMachine.setJump(initialisationState, initialisationState, startWorkSignal);
    stateMachine.setJump(initialisationState, exitState, initialisationFailSignal);
    stateMachine.setJump(initialisationState, action1State, initialisationCompleteSignal);
    stateMachine.setJump(action1State, action1State, repeatSignal);
    stateMachine.setJump(action1State, action2State, startAction2Signal);
    stateMachine.setJump(action2State, exitState, endWorkSignal);

    stateMachine.setStart(initialisationState);
    stateMachine.setNextStepSignal(startWorkSignal);
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
    expectedInvocationsOrder.add(StatesHandlers.INITIALISATION_ACTIVITY);
    expectedInvocationsOrder.add(StatesHandlers.INITIALISATION_LEAVE);
    expectedInvocationsOrder.add(StatesHandlers.ACTION_1_ENTER);
    expectedInvocationsOrder.add(StatesHandlers.ACTION_1_ACTIVITY);
    expectedInvocationsOrder.add(StatesHandlers.ACTION_1_ACTIVITY);
    expectedInvocationsOrder.add(StatesHandlers.ACTION_1_LEAVE);
    expectedInvocationsOrder.add(StatesHandlers.ACTION_2_ENTER);
    expectedInvocationsOrder.add(StatesHandlers.ACTION_2_ACTIVITY);
    expectedInvocationsOrder.add(StatesHandlers.ACTION_2_LEAVE);
    expectedInvocationsOrder.add(StatesHandlers.EXIT_ENTER);
    expectedInvocationsOrder.add(StatesHandlers.EXIT_ACTIVITY);
    return;
  }

  //methods_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  void makeStep()
  throws Exception{
    stateMachine.makeStep();
    registerCurrentState();
    return;
  }

  private void registerCurrentState(){
    StateMachineState currentState = stateMachine.getCurrentState();
    realStatesOrder.add(currentState);
    return;
  }

  boolean isExecutionComplete(){
    StateMachineState currentState = stateMachine.getCurrentState();
    boolean isExecutionComplete = (currentState == exitState);
    return isExecutionComplete;
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

  void testGetPreviousState(){
    StateMachineState previousState = stateMachine.getPreviousState();
    boolean isCorrect = (previousState == action2State);
    assertTrue("test fail: getPreviousState()", isCorrect);
    return;
  }
}