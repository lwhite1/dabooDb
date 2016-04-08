package com.deathrayresearch.dabu.shared.compression;

import static com.deathrayresearch.dabu.shared.compression.CompressionType.SNAPPY;

/**
 * Returns a compressor of the type specified in the input
 */
public class CompressorFactory {

  /**
   * Returns a compressor of the type specified in the input
   */
  public static CompressorDeCompressor get(CompressionType type) {

    switch (type) {
      case SNAPPY: return SnappyCompressor.get();
      case NONE: return NullCompressor.get();
      default: throw new RuntimeException("No compressor available for specified type: " + type.name());
    }
  }
}
