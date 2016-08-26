package ru.sugarbaron_bicycles.library.log;

//[standard libraries]
import java.io.*;
import java.util.*;
//[my bicycles]
import ru.sugarbaron_bicycles.library.exceptions.*;
import ru.sugarbaron_bicycles.library.time.*;


/**
 * instance of <code>LogUnit</code> class creates the log file and allows to
 * register any event of users program. also allows to trace users program and
 * can use for debugging.
 * 
 * every record in log contains the time point. it shows when <code>write</code>
 * method was invoked.
 * 
 * <code>LogUnit</code> class requires <code>ClockUnit</code> instance for using
 * 
 * api of LogUnit class:
 * 01.LogUnit(filename, clock)
 *    - constructor
 *     
 * 02.void write(printfStyleString, parameter1, parameter2, ...)
 *    - writing record to log
 * 
 * @author sugarbaron ([sugarbaron_bicycles] e-mail: sugarbaron1@mail.ru)
 */
public final class LogUnit{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  //max length of file name
  private final int MAX_FILENAME_LENGTH = 20;
  
  //log file
  private File         logFile;
  //tool for writing records to log file
  private FileWriter   logWriter;
  //directory, which contains log files
  private File         logsDir;
  //link to clock unit
  private ClockUnit    systemClock;
  //tool for constructing log record
  private Formatter    recordConstructor;
  //buffer, where the record is constructing
  private StringBuffer buffer;
  //file, with errors occurred during LogUnit works
  private File         errFile;
  //tool for writing records to "log_errors.txt"
  private PrintWriter  errWriter;
  //log record
  private String       record;
  
  
  
  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  /**
   * constructor
   * creates a new LogUnit instance.
   * creates "./logs" directory, if it not exist.
   * creates log file inside "logs" directory.
   * creates "log_errors" file inside "logs" directory.
   * 
   * @param fileName    name of log file
   * @param clock       link to ClockUnit for registering time of every record
   * 
   * @throws NeedFixCode              if file name contains denied characters,
   *                                  or if it is too long,
   *                                  or if it is null,
   *                                  or if "clock" argument is null,
   *                                  or if was detected wrong work of a
   *                                    program, because of errors in code.
   *                                    
   * @throws CriticalOperationFailed  if can't create "logs" directory, or
   *                                  if an IOException appears while working
   *                                  <code>new FileWriter(logFile)</code>, or
   *                                  FileNotFoundException appears while
   *                                    working <code>new PrintWriter()</code>,
   *                                  or if some operation was failed, and this
   *                                    fact does not allow continue execution
   *                                    of a program.
   */
  LogUnit(String fileName, ClockUnit clock)
  throws NeedFixCode, CriticalOperationFailed{
    boolean isOk = false;
    
    //1)checking parameters validation:
    //checking link to clock unit
    if(clock == null){
      Dbg.out("[x][LogUnit]link to clock unit is null");
      throw new NeedFixCode("[x]link to clock unit is null");
    }
    //checking log file name
    checkLogFileName(fileName);

    //2)creating and opening log file
    //creating directory for logs
    logsDir = new File("logs/");
    //maybe such directory is already exists
    if(!logsDir.exists()){
      //if not exists - creating
      isOk = logsDir.mkdir();
      if(!isOk){
        Dbg.out("[x][LogUnit]creating log directory: failed");
        throw new CriticalOperationFailed("[x]creating log directory: failed"); 
      }
    }
    
    //creating log file
    logFile = new File(logsDir, fileName);
    
    //3)creating writer
    try{
      logWriter = new FileWriter(logFile);
    }
    catch(IOException exc){
      Dbg.out("[x][LogUnit]error constructing FileWriter");
      throw new CriticalOperationFailed(exc);
    }
    
    //4)saving link to clock unit
    systemClock = clock;
    
    //5)creating buffer, where log records will be constructed
    buffer = new StringBuffer();
    
    //6)creating record constructor
    recordConstructor = new Formatter(buffer);
    
    //7)creating errFile
    String errFileName = "0_errors_of_" + fileName;
    errFile = new File(logsDir, errFileName);
    
    //8)initialising errWriter
    try{
      errWriter = new PrintWriter(errFile);
    }
    catch(FileNotFoundException exc){
      Dbg.out("[x][LogUnit]troubles with errFile");
      throw new CriticalOperationFailed(exc);
    }
  }
  
  //primary_section__________________________________________________
  ///////////////////////////////////////////////////////////////////
  /**
   * writing record to log. (primary method of this class)
   * takes printf-style parameters.
   * 
   * @param text          string for writing to log. can to contain
   *                      printf-style format specifiers.
   * @param parameters    parameters for registering in log file.
   */
  public synchronized void write(String text, Object... parameters){
    long time = systemClock.getTime();
    
    //checking parameters validation
    if(text == null){
      writeError("[%010d][x][LogUnit]text is null \n", time);
      return;  
    }
    
    //constructing record (step 1)
    recordConstructor.format(text, parameters);
    record = buffer.toString();
    
    //cleaning buffer
    buffer.delete(0, buffer.length());
    
    //constructing record (step 2)
    recordConstructor.format("[%010d]%s \n", time, record);
    record = buffer.toString();
    
    //cleaning buffer
    buffer.delete(0, buffer.length());
    
    //writing record to log
    try{
      logWriter.write(record, 0, record.length());
      logWriter.flush();
    }
    catch(IOException exc){
      writeError("[%010d][x][LogUnit]%s \n", time, exc.toString());
    }
    
    return;
  }
  
  //secondary_section________________________________________________
  ///////////////////////////////////////////////////////////////////
  /**
   * checking correction of name for log file. characters, besides
   * an english letters, "(", ")", "[", "]", "{", "}" ".", "_", " ",
   * "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" are not allowed.
   * leading space is not allowed. long name (more than MAX_FILENAME_LENGTH)
   * is not allowed.
   * 
   * @param fileName    name of log file for checking
   * 
   * @throws NeedFixCode    if <code>fileName</code> contains denied
   *                                 characters (see method description), or
   *                                 if it is null.
   */
  private void checkLogFileName(String fileName) 
  throws NeedFixCode{
    //length of file name
    int length = 0;
    
    //1)checking for "null"
    if(fileName == null){
      Dbg.out("[x][LogUnit]fileName is null");
      throw new NeedFixCode("[x]fileName is null");
    }
    
    //2)checking length of file name
    length = fileName.length();
    if(!( (length > 0)&&(length <= MAX_FILENAME_LENGTH) )){
      Dbg.out("[x][LogUnit]too big length of file name");
      throw new NeedFixCode("[x]too big length of file name");
    }
    
    //3)checking for characters, which are out of allowed range
    for(int i=0; i<length; i++){
      int character = fileName.codePointAt(i);
      boolean isCorrectCharacter;
      isCorrectCharacter =  ((character == 0x20)||  // <space>
                             (character == 0x28)||  // (
                             (character == 0x29)||  // )
                             (character == 0x2e)||  // .
                             (character == 0x5d)||  // ]
                             (character == 0x5f)||  // _
                             (character == 0x7d)||  // }
      ((character >= 0x30)&&(character <= 0x39))||  // 0...9
      ((character >= 0x41)&&(character <= 0x5b))||  // A...Z [
      ((character >= 0x61)&&(character <= 0x7b)));  // a...z {

      if(!isCorrectCharacter){
        Dbg.out("[x][LogUnit]bad characters in log file name");
        throw new NeedFixCode("[x]bad characters in log file name");
      }
    }
    
    //4)leading space is not allowed
    if(fileName.codePointAt(0) == 0x20){
      Dbg.out("[x][LogUnit]leading space in file name");
      throw new NeedFixCode("[x]leading space in file name");
    }
    
    //5)what have i forget?
    
    return;
  }
  
  /**
   * writing message to <code>errFile</code>.
   * using for registering errors, which appears while LogUnit is working.
   * takes printf-style parameters.
   * 
   * @param text          string for writing to log. can to contain
   *                      printf-style format specifiers.
   * @param parameters    parameters for registering in log file.
   */
   private synchronized void writeError(String text, Object... parameters){
     errWriter.printf(text, parameters);
     errWriter.flush();
     return;
   }

  
  //terminating______________________________________________________
  ///////////////////////////////////////////////////////////////////
  /**
   * ensures closing of log resources when the work is over.
   * (destructor)
   */
  protected void finalize(){
    //closing logWriter
    try{
      logWriter.close();
    }
    catch(IOException unusable){
      long time = systemClock.getTime();
      writeError("[%10][x][LogUnit]closing FileWriter error \n", time);
    }
    //closing recordConstructor
    recordConstructor.close();
    //closing errWriter
    errWriter.close();
    
    logWriter         = null;
    recordConstructor = null;
    buffer            = null;
    errWriter         = null;
    errFile           = null;
    logFile           = null;
    logsDir           = null;
    systemClock       = null;
    record            = null;
  }
}