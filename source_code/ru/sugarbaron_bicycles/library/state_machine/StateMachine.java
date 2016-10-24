/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 31.08.2016 */
package ru.sugarbaron_bicycles.library.state_machine;

//[standard libraries]
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
//[my bicycles]
import ru.sugarbaron_bicycles.library.exceptions.*;

/**
 * provides graph (actually, state machine)
 * provides tools for building structure of this graph,
 * creating states, creating signals, creating jumps and
 * switching states */
public final class StateMachine{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  private Map<Enum, StateMachineSignal> allSignals;
  private StateMachineSignal nextStepSignal;
  private StateMachineState  currentState;
  private StateMachineState  previousState;
  private LinkedList<StateMachineSignal> signalsQueue;
  private boolean queueIsUnderProcessing;

  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /**
   * create state machine (thread-safe)
   *
   * @return state machine instance */
  public static synchronized StateMachine createNew(){
    StateMachine stateMachine = new StateMachine();
    return stateMachine;
  }

  /**
   * constructor is private for prohibition of non-synchronized invocation
   * from different threads. */
  private StateMachine(){
    allSignals = new HashMap<>();
    signalsQueue = new LinkedList<>();
    currentState = null;
    previousState  = null;
    nextStepSignal = null;
    queueIsUnderProcessing = false;
    return;
  }

  //primary_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /**
   * create new signal
   * @param signalName  - member of enum, created by user.
   *                      this enum contains names of state machine signals
   * @return new signal */
  public synchronized StateMachineSignal createSignal(Enum signalName){
    StateMachineSignal newSignal = StateMachineSignal.createNew();
    allSignals.put(signalName, newSignal);
    return newSignal;
  }

  /**
   * get signal by name
   * @param signalName  - member of enum, created by user.
   *                      this enum contains names of state machine signals
   * @return signal for specified name */
  public synchronized StateMachineSignal getSignalByName(Enum signalName){
    StateMachineSignal requiredSignal = allSignals.get(signalName);
    return requiredSignal;
  }

  /**
   * get collection of all state machine signals.
   * key for each signal in this collection is it's signal name.
   * this signal name is a member of enum with signal names, created by user
   * @return collection of all state machine signals */
  public synchronized Map<Enum, StateMachineSignal> getSignals(){
    return allSignals;
  }

  /**
   * get current state
   *
   * @return current state */
  public synchronized StateMachineState getCurrentState(){
    return currentState;
  }

  /**
   * get past state
   *
   * @return past state */
  public synchronized StateMachineState getPreviousState(){
    return previousState;
  }

  /**
   * set jump between states
   *
   * @param from  - start state for jump
   * @param to  - destination state for jump
   * @param signal  - signal, which must be received by state machine for jump
   *
   * @throws NeedFixCode  in case, when was detected wrong work of a program
   *                      because of errors in code */
  public synchronized void setJump(StateMachineState from, StateMachineState to, StateMachineSignal signal){
    //[checking arguments validation]
    if( (null == from)
      ||(null == to)
      ||(null == signal) ){
      throw new NeedFixCode("invalid arguments");
    }
    from.addJump(to, signal);
    return;
  }

  /**
   * set initial state for state machine
   *
   * @param start  - initial state
   *
   * @throws NeedFixCode  in case, when was detected wrong work of a program
   *                      because of errors in code */
  public synchronized void setStart(StateMachineState start){
    //[checking argument validation]
    if(null == start){
      throw new NeedFixCode("argument is null");
    }

    if(currentState != null){
      throw new NeedFixCode("trying to set initial state for already working state machine");
    }

    currentState = start;
    return;
  }

  /**
   * set signal for next <code>makeStep()</code>.
   * this signal defines a jump, which will be executed, when
   * <code>makeStep()</code> will be invoked.
   * this method is a way to deliver a signal to state machine.
   * this method must be invoked for every <code>makeStep()</code>
   * @param signal  - signal for making next state machine step */
  public synchronized void setNextStepSignal(StateMachineSignal signal){
    nextStepSignal = signal;
    return;
  }

  /**
   * make step of state machine with a signal, delivered by
   * last invocation of <code>setNextStepSignal()</code>.
   * this method resets next step signal after making a step,
   * so <code>setNextStepSignal()</code> must be invoked again
   * for granting ability to make next step
   *
   * @throws NeedFixCode  - if <code>setNextStepSignal()</code> was not invoked
   *                        and signal for step is not set. also NeedFixCode may
   *                        be thrown by state handlers
   * @throws Exception  - in case, when state handler throws any exceptions */
  public synchronized void makeStep()
  throws Exception{
    checkSignal(nextStepSignal);
    takeSignalForProcessing(nextStepSignal);
    resetNextStepSignal();
    processSignalsQueue();
    return;
  }

  private void checkSignal(StateMachineSignal signal){
    if(null == signal){
      throw new NeedFixCode("signal in null");
    }
    return;
  }

  private void takeSignalForProcessing(StateMachineSignal signal){
    signalsQueue.add(signal);
    return;
  }

  private void resetNextStepSignal(){
    nextStepSignal = null;
    return;
  }

  private void processSignalsQueue()
  throws Exception{
    if(queueIsUnderProcessing){
      return;
    }
    queueIsUnderProcessing = true;
    while( !(signalsQueue.isEmpty()) ){
      StateMachineSignal signal = signalsQueue.pollFirst();
      processSignal(signal);
    }
    queueIsUnderProcessing = false;
    return;
  }

  private void processSignal(StateMachineSignal signal)
  throws Exception{
    Jump requiredJump = currentState.getJumpForSignal(signal);
    if(null == requiredJump){
      return;
    }
    tryJump(requiredJump);
    return;
  }

  /** this method is for ensuring ability of working <code>makeStep()</code>
   *  after occurring an exception inside one of state handlers
   *  <code>enter()</code> <code>activity()</code> or <code>leave()</code> */
  private void tryJump(Jump requiredJump)
  throws Exception{
    try{
      makeJump(requiredJump);
    }
    catch(Exception exception){
      queueIsUnderProcessing = false;
      throw exception;
    }
    return;
  }

  private void makeJump(Jump jump)
  throws Exception{
    StateMachineState nextState = jump.to;
    if(nextState != currentState){
      currentState.leave();
      previousState = currentState;
      currentState  = nextState;
      currentState.enter();
      currentState.activity();
    }
    else{
      previousState = currentState;
      currentState.activity();
    }
    return;
  }
}
