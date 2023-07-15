package com.example.demo.library.filereader;

import java.io.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileLineReaderTest {
  
  @Test
  void itCanReadLineFromFile() throws Exception {
    FileLineReader reader = new FileLineReader();
    String testFilePath = "src/test/resources/testFile.txt";
    int desiredLine = 3;

    var result = reader.readLineInFile(testFilePath, desiredLine);

    Assertions.assertEquals("orange", result);
    
    cleanupFileIndex(testFilePath);
  }
  
  @Test
  void itCreatesIndexFileAfterFirstRead() throws Exception {
    FileLineReader reader = new FileLineReader();
    String testFilePath = "src/test/resources/testFile.txt";
    String indexFilePath = getIndexPath(testFilePath);
    int desiredLine = 3;

    reader.readLineInFile(testFilePath, desiredLine);

    File indexFile = new File(indexFilePath);
    
    Assertions.assertTrue(indexFile.exists());
    cleanupFileIndex(testFilePath);
  }

  private static void cleanupFileIndex(String indexFilePath) {
    File indexFile = new File(getIndexPath(indexFilePath));
    if (indexFile.exists()) {
      indexFile.delete();
    }
  }

  private static String getIndexPath(String indexFilePath) {
    return indexFilePath + ".idx";
  }
}