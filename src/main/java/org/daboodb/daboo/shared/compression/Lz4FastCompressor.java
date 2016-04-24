package org.daboodb.daboo.shared.compression;

import net.jpountz.lz4.LZ4Factory;

/**
 * A compressor/de-compressor that uses Lz4 fast-mode for compression.
 * <p>
 * It provides very fast compression, but with low compression ratios.
 */
public class LZ4FastCompressor extends LZ4AbstractCompressor {

  private final static LZ4Factory LZ_4_FACTORY = LZ4Factory.fastestInstance();


  // TODO(lwhite): Either make this a singleton (if thread-safe) or make a pool of pre-allocated compressors
  public static LZ4FastCompressor get() {
    return new LZ4FastCompressor();
  }

  /**
   * No direct instantiation from outside
   */
  private LZ4FastCompressor() {
    super(LZ_4_FACTORY.fastCompressor(), LZ_4_FACTORY.fastDecompressor());
  }
}
