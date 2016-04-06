package com.deathrayresearch.dabu.server;

/**
 *
 */
public class DbConfig {

  private final Class dbClass;

  public DbConfig() {
    dbClass = MemoryDb.class;
  }

  public Class getDbClass() {
    return dbClass;
  }
}
