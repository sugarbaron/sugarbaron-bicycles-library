/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 03.09.2016 */
package ru.sugarbaron_bicycles.library.state_machine.manual_state_machine;

import org.junit.Test;
import java.util.List;
import java.util.LinkedList;
//[my bicycles]
import ru.sugarbaron_bicycles.library.state_machine.StateMachineSignal;

public final class ManualStateMachineTest{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  private StateMachineStructure machine;
  private List<StateMachineSignal> controlSignalsOrder;

  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  public ManualStateMachineTest(){
    machine = new StateMachineStructure();

    controlSignalsOrder = new LinkedList<>();
    fillControlSignalsOrder();
    return;
  }

  private void fillControlSignalsOrder(){
    controlSignalsOrder.add(Signals.startWork);
    controlSignalsOrder.add(Signals.initialisationComplete);
    controlSignalsOrder.add(Signals.repeat);
    controlSignalsOrder.add(Signals.startAction2);
    controlSignalsOrder.add(Signals.endWork);
    return;
  }

  //methods_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  @Test
  public void run()
  throws Exception{
    executeStateMachine();
    checkCorrectnessOfStateMachineWork();
    return;
  }

  private void executeStateMachine()
  throws Exception{
    for(StateMachineSignal controlSignal: controlSignalsOrder){
      machine.makeStep(controlSignal);
    }
    return;
  }

  private void checkCorrectnessOfStateMachineWork(){
    machine.checkSelfCorrectness();
    return;
  }
}
