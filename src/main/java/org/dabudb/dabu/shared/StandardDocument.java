package org.dabudb.dabu.shared;

import org.dabudb.dabu.client.Settings;
import org.dabudb.dabu.shared.serialization.DocumentSerializer;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

/**
 * A basic document implementation
 */
public class StandardDocument implements Document {

  private volatile static ContentsPipe contentsPipe;

  private volatile static DocumentSerializer documentSerializer;

  private static DocumentSerializer getDocumentSerializer() {
    if (documentSerializer == null) {
      documentSerializer = Settings.getInstance().getDocumentSerializer();
    }
    return documentSerializer;
  }

  private static ContentsPipe getContentsPipe() {
    if (contentsPipe == null) {
      contentsPipe = Settings.getInstance().getContentsPipe();
    }
    return contentsPipe;
  }

  private byte[] contents;

  private int instanceVersion;

  private short schemaVersion = 0;

  private byte[] key;

  /** The kind of document represented by the contents */
  private String contentType;

  private String contentClass;

  public StandardDocument(DocumentContents contents) {
    this.contents = getContentsPipe().contentsToBytes(contents);
    this.instanceVersion = 0;
    this.contentType = contents.getContentType();
    if (contents.getKey() == null) {
      key = UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);
    } else {
      key = contents.getKey();
    }
    this.contentClass = contents.getClass().getCanonicalName();
  }

  /**
   * visible for serialization only.
   */
  StandardDocument() {}

  public StandardDocument(Document other) {
    this.contents = other.getContents();
    synchronized(this) {
      this.instanceVersion = other.documentVersion() + 1;
    }
    this.contentType = other.getContentType();
    this.key = other.key();
    this.contentClass = other.getContentClass();
  }

  @Override
  public byte[] key() {
    return key;
  }

  @Override
  public byte[] getContents() {
    return contents;
  }

  @Override
  public String getContentType() {
    return contentType;
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
  public int documentVersion() {
    return instanceVersion;
  }

  @Override
  public void setContents(byte[] contents) {
    this.contents = contents;
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
  public int getInstanceVersion() {
    return instanceVersion;
  }

  @Override
  public short getSchemaVersion() {
    return schemaVersion;
  }

  @Override
  public void setSchemaVersion(short schemaVersion) {
    this.schemaVersion = schemaVersion;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("StandardDocument{");
    sb.append("contents=").append(Arrays.toString(contents));
    sb.append(", instanceVersion=").append(instanceVersion);
    sb.append(", schemaVersion=").append(schemaVersion);
    sb.append(", contentType='").append(contentType).append('\'');
    sb.append('}');
    return sb.toString();
  }

  public DocumentContents documentContents() {
    DocumentContents documentContents = null;
    try {
      documentContents = getContentsPipe().bytesToContents(Class.forName(contentClass), contents);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return documentContents;
  }

  @Override
  public byte[] marshall() {
    return getDocumentSerializer().documentToBytes(this);
  }
}
