package ru.sugarbaron_bicycles.library.state_machine;

import ru.sugarbaron_bicycles.library.exceptions.*;



/**
 * this class describes a state machine jump (step).
 * jump is defined by:
 *   initial state for jumping,
 *   destination state for jumping,
 *   signal, which must be received by state machine to make jump possible.
 * 
 * result of jump (step) is changing state machine current state
 * 
 * @author sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
 */
final class Jump{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /** start state */
  GraphState from;
  /** destination state */
  GraphState to;
  /** signal for jumping from start to destination */
  GraphSignal signal;
  
  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /**
   * create jump
   * 
   * @param from    start state for jump
   * @param to      destination state for jump
   * @param signal  signal, which must be received by state machine for jump
   * 
   * @throws NeedFixCode  in case, when was detected wrong work of a programm
   *                      because of errors in code
   */
  Jump(GraphState from, GraphState to, GraphSignal signal)
  throws NeedFixCode{
    //[01.checking arguments validation]
    if(null == from){
      throw new NeedFixCode("[x][Jump]#from is null");
    }
    if(null == to){
      throw new NeedFixCode("[x][Jump]#to is null");
    }
    if(null == signal){
      throw new NeedFixCode("[x][Jump]#signal is null");
    }
    //[02.initing fields]
    this.from   = from;
    this.to     = to;
    this.signal = signal;
  }
}
