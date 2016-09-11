/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 30.08.2016 */
package ru.sugarbaron_bicycles.library.state_machine;

/** provides state machine signal */
public final class StateMachineSignal{
  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /** create signal (thread-safe) */
  static synchronized StateMachineSignal createNew(){
    StateMachineSignal newSignal = new StateMachineSignal();
    return newSignal;
  }

  /**
   * constructor is private for prohibition of non-synchronized invocation
   * from different threads. */
  private StateMachineSignal(){
    return;
  }
}