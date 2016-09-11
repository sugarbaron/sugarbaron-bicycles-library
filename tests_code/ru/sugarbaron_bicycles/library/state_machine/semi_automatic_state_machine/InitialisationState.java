/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 10.09.2016 */
package ru.sugarbaron_bicycles.library.state_machine.semi_automatic_state_machine;

import ru.sugarbaron_bicycles.library.state_machine.*;

import java.util.List;

final class InitialisationState
  extends StateMachineState{
  private List<StateHandlers> handlersRecorder;

  InitialisationState(StateMachine machine, List<StateHandlers> handlersRecorder){
    super(machine);
    this.handlersRecorder = handlersRecorder;
    return;
  }

  @Override
  protected void enter()
  throws Exception{
    handlersRecorder.add(StateHandlers.INITIALISATION_ENTER);
    return;
  }

  @Override
  protected void activity()
  throws Exception{
    handlersRecorder.add(StateHandlers.INITIALISATION_ACTIVITY);

    boolean isInitialisationOk = initialisation();

    StateMachineSignal controlSignal = defineControlSignal(isInitialisationOk);
    stateMachine.setNextStepSignal(controlSignal);
    return;
  }

  private StateMachineSignal defineControlSignal(boolean isInitialisationOk){
    StateMachineSignal controlSignal;
    if(isInitialisationOk){
      controlSignal = stateMachine.getSignalByName(SignalsNames.INITIALISATION_COMPLETE);
    }
    else{
      controlSignal = stateMachine.getSignalByName(SignalsNames.INITIALISATION_FAIL);
    }
    return controlSignal;
  }

  private boolean initialisation(){
    final boolean IS_INITIALISATION_OK = true;
    return IS_INITIALISATION_OK;
  }

  @Override
  protected void leave()
  throws Exception{
    handlersRecorder.add(StateHandlers.INITIALISATION_LEAVE);
    return;
  }
}