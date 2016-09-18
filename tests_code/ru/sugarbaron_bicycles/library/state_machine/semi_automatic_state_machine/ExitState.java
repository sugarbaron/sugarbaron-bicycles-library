/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 03.09.2016 */
package ru.sugarbaron_bicycles.library.state_machine.semi_automatic_state_machine;

import ru.sugarbaron_bicycles.library.state_machine.*;

import java.util.List;

final class ExitState
  extends StateMachineState{
  private List<StateHandlers> handlersRecorder;

  ExitState(StateMachine machine, List<StateHandlers> handlersRecorder){
    super(machine);
    this.handlersRecorder = handlersRecorder;
    return;
  }

  @Override
  protected void enter()
  throws Exception{
    handlersRecorder.add(StateHandlers.EXIT_ENTER);
    return;
  }

  @Override
  protected void activity()
  throws Exception{
    handlersRecorder.add(StateHandlers.EXIT_ACTIVITY);
    return;
  }

  @Override
  protected void leave()
  throws Exception{
    handlersRecorder.add(StateHandlers.EXIT_LEAVE);
    return;
  }
}