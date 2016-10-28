/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 24.10.2016 */
package ru.sugarbaron_bicycles.library.state_machine.unit_tests;

import ru.sugarbaron_bicycles.library.state_machine.StateMachine;
import ru.sugarbaron_bicycles.library.state_machine.StateMachineState;
import ru.sugarbaron_bicycles.library.state_machine.StateMachineSignal;

final class StateMachineStructure{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  private StateMachine stateMachine;
  private State1 state1;
  private State2 state2;
  private StateMachineSignal startSignal;
  private StateMachineSignal toState2Signal;

  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  StateMachineStructure(){
    stateMachine = StateMachine.createNew();
    createSignals();
    createStates();
    constructStructure();
    return;
  }

  private void createSignals(){
    startSignal = stateMachine.createSignal(SignalName.START);
    toState2Signal = stateMachine.createSignal(SignalName.TO_STATE_2);
    return;
  }

  private void createStates(){
    state1 = new State1(stateMachine);
    state2 = new State2(stateMachine);
    return;
  }

  private void constructStructure(){
    stateMachine.setJump(state1, state1, startSignal);
    stateMachine.setJump(state1, state2, toState2Signal);

    stateMachine.setStart(state1);
    return;
  }

  //methods_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  void makeStep(SignalName signalName)
  throws Exception{
    StateMachineSignal signal = stateMachine.getSignal(signalName);
    stateMachine.setNextStepSignal(signal);
    stateMachine.makeStep();
    return;
  }

  StateMachineState getCurrentState(){
    StateMachineState currentState = stateMachine.getCurrentState();
    return currentState;
  }
}