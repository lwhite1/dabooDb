package org.daboodb.daboo.shared.compression;

/**
 * Returns a compressor of the type specified in the input
 */
public class CompressorFactory {

  /**
   * Returns a compressor of the type specified in the input
   */
  public static CompressorDeCompressor get(CompressionType type) {

    switch (type) {
      case SNAPPY:
        return SnappyCompressor.get();
      case LZ4_FAST:
        return LZ4FastCompressor.get();
      case LZ4_MC:
        return LZ4ModerateCompressor.get();
      case LZ4_HC:
        return LZ4HCCompressor.get();
      case NONE:
        return NullCompressor.get();
      default:
        throw new RuntimeException("No compressor available for specified type: " + type.name());
    }
  }
}
