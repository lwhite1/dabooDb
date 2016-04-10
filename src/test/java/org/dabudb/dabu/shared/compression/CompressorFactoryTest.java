package org.dabudb.dabu.shared.compression;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the CompressorFactory
 */
public class CompressorFactoryTest {

  @Test
  public void testGet() {
    assertTrue(CompressorFactory.get(CompressionType.NONE) instanceof NullCompressor);
    assertTrue(CompressorFactory.get(CompressionType.SNAPPY) instanceof SnappyCompressor);
  }
}