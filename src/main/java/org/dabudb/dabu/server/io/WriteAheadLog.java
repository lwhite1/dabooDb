package org.dabudb.dabu.server.io;

import java.io.IOException;
import java.util.Iterator;

/**
 *  Interface for Write-Ahead Log implementations
 */
public interface WriteAheadLog extends Iterator<byte[]> {

  void clear() throws IOException;

  void log(byte[] request) throws IOException;
}
