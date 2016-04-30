package org.daboodb.daboo.shared;

import com.google.common.base.Preconditions;
import org.daboodb.daboo.client.ClientSettings;
import org.daboodb.daboo.client.exceptions.RuntimeSerializationException;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

/**
 * A basic document implementation
 */
public class DirectDocument implements Document {

  private volatile static ContentsPipe contentsPipe;

  private static ContentsPipe getContentsPipe() {
    if (contentsPipe == null) {
      contentsPipe = ClientSettings.getInstance().getContentsPipe();
    }
    return contentsPipe;
  }

  private byte[] contents;

  private int instanceVersion;

  private short schemaVersion = 0;

  private byte[] key;

  /**
   * The kind of document represented by the serialized
   */
  private String contentType;

  private String contentClass;

  public DirectDocument(DocumentContents contents) {
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
  DirectDocument() {}

  /**
   * Returns a new instance of StandardDocument created from the given instance, with all elements the same as in other,
   * except that the instance version is incremented
   *
   * @throws NullPointerException if the given document is null
   */
  public DirectDocument(@Nonnull Document other) {

    Preconditions.checkNotNull(other);

    this.contents = other.serialized();
    this.instanceVersion = other.instanceVersion();
    this.contentType = other.contentType();
    this.key = other.key();
    this.contentClass = other.getContentClass();
  }

  @Override
  public byte[] key() {
    return key;
  }

  @Override
  public byte[] serialized() {
    return contents;
  }

  @Override
  public String contentType() {
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
  public int instanceVersion() {
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
  public void setSchemaVersion(short schemaVersion) {
    this.schemaVersion = schemaVersion;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("StandardDocument{");
    sb.append("serialized=").append(Arrays.toString(contents));
    sb.append(", instanceVersion=").append(instanceVersion);
    sb.append(", schemaVersion=").append(schemaVersion);
    sb.append(", contentType='").append(contentType).append('\'');
    sb.append('}');
    return sb.toString();
  }

  public DocumentContents documentContents() {
    DocumentContents documentContents;
    try {
      documentContents = getContentsPipe().bytesToContents(Class.forName(contentClass), contents);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      //TODO(lwhite): Log
      String msg = "Unable to deserialize JSON serialized because content class could not be found";
      throw new RuntimeSerializationException(msg, e);
    }
    return documentContents;
  }
}
