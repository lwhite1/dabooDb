package org.daboodb.daboo.shared.compression;

import com.google.common.primitives.Bytes;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4FastDecompressor;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Abstract superclass for LZ4-based compression algorithms. Subclasses implement LZ4 with the compression rate set
 * to some point in the allowable range:
 * <p>
 * The "Fast" version provides minimal compression.
 * The "High Compression" version provides the best compression, with the slowest performance.
 * The Moderate version splits the difference for both speed and compression ratio.
 * <p>
 * Implementation note:
 * LZ4 requires that the size of the original message be known at decompression time, so it can allocate an
 * appropriately sized byte array for the results. To support this, we append the uncompressed length (an integer)
 * as four bytes to the end of the compressed data when compression, and pull those four bytes off the end to convert
 * back to an int for use when decompressing.
 */
public abstract class LZ4AbstractCompressor implements CompressorDeCompressor {

  private final LZ4Compressor compressor;
  private final LZ4FastDecompressor decompressor;

  LZ4AbstractCompressor(LZ4Compressor compressor, LZ4FastDecompressor decompressor) {
    this.compressor = compressor;
    this.decompressor = decompressor;
  }

  public LZ4Compressor getCompressor() {
    return compressor;
  }

  public LZ4FastDecompressor getDecompressor() {
    return decompressor;
  }

  @Override
  public byte[] compress(byte[] uncompressedBytes) {
    byte[] compressed = compressor.compress(uncompressedBytes);

    // see implementation note in class comment regarding storing the uncompressed length
    byte[] lengthBytes = ByteBuffer.allocate(4).putInt(uncompressedBytes.length).array();
    return Bytes.concat(compressed, lengthBytes);
  }

  @Override
  public byte[] decompress(byte[] compressedBytes) {

    // see implementation note in class comment regarding storing the uncompressed length
    int decompressedLength =
        bytesToInt(Arrays.copyOfRange(compressedBytes, compressedBytes.length - 4, compressedBytes.length));

    byte[] actualCompressedBytes = Arrays.copyOf(compressedBytes, compressedBytes.length - 4);

    // put the decompressed bytes here
    byte[] destination = new byte[decompressedLength];

    getDecompressor().decompress(actualCompressedBytes, 0, destination, 0, decompressedLength);
    return destination;
  }

  /**
   * Returns an int equivilent derived from the given byte array
   * @param arr   An array of bytes representing a single integer in standard Java Big-Endian format
   */
  private int bytesToInt(byte[] arr) {
    ByteBuffer bb = ByteBuffer.wrap(arr);
    return bb.getInt();
  }
}
