package com.deathrayresearch.dabu.shared.encryption;

/**
 *
 */
public interface EncryptorDecryptor {

  byte[] encrypt(byte[] plainText);

  byte[] decrypt(byte[] cipherText);
}
