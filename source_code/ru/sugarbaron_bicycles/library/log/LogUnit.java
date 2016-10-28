/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)
   date: 27.08.2016 */
package ru.sugarbaron_bicycles.library.log;

//[standard libraries]
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
//[my bicycles]
import ru.sugarbaron_bicycles.library.exceptions.NeedFixCode;
import ru.sugarbaron_bicycles.library.exceptions.ExecutionAborted;
import ru.sugarbaron_bicycles.library.exceptions.CriticalOperationFailed;
import ru.sugarbaron_bicycles.library.time.Clock;


/**
 * provides a log tool for journaling ability.
 * every record in log contains the time point. it shows when this record was made. */
final class LogUnit
implements Log{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  private final String LOGS_DIRECTORY = "logs/";

  private File logFile;
  private Clock systemClock;
  private FileWriter logWriter;
  private Formatter  recordConstructor;
  private StringBuffer recordConstructorResult;

  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /**
   * constructor
   * creates a new LogUnit instance.
   * creates "./logs" directory, if it not exist.
   * creates log file inside "logs" directory.
   *
   * @param fileName    name of log file
   * @param clock       link to #Clock for registering time of every record
   * 
   * @throws NeedFixCode    if arguments are invalid,
   *                        or if was detected wrong work of a
   *                        program, because of errors in code.
   *                                    
   * @throws CriticalOperationFailed  if can't create "logs" directory,
   *                                  or if an IOException appears while working
   *                                    <code>new FileWriter(logFile)</code>,
   *                                  or if some operation was failed, and this
   *                                    fact does not allow continue execution
   *                                    of a program. */
  LogUnit(String fileName, Clock clock){
    //[checking arguments correctness]
    checkLogFileName(fileName);
    checkClock(clock);

    File logsDirectory = createLogsDirectory();
    logFile = new File(logsDirectory, fileName);
    logWriter = createLogWriter();
    systemClock = clock;
    recordConstructorResult = new StringBuffer();
    recordConstructor = new Formatter(recordConstructorResult);
    return;
  }

  /**
   * checking correction of name for log file. characters, besides
   * an english letters, "(", ")", "[", "]", "{", "}" ".", "_", " ",
   * "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" are not allowed.
   * leading space is not allowed. long name (more than MAX_FILENAME_LENGTH)
   * is not allowed.
   *
   * @param fileName    name of log file for checking
   *
   * @throws NeedFixCode    if <code>fileName</code> is invalid */
  private void checkLogFileName(String fileName){
    checkNameForNull(fileName);
    checkNameLength(fileName);
    checkNameForDeniedCharacters(fileName);
    checkNameForLeadingSpace(fileName);
    checkNameForAlreadyExisting(fileName);
    return;
  }

  private void checkNameForNull(String name){
    if(name == null){
      Dbg.out("[x][LogUnit]fileName is null");
      throw new NeedFixCode("[x]fileName is null");
    }
    return;
  }

  private void checkNameLength(String name){
    final int MAX_FILENAME_LENGTH = 80;
    int length = name.length();
    boolean isLengthCorrect = ( (length > 0)&&(length <= MAX_FILENAME_LENGTH) );
    if(isLengthCorrect == false){
      Dbg.out("[x][LogUnit]wrong length of file name");
      throw new NeedFixCode("[x]wrong length of file name");
    }
    return;
  }

  private void checkNameForDeniedCharacters(String name){
    int length = name.length();
    for(int i=0; i<length; i++){
      int character = name.codePointAt(i);
      boolean isCharacterCorrect;
      isCharacterCorrect =  ((character == 0x20)||  // <space>
        (character == 0x28)||  // (
        (character == 0x29)||  // )
        (character == 0x2e)||  // .
        (character == 0x5d)||  // ]
        (character == 0x5f)||  // _
        (character == 0x7d)||  // }
        ((character >= 0x30)&&(character <= 0x39))||  // 0...9
        ((character >= 0x41)&&(character <= 0x5b))||  // A...Z [
        ((character >= 0x61)&&(character <= 0x7b)));  // a...z {

      if(isCharacterCorrect == false){
        Dbg.out("[x][LogUnit]bad characters in log file name");
        throw new NeedFixCode("[x]bad characters in log file name");
      }
    }
    return;
  }

  private void checkNameForLeadingSpace(String name){
    if(name.codePointAt(0) == 0x20){
      Dbg.out("[x][LogUnit]leading space in file name");
      throw new NeedFixCode("[x]leading space in file name");
    }
    return;
  }

  private void checkNameForAlreadyExisting(String name){
    String path = LOGS_DIRECTORY + name;
    File fileToCheck = new File(path);
    boolean isAlreadyExists = fileToCheck.exists();
    if(isAlreadyExists){
      throw new NeedFixCode("[x]such file name is already exists");
    }
    return;
  }

  private void checkClock(Clock clock){
    if(clock == null){
      Dbg.out("[x][LogUnit]link to clock unit is null");
      throw new NeedFixCode("[x]link to clock unit is null");
    }
    return;
  }

  private File createLogsDirectory()
  throws CriticalOperationFailed{
    File directory = new File(LOGS_DIRECTORY);
    //[maybe such directory is already exists]
    if(!directory.exists()){
      //[if not exists - creating]
      boolean isOk = directory.mkdir();
      if(isOk == false){
        Dbg.out("[x][LogUnit]creating log directory: failed");
        throw new CriticalOperationFailed("[x]creating log directory: failed");
      }
    }
    return directory;
  }

  private FileWriter createLogWriter()
  throws CriticalOperationFailed{
    FileWriter writer;
    try{
      writer = new FileWriter(logFile);
    }
    catch(IOException exception){
      Dbg.out("[x][LogUnit]error constructing FileWriter");
      throw new CriticalOperationFailed(exception);
    }
    return writer;
  }
  
  //methods_section__________________________________________________
  ///////////////////////////////////////////////////////////////////
  /**
   * write debug record to log. takes printf-style parameters.
   * 
   * @param text          string for writing to log. can to contain
   *                      printf-style format specifiers.
   * @param parameters    parameters for registering in log file. */
  public synchronized void debug(String text, Object... parameters){
    final int PREVIOUS_INVOKER_LEVEL = 1;
    debugForPreviousInvoker(PREVIOUS_INVOKER_LEVEL, text, parameters);
    return;
  }

  /**
   * write record about an error to log. takes printf-style parameters.
   *
   * @param text          string for writing to log. can to contain
   *                      printf-style format specifiers.
   * @param parameters    parameters for registering in log file. */
  public synchronized void error(String text, Object... parameters){
    final int PREVIOUS_INVOKER_LEVEL = 1;
    errorForPreviousInvoker(PREVIOUS_INVOKER_LEVEL, text, parameters);
    return;
  }

  /**
   * write warning record to log. takes printf-style parameters.
   *
   * @param text          string for writing to log. can to contain
   *                      printf-style format specifiers.
   * @param parameters    parameters for registering in log file. */
  public synchronized void warning(String text, Object... parameters){
    final int PREVIOUS_INVOKER_LEVEL = 1;
    warningForPreviousInvoker(PREVIOUS_INVOKER_LEVEL, text, parameters);
    return;
  }

  /**
   * write an exception to log
   * @param exceptionToRegister  - exception to register */
  public synchronized void exception(Exception exceptionToRegister){
    final int PREVIOUS_INVOKER_LEVEL = 1;
    exceptionForPreviousInvoker(PREVIOUS_INVOKER_LEVEL, exceptionToRegister);
    return;
  }

  /**
   * write an exception to log with definition of an invoker name, specified by
   * <code>invocationLevel</code> parameter.
   * @param invocationLevel  - level of invoker to be registered in log.
   *   <code>invocationLevel = 0</code> - specifies a method, which invokes <code>exceptionForPreviousInvoker()</code>
   *   <code>invocationLevel = 1</code> - specifies previous method for invoker of <code>exceptionForPreviousInvoker()</code>
   *   etc...
   *   incorrect value of <code>invocationLevel</code> will became a reason
   *   of appearing "[xxxxx]xxxxx():" instead of an invoker name. no exceptions
   *   will be thrown in this case.
   * @param exceptionToRegister  - exception to register */
  public synchronized void exceptionForPreviousInvoker(int invocationLevel, Exception exceptionToRegister){
    if(null == exceptionToRegister){
      final int PREVIOUS_INVOKER_LEVEL = 0;
      write("[exc]", PREVIOUS_INVOKER_LEVEL, "invalid argument for log.exception()");
      return;
    }
    String exceptionMessage = exceptionToRegister.getMessage();
    StackTraceElement[] stackTrace = exceptionToRegister.getStackTrace();
    write("[exc]", invocationLevel, "exception!");
    write("[exc]", invocationLevel, "exception message: %s", exceptionMessage);
    write("[exc]", invocationLevel, "exception occurs at:");
    String invokerName;
    String invocationPlace;
    for(StackTraceElement invoker: stackTrace){
      invokerName = extractName(invoker);
      invocationPlace = extractPlace(invoker);
      write("[exc]", invocationLevel, "%s (%s) invoked from:", invokerName, invocationPlace);
    }
    return;
  }

  /**
   * write debug record to log with definition of an invoker name, specified by
   * <code>invocationLevel</code> parameter. takes printf-style parameters.
   *
   * @param invocationLevel  - level of invoker to be registered in log.
   *   <code>invocationLevel = 0</code> - specifies a method, which invokes <code>debugForPreviousInvoker()</code>
   *   <code>invocationLevel = 1</code> - specifies previous method for invoker of <code>debugForPreviousInvoker()</code>
   *   etc...
   *   incorrect value of <code>invocationLevel</code> will became a reason
   *   of appearing "[xxxxx]xxxxx():" instead of an invoker name. no exceptions
   *   will be thrown in this case.
   * @param text  - text to be written to log.  can to contain printf-style format specifiers.
   * @param parameters  - parameters for registering in log file. */
  public synchronized void debugForPreviousInvoker(int invocationLevel, String text, Object... parameters){
    write("[dbg]", invocationLevel, text, parameters);
    return;
  }

  /**
   * write record about an error to log with definition of an invoker name, specified by
   * <code>invocationLevel</code> parameter. takes printf-style parameters.
   *
   * @param invocationLevel  - level of invoker to be registered in log.
   *   <code>invocationLevel = 0</code> - specifies a method, which invokes <code>errorForPreviousInvoker()</code>
   *   <code>invocationLevel = 1</code> - specifies previous method for invoker of <code>errorForPreviousInvoker()</code>
   *   etc...
   *   incorrect value of <code>invocationLevel</code> will became a reason
   *   of appearing "[xxxxx]xxxxx():" instead of an invoker name. no exceptions
   *   will be thrown in this case.
   * @param text  - text to be written to log.  can to contain printf-style format specifiers.
   * @param parameters  - parameters for registering in log file. */
  public synchronized void errorForPreviousInvoker(int invocationLevel, String text, Object... parameters){
    write("[err]", invocationLevel, text, parameters);
    return;
  }

  /**
   * write warning record to log with definition of an invoker name, specified by
   * <code>invocationLevel</code> parameter. takes printf-style parameters.
   *
   * @param invocationLevel  - level of invoker to be registered in log.
   *   <code>invocationLevel = 0</code> - specifies a method, which invokes <code>warningForPreviousInvoker()</code>
   *   <code>invocationLevel = 1</code> - specifies previous method for invoker of <code>warningForPreviousInvoker()</code>
   *   etc...
   *   incorrect value of <code>invocationLevel</code> will became a reason
   *   of appearing "[xxxxx]xxxxx():" instead of an invoker name. no exceptions
   *   will be thrown in this case.
   * @param text  - text to be written to log.  can to contain printf-style format specifiers.
   * @param parameters  - parameters for registering in log file. */
  public synchronized void warningForPreviousInvoker(int invocationLevel, String text, Object... parameters){
    write("[wrn]", invocationLevel, text, parameters);
    return;
  }

  private void write(String marker, int invocationLevel, String text, Object... parameters){
    long time = systemClock.getTime();
    String formattedTime = formatTime(time);
    String combination = combine(text, parameters);
    String invokerName = defineInvokerName(invocationLevel);
    String record = formattedTime + marker + invokerName + combination + " \n";
    writeRecordToFile(record);
    return;
  }

  private String formatTime(long time){
    recordConstructor.format("[%010d]", time);
    String formattedTime = recordConstructorResult.toString();
    makeEmpty(recordConstructorResult);
    return formattedTime;
  }

  private String combine(String text, Object... parameters){
    String combination;
    try{
      combination = tryToCombine(text, parameters);
    }
    catch(Exception combiningFailed){
      combination = "cant combine text with parameters. text is null, or wrong parameters format";
    }
    makeEmpty(recordConstructorResult);
    return combination;
  }

  private String tryToCombine(String text, Object... parameters){
    if(text == null){
      throw new NeedFixCode("text is null");
    }
    recordConstructor.format(text, parameters);
    String combination = recordConstructorResult.toString();
    return combination;
  }

  private void makeEmpty(StringBuffer buffer){
    final int BEGIN_INDEX = 0;
    final int END_INDEX = buffer.length();
    buffer.delete(BEGIN_INDEX, END_INDEX);
    return;
  }

  private String defineInvokerName(int specifiedByUserLevel){
    String invokerName;
    try{
      invokerName = tryToDefineInvokerName(specifiedByUserLevel);
    }
    catch(Exception nameIsNotDefined){
      invokerName = "[xxxxx]xxxxx():";
    }
    return invokerName;
  }

  private String tryToDefineInvokerName(int specifiedByUserLevel)
  throws ExecutionAborted{
    //[level == 0  - is a level (in stack trace) of method getStackTrace() in class Thread, so,
    // level == 1  - is a level of this method
    // level == 2  - is a level of defineInvokerName() method
    // level == 3  - is a level of write() method
    // level == 4  - is a level for library interface methods
    //               ( they are: debugForPreviousInvoker(), errorForPreviousInvoker(), warningForPreviousInvoker() )]
    final int LIBRARY_INTERFACE_LEVEL = 4;
    //[invocation level (in stack trace) for method in users code, which invokes library methods]
    final int USERS_INVOKER_LEVEL = LIBRARY_INTERFACE_LEVEL + 1;
    int requiredLevel = USERS_INVOKER_LEVEL + specifiedByUserLevel;

    StackTraceElement[] invokers = Thread.currentThread().getStackTrace();
    int lastIndex = invokers.length - 1;
    requireInsideOfBounds(requiredLevel, lastIndex);
    StackTraceElement invoker = invokers[requiredLevel];
    String invokerName = extractName(invoker);
    return invokerName;
  }

  private void requireInsideOfBounds(int valueToCheck, int bounds)
  throws ExecutionAborted{
    boolean isOutOfBounds = (valueToCheck > bounds);
    if(isOutOfBounds){
      throw new ExecutionAborted("wrong value of #requiredLevel");
    }
    return;
  }

  private String extractName(StackTraceElement invoker){
    String name;
    try{
      name = tryToExtractName(invoker);
    }
    catch(Exception cantExtract){
      name = "[xxxxx]xxxxx():";
    }
    return name;
  }

  private String tryToExtractName(StackTraceElement invoker){
    String raw = invoker.toString();
    String[] parts = raw.split("\\.");
    int lastPartIndex = parts.length - 1;
    int methodPartIndex = lastPartIndex - 1;
    int classPartIndex  = methodPartIndex - 1;
    String methodPart = parts[methodPartIndex];
    String className  = parts[classPartIndex];
    parts = methodPart.split("\\(");
    String methodName  = parts[0];
    String invokerName = "[" + className + "]" + methodName + "():";
    return invokerName;
  }

  private String extractPlace(StackTraceElement invoker){
    String place;
    try{
      place = tryToExtractPlace(invoker);
    }
    catch(Exception cantExtract){
      place = "xxxxx";
    }
    return place;
  }

  private String tryToExtractPlace(StackTraceElement invoker){
    String raw = invoker.toString();
    int leftBraceIndex  = raw.indexOf("(");
    int rightBraceIndex = raw.indexOf(")");
    int placeIndex = leftBraceIndex + 1;
    String place = raw.substring(placeIndex, rightBraceIndex);
    return place;
  }

  private void writeRecordToFile(String record){
    try{
      logWriter.write(record, 0, record.length());
      logWriter.flush();
    }
    catch(IOException exception){
      /* negative try of writing record to file must not became
       * a reason of breaking application work. doing nothing */
    }
    return;
  }

  /**
   * release LogUnit instance resources
   *
   * @throws IOException  in case of problems with closing FileWriter */
  public void close()
  throws IOException{
    recordConstructor.close();
    logWriter.close();
    return;
  }
}