package org.dabudb.dabu.server;

import org.dabudb.dabu.server.db.Db;
import org.dabudb.dabu.server.db.OffHeapBTreeDb;
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
import java.util.Properties;

/**
 *
 */
public class Settings {

  private MessagePipe messagePipe = MessagePipe.create(CompressionType.SNAPPY);

  private Class documentClass = StandardDocument.class;

  private DocumentSerializer documentSerializer;

  private Db db = new OffHeapBTreeDb();

  private File databaseDirectory = new File(System.getProperty("user.dir"));

  private WriteAheadLog writeAheadLog = WriteLog.getInstance(databaseDirectory);

  private CommServer commServer = new DirectCommServer();

  private static Settings ourInstance;

  private Settings() {
  }

  public static Settings getInstance() {
    if (ourInstance == null) {
      ourInstance = new Settings();
    }
    return ourInstance;
  }

  Settings(Properties properties) {

    setDocumentClass(properties);
    setDocumentSerializer(properties);
    setDb(properties);
    setWriteAheadLog(properties);
    setCommServer(properties);
    setMessagePipe(properties);
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
      throw new RuntimeException("Unable to load DocumentSerializer as specified in server.properties");
    }
  }

  private void setDocumentClass(Properties properties) {
    try {
      this.documentClass = Class.forName(String.valueOf(properties.get("document.class")));
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      throw new RuntimeException("Unable to load document class specified in server.properties");
    }
  }

  private void setDb(Properties properties) {
    try {
      this.db = (Db) Class.forName(String.valueOf(properties.getProperty("db.class"))).newInstance();
    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
      e.printStackTrace();
      throw new RuntimeException("Unable to load db as specified in server.properties");
    }
  }

  private void setWriteAheadLog(Properties properties) {
    try {
      this.writeAheadLog =
          (WriteAheadLog)
              Class.forName(String.valueOf(properties.getProperty("db.write_ahead_log.class"))).newInstance();
    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
      e.printStackTrace();
      throw new RuntimeException("Unable to load WriteAheadLog as specified in server.properties");
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
}
