package ru.sugarbaron_bicycles.library.state_machine;

import ru.sugarbaron_bicycles.library.exceptions.*;



/**
 * this class describes a state of state machine 
 * 
 * @author sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
 */
public class GraphState{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /** maximum jumps number for state */
  static final int MAX_JUMPS = 10;
  /** possible jumps from this state */
  Jump[] jumps = null;
  /** current jumps quantity */
  int currentJumpsQuantity = -1;
  
  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /**
   * create state of state machine
   */
  public GraphState(){
    synchronized(this){
      //[02.initing fields]
      jumps = new Jump[MAX_JUMPS];
      for(int i=0; i<MAX_JUMPS; i++){
        jumps[i] = null;
      }
      currentJumpsQuantity = 0;
    }
  }
  
  //primary_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /**
   * constructor is private for prohibition of non-synchronized invocation 
   * from different threads. for creating state instanse should to use
   * this method
   * 
   * @return reference to just-created state instance
   */
  static synchronized public GraphState createGraphState(){
    return new GraphState();
  } 
  
  /**
   * method for overriding. in overrided method user can define
   * actions, which will execute, when this graph state will become
   * current from other ghaph state.
   * 
   * @throws NeedFixCode  in case, when was detected wrong work of a programm
   *                      because of errors in code
   */
  synchronized public void enter()
  throws NeedFixCode{}
  
  /**
   * method for overriding. in overrided method user can define
   * inner activity of this graph state state.
   * 
   * @throws NeedFixCode  in case, when was detected wrong work of a programm
   *                      because of errors in code
   */
  synchronized public void activity()
  throws NeedFixCode{}
  
  /**
   * method for overriding. in overrided method user can define
   * actions, which will execute, when this graph state will change
   * to other graph state
   * 
   * @throws NeedFixCode  in case, when was detected wrong work of a programm
   *                      because of errors in code
   */
  synchronized public void leave()
  throws NeedFixCode{}
  
  
  /**
   * set jump from this state
   * 
   * @param to      destination state for jump
   * @param signal  signal, which must be received by state machine for jump
   * 
   * @throws NeedFixCode  in case, when was detected wrong work of a programm
   *                      because of errors in code
   */
  void addJump(GraphState to, GraphSignal signal)
  throws NeedFixCode{
    //[01.checking arguments validation]
    if(null == to){
      throw new NeedFixCode("[x][State]#setJump():#to is null");
    }
    if(null == signal){
      throw new NeedFixCode("[x][State]#setJump():#signal is null");
    }
    //[02.checking possibility of creating one more jump for this state]
    if(!(currentJumpsQuantity < MAX_JUMPS)){
      throw new NeedFixCode("[x][State]#setJump():maximum quantity of jumps for state is reached");
    }
    //[03.checking corectness of adding specified jump]
    for(int i=0; i<currentJumpsQuantity; i++){
      if(signal == jumps[i].signal){
        throw new NeedFixCode("[x][State]#setJump():jump with such signal is already registered for this state");
      }
    }
    //[04.creating specified jump]
    jumps[currentJumpsQuantity] = new Jump(this, to, signal);
    ++currentJumpsQuantity;
  }
}
