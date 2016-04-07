package com.deathrayresearch.dabu.server;

import com.deathrayresearch.dabu.server.io.Compression;
import com.deathrayresearch.dabu.shared.StandardDocument;

/**
 *
 */
public class DbConfig {

  private final Class dbClass;

  private final Compression compression = Compression.SNAPPY;

  private final Class documentClass = StandardDocument.class;

  public DbConfig() {
    dbClass = MemoryDb.class;
  }

  public Class getDbClass() {
    return dbClass;
  }

  public Compression getCompression() {
    return compression;
  }

  public Class getDocumentClass() {
    return documentClass;
  }
}
