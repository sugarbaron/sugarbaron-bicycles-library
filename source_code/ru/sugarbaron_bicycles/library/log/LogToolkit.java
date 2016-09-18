/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 30.08.2016 */
package ru.sugarbaron_bicycles.library.log;

import ru.sugarbaron_bicycles.library.exceptions.*;
import ru.sugarbaron_bicycles.library.time.*;



/** provides toolkit for logging */
public final class LogToolkit{
  private static final int MAX_LOGS_QUANTITY = 10;
  private static Log[] allLogs = new Log[MAX_LOGS_QUANTITY];
  private static String[] logFilesNames = new String[MAX_LOGS_QUANTITY];
  private static int currentLogsQuantity = 0;
  
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
    
    if(currentLogsQuantity >= MAX_LOGS_QUANTITY){
      throw new NeedFixCode("[x]can not create one more log");
    }

    Log newLog = new LogUnit(logFileName, clock);
    allLogs[currentLogsQuantity] = newLog;
    logFilesNames[currentLogsQuantity] = logFileName;
    ++currentLogsQuantity;
    return;
  }

  /**
   * get log by file name.
   *
   * @param logFileName   - name of log file
   *
   * @throws NeedFixCode  - in case, when log with specified file name is not exist,
   *                        or if was detected wrong work of a program,
   *                        because of errors in code.
   *
   * @return log for specified file name */
   public static synchronized Log getLog(String logFileName){
     boolean isDetected;
     for(int i=0; i<currentLogsQuantity; i++){
       isDetected = logFilesNames[i].equals(logFileName);
       if(isDetected){
         Log detectedLog = allLogs[i];
         return detectedLog;
       }
     }
     throw new NeedFixCode("[x]log with specified file name is not exist");
   }
}