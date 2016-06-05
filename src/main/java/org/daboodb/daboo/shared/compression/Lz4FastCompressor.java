package org.daboodb.daboo.shared.compression;

import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;

/**
 * A compressor/de-compressor that uses LZ4 fast-mode for compression.
 * <p>
 * It provides very fast compression, but with low compression ratios.
 */
class LZ4FastCompressor extends LZ4AbstractCompressor {

  private final static LZ4Factory LZ_4_FACTORY = LZ4Factory.fastestInstance();

  private static final LZ4FastCompressor INSTANCE = new LZ4FastCompressor();

  public static LZ4FastCompressor get() {
    return INSTANCE;
  }

  /**
   * No direct instantiation from outside
   */
  private LZ4FastCompressor() {
    super(LZ_4_FACTORY.fastCompressor(), LZ_4_FACTORY.fastDecompressor());
  }
}
