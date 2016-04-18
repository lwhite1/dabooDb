package org.dabudb.dabu.server;

import java.io.File;

/**
 * A database administer
 */
public interface DatabaseAdmin {

  /**
   * Clears the primary database storage and the WAL
   */
  void clear();

  /**
   * Exports the entire contents of the database to the given backup file
   */
  void export(File backupFile);

  /**
   * Exports the entire contents of the database to the given backup file,
   * and clears the WAL to the point of backup
   */
  void backup(File backupFile);

  /**
   * Reads all the data from the backupFile into the database
   */
  void recoverFromBackup(File backupFile);
}
