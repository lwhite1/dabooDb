package org.daboodb.daboo.server.io;

import com.google.common.io.ByteSink;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.google.protobuf.InvalidProtocolBufferException;
import org.daboodb.daboo.shared.exceptions.RuntimeDatastoreException;
import org.daboodb.daboo.generated.protobufs.Request;
import org.daboodb.daboo.shared.exceptions.RuntimePersistenceException;
import org.daboodb.daboo.shared.util.logging.LoggerWriter;
import org.daboodb.daboo.shared.util.logging.LoggingFactory;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.daboodb.daboo.generated.protobufs.Request.Document.parseFrom;

/**
 * Logs all write requests to a file
 */
public class DatabaseExporter implements Iterator<Request.Document>, Closeable {

  private static LoggerWriter loggerWriter = LoggingFactory.getLogger(DatabaseExporter.class);

  public static DatabaseExporter get(File databaseDirectory) {
    DatabaseExporter exporter = null;
    try {
      exporter = new DatabaseExporter(databaseDirectory);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return exporter;
  }

  //TODO(lwhite) handle locking/synchronization for these files
  public final static String FOLDER = "db_export";
  private final static String DATA_FILE = "dataFile";
  private final static String INDEX_FILE = "indexFile";

  // The length of the current request in bytes
  private int length = -1;

  private BufferedReader indexReader;

  private OutputStream requestOutputByteStream;

  private Writer indexWriter;
  private DataInputStream dataInputStream;

  private DatabaseExporter(File rootFolder) throws IOException {
    initializeLog(rootFolder);
  }

  public void logDocument(Request.Document document) throws IOException {
    log(document.toByteArray());
  }

  @Override
  public void close() {
    try {
      requestOutputByteStream.close();
      dataInputStream.close();
      indexWriter.close();
      indexReader.close();
    } catch (IOException e) {
      loggerWriter.logError("An IOException occurred closing exporter log files.", e);
      throw new RuntimePersistenceException("An IOException occurred closing exporter log files.", e);
    }

  }

  @Override
  public boolean hasNext() {
    try {
      String lengthString = indexReader.readLine();
      if (lengthString != null) {
        length = Integer.parseInt(lengthString);
        return true;
      }
    } catch (IOException e) {
      loggerWriter.logError("An IOException occurred reading index file for export log.", e);
      throw new RuntimePersistenceException("An IOException occurred reading index file for export log.", e);
    }
    length = -1;
    return false;
  }

  @Override
  public Request.Document next() {
    if (length < 0) {
      throw new NoSuchElementException("Reached the end of the log file.");
    }
    byte[] requestBytes = new byte[length];
    try {
      dataInputStream.read(requestBytes);
    } catch (IOException e) {
      e.printStackTrace();
      loggerWriter.logError("An IOException occurred reading requests from document log.", e);
      throw new RuntimePersistenceException("An IOException occurred reading requests from document log.", e);
    }
    Request.Document document;
    try {
      document = parseFrom(requestBytes);
    } catch (InvalidProtocolBufferException e) {
      e.printStackTrace();
      loggerWriter.logError("Failed to parse protobuf document from export file", e);
      throw new RuntimeDatastoreException("Failed to parse protobuf document from export file", e);
    }
    return document;
  }

  public void log(byte[] document) throws IOException {
    requestOutputByteStream.write(document);
    requestOutputByteStream.flush();

    indexWriter.write(String.valueOf(document.length) + "\n");
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

    File indexFile = Paths.get(targetDirectory + String.valueOf(File.separatorChar) + INDEX_FILE).toFile();
    File dataFile = Paths.get(targetDirectory + String.valueOf(File.separatorChar) + DATA_FILE).toFile();

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
}
