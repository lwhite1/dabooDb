package org.daboodb.daboo.shared.compression;


import com.google.common.primitives.Bytes;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * A compressor/de-compressor that uses Lz4 HC (High Compression) mode for compression.
 * <p>
 * It provides good compression ratios, but is much slower than the fastest LZ mode.
 */
public class LZ4HCCompressor extends LZ4AbstractCompressor {

  private static LZ4Factory factory = LZ4Factory.unsafeInstance();


  // TODO(lwhite): Either make this a singleton (if thread-safe) or make a pool of pre-allocated compressors
  public static LZ4HCCompressor get() {
    return new LZ4HCCompressor();
  }

  /**
   * No direct instantiation from outside
   */
  private LZ4HCCompressor() {
    super(factory.highCompressor(), factory.fastDecompressor());
  }
}
