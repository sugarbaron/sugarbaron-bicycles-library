/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 25.10.2016 */
package ru.sugarbaron_bicycles.library.useful_tools;

import org.junit.Test;
import ru.sugarbaron_bicycles.library.exceptions.NeedFixCode;

public final class UsefulToolsTest{
  @Test
  public void ensureNotNull(){
    String notNull = "not_null";
    UsefulTools.requireNotNull(notNull);
    return;
  }

  @Test(expected = NeedFixCode.class)
  public void ensureNotNullForNull(){
    deceiveIntelliJ(null);
    return;
  }

  private void deceiveIntelliJ(String alwaysNull){
    UsefulTools.requireNotNull(alwaysNull);
    return;
  }

  @Test
  public void ensureNotEmpty(){
    String notEmpty = "not_empty";
    UsefulTools.requireNotEmpty(notEmpty);
    return;
  }

  @Test(expected = NeedFixCode.class)
  public void ensureNotEmptyForEmpty(){
    String empty = "";
    UsefulTools.requireNotEmpty(empty);
    return;
  }
}