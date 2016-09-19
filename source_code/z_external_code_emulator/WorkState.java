/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 01.09.2016 */
package z_external_code_emulator;

//[my bicycles]
import ru.sugarbaron_bicycles.library.state_machine.*;

final class WorkState
extends StateMachineState{
  WorkState(StateMachine machine){
    super(machine);
    return;
  }

  @Override
  protected void enter()
    throws Exception{
    return;
  }

  @Override
  protected void activity()
    throws Exception{
    StateMachineSignal finishSignal = stateMachine.getSignalByName(SignalsNames.END);
    stateMachine.setNextStepSignal(finishSignal);
    return;
  }

  @Override
  protected void leave()
    throws Exception{
    return;
  }
}
