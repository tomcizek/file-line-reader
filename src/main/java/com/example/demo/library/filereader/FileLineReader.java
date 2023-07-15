package com.example.demo.library.filereader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileLineReader {
  public String readLineInFile(String filePath, int lineNumber) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line = null;
      int currentLineNumber = 0;
      while ((line = reader.readLine()) != null) {
        if (currentLineNumber == lineNumber) {
          return line;
        }
        currentLineNumber++;
      }
    }
    throw new IllegalArgumentException("Line number is greater than the number of lines in the file");
  }
}
