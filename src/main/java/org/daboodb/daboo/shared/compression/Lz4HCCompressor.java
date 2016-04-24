package org.daboodb.daboo.shared.compression;


import net.jpountz.lz4.LZ4Factory;

/**
 * A compressor/de-compressor that uses Lz4 HC (High Compression) mode for compression.
 * <p>
 * It provides good compression ratios, but is much slower than the fastest LZ mode.
 */
public class LZ4HCCompressor extends LZ4AbstractCompressor {

  private final static LZ4Factory LZ_4_FACTORY = LZ4Factory.unsafeInstance();


  // TODO(lwhite): Either make this a singleton (if thread-safe) or make a pool of pre-allocated compressors
  public static LZ4HCCompressor get() {
    return new LZ4HCCompressor();
  }

  /**
   * No direct instantiation from outside
   */
  private LZ4HCCompressor() {
    super(LZ_4_FACTORY.highCompressor(), LZ_4_FACTORY.fastDecompressor());
  }
}
