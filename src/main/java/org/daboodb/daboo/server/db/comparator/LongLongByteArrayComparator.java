package org.daboodb.daboo.server.db.comparator;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Compares a byte array containing two longs, one after the other, with the most significant long first.
 * <p/>
 * There are two expected use-cases:
 * First, java.time.LocalDateTime is implemented as two longs, one for date and one for time
 * Second, java.util.UUID is implemented as two longs also, with the most significant and least significant returned
 * by appropriately named methods
 */
public enum LongLongByteArrayComparator implements Comparator<byte[]> {

  INSTANCE;

  @Override
  public int compare(byte[] left, byte[] right) {

    // first we have to get the correct bits
    byte[] mostSignificantLeft = Arrays.copyOfRange(left, 0, 8);
    byte[] mostSignificantRight = Arrays.copyOfRange(right, 0, 8);

    // then we compare them
    long a = asPrimitiveLong(mostSignificantLeft);
    long b = asPrimitiveLong(mostSignificantRight);

    int result = Long.compare(a, b);
    if (result != 0) {
      return result;
    }
    byte[] leastSignificantLeft = Arrays.copyOfRange(left, 8, 16);
    byte[] leastSignificantRight = Arrays.copyOfRange(right, 8, 16);

    long c = asPrimitiveLong(leastSignificantLeft);
    long d = asPrimitiveLong(leastSignificantRight);

    return (c < d) ? -1 : ((c == d) ? 0 : 1);
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
