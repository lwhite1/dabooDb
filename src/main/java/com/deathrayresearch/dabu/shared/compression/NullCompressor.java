package com.deathrayresearch.dabu.shared.compression;

/**
 * A compressor/decompressor that does nothing. Bytes input = bytes output
 */
public class NullCompressor implements CompressorDeCompressor {

  private static final NullCompressor INSTANCE = new NullCompressor();

  public static NullCompressor get() {
    return INSTANCE;
  }

  private NullCompressor() {}

  @Override
  public byte[] compress(byte[] uncompressedBytes) {
    return uncompressedBytes;
  }

  @Override
  public byte[] decompress(byte[] compressedBytes) {
    return compressedBytes;
  }
}
