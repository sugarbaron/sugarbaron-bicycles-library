/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 04.09.2016 */
package ru.sugarbaron_bicycles.library.state_machine.automatic_state_machine;

public final class AutomaticStateMachineTest{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  private StateMachineStructure machine;

  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  public AutomaticStateMachineTest(){
    machine = new StateMachineStructure();
    return;
  }

  //methods_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  public void run()
  throws Exception{
    machine.execute();
    machine.checkSelfCorrectness();
    return;
  }
}