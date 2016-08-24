package ru.sugarbaron_bicycles.library.random;

//[my bicycles]
import ru.sugarbaron_bicycles.library.exceptions.*;



/**
 * this class gives tools for working withdifferent "randoms"
 * 
 * @author sugarbaron ([sugarbaron_bicycles] e-mail:sugarbraon1@mail.ru)
 */
 final public class Random{
   //primary_section___________________________________________________________
   ////////////////////////////////////////////////////////////////////////////
   /**
    * choose randomly one of #n variants. #n is specified by argument.
    * 
    * @param variantsQuantity  it is #n. quanity of variants for random choose.
    * 
    * @return randomly choosed variant. value belongs [0, #n-1] 
    * 
    * @throws NeedFixCode  if argument is wrong,
    *                      or if was detected wrong work of a
    *                      programm, because of errors in code.
    */
   static public int roll(int variantsQuantity)
   throws NeedFixCode{
     if(!(variantsQuantity > 0)){
       throw new NeedFixCode("[x][Random]#roll():negative argument");
     }
     //[rolling]
     double roll            = Math.random();
     //[calculating interval value]
     double interval        = 1.0/variantsQuantity;
     //[defining interval, which contains rolled value]
     double intervalsInRoll = roll/interval;
     //[converting to int]
     int result             = (int)intervalsInRoll;
     //[special case]
     if(result == variantsQuantity){
       result = result - 1;
     }
     //[profit!]
     return result;
   }
 }
 