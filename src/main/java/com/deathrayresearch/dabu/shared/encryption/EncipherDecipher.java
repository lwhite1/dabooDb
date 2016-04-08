package com.deathrayresearch.dabu.shared.encryption;

/**
 *
 */
public interface EncipherDecipher {

  byte[] encrypt(byte[] plainText);

  byte[] decrypt(byte[] cipherText);
}
