/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 03.09.2016 */
package ru.sugarbaron_bicycles.library.state_machine.automatic_state_machine;

import ru.sugarbaron_bicycles.library.state_machine.*;

import java.util.List;

final class Action2State
  extends StateMachineState{
  private List<StateMachineState> statesRecorder;
  private List<StatesHandlers> handlersRecorder;

  Action2State(StateMachine machine, List<StateMachineState> statesRecorder, List<StatesHandlers> handlersRecorder){
    super(machine);
    this.statesRecorder = statesRecorder;
    this.handlersRecorder = handlersRecorder;
    return;
  }

  @Override
  protected void enter()
  throws Exception{
    handlersRecorder.add(StatesHandlers.ACTION_2_ENTER);
    return;
  }

  @Override
  protected void activity()
  throws Exception{
    handlersRecorder.add(StatesHandlers.ACTION_2_ACTIVITY);
    StateMachineState currentState = stateMachine.getCurrentState();
    statesRecorder.add(currentState);

    StateMachineSignal controlSignal = stateMachine.getSignalByName(SignalsNames.END_WORK);
    stateMachine.setNextStepSignal(controlSignal);
    stateMachine.makeStep();
    return;
  }

  @Override
  protected void leave()
  throws Exception{
    handlersRecorder.add(StatesHandlers.ACTION_2_LEAVE);
    return;
  }
}