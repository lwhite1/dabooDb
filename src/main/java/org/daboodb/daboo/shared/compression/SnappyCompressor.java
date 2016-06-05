package org.daboodb.daboo.shared.compression;

import org.iq80.snappy.Snappy;

import javax.annotation.concurrent.ThreadSafe;

/**
 * A compressor/de-compressor that uses Snappy for compression.
 * <p>
 * Snappy provides very fast compression, but with low compression ratios.
 */
@ThreadSafe
class SnappyCompressor implements CompressorDeCompressor {

  static final SnappyCompressor compressor = new SnappyCompressor();

  public static SnappyCompressor get() {
    return compressor;
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
