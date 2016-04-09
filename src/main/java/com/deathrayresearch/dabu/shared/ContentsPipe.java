package com.deathrayresearch.dabu.shared;

import com.deathrayresearch.dabu.client.serialization.ContentSerializerDeserializer;
import com.deathrayresearch.dabu.shared.compression.CompressorDeCompressor;
import com.deathrayresearch.dabu.shared.encryption.EncryptorDecryptor;

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

  public ContentsPipe(CompressorDeCompressor compressorDeCompressor,
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
