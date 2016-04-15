package org.dabudb.dabu.server.io;


import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Logs nothing. Assumes that there is an alternate log (as would be the case if the storage engine provided their own)
 */
public class NullLog implements WriteAheadLog, Closeable, Iterator<byte[]> {

  private static NullLog instance;

  public static NullLog getInstance(File databaseDirectory) {
    if (instance == null) {
      instance = new NullLog(databaseDirectory);
    }
    return instance;
  }

  private NullLog(File rootFolder) {}

  @Override
  public void close() throws IOException {
  }

  @Override
  public boolean hasNext() {
    return false;
  }

  @Override
  public byte[] next() {
    throw new UnsupportedOperationException("Null log doesn't support iteration. Use a real log implementation");
  }

  @Override
  public void log(byte[] request) throws IOException {
  }
}
