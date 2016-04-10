package org.dabudb.dabu.shared.msg;

import org.dabudb.dabu.shared.compression.CompressionType;
import org.dabudb.dabu.shared.compression.CompressorDeCompressor;
import org.dabudb.dabu.shared.compression.CompressorFactory;
import org.dabudb.dabu.shared.encryption.EncryptorDecryptor;
import org.dabudb.dabu.shared.encryption.EncryptorFactory;
import org.dabudb.dabu.shared.msg.serialization.MessageSerializerFactory;
import org.dabudb.dabu.shared.msg.serialization.MessageSerializerDeserializer;
import org.dabudb.dabu.shared.msg.serialization.MessageSerializerType;

import java.util.Objects;

/**
 * A pipeline for transforming messages, where transforming means converting the reference object to a byte array.
 *
 * The message can also be compressed and/or encrypted depending on the transformation type.
 */
public class MessagePipe {

  private EncryptorDecryptor encryptorDecryptor;
  private CompressorDeCompressor compressorDeCompressor;
  private MessageSerializerDeserializer serializerDeserializer;

  /**
   * Creates a messagePipe with no encryption, and the other filters as defined
   */
  public static MessagePipe create(CompressionType compressionType, MessageSerializerType serializerType) {
    CompressorDeCompressor compressor = CompressorFactory.get(compressionType);
    EncryptorDecryptor encryptor = EncryptorFactory.NONE;
    MessageSerializerDeserializer serializer = MessageSerializerFactory.get(serializerType);

    return new MessagePipe(
        compressor,
        encryptor,
        serializer);
  }


  public MessagePipe(
      CompressorDeCompressor compressorDeCompressor,
      EncryptorDecryptor encryptorDecryptor,
      MessageSerializerDeserializer serializerDeserializer) {
    this.encryptorDecryptor = encryptorDecryptor;
    this.compressorDeCompressor = compressorDeCompressor;
    this.serializerDeserializer = serializerDeserializer;
  }

  public byte[] messageToBytes(Message message) {
    return
        encryptorDecryptor.encrypt(
            compressorDeCompressor.compress(
                serializerDeserializer.serialize(message)));
  }

  public Message bytesToMessage(Class messageClass, byte[] contentAsBytes) {
    return
        serializerDeserializer.deserialize(
            messageClass,
            compressorDeCompressor.decompress(
                encryptorDecryptor.decrypt(contentAsBytes)));
  }

  public EncryptorDecryptor getEncryptorDecryptor() {
    return encryptorDecryptor;
  }

  public CompressorDeCompressor getCompressorDeCompressor() {
    return compressorDeCompressor;
  }

  public MessageSerializerDeserializer getSerializerDeserializer() {
    return serializerDeserializer;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MessagePipe that = (MessagePipe) o;
    return Objects.equals(encryptorDecryptor, that.encryptorDecryptor) &&
        Objects.equals(compressorDeCompressor, that.compressorDeCompressor) &&
        Objects.equals(serializerDeserializer, that.serializerDeserializer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(encryptorDecryptor, compressorDeCompressor, serializerDeserializer);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("MessagePipe{");
    sb.append("encryptorDecryptor=").append(encryptorDecryptor);
    sb.append(", compressorDeCompressor=").append(compressorDeCompressor);
    sb.append(", serializerDeserializer=").append(serializerDeserializer);
    sb.append('}');
    return sb.toString();
  }
}
