//[author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)]
//[date: 25.08.2016]
package ru.sugarbaron_bicycles.library.time;

/** interface of a clock, which are provided by sugarbaron_bicycles library */
public interface Clock{
  /**
   * get current time value for this clock.
   *
   * @return time value (in milliseconds). */
  public long getTime();
}
