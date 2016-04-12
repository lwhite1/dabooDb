package org.dabudb.dabu.shared.msg;

import com.google.protobuf.InvalidProtocolBufferException;
import org.dabudb.dabu.shared.compression.CompressionType;
import org.dabudb.dabu.shared.compression.CompressorDeCompressor;
import org.dabudb.dabu.shared.compression.CompressorFactory;
import org.dabudb.dabu.shared.encryption.EncryptorDecryptor;
import org.dabudb.dabu.shared.encryption.EncryptorFactory;
import org.dabudb.dabu.shared.protobufs.Request;

import java.util.Objects;

/**
 * A pipeline for transforming messages, where transforming means converting the reference object to a byte array.
 *
 * The message can also be compressed and/or encrypted depending on the transformation type.
 */
public class MessagePipe {

  private final EncryptorDecryptor encryptorDecryptor;
  private final CompressorDeCompressor compressorDeCompressor;

  /**
   * Creates a messagePipe with no encryption, and the other filters as defined
   */
  public static MessagePipe create(CompressionType compressionType) {
    CompressorDeCompressor compressor = CompressorFactory.get(compressionType);
    EncryptorDecryptor encryptor = EncryptorFactory.NONE;

    return new MessagePipe(compressor, encryptor);
  }


  public MessagePipe(
      CompressorDeCompressor compressorDeCompressor,
      EncryptorDecryptor encryptorDecryptor) {
    this.encryptorDecryptor = encryptorDecryptor;
    this.compressorDeCompressor = compressorDeCompressor;
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

  public Request.WriteRequest bytesToWriteRequest(byte[] contentAsBytes) {

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MessagePipe that = (MessagePipe) o;
    return Objects.equals(encryptorDecryptor, that.encryptorDecryptor) &&
        Objects.equals(compressorDeCompressor, that.compressorDeCompressor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(encryptorDecryptor, compressorDeCompressor);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("MessagePipe{");
    sb.append("encryptorDecryptor=").append(encryptorDecryptor);
    sb.append(", compressorDeCompressor=").append(compressorDeCompressor);
    sb.append('}');
    return sb.toString();
  }
}
