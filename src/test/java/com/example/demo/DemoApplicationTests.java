package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

  @Test
  void testItThrowsErrorOnInvalidParams() {
    String testFilePath = "src/test/resources/testFile.txt";
    String[] args = {testFilePath};

    Exception exception = assertThrows(RuntimeException.class, () -> {
      DemoApplication.main(args);
    });
    String expectedMessage = "Invalid number of params";
    assertTrue(exception.getMessage().contains(expectedMessage));
  }

  @Test
  void testItThrowsErrorOnInvalidFile() {
    String testFilePath = "src/test/resources/nonExistingFile.txt";
    String[] args = {testFilePath, "2"};

    Exception exception = assertThrows(RuntimeException.class, () -> {
      DemoApplication.main(args);
    });
    String expectedMessage = "Error reading file";
    assertTrue(exception.getMessage().contains(expectedMessage));
  }
  
  @Test
  void testItPrintsOutResult() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));

    String testFilePath = "src/test/resources/testFile.txt";
    String[] args = {testFilePath, "2"};

    DemoApplication.main(args);

    cleanupFileIndex(testFilePath);
    assertEquals("grape" + System.lineSeparator(), outContent.toString());
    assertEquals("", errContent.toString());
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
