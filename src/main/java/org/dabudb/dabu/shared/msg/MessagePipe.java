package org.dabudb.dabu.shared.msg;

import com.google.protobuf.InvalidProtocolBufferException;
import org.dabudb.dabu.shared.compression.CompressionType;
import org.dabudb.dabu.shared.compression.CompressorDeCompressor;
import org.dabudb.dabu.shared.compression.CompressorFactory;
import org.dabudb.dabu.shared.encryption.EncryptorDecryptor;
import org.dabudb.dabu.shared.encryption.EncryptorFactory;
import org.dabudb.dabu.shared.msg.serialization.MessageSerializerFactory;
import org.dabudb.dabu.shared.msg.serialization.MessageSerializerDeserializer;
import org.dabudb.dabu.shared.msg.serialization.MessageSerializerType;
import org.dabudb.dabu.shared.protobufs.Request;

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

  public byte[] messageToBytes(Request.WriteRequest message) {
    return
        encryptorDecryptor.encrypt(
            compressorDeCompressor.compress(message.toByteArray()));
  }

  public byte[] messageToBytes(Request.DeleteRequest message) {
    return
        encryptorDecryptor.encrypt(
            compressorDeCompressor.compress(message.toByteArray()));
  }

  public Request.WriteRequest bytesToWriteRequst(byte[] contentAsBytes) {

    try {
      return Request.WriteRequest.parseFrom(
              compressorDeCompressor.decompress(
                  encryptorDecryptor.decrypt(contentAsBytes)));
    } catch (InvalidProtocolBufferException e) {
      e.printStackTrace();
      throw new RuntimeException("PROTOBUF FAIL");
    }
  }

  public Request.DeleteRequest bytesToMessage(byte[] contentAsBytes) {
    try {
      return Request.DeleteRequest.parseFrom(
              compressorDeCompressor.decompress(
                  encryptorDecryptor.decrypt(contentAsBytes)));
    } catch (InvalidProtocolBufferException e) {
      e.printStackTrace();
      throw new RuntimeException("PROTOBUF FAIL");
    }
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
