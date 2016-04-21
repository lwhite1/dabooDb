package org.daboodb.daboo.shared;

import org.daboodb.daboo.shared.compression.CompressionType;
import org.daboodb.daboo.shared.serialization.ContentSerializerDeserializer;
import org.daboodb.daboo.shared.compression.CompressorDeCompressor;
import org.daboodb.daboo.shared.compression.CompressorFactory;
import org.daboodb.daboo.shared.encryption.EncryptorDecryptor;
import org.daboodb.daboo.shared.encryption.EncryptorFactory;
import org.daboodb.daboo.shared.serialization.ContentSerializerFactory;
import org.daboodb.daboo.shared.serialization.ContentSerializerType;

/**
 * A processing pipeline for DocumentContents
 * <p>
 * It works two ways:
 * (1) Converts objects that implement DocumentContents to a byte[]
 * (2) Converts byte[] back to DocumentContents
 * <p>
 * The conversion includes, necessarily, serialization (and deserialization),
 * and, optionally, compression (decryption) and encryption (decryption)
 * <p>
 * Construct a ContentsPipe with a NullCompressor to skip compression and a NullEncryptor to skip encryption.
 * <p>
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
