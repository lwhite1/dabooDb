package org.daboodb.daboo.shared.encryption;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Test for an encryptor that does nothing
 */
public class NullEncryptorTest {

  @Test
  public void testEncrypt() {
    byte[] input = "This is the input string. now convert me to bytes.".getBytes(StandardCharsets.UTF_8);
    byte[] cipherText = NullEncryptor.get().encrypt(input);
    byte[] plainText = NullEncryptor.get().decrypt(cipherText);
    assertTrue(Arrays.equals(input, cipherText));
    assertTrue(Arrays.equals(input, plainText));
  }
}