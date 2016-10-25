/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 25.10.2016 */
package ru.sugarbaron_bicycles.library.useful_tools;

import ru.sugarbaron_bicycles.library.exceptions.NeedFixCode;

public final class UsefulTools{
  public static void ensureNotNull(Object referenceToCheck){
    if(null == referenceToCheck){
      throw new NeedFixCode("unexpected null value");
    }
    return;
  }

  public static void ensureNotEmpty(String stringToCheck){
    boolean isEmpty = stringToCheck.isEmpty();
    if(isEmpty){
      throw new NeedFixCode("unexpected empty string");
    }
    return;
  }
}