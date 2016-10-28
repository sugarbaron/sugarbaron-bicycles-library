/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 25.10.2016 */
package ru.sugarbaron_bicycles.library.useful_tools;

import ru.sugarbaron_bicycles.library.exceptions.NeedFixCode;

public final class UsefulTools{
  /** throw <code>NeedFixCode</code> if <code>referenceToCheck</code> is null.
   *  @param referenceToCheck  - reference to check
   *  @throws NeedFixCode if reference to check is null */
  public static void requireNotNull(Object referenceToCheck){
    if(null == referenceToCheck){
      throw new NeedFixCode("unexpected null value");
    }
    return;
  }

  /** throw <code>NeedFixCode</code> if <code>stringToCheck</code> is null.
   *  @param stringToCheck  - string to check
   *  @throws NeedFixCode if string to check is null */
  public static void requireNotEmpty(String stringToCheck){
    boolean isEmpty = stringToCheck.isEmpty();
    if(isEmpty){
      throw new NeedFixCode("unexpected empty string");
    }
    return;
  }
}