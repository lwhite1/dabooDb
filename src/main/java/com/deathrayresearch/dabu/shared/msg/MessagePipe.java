package com.deathrayresearch.dabu.shared.msg;

import com.deathrayresearch.dabu.shared.compression.CompressionType;
import com.deathrayresearch.dabu.shared.compression.CompressorDeCompressor;
import com.deathrayresearch.dabu.shared.compression.CompressorFactory;
import com.deathrayresearch.dabu.shared.encryption.EncryptorDecryptor;
import com.deathrayresearch.dabu.shared.encryption.EncryptorFactory;
import com.deathrayresearch.dabu.shared.msg.serialization.MessageSerializerFactory;
import com.deathrayresearch.dabu.shared.msg.serialization.MsgSerializerDeserializer;
import com.deathrayresearch.dabu.shared.msg.serialization.MsgSerializerType;

/**
 *
 */
public class MessagePipe {

  private EncryptorDecryptor encryptorDecryptor;
  private CompressorDeCompressor compressorDeCompressor;
  private MsgSerializerDeserializer serializerDeserializer;

  /**
   * Creates a messagePipe with no encryption, and the other filters as defined
   */
  public static MessagePipe create(CompressionType compressionType, MsgSerializerType serializerType) {
    CompressorDeCompressor compressor = CompressorFactory.get(compressionType);
    EncryptorDecryptor encryptor = EncryptorFactory.NONE;
    MsgSerializerDeserializer serializer = MessageSerializerFactory.get(serializerType);

    return new MessagePipe(
        compressor,
        encryptor,
        serializer);
  }


  public MessagePipe(
      CompressorDeCompressor compressorDeCompressor,
      EncryptorDecryptor encryptorDecryptor,
      MsgSerializerDeserializer serializerDeserializer) {
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

}
