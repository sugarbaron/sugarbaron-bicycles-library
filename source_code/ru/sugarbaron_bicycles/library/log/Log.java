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

  /** write debug record to log with definition of an invoker name, specified by
   *  <code>invocationLevel</code> parameter. takes printf-style parameters.
   *
   *  @param invocationLevel  - level of invoker to be registered in log.
   *    <code>invocationLevel = 0</code> - specifies a method, which invokes <code>debugForPreviousInvoker()</code>
   *    <code>invocationLevel = 1</code> - specifies previous method for invoker of <code>debugForPreviousInvoker()</code>
   *    etc...
   *  @param text  - text to be written to log.  can to contain printf-style format specifiers.
   *  @param parameters  - parameters for registering in log file. */
  void debugForPreviousInvoker(int invocationLevel, String text, Object... parameters);

  /** write record about an error to log with definition of an invoker name, specified by
   *  <code>invocationLevel</code> parameter. takes printf-style parameters.
   *
   *  @param invocationLevel  - level of invoker to be registered in log.
   *    <code>invocationLevel = 0</code> - specifies a method, which invokes <code>errorForPreviousInvoker()</code>
   *    <code>invocationLevel = 1</code> - specifies previous method for invoker of <code>errorForPreviousInvoker()</code>
   *    etc...
   *  @param text  - text to be written to log.  can to contain printf-style format specifiers.
   *  @param parameters  - parameters for registering in log file. */
  void errorForPreviousInvoker(int invocationLevel, String text, Object... parameters);

  /** write warning record to log with definition of an invoker name, specified by
   *  <code>invocationLevel</code> parameter. takes printf-style parameters.
   *
   *  @param invocationLevel  - level of invoker to be registered in log.
   *    <code>invocationLevel = 0</code> - specifies a method, which invokes <code>warningForPreviousInvoker()</code>
   *    <code>invocationLevel = 1</code> - specifies previous method for invoker of <code>warningForPreviousInvoker()</code>
   *    etc...
   *  @param text  - text to be written to log.  can to contain printf-style format specifiers.
   *  @param parameters  - parameters for registering in log file. */
  void warningForPreviousInvoker(int invocationLevel, String text, Object... parameters);

  /** release log resources */
  public void close() throws IOException;
}