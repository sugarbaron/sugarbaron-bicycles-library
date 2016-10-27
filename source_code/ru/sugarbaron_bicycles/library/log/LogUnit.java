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
   * writing debug record to log. takes printf-style parameters.
   * 
   * @param text          string for writing to log. can to contain
   *                      printf-style format specifiers.
   * @param parameters    parameters for registering in log file. */
  public synchronized void debug(String text, Object... parameters){
    final int LEVEL_IS_NOT_SPECIFIED = 0;
    String invokersName = getInvokersName(LEVEL_IS_NOT_SPECIFIED);
    text = "[dbg]" + invokersName + text;
    write(text, parameters);
    return;
  }

  /**
   * writing error record to log. takes printf-style parameters.
   *
   * @param text          string for writing to log. can to contain
   *                      printf-style format specifiers.
   * @param parameters    parameters for registering in log file. */
  public synchronized void error(String text, Object... parameters){
    final int LEVEL_IS_NOT_SPECIFIED = 0;
    String invokersName = getInvokersName(LEVEL_IS_NOT_SPECIFIED);
    text = "[err]" + invokersName + text;
    write(text, parameters);
    return;
  }

  /**
   * writing warning record to log. takes printf-style parameters.
   *
   * @param text          string for writing to log. can to contain
   *                      printf-style format specifiers.
   * @param parameters    parameters for registering in log file. */
  public synchronized void warning(String text, Object... parameters){
    final int LEVEL_IS_NOT_SPECIFIED = 0;
    String invokersName = getInvokersName(LEVEL_IS_NOT_SPECIFIED);
    text = "[wrn]" + invokersName + text;
    write(text, parameters);
    return;
  }

  private String getInvokersName(int specifiedByUserLevel){
    String invokersName;
    try{
      invokersName = defineInvokersName(specifiedByUserLevel);
    }
    catch(ExecutionAborted nameIsNotDefined){
      invokersName = "[xxxxx]xxxxx():";
    }
    return invokersName;
  }

  private String defineInvokersName(int specifiedByUserLevel)
  throws ExecutionAborted{
    //[level == 0  - is a level (in stack trace) of method getStackTrace() in class Thread, so,
    // level == 1  - is a level of this method.
    // level == 3  - is a level for library interface methods ( they are: debug(), error(), warning() )]
    final int LIBRARY_INTERFACE_LEVEL = 3;
    //[invocation level (in stack trace) for method in users code, which invokes library methods]
    final int USERS_INVOKER_LEVEL = LIBRARY_INTERFACE_LEVEL + 1;
    int requiredLevel = USERS_INVOKER_LEVEL + specifiedByUserLevel;

    StackTraceElement[] invokers = Thread.currentThread().getStackTrace();
    int lastIndex = invokers.length - 1;
    boolean isOutOfBounds = (requiredLevel > lastIndex);
    if(isOutOfBounds){
      throw new ExecutionAborted("wrong value of #requiredInvokersLevel");
    }
    StackTraceElement invoker = invokers[requiredLevel];
    String invokersName = extractName(invoker);
    return invokersName;
  }

  private String extractName(StackTraceElement invoker){
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

  private synchronized void write(String text, Object... parameters){
    long time = systemClock.getTime();
    
    //[checking arguments correctness]
    if(text == null){
      return;
    }
    
    //[constructing record (step 1):combining text and parameters]
    String record;
    recordConstructor.format(text, parameters);
    record = recordConstructorResult.toString();
    makeEmpty(recordConstructorResult);

    //[constructing record (step 2):prepending time]
    recordConstructor.format("[%010d]%s \n", time, record);
    record = recordConstructorResult.toString();
    makeEmpty(recordConstructorResult);

    writeRecordToFile(record);
    return;
  }

  private void makeEmpty(StringBuffer buffer){
    final int BEGIN_INDEX = 0;
    final int END_INDEX = buffer.length();
    buffer.delete(BEGIN_INDEX, END_INDEX);
    return;
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