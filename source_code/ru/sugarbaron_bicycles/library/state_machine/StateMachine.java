package ru.sugarbaron_bicycles.library.state_machine;

//[standard libraries]
import java.util.LinkedList;
//[my bicycles]
import ru.sugarbaron_bicycles.library.exceptions.*;



/**
 * this class defines graph (actually, state machine)
 * and describes work of this graph. 
 * 
 * @author sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
 */
public final class StateMachine{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /** current state */
  private GraphState currentState = null;
  /** past state */
  private GraphState pastState = null;
  /** jumps queue */
  private LinkedList<Jump> jumpsQueue = null;
  /** jumps queue processing flag */
  private boolean queueIsUnderProcessing = false;

  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /**
   * create state machine
   */
  private StateMachine(){
    jumpsQueue = new LinkedList<>();
  }
  
  //primary_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /**
   * constructor is private for prohibition of non-synchronized invocation 
   * from different threads. for creating state machine instance should
   * to use this method
   * 
   * @return reference to just-created state machine instance
   */
  static synchronized public StateMachine create(){
    return new StateMachine();
  }
  
  /**
   * set jump between states
   * 
   * @param from    start state for jump
   * @param to      destination state for jump
   * @param signal  signal, which must be received by state machine for jump
   * 
   * @throws NeedFixCode  in case, when was detected wrong work of a program
   *                      because of errors in code
   */
  static synchronized public void setJump(GraphState from, GraphState to, GraphSignal signal)
  throws NeedFixCode{
    //[01.checking arguments validation]
    if(null == from){
      throw new NeedFixCode("[x][StateMachine]#setJump():#from is null");
    }
    if(null == to){
      throw new NeedFixCode("[x][StateMachine]#setJump():#to is null");
    }
    if(null == signal){
      throw new NeedFixCode("[x][StateMachine]#setJump():#signal is null");
    }
    //[02.setting jump]
    from.addJump(to, signal);
  }
  
  /**
   * set initial state for state machine
   * 
   * @param start  initial state
   * 
   * @throws NeedFixCode  in case, when was detected wrong work of a program
   *                      because of errors in code
   */
  synchronized public void setStart(GraphState start)
  throws NeedFixCode{
    //[01.checking argument validation]
    if(null == start){
      throw new NeedFixCode("[x][StateMachine]#setStart():argument is null");
    }
    //[02.checking correctness of using]
    if(currentState != null){
      throw new NeedFixCode("[x][StateMachine]#setStart():trying to set initial state for already working state machine");
    }
    //[03.setting initial state]
    currentState = start;
  }
  
  /**
   * make step of state machine. delivering signal to state machine cause it
   * to change state if such jump is defined (by #setJump())
   * 
   * @param signal  signal, delivered to state machine
   * 
   * @throws NeedFixCode  in case, when was detected wrong work of a program
   *                      because of errors in code
   */
  synchronized public void makeStep(GraphSignal signal)
  throws NeedFixCode{
    //[01.checking argument validation]
    if(null == signal){
      throw new NeedFixCode("[x][StateMachine]#makeStep():argument is null");
    }
    //[02.searching for required signal among jumps, registered for this state]
    for(int i=0; i<currentState.currentJumpsQuantity; i++){
      if(currentState.jumps[i].signal == signal){
        //[required signal is found]
        jumpsQueue.add(currentState.jumps[i]);
        processJumpsQueue();
        //[profit!]
        return;
      }
    }
    //[case, when there is no jump for specified signal]
    return;
  }

  /**
   * process jumps, which are stored in jumpsQueue. order is fifo
   */
  synchronized private void processJumpsQueue()
  throws NeedFixCode{
    if(queueIsUnderProcessing){
      return;
    }
    queueIsUnderProcessing = true;
    while( !(jumpsQueue.isEmpty()) ){
      Jump jump = jumpsQueue.pollFirst();
      makeJump(jump);
    }
    queueIsUnderProcessing = false;
    return;
  }
  
  /**
   * get current state
   * 
   * @return current state
   */
  synchronized public GraphState getCurrentState(){
    return currentState;
  }
  
  /**
   * get past state
   * 
   * @return past state
   */
  synchronized public GraphState getPastState(){
    return pastState;
  }
  
  //secondary_section__________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /**
   * make jump from current state to other state, defined by index of
   * #jumps[] array for current state
   * 
   * @param jump  jump for current state, which must be executed
   * 
   * @throws NeedFixCode  in case, when was detected wrong work of a program
   *                      because of errors in code
   */
  private void makeJump(Jump jump)
  throws NeedFixCode{
    GraphState nextState = jump.to;

    if(nextState != currentState){
      //[jumping to other state]
      //[leaving this state]
      currentState.leave();
      //[changing state]
      pastState    = currentState;
      currentState = nextState;
      //[entering new state]
      currentState.enter();
      //[executing activity of new state]
      currentState.activity();
    }
    else{
      //[jumping to same state]
      pastState    = currentState;
      //[executing activity of current state]
      currentState.activity();
    }
    //[jump complete]
    return;
  }
}
