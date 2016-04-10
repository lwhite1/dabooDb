package org.dabudb.dabu.client;

import org.dabudb.dabu.shared.ContentsPipe;
import org.dabudb.dabu.shared.DocumentJsonSerializer;
import org.dabudb.dabu.shared.DocumentSerializer;
import org.dabudb.dabu.shared.StandardDocument;
import org.dabudb.dabu.shared.compression.CompressionType;
import org.dabudb.dabu.shared.encryption.EncryptionType;
import org.dabudb.dabu.shared.serialization.ContentSerializerType;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.util.Properties;

/**
 *
 */
public class Settings {

  private ContentsPipe contentsPipe = ContentsPipe.create(CompressionType.SNAPPY, ContentSerializerType.JSON);

  private Class documentClass = StandardDocument.class;

  private DocumentSerializer documentSerializer = new DocumentJsonSerializer();

  private CommClient commClient = new DirectCommClient();

  private static Settings ourInstance;

  public Settings() {}

  public static Settings getInstance() {
    if (ourInstance == null) {
      //throw new RuntimeException("Database settings have not been initialized.");
      ourInstance = new Settings();
    }
    return ourInstance;
  }

  Settings(Properties properties) {

    setDocumentClass(properties);
    setDocumentSerializer(properties);
    setCommClient(properties);
    setContentsPipe(properties);
  }

  public ContentsPipe getContentsPipe() {
    return contentsPipe;
  }

  public Class getDocumentClass() {
    return documentClass;
  }

  public DocumentSerializer getDocumentSerializer() {
    return documentSerializer;
  }

  public CommClient getCommClient() {
    return commClient;
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

  private void setCommClient(Properties properties) {
    try {
      this.commClient =
          (CommClient)
              Class.forName(String.valueOf(properties.getProperty("comm.client.class"))).newInstance();
    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
      e.printStackTrace();
      throw new RuntimeException("Unable to load CommServer as specified in server.properties");
    }
  }

  private void setContentsPipe(Properties properties) {
    CompressionType compressionType = CompressionType.valueOf(properties.getProperty("document.content.compression"));
    ContentSerializerType serializerType =
        ContentSerializerType.valueOf(properties.getProperty("document.content.serialization"));
    EncryptionType encryptionType = EncryptionType.valueOf(properties.getProperty("document.content.encryption"));
    String encryptionPwd = String.valueOf(properties.getProperty("document.content.encryption.pwd"));

    Preconditions.checkState(encryptionType == EncryptionType.NONE || !Strings.isNullOrEmpty(encryptionPwd));

    this.contentsPipe = ContentsPipe.create(compressionType, serializerType);
  }
}
