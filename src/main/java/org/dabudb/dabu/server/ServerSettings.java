package org.dabudb.dabu.server;

import org.dabudb.dabu.server.db.Db;
import org.dabudb.dabu.server.io.NullLog;
import org.dabudb.dabu.server.io.WriteAheadLog;
import org.dabudb.dabu.server.io.WriteLog;
import org.dabudb.dabu.shared.exceptions.StartupException;
import org.dabudb.dabu.shared.serialization.DocumentSerializer;
import org.dabudb.dabu.shared.StandardDocument;
import org.dabudb.dabu.shared.compression.CompressionType;
import org.dabudb.dabu.shared.encryption.EncryptionType;
import org.dabudb.dabu.shared.msg.MessagePipe;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Configuratin settings for a Dabu server, or any Dabu server running in embedded mode
 */
public class ServerSettings {

  private MessagePipe messagePipe;

  private Class documentClass = StandardDocument.class;

  private DocumentSerializer documentSerializer;

  private Db db;

  private String databaseDirectory;

  private WriteAheadLog writeAheadLog;

  //private CommServer commServer = new DirectCommServer();
  private CommServer commServer;

  private static ServerSettings ourInstance;

  public static ServerSettings getInstance() {
    if (ourInstance == null) {
      ourInstance = loadServerSettings();
    }
    return ourInstance;
  }

  /**
   * Returns ServerSettings initialized from the given Properties object
   */
  ServerSettings(Properties properties) {

    setDocumentClass(properties);
    setDocumentSerializer(properties);
    setDatabaseDirectory(properties);
    setDb(properties);
    setWriteAheadLog(properties);
    setCommServer(properties);
    setMessagePipe(properties);

    ourInstance = this;
  }

  public MessagePipe getMessagePipe() {
    return messagePipe;
  }

  public Class getDocumentClass() {
    return documentClass;
  }

  public DocumentSerializer getDocumentSerializer() {
    return documentSerializer;
  }

  public Db getDb() {
    return db;
  }

  WriteAheadLog getWriteAheadLog() {
    return writeAheadLog;
  }

  public CommServer getCommServer() {
    return commServer;
  }

  private void setDocumentSerializer(Properties properties) {
    try {
      this.documentSerializer =
          (DocumentSerializer)
              Class.forName(String.valueOf(properties.getProperty("document.serializer.class"))).newInstance();
    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
      e.printStackTrace();
      throw new RuntimeException("Unable to load DocumentSerializer as specified in server.properties", e);
    }
  }

  private void setDocumentClass(Properties properties) {
    try {
      this.documentClass = Class.forName(String.valueOf(properties.get("document.class")));
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      throw new RuntimeException("Unable to load document class specified in server.properties", e);
    }
  }

  private void setDb(Properties properties) {
    try {
      this.db = (Db) Class.forName(String.valueOf(properties.getProperty("db.class"))).newInstance();
    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
      e.printStackTrace();
      throw new StartupException("Unable to load db as specified in server.properties", e);
    }
  }

  private void setWriteAheadLog(Properties properties) {

    String folderName = String.valueOf(properties.getProperty("db.write_ahead_log.folderName"));

    String writeAheadClassName = String.valueOf(properties.getProperty("db.write_ahead_log.class"));

    Path logFolderPath = Paths.get(getDatabaseDirectory(), folderName);
    File logFolder = logFolderPath.toFile();
    if (writeAheadClassName.equals(WriteLog.class.getCanonicalName())) {
      this.writeAheadLog = WriteLog.getInstance(logFolder);
    } else if (writeAheadClassName.equals(NullLog.class.getCanonicalName())) {
      this.writeAheadLog = NullLog.getInstance(logFolder);
    } else {
      throw new StartupException("Write ahead log not configured.");
    }
  }

  private void setCommServer(Properties properties) {
    try {
      this.commServer =
          (CommServer)
              Class.forName(String.valueOf(properties.getProperty("comm.server.class"))).newInstance();
    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
      e.printStackTrace();
      throw new StartupException("Unable to load CommServer as specified in server.properties", e);
    }
  }

  private void setMessagePipe(Properties properties) {
    CompressionType compressionType = CompressionType.valueOf(properties.getProperty("message.compression"));
    EncryptionType encryptionType = EncryptionType.valueOf(properties.getProperty("message.encryption"));
    String encryptionPwd = String.valueOf(properties.getProperty("message.encryption.pwd"));

    try {
      Preconditions.checkState(encryptionType == EncryptionType.NONE || !Strings.isNullOrEmpty(encryptionPwd));
    } catch (IllegalStateException e) {
      e.printStackTrace();
      throw new StartupException("Unable to load MessagePipe as specified in server.properties", e);
    }

    this.messagePipe = MessagePipe.create(compressionType);
  }

  private void setDatabaseDirectory(Properties properties) {
    databaseDirectory = String.valueOf(properties.getProperty("db.folderName"));
  }


  public String getDatabaseDirectory() {
    return databaseDirectory;
  }

  private static ServerSettings loadServerSettings() {
    Properties serverProperties = new Properties();
    try {
      FileInputStream inputStream = new FileInputStream("src/main/resources/server.properties");
      serverProperties.load(inputStream);
      inputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
      throw new StartupException("Unable to load server settings as Java properties", e);
    }
    return new ServerSettings(serverProperties);
  }
}
