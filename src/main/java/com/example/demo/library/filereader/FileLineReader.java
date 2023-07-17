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

    long byteOffset = getByteOffset(lineNumber, indexFile);

    return getLineByOffset(filePath, byteOffset);
  }

  private static long getByteOffset(int lineNumber, File indexFile) throws IOException {
    long byteOffset;
    try (RandomAccessFile raf = new RandomAccessFile(indexFile, "r")) {
      raf.seek(lineNumber * Long.BYTES);
      byteOffset = raf.readLong();
    }
    return byteOffset;
  }

  private static String getLineByOffset(String filePath, long byteOffset) throws IOException {
    try (RandomAccessFile raf = new RandomAccessFile(filePath, "r")) {
      raf.seek(byteOffset);
      return raf.readLine();
    }
  }

  private static void createIndexFile(String filePath, File indexFile) throws IOException {
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
