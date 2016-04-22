package org.daboodb.daboo.shared.compression;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Tests the null compressor, a CompressorDeCompressor implementation that does no compression at all
 */
public class NullCompressorTest {

  @Test
  public void testCompress() {
    byte[] input = "This is the input string. now convert me to bytes.".getBytes(StandardCharsets.UTF_8);
    byte[] compressed = NullCompressor.get().compress(input);
    byte[] decompressed = NullCompressor.get().decompress(compressed);
    assertTrue(Arrays.equals(input, decompressed));
  }
}