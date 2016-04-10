package org.dabudb.dabu.shared.compression;

import org.iq80.snappy.Snappy;

/**
 * A compressor/de-compressor that uses Snappy for compression.
 *
 * Snappy provides very fast compression, but with low compression ratios.
 */
public class SnappyCompressor implements CompressorDeCompressor {

  // TODO(lwhite): Either make this a singleton (if thread-safe) or make a pool of preallocated compressors
  public static SnappyCompressor get() {
    return new SnappyCompressor();
  }

  /**
   * No direct instantiation from outside
   */
  private SnappyCompressor() {
  }

  @Override
  public byte[] compress(byte[] uncompressedBytes) {

    return Snappy.compress(uncompressedBytes);
  }

  @Override
  public byte[] decompress(byte[] compressedBytes) {
    return Snappy.uncompress(compressedBytes, 0, compressedBytes.length);
  }
}
