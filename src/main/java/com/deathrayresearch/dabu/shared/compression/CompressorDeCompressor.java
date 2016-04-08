package com.deathrayresearch.dabu.shared.compression;

/**
 *
 */
public interface CompressorDeCompressor {

  byte[] compress(byte[] uncompressedBytes);

  byte[] decompress(byte[] compressedBytes);
}
