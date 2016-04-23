package org.daboodb.daboo.server.db.comparator;

import java.util.Comparator;

/**
 * Compares long values after converting from a byte array encoding.
 *
 * This ONLY works if the doubles are encoded using long bits
 */
public enum LongByteArrayComparator implements Comparator<byte[]> {

  INSTANCE;

  @Override
  public int compare(byte[] left, byte[] right) {
    long a = asPrimitiveLong(left);

    long b = asPrimitiveLong(right);
    return (a < b) ? -1 : ((a == b) ? 0 : 1);
  }

  private long asPrimitiveLong(byte[] right) {
    return (right[0] & 0xFFL) << 56
        | (right[1] & 0xFFL) << 48
        | (right[2] & 0xFFL) << 40
        | (right[3] & 0xFFL) << 32
        | (right[4] & 0xFFL) << 24
        | (right[5] & 0xFFL) << 16
        | (right[6] & 0xFFL) << 8
        | (right[7] & 0xFFL);
  }
}
