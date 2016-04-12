package org.dabudb.dabu.server;

import org.dabudb.dabu.server.db.Db;
import org.dabudb.dabu.server.io.WriteAheadLog;
import org.dabudb.dabu.server.io.WriteLog;
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
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;

/**
 *
 */
public class ServerSettings {

  private MessagePipe messagePipe;

  private Class documentClass = StandardDocument.class;

  private DocumentSerializer documentSerializer;

  private Db db;

  private File databaseDirectory = new File(System.getProperty("user.dir"));

  private WriteAheadLog writeAheadLog;

  //private CommServer commServer = new DirectCommServer();
  private CommServer commServer;

  private static ServerSettings ourInstance;

  private ServerSettings() {
  }

  public static ServerSettings getInstance() {
    if (ourInstance == null) {
      try {
        ourInstance = loadServerSettings();
      } catch (IOException e) {
        e.printStackTrace();
        throw new RuntimeException("Unable to load ServerSettings", e);
      }
    }
    return ourInstance;
  }

  ServerSettings(Properties properties) {

    setDocumentClass(properties);
    setDocumentSerializer(properties);
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

  public WriteAheadLog getWriteAheadLog() {
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
      throw new RuntimeException("Unable to load db as specified in server.properties", e);
    }
  }

  private void setWriteAheadLog(Properties properties) {
    String folderName = String.valueOf(properties.getProperty("db.write_ahead_log.folderName"));
    String writeAheadClassName = String.valueOf(properties.getProperty("db.write_ahead_log.class"));
    if (writeAheadClassName.equals(WriteLog.class.getCanonicalName())) {
      this.writeAheadLog = WriteLog.getInstance(Paths.get(folderName).toFile());
    }
  }

  private void setCommServer(Properties properties) {
    try {
      this.commServer =
          (CommServer)
              Class.forName(String.valueOf(properties.getProperty("comm.server.class"))).newInstance();
    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
      e.printStackTrace();
      throw new RuntimeException("Unable to load CommServer as specified in server.properties");
    }
  }

  private void setMessagePipe(Properties properties) {
    CompressionType compressionType = CompressionType.valueOf(properties.getProperty("message.compression"));
    EncryptionType encryptionType = EncryptionType.valueOf(properties.getProperty("message.encryption"));
    String encryptionPwd = String.valueOf(properties.getProperty("message.encryption.pwd"));

    Preconditions.checkState(encryptionType == EncryptionType.NONE || !Strings.isNullOrEmpty(encryptionPwd));

    this.messagePipe = MessagePipe.create(compressionType);
  }

  public File getDatabaseDirectory() {
    return databaseDirectory;
  }

  private static ServerSettings loadServerSettings() throws IOException {
    Properties serverProperties = new Properties();
    InputStream inputStream = new FileInputStream("src/main/resources/server.properties");
    serverProperties.load(inputStream);

    return new ServerSettings(serverProperties);
  }
}
