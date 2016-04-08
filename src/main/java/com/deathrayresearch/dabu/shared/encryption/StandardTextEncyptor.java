package com.deathrayresearch.dabu.shared.encryption;

import org.jasypt.encryption.pbe.PooledPBEByteEncryptor;

/**
 *
 */
public class StandardTextEncyptor implements EncipherDecipher {

  private final PooledPBEByteEncryptor textEncryptor = new PooledPBEByteEncryptor();

  public StandardTextEncyptor(String password) {
    textEncryptor.setPassword(password);
    textEncryptor.setPoolSize(3);
  }

  @Override
  public byte[] encrypt(byte[] plainText) {
    return textEncryptor.encrypt(plainText);
  }

  @Override
  public byte[] decrypt(byte[] cipherText) {
    return textEncryptor.decrypt(cipherText);
  }
}
