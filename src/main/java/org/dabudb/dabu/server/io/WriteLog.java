package org.dabudb.dabu.server.io;

import com.google.common.io.ByteSink;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

/**
 * Logs all write requests to a file
 */
public class WriteLog implements WriteAheadLog, Closeable {

  private static WriteLog instance;

  public static WriteLog getInstance(File databaseDirectory) {
    try {
      if (instance == null) {
        instance = new WriteLog(databaseDirectory);
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return instance;
  }

  //TODO(lwhite) handle locking/synchronization for these files
  private final static String DATA_FILE = "dataFile";
  private final static String INDEX_FILE = "indexFile";

  // The length of the current request in bytes
  private int length = -1;

  private BufferedReader indexReader;

  private OutputStream requestOutputByteStream;

  private Writer indexWriter;
  private DataInputStream dataInputStream;

  private File rootFolder;

  private WriteLog(File rootFolder) throws IOException {
    this.rootFolder = rootFolder;
    initializeLog(rootFolder);
  }

  @Override
  public void close()  {

    try {
      requestOutputByteStream.close();
      dataInputStream.close();
      indexWriter.close();
      indexReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void clear() throws IOException {
    close();
    deleteLog();
    initializeLog(rootFolder);
  }

  private void deleteLog() throws IOException {
    java.nio.file.Files.deleteIfExists(getIndexFile(rootFolder).toPath());
    java.nio.file.Files.deleteIfExists(getDataFile(rootFolder).toPath());
  }

  @Override
  public boolean hasNext() {
    try {
      String lengthString = indexReader.readLine();
      if (lengthString != null) {
        length = Integer.parseInt(lengthString);
        return true;
      }
    } catch (IOException ex) {
      //TODO(lwhite): handle exception
    }
    length = -1;
    return false;
  }

  @Override
  public byte[] next() {
    if (length < 0) {
      throw new NoSuchElementException("Reached the end of the log file.");
    }
    byte[] requestBytes = new byte[length];
    try {
      dataInputStream.read(requestBytes);

    } catch (IOException e) {
      e.printStackTrace();
      // TODO(lwhite): Handle exception
    }

    return requestBytes;
  }

  @Override
  public void log(byte[] request) throws IOException {
    requestOutputByteStream.write(request);
    requestOutputByteStream.flush();

    indexWriter.write(String.valueOf(request.length) + "\n");
    indexWriter.flush();
  }

  /**
   * Initializes the log file, creating tools for reading and writing it
   *
   * @throws IOException
   */
  private void initializeLog(File targetDirectory) throws IOException {

    if (!targetDirectory.exists()) {
      targetDirectory.mkdirs();
    }

    File indexFile = getIndexFile(targetDirectory);
    File dataFile = getDataFile(targetDirectory);

    if (!indexFile.exists()) {
      indexFile.createNewFile();
    }
    if (!dataFile.exists()) {
      dataFile.createNewFile();
    }

    FileInputStream dataFis = new FileInputStream(dataFile);
    dataInputStream = new DataInputStream(dataFis);
    FileInputStream indexFis = new FileInputStream(indexFile);
    indexReader = new BufferedReader(new InputStreamReader(indexFis, StandardCharsets.UTF_8));

    ByteSink byteSink = Files.asByteSink(dataFile, FileWriteMode.APPEND);
    requestOutputByteStream = byteSink.openStream();

    CharSink charSink = Files.asCharSink(indexFile, StandardCharsets.UTF_8, FileWriteMode.APPEND);
    indexWriter = charSink.openStream();
  }

  private File getDataFile(File targetDirectory) {
    return Paths.get(targetDirectory + String.valueOf(File.separatorChar) + DATA_FILE).toFile();
  }

  private File getIndexFile(File targetDirectory) {
    return Paths.get(targetDirectory + String.valueOf(File.separatorChar) + INDEX_FILE).toFile();
  }
}
