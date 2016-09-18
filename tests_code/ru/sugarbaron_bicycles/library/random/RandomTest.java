/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 14.09.2016 */
package ru.sugarbaron_bicycles.library.random;

import static org.junit.Assert.*;

public class RandomTest{
  public void testRoll(){
    final int ITERATIONS_QUANTITY = 100000;
    final int VARIANTS_QUANTITY = 5;
    final int MAX_VALUE = VARIANTS_QUANTITY - 1;
    final int MIN_VALUE = 0;

    int variant;
    boolean isInRange;
    for(int i=0; i<ITERATIONS_QUANTITY; i++){
      variant = Random.roll(VARIANTS_QUANTITY);
      isInRange = ( (variant >= MIN_VALUE)&&(variant <= MAX_VALUE) );
      assertTrue("test fail: roll() has returned a value, which is out of range", isInRange);
    }
    return;
  }
}