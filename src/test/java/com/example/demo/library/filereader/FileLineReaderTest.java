package com.example.demo.library.filereader;

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
  }
}