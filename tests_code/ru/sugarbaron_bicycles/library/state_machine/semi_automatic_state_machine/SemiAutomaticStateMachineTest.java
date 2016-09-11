/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 10.09.2016 */
package ru.sugarbaron_bicycles.library.state_machine.semi_automatic_state_machine;

public final class SemiAutomaticStateMachineTest{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  private StateMachineStructure machine;

  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  public SemiAutomaticStateMachineTest(){
    machine = new StateMachineStructure();
    return;
  }

  //methods_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  public void run()
  throws Exception{
    executeStateMachine();
    checkCorrectnessOfStateMachineWork();
    return;
  }

  private void executeStateMachine()
  throws Exception{
    while( machine.isExecutionComplete() == false ){
      machine.makeStep();
    }
    return;
  }

  private void checkCorrectnessOfStateMachineWork(){
    machine.checkSelfCorrectness();
    machine.testGetPreviousState();
    return;
  }
}