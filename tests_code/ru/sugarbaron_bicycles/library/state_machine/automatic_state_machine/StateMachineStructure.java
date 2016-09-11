/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 10.09.2016 */
package ru.sugarbaron_bicycles.library.state_machine.automatic_state_machine;

import java.util.Map;
import java.util.List;
import java.util.LinkedList;
//[my bicycles]
import ru.sugarbaron_bicycles.library.state_machine.*;

import static org.junit.Assert.assertTrue;

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
    initialisationState = new InitialisationState(stateMachine, realStatesOrder, realInvocationsOrder);
    action1State = new Action1State(stateMachine, realStatesOrder, realInvocationsOrder);
    action2State = new Action2State(stateMachine, realStatesOrder, realInvocationsOrder);
    exitState = new ExitState(stateMachine, realStatesOrder, realInvocationsOrder);
    return;
  }

  private void createSignals(){
    for(SignalsNames signalName: SignalsNames.values()){
      stateMachine.createSignal(signalName);
    }
    return;
  }

  private void constructStateMachineStructure(){
    Map<Enum, StateMachineSignal> signals = stateMachine.getSignals();
    StateMachineSignal startWorkSignal = signals.get(SignalsNames.START_WORK);
    StateMachineSignal initialisationFailSignal = signals.get(SignalsNames.INITIALISATION_FAIL);
    StateMachineSignal initialisationCompleteSignal = signals.get(SignalsNames.INITIALISATION_COMPLETE);
    StateMachineSignal startAction2Signal = signals.get(SignalsNames.START_ACTION_2);
    StateMachineSignal repeatSignal  = signals.get(SignalsNames.REPEAT);
    StateMachineSignal endWorkSignal = signals.get(SignalsNames.END_WORK);

    stateMachine.setJump(initialisationState, initialisationState, startWorkSignal);
    stateMachine.setJump(initialisationState, exitState, initialisationFailSignal);
    stateMachine.setJump(initialisationState, action1State, initialisationCompleteSignal);
    stateMachine.setJump(action1State, action1State, repeatSignal);
    stateMachine.setJump(action1State, action2State, startAction2Signal);
    stateMachine.setJump(action2State, exitState, endWorkSignal);

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
  void execute()
  throws Exception{
    StateMachineSignal startSignal = stateMachine.getSignalByName(SignalsNames.START_WORK);
    stateMachine.setNextStepSignal(startSignal);
    stateMachine.makeStep();

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