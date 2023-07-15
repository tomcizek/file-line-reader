package com.example.demo.library.filereader;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileLineReaderTest {
  
  @Test
  void itCanReadLineFromFile() throws Exception {
    FileLineReader reader = new FileLineReader();
    String testFilePath = "src/test/resources/testFile.txt";
    int desiredLine = 3;

    var result = reader.readLineInFile(testFilePath, desiredLine);

    cleanupFileIndex(testFilePath);
    Assertions.assertEquals("orange", result);
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

  @Test
  void itIsAtLeast10TimesFasterSecondTime() throws Exception {
    FileLineReader reader = new FileLineReader();
    String testFilePath = "src/test/resources/testFile.txt";
    int desiredLine = 3;

    long duration1 = measureReadLineInFileDuration(reader, testFilePath, desiredLine);
    long duration2 = measureReadLineInFileDuration(reader, testFilePath, desiredLine);

    cleanupFileIndex(testFilePath);
    Assertions.assertTrue(duration2 * 10 < duration1);
  }

  private long measureReadLineInFileDuration(FileLineReader reader, String filePath, int lineNumber) throws IOException {
    long startTime = System.nanoTime();
    reader.readLineInFile(filePath, lineNumber);
    long endTime = System.nanoTime();
    return endTime - startTime;
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