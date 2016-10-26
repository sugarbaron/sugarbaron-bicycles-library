/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 03.09.2016 */
package ru.sugarbaron_bicycles.library.state_machine.semi_automatic_state_machine;

import ru.sugarbaron_bicycles.library.state_machine.*;

import java.util.List;

final class Action2State
  extends StateMachineState{
  private List<StatesHandlers> handlersRecorder;

  Action2State(StateMachine machine, List<StatesHandlers> handlersRecorder){
    super(machine);
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

    StateMachineSignal controlSignal = stateMachine.getSignal(SignalName.END_WORK);
    stateMachine.setNextStepSignal(controlSignal);
    return;
  }

  @Override
  protected void leave()
  throws Exception{
    handlersRecorder.add(StatesHandlers.ACTION_2_LEAVE);
    return;
  }
}