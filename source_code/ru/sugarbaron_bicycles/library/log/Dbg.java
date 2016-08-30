//[author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)]
//[date: 25.08.2016]
package ru.sugarbaron_bicycles.library.log;

/**
 * this class was created for using Dbg.out() 
 * instead of System.out.println(). */
public final class Dbg{
  //writing string to console
  public static void out(String arg){
    System.out.println(arg);
    return;
  }
}