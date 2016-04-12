package org.dabudb.dabu.server.io;

import org.dabudb.dabu.shared.protobufs.Request;

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
    try {
      if (instance == null) {
        instance = new NullLog(databaseDirectory);
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return instance;
  }

  private NullLog(File rootFolder) throws IOException {
  }

  @Override
  public void logRequest(Request.WriteRequest request) throws IOException {

  }

  @Override
  public void logRequest(Request.DeleteRequest request) throws IOException {
  }
  
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
