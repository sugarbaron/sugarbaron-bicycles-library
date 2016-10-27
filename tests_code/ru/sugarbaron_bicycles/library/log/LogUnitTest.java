//[author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)]
//[date: 25.08.2016]
package ru.sugarbaron_bicycles.library.log;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
//[my bicycles]
import ru.sugarbaron_bicycles.library.exceptions.NeedFixCode;
import ru.sugarbaron_bicycles.library.exceptions.CriticalOperationFailed;
import ru.sugarbaron_bicycles.library.time.Clock;
import ru.sugarbaron_bicycles.library.time.ClockUnit;

public final class LogUnitTest{
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
  private final String FORMATTED_TEXT = DIAGNOSTICS + " %d %s 0x%X";
  private final String EXPECTED_RECORD_BODY = DIAGNOSTICS + " " + DECIMAL_AS_TEXT + " " + STRING + " " + HEXADECIMAL_AS_TEXT;
  private final int RECORD_BODY_LENGTH = EXPECTED_RECORD_BODY.length();

  private Clock clock;

  //methods_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  @Before
  public void setUp(){
    clock = new ClockUnit();
    return;
  }

  @Test
  public void loggingForPreviousInvoker(){
    thisMethodMustBeRegisteredInLog();
    return;
  }

  private void thisMethodMustBeRegisteredInLog(){
    oneMoreLevelForDebug();
    return;
  }

  private void oneMoreLevelForDebug(){
    andOneMoreLevelForDebug();
    return;
  }

  private void andOneMoreLevelForDebug(){
    final String LOG_FILE_NAME = "testing_previous_invokers_registering.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    final int PREVIOUS_INVOKER = 2;
    log.debugForPreviousInvoker(PREVIOUS_INVOKER, "what method do you see? must be: thisMethodMustBeRegisteredInLog()");
    log.errorForPreviousInvoker(PREVIOUS_INVOKER, "what method do you see? must be: thisMethodMustBeRegisteredInLog()");
    log.warningForPreviousInvoker(PREVIOUS_INVOKER, "what method do you see? must be: thisMethodMustBeRegisteredInLog()");
    return;
  }

  @Test
  public void debugForPreviousInvokerForNullText(){
    final String LOG_FILE_NAME = "testing_debugForPreviousInvoker_for_null_text.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    final int THIS_METHOD_LEVEL = 0;
    log.debugForPreviousInvoker(THIS_METHOD_LEVEL, null);
    return;
  }

  @Test
  public void debugForPreviousInvokerForEmptyText(){
    final String LOG_FILE_NAME = "testing_debugForPreviousInvoker_for_empty_text.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    final int THIS_METHOD_LEVEL = 0;
    log.debugForPreviousInvoker(THIS_METHOD_LEVEL, "");
    return;
  }

  @Test
  public void debugForPreviousInvokerForNullParameters(){
    final String LOG_FILE_NAME = "testing_debugForPreviousInvoker_for_null_parameters.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    final int THIS_METHOD_LEVEL = 0;
    log.debugForPreviousInvoker(THIS_METHOD_LEVEL, "here comes null parameters: %d %d %s", null, 7, "ace");
    log.debugForPreviousInvoker(THIS_METHOD_LEVEL, "here comes null parameters: %d %d %s", 3, null, "ace");
    log.debugForPreviousInvoker(THIS_METHOD_LEVEL, "here comes null parameters: %d %d %s", 3, 7, null);
    return;
  }

  @Test
  public void debugForPreviousInvokerForWrongParameters(){
    final String LOG_FILE_NAME = "testing_debugForPreviousInvoker_for_wrong_parameters.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    final int THIS_METHOD_LEVEL = 0;
    log.debugForPreviousInvoker(THIS_METHOD_LEVEL, "here comes null parameters: %d %d %s", "wrong_parameter", 7, "ace");
  }

  @Test
  public void debugForPreviousInvokerForEmptyStringParameters(){
    final String LOG_FILE_NAME = "testing_debugForPreviousInvoker_for_empty_string_parameters.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    final String EMPTY_STRING = "";
    final int THIS_METHOD_LEVEL = 0;
    log.debugForPreviousInvoker(THIS_METHOD_LEVEL, "here comes null parameters: %d %d %s", 3, 7, EMPTY_STRING);
  }

  @Test
  public void debugForPreviousInvokerForMultipleLines(){
    final String DEBUG_LOG_FILE_NAME = "testing_debugForPreviousInvoker_for_multiple_lines.log";
    TestingLogsToolkit.deleteOldLog(DEBUG_LOG_FILE_NAME);
    Log log = createLog(DEBUG_LOG_FILE_NAME);
    final int THIS_METHOD_LEVEL = 0;
    log.debugForPreviousInvoker(THIS_METHOD_LEVEL, FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    log.debugForPreviousInvoker(THIS_METHOD_LEVEL, FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    log.debugForPreviousInvoker(THIS_METHOD_LEVEL, FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    log.debugForPreviousInvoker(THIS_METHOD_LEVEL, FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    log.debugForPreviousInvoker(THIS_METHOD_LEVEL, FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    return;
  }

  @Test
  public void debugForNullText(){
    final String LOG_FILE_NAME = "testing_debug_for_null_text.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    log.debug(null);
    return;
  }

  @Test
  public void debugForEmptyText(){
    final String LOG_FILE_NAME = "testing_debug_for_empty_text.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    log.debug("");
    return;
  }

  @Test
  public void debugForNullParameters(){
    final String LOG_FILE_NAME = "testing_debug_for_null_parameters.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    log.debug("here comes null parameters: %d %d %s", null, 7, "ace");
    log.debug("here comes null parameters: %d %d %s", 3, null, "ace");
    log.debug("here comes null parameters: %d %d %s", 3, 7, null);
    return;
  }

  @Test
  public void debugForWrongParameters(){
    final String LOG_FILE_NAME = "testing_debug_for_wrong_parameters.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    log.debug("here comes null parameters: %d %d %s", "wrong_parameter", 7, "ace");
  }

  @Test
  public void debugForEmptyStringParameters(){
    final String LOG_FILE_NAME = "testing_debug_for_empty_string_parameters.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    final String EMPTY_STRING = "";
    log.debug("here comes null parameters: %d %d %s", 3, 7, EMPTY_STRING);
  }

  @Test
  public void debugForMultipleLines(){
    final String DEBUG_LOG_FILE_NAME = "testing_debug_for_multiple_lines.log";
    TestingLogsToolkit.deleteOldLog(DEBUG_LOG_FILE_NAME);
    Log log = createLog(DEBUG_LOG_FILE_NAME);

    log.debug(FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    log.debug(FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    log.debug(FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    log.debug(FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    log.debug(FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    return;
  }

  @Test
  public void errorForPreviousInvokerForNullText(){
    final String LOG_FILE_NAME = "testing_errorForPreviousInvoker_for_null_text.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    final int THIS_METHOD_LEVEL = 0;
    log.errorForPreviousInvoker(THIS_METHOD_LEVEL, null);
    return;
  }

  @Test
  public void errorForPreviousInvokerForEmptyText(){
    final String LOG_FILE_NAME = "testing_errorForPreviousInvoker_for_empty_text.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    final int THIS_METHOD_LEVEL = 0;
    log.errorForPreviousInvoker(THIS_METHOD_LEVEL, "");
    return;
  }

  @Test
  public void errorForPreviousInvokerForNullParameters(){
    final String LOG_FILE_NAME = "testing_errorForPreviousInvoker_for_null_parameters.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    final int THIS_METHOD_LEVEL = 0;
    log.errorForPreviousInvoker(THIS_METHOD_LEVEL, "here comes null parameters: %d %d %s", null, 7, "ace");
    log.errorForPreviousInvoker(THIS_METHOD_LEVEL, "here comes null parameters: %d %d %s", 3, null, "ace");
    log.errorForPreviousInvoker(THIS_METHOD_LEVEL, "here comes null parameters: %d %d %s", 3, 7, null);
    return;
  }

  @Test
  public void errorForPreviousInvokerForWrongParameters(){
    final String LOG_FILE_NAME = "testing_errorForPreviousInvoker_for_wrong_parameters.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    final int THIS_METHOD_LEVEL = 0;
    log.errorForPreviousInvoker(THIS_METHOD_LEVEL, "here comes null parameters: %d %d %s", "wrong_parameter", 7, "ace");
  }

  @Test
  public void errorForPreviousInvokerForEmptyStringParameters(){
    final String LOG_FILE_NAME = "testing_errorForPreviousInvoker_for_empty_string_parameters.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    final String EMPTY_STRING = "";
    final int THIS_METHOD_LEVEL = 0;
    log.errorForPreviousInvoker(THIS_METHOD_LEVEL, "here comes null parameters: %d %d %s", 3, 7, EMPTY_STRING);
  }

  @Test
  public void errorForPreviousInvokerForMultipleLines(){
    final String DEBUG_LOG_FILE_NAME = "testing_errorForPreviousInvoker_for_multiple_lines.log";
    TestingLogsToolkit.deleteOldLog(DEBUG_LOG_FILE_NAME);
    Log log = createLog(DEBUG_LOG_FILE_NAME);
    final int THIS_METHOD_LEVEL = 0;
    log.errorForPreviousInvoker(THIS_METHOD_LEVEL, FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    log.errorForPreviousInvoker(THIS_METHOD_LEVEL, FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    log.errorForPreviousInvoker(THIS_METHOD_LEVEL, FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    log.errorForPreviousInvoker(THIS_METHOD_LEVEL, FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    log.errorForPreviousInvoker(THIS_METHOD_LEVEL, FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    return;
  }

  @Test
  public void errorForNullText(){
    final String LOG_FILE_NAME = "testing_error_for_null_text.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    log.error(null);
    return;
  }

  @Test
  public void errorForEmptyText(){
    final String LOG_FILE_NAME = "testing_error_for_empty_text.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    log.error("");
    return;
  }

  @Test
  public void errorForNullParameters(){
    final String LOG_FILE_NAME = "testing_error_for_null_parameters.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    log.error("here comes null parameters: %d %d %s", null, 7, "ace");
    log.error("here comes null parameters: %d %d %s", 3, null, "ace");
    log.error("here comes null parameters: %d %d %s", 3, 7, null);
    return;
  }

  @Test
  public void errorForWrongParameters(){
    final String LOG_FILE_NAME = "testing_error_for_wrong_parameters.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    log.error("here comes null parameters: %d %d %s", "wrong_parameter", 7, "ace");
  }

  @Test
  public void errorForEmptyStringParameters(){
    final String LOG_FILE_NAME = "testing_error_for_empty_string_parameters.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    final String EMPTY_STRING = "";
    log.error("here comes null parameters: %d %d %s", 3, 7, EMPTY_STRING);
  }

  @Test
  public void errorForMultipleLines(){
    final String DEBUG_LOG_FILE_NAME = "testing_error_for_multiple_lines.log";
    TestingLogsToolkit.deleteOldLog(DEBUG_LOG_FILE_NAME);
    Log log = createLog(DEBUG_LOG_FILE_NAME);

    log.error(FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    log.error(FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    log.error(FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    log.error(FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    log.error(FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    return;
  }

  @Test
  public void warningForPreviousInvokerForNullText(){
    final String LOG_FILE_NAME = "testing_warningForPreviousInvoker_for_null_text.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    final int THIS_METHOD_LEVEL = 0;
    log.warningForPreviousInvoker(THIS_METHOD_LEVEL, null);
    return;
  }

  @Test
  public void warningForPreviousInvokerForEmptyText(){
    final String LOG_FILE_NAME = "testing_warningForPreviousInvoker_for_empty_text.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    final int THIS_METHOD_LEVEL = 0;
    log.warningForPreviousInvoker(THIS_METHOD_LEVEL, "");
    return;
  }

  @Test
  public void warningForPreviousInvokerForNullParameters(){
    final String LOG_FILE_NAME = "testing_warningForPreviousInvoker_for_null_parameters.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    final int THIS_METHOD_LEVEL = 0;
    log.warningForPreviousInvoker(THIS_METHOD_LEVEL, "here comes null parameters: %d %d %s", null, 7, "ace");
    log.warningForPreviousInvoker(THIS_METHOD_LEVEL, "here comes null parameters: %d %d %s", 3, null, "ace");
    log.warningForPreviousInvoker(THIS_METHOD_LEVEL, "here comes null parameters: %d %d %s", 3, 7, null);
    return;
  }

  @Test
  public void warningForPreviousInvokerForWrongParameters(){
    final String LOG_FILE_NAME = "testing_warningForPreviousInvoker_for_wrong_parameters.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    final int THIS_METHOD_LEVEL = 0;
    log.warningForPreviousInvoker(THIS_METHOD_LEVEL, "here comes null parameters: %d %d %s", "wrong_parameter", 7, "ace");
  }

  @Test
  public void warningForPreviousInvokerForEmptyStringParameters(){
    final String LOG_FILE_NAME = "testing_warningForPreviousInvoker_for_empty_string_parameters.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    final String EMPTY_STRING = "";
    final int THIS_METHOD_LEVEL = 0;
    log.warningForPreviousInvoker(THIS_METHOD_LEVEL, "here comes null parameters: %d %d %s", 3, 7, EMPTY_STRING);
  }

  @Test
  public void warningForPreviousInvokerForMultipleLines(){
    final String DEBUG_LOG_FILE_NAME = "testing_warningForPreviousInvoker_for_multiple_lines.log";
    TestingLogsToolkit.deleteOldLog(DEBUG_LOG_FILE_NAME);
    Log log = createLog(DEBUG_LOG_FILE_NAME);
    final int THIS_METHOD_LEVEL = 0;
    log.warningForPreviousInvoker(THIS_METHOD_LEVEL, FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    log.warningForPreviousInvoker(THIS_METHOD_LEVEL, FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    log.warningForPreviousInvoker(THIS_METHOD_LEVEL, FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    log.warningForPreviousInvoker(THIS_METHOD_LEVEL, FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    log.warningForPreviousInvoker(THIS_METHOD_LEVEL, FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    return;
  }

  @Test
  public void warningForNullText(){
    final String LOG_FILE_NAME = "testing_warning_for_null_text.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    log.warning(null);
    return;
  }

  @Test
  public void warningForEmptyText(){
    final String LOG_FILE_NAME = "testing_warning_for_empty_text.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    log.warning("");
    return;
  }

  @Test
  public void warningForNullParameters(){
    final String LOG_FILE_NAME = "testing_warning_for_null_parameters.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    log.warning("here comes null parameters: %d %d %s", null, 7, "ace");
    log.warning("here comes null parameters: %d %d %s", 3, null, "ace");
    log.warning("here comes null parameters: %d %d %s", 3, 7, null);
    return;
  }

  @Test
  public void warningForWrongParameters(){
    final String LOG_FILE_NAME = "testing_warning_for_wrong_parameters.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    log.warning("here comes null parameters: %d %d %s", "wrong_parameter", 7, "ace");
  }

  @Test
  public void warningForEmptyStringParameters(){
    final String LOG_FILE_NAME = "testing_warning_for_empty_string_parameters.log";
    TestingLogsToolkit.deleteOldLog(LOG_FILE_NAME);
    Log log = createLog(LOG_FILE_NAME);
    final String EMPTY_STRING = "";
    log.warning("here comes null parameters: %d %d %s", 3, 7, EMPTY_STRING);
  }

  @Test
  public void warningForMultipleLines(){
    final String DEBUG_LOG_FILE_NAME = "testing_warning_for_multiple_lines.log";
    TestingLogsToolkit.deleteOldLog(DEBUG_LOG_FILE_NAME);
    Log log = createLog(DEBUG_LOG_FILE_NAME);

    log.warning(FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    log.warning(FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    log.warning(FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    log.warning(FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    log.warning(FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);
    return;
  }

  @Test
  public void debug()
  throws Exception{
    final String DEBUG_LOG_FILE_NAME = "testing_debug.log";
    TestingLogsToolkit.deleteOldLog(DEBUG_LOG_FILE_NAME);
    Log log = createLog(DEBUG_LOG_FILE_NAME);

    log.debug(FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);

    String logRecord = readLog(DEBUG_LOG_FILE_NAME);
    checkDebugRecordLength(logRecord);
    checkDebugMarker(logRecord);
    checkDiagnostics(logRecord);

    log.close();
    return;
  }

  @Test
  public void error()
    throws Exception{
    final String ERROR_LOG_FILE_NAME = "testing_error.log";
    TestingLogsToolkit.deleteOldLog(ERROR_LOG_FILE_NAME);
    Log log = createLog(ERROR_LOG_FILE_NAME);

    log.error(FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);

    String logRecord = readLog(ERROR_LOG_FILE_NAME);
    checkErrorRecordLength(logRecord);
    checkErrorMarker(logRecord);
    checkDiagnostics(logRecord);

    log.close();
    return;
  }

  @Test
  public void warning()
    throws Exception{
    final String WARNING_LOG_FILE_NAME = "testing_warning.log";
    TestingLogsToolkit.deleteOldLog(WARNING_LOG_FILE_NAME);
    Log log = createLog(WARNING_LOG_FILE_NAME);

    log.warning(FORMATTED_TEXT, DECIMAL, STRING, HEXADECIMAL);

    String logRecord = readLog(WARNING_LOG_FILE_NAME);
    checkWarningRecordLength(logRecord);
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
    //[invoker has such format: [<className>]<methodName>():
    // <className> for our case is #LogUnitTest
    // <methodName> for our case can be #debug, #error or #warning.
    // so invoker will be not bigger, than: [LogUnitTest]warning():]
    final int RECORD_INVOKER_LENGTH = 23;
    final int BUFFER_SIZE = RECORD_TIME_LENGTH
                          + RECORD_MARKER_LENGTH
                          + RECORD_BODY_LENGTH
                          + RECORD_TAIL_LENGTH
                          + RECORD_INVOKER_LENGTH
                          + ADDITIONAL_CHARS_QUANTITY;
    char[] logAsCharSequence = new char[BUFFER_SIZE];

    int logSize = logReader.read(logAsCharSequence);

    final int BEGIN_INDEX = 0;
    String logAsText = String.valueOf(logAsCharSequence, BEGIN_INDEX, logSize);
    return logAsText;
  }

  private void checkDebugRecordLength(String record){
    int actualLength = record.length();
    int expectedLength = RECORD_TIME_LENGTH
      + RECORD_MARKER_LENGTH
      + "[LogUnitTest]debug():".length()
      + RECORD_BODY_LENGTH
      + RECORD_TAIL_LENGTH;
    boolean isCorrectLength = (actualLength == expectedLength);
    assertTrue(isCorrectLength);
    return;
  }

  private void checkErrorRecordLength(String record){
    int actualLength = record.length();
    int expectedLength = RECORD_TIME_LENGTH
      + RECORD_MARKER_LENGTH
      + "[LogUnitTest]error():".length()
      + RECORD_BODY_LENGTH
      + RECORD_TAIL_LENGTH;
    boolean isCorrectLength = (actualLength == expectedLength);
    assertTrue(isCorrectLength);
    return;
  }

  private void checkWarningRecordLength(String record){
    int actualLength = record.length();
    int expectedLength = RECORD_TIME_LENGTH
      + RECORD_MARKER_LENGTH
      + "[LogUnitTest]warning():".length()
      + RECORD_BODY_LENGTH
      + RECORD_TAIL_LENGTH;
    boolean isCorrectLength = (actualLength == expectedLength);
    assertTrue(isCorrectLength);
    return;
  }

  private void checkDebugMarker(String record){
    int MARKER_BEGIN_INDEX = RECORD_TIME_LENGTH;
    int MARKER_END_INDEX = MARKER_BEGIN_INDEX + RECORD_MARKER_LENGTH;
    String  recordMarker = record.substring(MARKER_BEGIN_INDEX, MARKER_END_INDEX);
    boolean isMarkerCorrect = recordMarker.equals("[dbg]");
    assertTrue("debug marker is incorrect", isMarkerCorrect);
    return;
  }

  private void checkErrorMarker(String record){
    int MARKER_BEGIN_INDEX = RECORD_TIME_LENGTH;
    int MARKER_END_INDEX = MARKER_BEGIN_INDEX + RECORD_MARKER_LENGTH;
    String  recordMarker = record.substring(MARKER_BEGIN_INDEX, MARKER_END_INDEX);
    boolean isMarkerCorrect = recordMarker.equals("[err]");
    assertTrue("error marker is incorrect", isMarkerCorrect);
    return;
  }

  private void checkWarningMarker(String record){
    int MARKER_BEGIN_INDEX = RECORD_TIME_LENGTH;
    int MARKER_END_INDEX = MARKER_BEGIN_INDEX + RECORD_MARKER_LENGTH;
    String  recordMarker = record.substring(MARKER_BEGIN_INDEX, MARKER_END_INDEX);
    boolean isMarkerCorrect = recordMarker.equals("[wrn]");
    assertTrue("error marker is incorrect", isMarkerCorrect);
    return;
  }

  private void checkDiagnostics(String record){
    String  expectedDiagnostics  = EXPECTED_RECORD_BODY + RECORD_TAIL_FORMAT;
    final int RECORD_BODY_BEGIN_INDEX = record.length() - expectedDiagnostics.length() - 1;//[-1 because excluding carriage return (CR) character];
    final int RECORD_BODY_END_INDEX = record.length() - 1;//[-1 because excluding carriage return (CR) character]
    String  logDiagnostics = record.substring(RECORD_BODY_BEGIN_INDEX, RECORD_BODY_END_INDEX);
    boolean isDiagnosticsCorrect = logDiagnostics.equals(expectedDiagnostics);
    assertTrue("diagnostics is incorrect", isDiagnosticsCorrect);
    return;
  }
}