/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)]
   date: 11.09.2016 */
package z_external_code_emulator.state_machine;

//[my bicycles]
import ru.sugarbaron_bicycles.library.state_machine.*;

public final class Machine{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  private StateMachine stateMachine;
  private StateMachineState initialState;
  private StateMachineState workState;
  private StateMachineState exitState;

  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  public Machine(){
    buildMachine();
  }

  private void buildMachine(){
    stateMachine = StateMachine.createNew();
    createStates(stateMachine);
    createSignals(stateMachine);
    constructMachineStructure(stateMachine);
    return;
  }

  private void createStates(StateMachine stateMachine){
    initialState = new InitialState(stateMachine);
    workState = new WorkState(stateMachine);
    exitState = new ExitState(stateMachine);
    return;
  }

  private void createSignals(StateMachine stateMachine){
    for(SignalsNames signalName: SignalsNames.values()){
      stateMachine.createSignal(signalName);
    }
    return;
  }

  private void constructMachineStructure(StateMachine stateMachine){
    StateMachineSignal startSignal = stateMachine.getSignalByName(SignalsNames.START);
    StateMachineSignal workSignal  = stateMachine.getSignalByName(SignalsNames.WORK);
    StateMachineSignal endSignal = stateMachine.getSignalByName(SignalsNames.END);

    stateMachine.setJump(initialState, initialState, startSignal);
    stateMachine.setJump(initialState, workState, workSignal);
    stateMachine.setJump(workState, exitState, endSignal);

    stateMachine.setStart(initialState);
    stateMachine.setNextStepSignal(startSignal);
    return;
  }

  //methods_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  public void makeStep()
  throws Exception{
    stateMachine.makeStep();
    return;
  }

  public boolean isStillWorking(){
    boolean isStillWorking = true;
    StateMachineState currentState = stateMachine.getCurrentState();
    if(currentState == exitState){
      isStillWorking = false;
    }
    return isStillWorking;
  }

  public StateMachineState getPreviousState(){
    StateMachineState previousState = stateMachine.getPreviousState();
    return previousState;
  }
}