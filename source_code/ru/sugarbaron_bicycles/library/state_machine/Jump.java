/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 31.08.2016 */
package ru.sugarbaron_bicycles.library.state_machine;

//[my bicycles]
import ru.sugarbaron_bicycles.library.exceptions.*;

/**
 * each step of state machine is defined by jump.
 * jump unites data about destination state and signal for making a step */
final class Jump{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /** destination state */
  StateMachineState to;
  /** signal for jumping from start to destination */
  StateMachineSignal signal;
  
  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /**
   * create a jump
   *
   * @param to  - destination state for jump
   * @param signal  - signal, which must be received by state machine for jump
   *
   * @throws NeedFixCode  - in case of invalid arguments */
  Jump(StateMachineState to, StateMachineSignal signal){
    //[01.checking arguments validation]
    if( (null == to)||(null == signal) ){
      throw new NeedFixCode("invalid arguments");
    }

    this.to = to;
    this.signal = signal;
    return;
  }
}
