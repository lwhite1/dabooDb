package org.dabudb.dabu.shared.compression;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 *
 */
public class SnappyCompressorTest {

  @Test
  public void testCompressDecompress() {
    byte[] input = "This is the input string. now convert me to bytes.".getBytes(StandardCharsets.UTF_8);
    byte[] compressed = SnappyCompressor.get().compress(input);
    byte[] decompressed = SnappyCompressor.get().decompress(compressed);
    assertTrue(Arrays.equals(input, decompressed));
  }
}