/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 31.08.2016 */
package ru.sugarbaron_bicycles.library.state_machine;

import java.util.List;
import java.util.ArrayList;
//[my bicycles]
import ru.sugarbaron_bicycles.library.exceptions.*;

/** provides a state of state machine */
public abstract class StateMachineState{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  protected StateMachine stateMachine;
  private List<Jump> jumps;

  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /**
   * constructor is protected for denying access from outside of this package
   * and granting ability of constructing subclasses instances
   * @param stateMachine  - state machine, which this state must belong to */
  protected StateMachineState(StateMachine stateMachine){
    synchronized(this){
      this.stateMachine = stateMachine;
      jumps = new ArrayList<>();
      return;
    }
  }

  //methods_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /**
   * method for overriding. in overrided method user can define
   * actions, which will execute, when state machine makes step from
   * another state to this state
   * 
   * @throws Exception  in case, when code of overrided version throws any exceptions */
  protected synchronized void enter()
  throws Exception{}
  
  /**
   * method for overriding. in overrided method user can define
   * actions, which will execute, when state machine makes step from
   * this state to itself (itself means this state).
   * 
   * @throws Exception  in case, when code of overrided version throws any exceptions */
  protected synchronized void activity()
  throws Exception{}
  
  /**
   * method for overriding. in overrided method user can define
   * actions, which will execute, when state machine makes step from
   * this state to another
   *
   * @throws Exception  in case, when code of overrided version throws any exceptions */
  protected synchronized void leave()
  throws Exception{}
  
  
  /**
   * set jump for this state
   * 
   * @param to  - destination state for jump
   * @param signal  - signal, which must be received by state machine for jump
   * 
   * @throws NeedFixCode  in case of invalid arguments */
  void addJump(StateMachineState to, StateMachineSignal signal){
    //[checking arguments validation]
    if( (null == to)||(null == signal) ){
      throw new NeedFixCode("invalid arguments");
    }

    checkSignalForUniqueness(signal);

    Jump newJump = new Jump(to, signal);
    jumps.add(newJump);
    return;
  }

  private void checkSignalForUniqueness(StateMachineSignal signalToCheck){
    Jump jumpForSpecifiedSignal = getJumpForSignal(signalToCheck);
    if(jumpForSpecifiedSignal != null){
      throw new NeedFixCode("jump with such signal is already registered for this state");
    }
    return;
  }

  Jump getJumpForSignal(StateMachineSignal signal){
    Jump requiredJump = null;
    StateMachineSignal currentSignal;
    for(Jump currentJump: jumps){
      currentSignal = currentJump.signal;
      if(currentSignal == signal){
        requiredJump = currentJump;
        break;
      }
    }
    return requiredJump;
  }
}
