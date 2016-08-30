//[author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)]
//[date: 25.08.2016]
package ru.sugarbaron_bicycles.library.log;

import java.io.*;
import static org.junit.Assert.*;
//[my bicycles]
import ru.sugarbaron_bicycles.library.exceptions.*;
import ru.sugarbaron_bicycles.library.time.*;

public class LogUnitTest{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  static final String LOGS_DIRECTORY = "logs/";

  private final String RECORD_TIME_FORMAT = "[0123456789]";
  private final int RECORD_TIME_LENGTH = RECORD_TIME_FORMAT.length();
  private final String RECORD_MARKER_FORMAT = "[dbg]";
  private final int RECORD_MARKER_LENGTH = RECORD_MARKER_FORMAT.length();
  private final String RECORD_TAIL_FORMAT = " ";
  private final int RECORD_TAIL_LENGTH = RECORD_TAIL_FORMAT.length() + 1; //[+1 means carriage return (CR) character]
  private final String STRING = "string_example";
  private final int DECIMAL = 55;
  private final String DECIMAL_AS_TEXT = "55";
  private final int HEXADECIMAL = 0xFA;
  private final String HEXADECIMAL_AS_TEXT = "0xFA";
  private final String DIAGNOSTICS = "some diagnostics";
  private final String EXPECTED_RECORD_BODY = DIAGNOSTICS + " " + DECIMAL_AS_TEXT + " " + STRING + " " + HEXADECIMAL_AS_TEXT;
  private final int EXPECTED_RECORD_BODY_LENGTH = EXPECTED_RECORD_BODY.length();
  private final String FORMATTED_TEXT = DIAGNOSTICS + " %d %s 0x%X";
  private final int EXPECTED_RECORD_LENGTH = RECORD_TIME_LENGTH + RECORD_MARKER_LENGTH + EXPECTED_RECORD_BODY_LENGTH + RECORD_TAIL_LENGTH;

  private Clock clock;

  //methods_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  public void setUp(){
    clock = new ClockUnit();
    return;
  }

  public void testLogUnit()
  throws Exception{
    testDebug();
    testError();
    testWarning();
    return;
  }

  private void testDebug()
  throws Exception{
    final String DEBUG_LOG_FILE_NAME = "test_debug_record_log.txt";
    TestingLogsToolkit.deleteOldLog(DEBUG_LOG_FILE_NAME);
    Log log = createLog(DEBUG_LOG_FILE_NAME);

    log.debug(FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);

    String logRecord = readLog(DEBUG_LOG_FILE_NAME);
    checkDebugMarker(logRecord);
    checkDiagnostics(logRecord);

    log.close();
    return;
  }

  private void testError()
    throws Exception{
    final String ERROR_LOG_FILE_NAME = "test_error_record_log.txt";
    TestingLogsToolkit.deleteOldLog(ERROR_LOG_FILE_NAME);
    Log log = createLog(ERROR_LOG_FILE_NAME);

    log.error(FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);

    String logRecord = readLog(ERROR_LOG_FILE_NAME);
    checkErrorMarker(logRecord);
    checkDiagnostics(logRecord);

    log.close();
    return;
  }

  private void testWarning()
    throws Exception{
    final String WARNING_LOG_FILE_NAME = "test_warning_record_log.txt";
    TestingLogsToolkit.deleteOldLog(WARNING_LOG_FILE_NAME);
    Log log = createLog(WARNING_LOG_FILE_NAME);

    log.warning(FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);

    String logRecord = readLog(WARNING_LOG_FILE_NAME);
    checkWarningMarker(logRecord);
    checkDiagnostics(logRecord);

    log.close();
    return;
  }

  private Log createLog(String logFileName)
    throws NeedFixCode, CriticalOperationFailed{
    Log log = new LogUnit(logFileName, clock);
    checkLogCreation(logFileName);
    return log;
  }

  private void checkLogCreation(String logFileName){
    String logFilePath = LOGS_DIRECTORY + logFileName;
    File logFile = new File(logFilePath);
    boolean isLogFileCreated = logFile.exists();
    assertTrue("log file is not created", isLogFileCreated);
    return;
  }

  private String readLog(String logFileName)
    throws IOException{
    String logFilePath = LOGS_DIRECTORY + logFileName;
    File logFile = new File(logFilePath);
    FileReader logReader = new FileReader(logFile);

    //[buffer size must be bigger than <code>EXPECTED_RECORD_LENGTH</code>.
    // it allows to check the case, when real record is bigger than expected.
    // that's why i use <code>ADDITIONAL_CHARS_QUANTITY</code>]
    final int ADDITIONAL_CHARS_QUANTITY = 1;
    final int BUFFER_SIZE = EXPECTED_RECORD_LENGTH + ADDITIONAL_CHARS_QUANTITY;
    char[] logAsCharSequence = new char[BUFFER_SIZE];

    int realLogSize = logReader.read(logAsCharSequence);

    checkRecordSize(realLogSize);

    final int BEGIN_INDEX = 0;
    String logAsText = String.valueOf(logAsCharSequence, BEGIN_INDEX, realLogSize);
    return logAsText;
  }

  private void checkRecordSize(int valueToCheck){
    boolean areEqual = (valueToCheck == EXPECTED_RECORD_LENGTH);
    assertTrue("incorrect log size", areEqual);
    return;
  }

  private void checkDebugMarker(String record){
    int MARKER_BEGIN_INDEX = RECORD_TIME_LENGTH;
    int MARKER_END_INDEX = MARKER_BEGIN_INDEX + RECORD_MARKER_LENGTH;
    String recordMarker  = record.substring(MARKER_BEGIN_INDEX, MARKER_END_INDEX);
    boolean isMarkerCorrect = recordMarker.equals("[dbg]");
    assertTrue("debug marker is incorrect", isMarkerCorrect);
    return;
  }

  private void checkErrorMarker(String record){
    int MARKER_BEGIN_INDEX = RECORD_TIME_LENGTH;
    int MARKER_END_INDEX = MARKER_BEGIN_INDEX + RECORD_MARKER_LENGTH;
    String recordMarker  = record.substring(MARKER_BEGIN_INDEX, MARKER_END_INDEX);
    boolean isMarkerCorrect = recordMarker.equals("[err]");
    assertTrue("error marker is incorrect", isMarkerCorrect);
    return;
  }

  private void checkWarningMarker(String record){
    int MARKER_BEGIN_INDEX = RECORD_TIME_LENGTH;
    int MARKER_END_INDEX = MARKER_BEGIN_INDEX + RECORD_MARKER_LENGTH;
    String recordMarker  = record.substring(MARKER_BEGIN_INDEX, MARKER_END_INDEX);
    boolean isMarkerCorrect = recordMarker.equals("[wrn]");
    assertTrue("error marker is incorrect", isMarkerCorrect);
    return;
  }

  private void checkDiagnostics(String record){
    final int RECORD_BODY_BEGIN_INDEX = RECORD_TIME_FORMAT.length() + RECORD_MARKER_FORMAT.length();
    final int RECORD_BODY_END_INDEX = record.length() - 1;//[-1 because excluding carriage return (CR) character]
    String logDiagnostics = record.substring(RECORD_BODY_BEGIN_INDEX, RECORD_BODY_END_INDEX);
    String expectedDiagnostics = EXPECTED_RECORD_BODY + RECORD_TAIL_FORMAT;
    boolean isDiagnosticsCorrect = logDiagnostics.equals(expectedDiagnostics);
    assertTrue("diagnostics is incorrect", isDiagnosticsCorrect);
    return;
  }
}