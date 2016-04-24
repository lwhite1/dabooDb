package org.daboodb.daboo.shared.compression;

import net.jpountz.lz4.LZ4Factory;

/**
 * A compressor/de-compressor that uses Lz4 for compression.
 * <p>
 * It's performance should be between that of the High Compression version and the Fast version, on both the speed of
 * compression and compression ratio.
 */
public class LZ4ModerateCompressor extends LZ4AbstractCompressor {

  private final static LZ4Factory LZ_4_FACTORY = LZ4Factory.fastestInstance();


  // TODO(lwhite): Either make this a singleton (if thread-safe) or make a pool of pre-allocated compressors
  public static LZ4ModerateCompressor get() {
    return new LZ4ModerateCompressor();
  }

  /**
   * No direct instantiation from outside
   */
  private LZ4ModerateCompressor() {
    super(LZ_4_FACTORY.highCompressor(12), LZ_4_FACTORY.fastDecompressor());
  }
}
