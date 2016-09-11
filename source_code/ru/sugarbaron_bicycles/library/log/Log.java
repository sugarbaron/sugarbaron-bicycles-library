/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 27.08.2016 */
package ru.sugarbaron_bicycles.library.log;

import java.io.IOException;

/** interface of log tool */
public interface Log{
  /** write debug record to log (printf-style) */
  public void debug(String text, Object... parameters);
  /** write error record to log (printf-style) */
  public void error(String text, Object... parameters);
  /** write warning record to log (printf-style) */
  public void warning(String text, Object... parameters);
  /** release log resources */
  public void close() throws IOException;
}