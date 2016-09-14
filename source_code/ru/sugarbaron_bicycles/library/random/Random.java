/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 14.09.2016 */
package ru.sugarbaron_bicycles.library.random;

//[my bicycles]
import ru.sugarbaron_bicycles.library.exceptions.*;

/**
 * this class provides tools for working with  different "randoms" */
 final public class Random{
   //primary_section___________________________________________________________
   ////////////////////////////////////////////////////////////////////////////
   /**
    * choose randomly one of #n variants. #n is specified by argument.
    * @param variantsQuantity  it is #n. quantity of variants for random choose.
    * @return randomly chosen variant. value belongs [0, #n-1]
    * @throws NeedFixCode  if argument is wrong */
   static public int roll(int variantsQuantity)
   throws NeedFixCode{
     //[checking arguments correctness]
     if( !(variantsQuantity > 0) ){
       throw new NeedFixCode("[x][Random]#roll():negative argument");
     }

     final double MAX_RANDOM = 1.0;
     double roll = Math.random();
     double interval = MAX_RANDOM/variantsQuantity;
     double intervalsInRoll = roll/interval;
     int result = (int)intervalsInRoll;
     //[special case. (if Math.random() has returned 1.0)]
     if(result == variantsQuantity){
       result = 0;
     }
     return result;
   }
 }
 