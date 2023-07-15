package com.example.demo.library.filereader;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileLineReader {
  public String readLineInFile(String filePath, int lineNumber) throws IOException {
    File indexFile = new File(filePath + ".idx");

    if (!indexFile.exists()) {
      createIndexFile(filePath, indexFile);
    }

    // Read the byte offset from the index file
    long byteOffset;
    try (RandomAccessFile raf = new RandomAccessFile(indexFile, "r")) {
      raf.seek(lineNumber * Long.BYTES);
      byteOffset = raf.readLong();
    }

    try (RandomAccessFile raf = new RandomAccessFile(filePath, "r")) {
      raf.seek(byteOffset);
      return raf.readLine();
    }
  }

  private void createIndexFile(String filePath, File indexFile) throws IOException {
    try (
        RandomAccessFile inputFile = new RandomAccessFile(filePath, "r");
        RandomAccessFile idxFile = new RandomAccessFile(indexFile, "rw")
    ) {
      String line;
      long prevByteOffset = 0;
      while ((line = inputFile.readLine()) != null) {
        idxFile.writeLong(prevByteOffset);
        prevByteOffset = inputFile.getFilePointer();
      }
    }
  }
}
