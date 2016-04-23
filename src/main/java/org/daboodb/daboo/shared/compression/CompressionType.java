package org.daboodb.daboo.shared.compression;

/**
 * Indicates the type of compression to use
 */
public enum CompressionType {

  SNAPPY,
  LZ4_FAST,   // Pure Java LZ4, fasted compression
  LZ4_HC,     // Pure Java LZ4, highest compression ratio (but slower)
  LZ4_MC,     // Pure Java LZ4, moderate compression ratio. Compression ratio and speed between HC and FAST versions.
  NONE,
}
