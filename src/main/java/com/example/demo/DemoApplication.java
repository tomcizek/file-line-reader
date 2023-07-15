package com.example.demo;

import com.example.demo.library.filereader.FileLineReader;
import java.io.IOException;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    if (args.length != 2) {
      throw new RuntimeException("Invalid number of params. There should be exactly two: <file> <line_number>");
    }

    String filePath = args[0];
    int lineNumber = Integer.parseInt(args[1]);
    FileLineReader reader = new FileLineReader();

    try {
      String line = reader.readLineInFile(filePath, lineNumber);
      System.out.println(line);
    } catch (IOException e) {

      throw new RuntimeException("Error reading file: " + e.getMessage());
    }
  }
}