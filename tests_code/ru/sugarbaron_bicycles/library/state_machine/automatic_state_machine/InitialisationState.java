/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 03.09.2016 */
package ru.sugarbaron_bicycles.library.state_machine.automatic_state_machine;

import ru.sugarbaron_bicycles.library.state_machine.*;

import java.util.List;

final class InitialisationState
  extends StateMachineState{
  private List<StateMachineState> statesRecorder;
  private List<StatesHandlers> handlersRecorder;

  InitialisationState(StateMachine machine, List<StateMachineState> statesRecorder, List<StatesHandlers> handlersRecorder){
    super(machine);
    this.statesRecorder = statesRecorder;
    this.handlersRecorder = handlersRecorder;
    return;
  }

  @Override
  protected void enter()
  throws Exception{
    handlersRecorder.add(StatesHandlers.INITIALISATION_ENTER);
    return;
  }

  @Override
  protected void activity()
  throws Exception{
    handlersRecorder.add(StatesHandlers.INITIALISATION_ACTIVITY);
    StateMachineState currentState = stateMachine.getCurrentState();
    statesRecorder.add(currentState);

    boolean isInitialisationOk = initialisation();

    StateMachineSignal controlSignal = defineControlSignal(isInitialisationOk);
    stateMachine.setNextStepSignal(controlSignal);
    stateMachine.makeStep();
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
    handlersRecorder.add(StatesHandlers.INITIALISATION_LEAVE);
    return;
  }
}