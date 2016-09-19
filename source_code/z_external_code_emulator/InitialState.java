/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 01.09.2016 */
package z_external_code_emulator;

//[my bicycles]
import ru.sugarbaron_bicycles.library.state_machine.*;

final class InitialState
extends StateMachineState{
  InitialState(StateMachine stateMachine){
    super(stateMachine);
    return;
  }

  @Override
  protected void activity()
    throws Exception{

    StateMachineSignal workSignal = stateMachine.getSignalByName(SignalsNames.WORK);
    stateMachine.setNextStepSignal(workSignal);
    return;
  }
}