package org.daboodb.daboo.shared;

import org.daboodb.daboo.shared.compression.CompressionType;
import org.daboodb.daboo.shared.encryption.EncryptionType;
import org.daboodb.daboo.shared.serialization.ContentSerializerType;

import javax.annotation.concurrent.Immutable;
import java.util.Objects;

/**
 * Defines a ContentsPipe
 */
@Immutable
final class ContentsPipeDefinition {

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
