package org.daboodb.daboo.shared;

import org.daboodb.daboo.client.ClientSettings;
import org.daboodb.daboo.server.ServerSettings;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * A basic document implementation
 */
public class AbstractDocument implements Document {

  private volatile static ContentsPipe contentsPipe;

  private static ContentsPipe getContentsPipe() {
    if (contentsPipe == null) {
      contentsPipe = ClientSettings.getInstance().getContentsPipe();
    }
    return contentsPipe;
  }

  private int instanceVersion;

  private short schemaVersion = 0;

  private byte[] key;

  /**
   * The kind of document represented by the serialized
   */
  private String contentType;

  private String contentClass;

  public AbstractDocument() {
    this.contentClass = this.getClass().getCanonicalName();
    this.contentType = this.getClass().getSimpleName();
    this.instanceVersion = 0;
    key = UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public byte[] serialized() {
    return ServerSettings.getInstance().getDocumentSerializer().documentToBytes(this);
  }

  @Override
  public String getContentClass() {
    return contentClass;
  }

  @Override
  public int schemaVersion() {
    return schemaVersion;
  }

  @Override
  public int instanceVersion() {
    return instanceVersion;
  }

  public void setInstanceVersion(int instanceVersion) {
    this.instanceVersion = instanceVersion;
  }

  @Override
  public void setKey(byte[] key) {
    this.key = key;
  }

  @Override
  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  @Override
  public void setContentClass(String contentClass) {
    this.contentClass = contentClass;
  }

  @Override
  public void setSchemaVersion(short schemaVersion) {
    this.schemaVersion = schemaVersion;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("AbstractDocument{");
    sb.append("instanceVersion=").append(instanceVersion);
    sb.append(", schemaVersion=").append(schemaVersion);
    sb.append(", contentClass=").append(contentClass);
    sb.append(", contentType='").append(contentType).append('\'');
    sb.append('}');
    return sb.toString();
  }

  /**
   * Returns the key to be used as the primary database key for this object
   */
  @Override
  public byte[] getKey() {
    return key;
  }

  /**
   * Returns a string, analogous to a database table name, that describes what kind of object this is
   */
  @Override
  public String getContentType() {
    return contentType;
  }
}
