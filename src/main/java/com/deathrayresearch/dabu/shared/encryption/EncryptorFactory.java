package com.deathrayresearch.dabu.shared.encryption;

/**
 * Returns a compressor of the type specified in the input
 */
public class EncryptorFactory {

  /**
   * Returns a compressor of the type specified in the input
   */
  public static EncryptorDecryptor get(EncryptionType type, String password) {

    switch (type) {
      // TODO(lwhite): instantiate the encryptor using a pool like the underlying algorithm does
      case STANDARD: return new StandardTextEncryptor(password);
      case NONE: return NullEncryptor.get();
      default: throw new RuntimeException("No Encryptor available for specified type: " + type.name());
    }
  }
}
