package org.daboodb.daboo.shared.encryption;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for the standard text encryptor
 */
public class StandardEncryptorTest {

  @Test
  public void testEncrypt() {
    byte[] input = "This is the input string. now convert me to bytes.".getBytes(StandardCharsets.UTF_8);
    EncryptorDecryptor encryptorDecryptor = EncryptorFactory.get(EncryptionType.STANDARD, "foobar");
    byte[] cipherText = encryptorDecryptor.encrypt(input);
    byte[] plainText = encryptorDecryptor.decrypt(cipherText);
    assertFalse(Arrays.equals(input, cipherText));
    assertTrue(Arrays.equals(input, plainText));
  }
}