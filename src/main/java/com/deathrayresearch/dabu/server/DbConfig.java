package com.deathrayresearch.dabu.server;

import com.deathrayresearch.dabu.shared.compression.CompressionType;
import com.deathrayresearch.dabu.shared.StandardDocument;

/**
 *
 */
public class DbConfig {

  // Db implementation class used
  private final Class dbClass;

  // Compression type used
  private final CompressionType documentContentCompressionType = CompressionType.NONE;
  private final CompressionType requestCompressionType = CompressionType.NONE;

  // Document implementation class used
  private final Class documentClass = StandardDocument.class;

  public DbConfig() {
    dbClass = MemoryDb.class;
  }

  public Class getDbClass() {
    return dbClass;
  }

  public CompressionType getDocumentContentCompressionType() {
    return documentContentCompressionType;
  }

  public Class getDocumentClass() {
    return documentClass;
  }

  public CompressionType getRequestCompressionType() {
    return requestCompressionType;
  }
}
