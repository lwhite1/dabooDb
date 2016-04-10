package com.deathrayresearch.dabu.shared;

import com.deathrayresearch.dabu.shared.compression.CompressionType;
import com.deathrayresearch.dabu.shared.encryption.EncryptionType;
import com.deathrayresearch.dabu.shared.serialization.ContentSerializerType;

import java.util.Objects;

/**
 * Defines a ContentsPipe
 */
public class ContentsPipeDefinition {

  private final ContentSerializerType contentSerializerType;
  private final CompressionType compressionType;
  private final EncryptionType encryptionType;

  public ContentsPipeDefinition(ContentSerializerType contentSerializerType,
                                CompressionType compressionType,
                                EncryptionType encryptionType) {

    this.contentSerializerType = contentSerializerType;
    this.compressionType = compressionType;
    this.encryptionType = encryptionType;
  }

  public ContentSerializerType getContentSerializerType() {
    return contentSerializerType;
  }

  public CompressionType getCompressionType() {
    return compressionType;
  }

  public EncryptionType getEncryptionType() {
    return encryptionType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContentsPipeDefinition that = (ContentsPipeDefinition) o;
    return contentSerializerType == that.contentSerializerType &&
        compressionType == that.compressionType &&
        encryptionType == that.encryptionType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(contentSerializerType, compressionType, encryptionType);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("ContentsPipeDefinition{");
    sb.append("contentSerializerType=").append(contentSerializerType);
    sb.append(", compressionType=").append(compressionType);
    sb.append(", encryptionType=").append(encryptionType);
    sb.append('}');
    return sb.toString();
  }
}
