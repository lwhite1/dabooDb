package org.dabudb.dabu.shared.encryption;

/**
 * An 'encryptor' that does nothing. Input bytes are the same as output bytes
 */
public class NullEncryptor implements EncryptorDecryptor {

  private static NullEncryptor INSTANCE = new NullEncryptor();

  static NullEncryptor get() {
    return INSTANCE;
  }

  private NullEncryptor() {}

  @Override
  public byte[] encrypt(byte[] plainText) {
    return plainText;
  }

  @Override
  public byte[] decrypt(byte[] cipherText) {
    return cipherText;
  }
}
