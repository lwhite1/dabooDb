package com.deathrayresearch.dabu.shared;

import com.deathrayresearch.dabu.shared.compression.CompressionType;
import com.deathrayresearch.dabu.shared.serialization.ContentSerializerDeserializer;
import com.deathrayresearch.dabu.shared.compression.CompressorDeCompressor;
import com.deathrayresearch.dabu.shared.compression.CompressorFactory;
import com.deathrayresearch.dabu.shared.encryption.EncryptorDecryptor;
import com.deathrayresearch.dabu.shared.encryption.EncryptorFactory;
import com.deathrayresearch.dabu.shared.serialization.ContentSerializerFactory;
import com.deathrayresearch.dabu.shared.serialization.ContentSerializerType;

/**
 * A processing pipeline for DocumentContents
 *
 * It works two ways:
 * (1) Converts objects that implement DocumentContents to a byte[]
 * (2) Converts byte[] back to DocumentContents
 *
 * The conversion includes, necessarily, serialization (and deserialization),
 * and, optionally, compression (decryption) and encryption (decryption)
 *
 * Construct a ContentsPipe with a NullCompressor to skip compression and a NullEncryptor to skip encryption.
 *
 * The same pipe must be used in both directions
 */
public class ContentsPipe {

  private final CompressorDeCompressor compressorDeCompressor;
  private final EncryptorDecryptor encryptorDecryptor;
  private final ContentSerializerDeserializer serializerDeserializer;

  public static ContentsPipe create(ContentsPipeDefinition definition, String encryptionPassword) {
    CompressorDeCompressor compressor = CompressorFactory.get(definition.getCompressionType());
    EncryptorDecryptor encryptor = EncryptorFactory.get(definition.getEncryptionType(), encryptionPassword);
    ContentSerializerDeserializer serializer = ContentSerializerFactory.get(definition.getContentSerializerType());

    return new ContentsPipe(
        compressor,
        encryptor,
        serializer);
  }

  /**
   * Creates a contentsPipe with no encryption, and the other filters as defined in the definition
   */
  public static ContentsPipe create(CompressionType compressionType, ContentSerializerType serializerType) {
    CompressorDeCompressor compressor = CompressorFactory.get(compressionType);
    EncryptorDecryptor encryptor = EncryptorFactory.NONE;
    ContentSerializerDeserializer serializer = ContentSerializerFactory.get(serializerType);

    return new ContentsPipe(
        compressor,
        encryptor,
        serializer);
  }

  private ContentsPipe(CompressorDeCompressor compressorDeCompressor,
                      EncryptorDecryptor encryptorDecryptor,
                      ContentSerializerDeserializer serializerDeserializer) {

    this.compressorDeCompressor = compressorDeCompressor;
    this.encryptorDecryptor = encryptorDecryptor;
    this.serializerDeserializer = serializerDeserializer;
  }

  public byte[] contentsToBytes(DocumentContents contents) {
    return
        encryptorDecryptor.encrypt(
            compressorDeCompressor.compress(
                serializerDeserializer.serialize(contents)));

  }

  public DocumentContents bytesToContents(Class contentClass, byte[] contentAsBytes) {
    return
        serializerDeserializer.deserialize(
            contentClass,
              compressorDeCompressor.decompress(
                encryptorDecryptor.decrypt(contentAsBytes)));
  }
}
