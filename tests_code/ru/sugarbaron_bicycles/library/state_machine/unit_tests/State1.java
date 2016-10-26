/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 24.10.2016 */
package ru.sugarbaron_bicycles.library.state_machine.unit_tests;

import ru.sugarbaron_bicycles.library.state_machine.StateMachineState;
import ru.sugarbaron_bicycles.library.state_machine.StateMachine;
import ru.sugarbaron_bicycles.library.exceptions.ProvokedForTest;

final class State1 extends StateMachineState{
  State1(StateMachine stateHolder){
    super(stateHolder);
    return;
  }

  public void activity()
  throws Exception{
    throw new ProvokedForTest("expected exception inside handler");
  }
}
