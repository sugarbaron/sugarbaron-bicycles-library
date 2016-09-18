/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 31.08.2016 */
package ru.sugarbaron_bicycles.library.state_machine;

import ru.sugarbaron_bicycles.library.state_machine.automatic_state_machine.*;
import ru.sugarbaron_bicycles.library.state_machine.manual_state_machine.*;
import ru.sugarbaron_bicycles.library.state_machine.semi_automatic_state_machine.*;

public class StateMachineTest{
  public void run()
  throws Exception{
    SemiAutomaticStateMachineTest semiAutomaticStateMachineTest = new SemiAutomaticStateMachineTest();
    semiAutomaticStateMachineTest.run();

    ManualStateMachineTest manualStateMachineTest = new ManualStateMachineTest();
    manualStateMachineTest.run();

    AutomaticStateMachineTest automaticStateMachineTest = new AutomaticStateMachineTest();
    automaticStateMachineTest.run();
    return;
  }
}