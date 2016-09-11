//[author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)]
//[date: 30.08.2016]
package ru.sugarbaron_bicycles.library.log;

import java.io.File;

final class TestingLogsToolkit{

  static void deleteOldLog(String fileName){
    File oldFile = new File(LogUnitTest.LOGS_DIRECTORY, fileName);
    boolean isFileExist = oldFile.exists();
    if(isFileExist == false){
      return;
    }
    boolean isOk = oldFile.delete();
    if(isOk == false){
      Dbg.out("error deleting old log file");
    }
    return;
  }
}