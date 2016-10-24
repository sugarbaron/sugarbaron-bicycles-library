/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 30.08.2016 */
package ru.sugarbaron_bicycles.library.log;

import java.util.Map;
import java.util.TreeMap;
//[my bicycles]
import ru.sugarbaron_bicycles.library.exceptions.NeedFixCode;
import ru.sugarbaron_bicycles.library.exceptions.CriticalOperationFailed;
import ru.sugarbaron_bicycles.library.time.Clock;



/** provides toolkit for logging */
public final class LogToolkit{
  private static Map<String, Log> allLogs = new TreeMap<>();

  /** denying creation instances */
  private LogToolkit(){}

  /**
   * create log with specified log file name.
   *
   * @param logFileName  - log file name
   *
   * @param clock  - clock for registering time of every record
   *
   * @throws NeedFixCode  - if arguments are invalid
   *                        or if was detected wrong work of a
   *                        program, because of errors in code.
   *
   * @throws CriticalOperationFailed  - in case of system operations fails */
  public static synchronized void createLog(String logFileName, Clock clock){
    //[checking parameters validation]
    if( (null == logFileName)||(null == clock) ){
      throw new NeedFixCode("[x]wrong parameters for #createLog() method");
    }

    Log newLog = new LogUnit(logFileName, clock);
    allLogs.put(logFileName, newLog);
    return;
  }

  /**
   * get log by file name.
   *
   * @param logFileName   - name of log file
   *
   * @throws NeedFixCode  - in case, when log with specified file name is not exist,
   *                        or if argument is null.
   *
   * @return log for specified file name */
   public static synchronized Log getLog(String logFileName){
     if(null == logFileName){
       throw new NeedFixCode("[x]wrong parameters for #getLog() method");
     }
     Log requiredLog = allLogs.get(logFileName);
     if(null == requiredLog){
       throw new NeedFixCode("[x]log with specified file name is not exist");
     }
     return requiredLog;
   }
}