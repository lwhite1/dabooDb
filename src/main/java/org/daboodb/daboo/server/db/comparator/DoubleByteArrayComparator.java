package org.daboodb.daboo.server.db.comparator;

import java.util.Comparator;

/**
 * Compares doubles converted from their byte array encoding.
 */
public enum DoubleByteArrayComparator implements Comparator<byte[]> {

    INSTANCE;

    /**
     * Compares doubles after converting from their 'long bits' byte array encoding.
     */
    @Override
    public int compare(byte[] left, byte[] right) {

      long leftBits = getLongBitsFromArray(left);
      long rightBits = getLongBitsFromArray(right);

      double d1 = Double.longBitsToDouble(leftBits);
      double d2 = Double.longBitsToDouble(rightBits);

      return Double.compare(d1, d2);
    }

  private long getLongBitsFromArray(byte[] left) {
    return (left[0] & 0xFFL) << 56
            | (left[1] & 0xFFL) << 48
            | (left[2] & 0xFFL) << 40
            | (left[3] & 0xFFL) << 32
            | (left[4] & 0xFFL) << 24
            | (left[5] & 0xFFL) << 16
            | (left[6] & 0xFFL) << 8
            | (left[7] & 0xFFL);
  }
}
