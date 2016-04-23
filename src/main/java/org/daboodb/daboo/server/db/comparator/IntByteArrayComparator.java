package org.daboodb.daboo.server.db.comparator;

import java.util.Comparator;

/**
 * Compares ints after converting from their byte array encoding.
 */
public enum IntByteArrayComparator implements Comparator<byte[]> {

    INSTANCE;

    @Override
    public int compare(byte[] left, byte[] right) {

      int x = asPrimitiveInteger(left);
      int y = asPrimitiveInteger(right);

      return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }

  private int asPrimitiveInteger(byte[] right) {
    return right[0] << 24
                  | (right[1] & 0xFF) << 16
                  | (right[2] & 0xFF) << 8
                  | (right[3] & 0xFF);
  }
}
