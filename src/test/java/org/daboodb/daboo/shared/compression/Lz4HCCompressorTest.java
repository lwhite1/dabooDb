package org.daboodb.daboo.shared.compression;

import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

/**
 * Tests for LZ4 High Compression compressor
 */
public class LZ4HCCompressorTest {

  private final static String STRING = "Yes, friends, I know this is news to absolutely nobody. But today I had " +
      "up-close-and-personal problems with Java garbage collection for the first time so " +
      "I am going to tell you about it.";

  @Test
  public void testCompress() {
    LZ4HCCompressor compressor = LZ4HCCompressor.get();
    byte[] compressed = compressor.compress(STRING.getBytes(StandardCharsets.UTF_8));
    byte[] decompressed = compressor.decompress(compressed);
    assertEquals(STRING, new String(decompressed, StandardCharsets.UTF_8));
  }
}