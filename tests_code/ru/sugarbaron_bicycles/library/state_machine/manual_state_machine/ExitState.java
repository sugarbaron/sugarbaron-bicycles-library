/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 10.09.2016 */
package ru.sugarbaron_bicycles.library.state_machine.manual_state_machine;

import java.util.List;
//[my bicycles]
import ru.sugarbaron_bicycles.library.state_machine.*;

final class ExitState
  extends StateMachineState{
  private List<StateHandlers> recorder;

  ExitState(StateMachine machine, List<StateHandlers> recorder){
    super(machine);
    this.recorder = recorder;
    return;
  }

  @Override
  protected void enter()
  throws Exception{
    recorder.add(StateHandlers.EXIT_ENTER);
    return;
  }

  @Override
  protected void activity()
  throws Exception{
    recorder.add(StateHandlers.EXIT_ACTIVITY);
    return;
  }

  @Override
  protected void leave()
  throws Exception{
    recorder.add(StateHandlers.EXIT_LEAVE);
    return;
  }
}