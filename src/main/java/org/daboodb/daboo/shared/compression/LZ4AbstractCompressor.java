package org.daboodb.daboo.shared.compression;

import com.google.common.primitives.Bytes;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4FastDecompressor;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 *
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
    byte[] lengthBytes = ByteBuffer.allocate(4).putInt(uncompressedBytes.length).array();
    return Bytes.concat(compressed, lengthBytes);
  }

  @Override
  public byte[] decompress(byte[] compressedBytes) {

    int decompressedLength =
        bytesToInt(Arrays.copyOfRange(compressedBytes, compressedBytes.length - 4, compressedBytes.length));

    byte[] actualCompressedBytes = Arrays.copyOf(compressedBytes, compressedBytes.length - 4);

    // put the decompressed bytes here
    byte[] destination = new byte[decompressedLength];

    getDecompressor().decompress(actualCompressedBytes, 0, destination, 0, decompressedLength);
    return destination;
  }

  private int bytesToInt(byte[] arr) {
    ByteBuffer bb = ByteBuffer.wrap(arr);
    return bb.getInt();
  }
}
