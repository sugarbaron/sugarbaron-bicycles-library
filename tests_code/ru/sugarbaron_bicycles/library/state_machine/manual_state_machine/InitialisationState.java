/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 31.08.2016 */
package ru.sugarbaron_bicycles.library.state_machine.manual_state_machine;

import ru.sugarbaron_bicycles.library.state_machine.*;

import java.util.List;

final class InitialisationState
extends StateMachineState{
  private List<StateHandlers> recorder;

  InitialisationState(StateMachine machine, List<StateHandlers> recorder){
    super(machine);
    this.recorder = recorder;
    return;
  }

  @Override
  protected void enter()
  throws Exception{
    recorder.add(StateHandlers.INITIALISATION_ENTER);
    return;
  }

  @Override
  protected void activity()
  throws Exception{
    recorder.add(StateHandlers.INITIALISATION_ACTIVITY);
    return;
  }

  @Override
  protected void leave()
  throws Exception{
    recorder.add(StateHandlers.INITIALISATION_LEAVE);
    return;
  }
}
