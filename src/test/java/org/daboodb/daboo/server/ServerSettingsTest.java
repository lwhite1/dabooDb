package org.daboodb.daboo.server;

import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Tests for ServerSettings
 */
public class ServerSettingsTest {

  private ServerSettings settings;

  @Before
  public void setUp() {

    Properties serverProperties = new Properties();
    serverProperties.put("document.class", "org.daboodb.daboo.shared.StandardDocument");
    serverProperties.put("document.serializer.class", "org.daboodb.daboo.shared.serialization.DocumentJsonSerializer");

    serverProperties.put("comm.server.class", "org.daboodb.daboo.server.DirectCommServer");

    serverProperties.put("db.class", "org.daboodb.daboo.server.db.OnHeapRBTreeDb");
    serverProperties.put("db.folderName", "/tmp/daboodb/testdata/db");
    serverProperties.put("db.write_ahead_log.class", "org.daboodb.daboo.server.io.WriteLog");
    serverProperties.put("db.write_ahead_log.folderName", "wal");

    settings = ServerSettings.create(serverProperties);
  }

  @Test
  public void testGetDocumentSerializer() throws Exception {
    Class c = Class.forName("org.daboodb.daboo.shared.serialization.DocumentJsonSerializer");
    assertTrue(settings.getDocumentSerializer().getClass().equals(c));
  }

  @Test
  public void testGetDb() throws Exception {
    Class c =  Class.forName("org.daboodb.daboo.server.db.OnHeapRBTreeDb");
    assertTrue(settings.getDb().getClass().equals(c));
  }

  @Test
  public void testGetWriteAheadLog() throws Exception {
    Class c = Class.forName("org.daboodb.daboo.server.io.WriteLog");
    assertTrue(settings.getWriteAheadLog().getClass().equals(c));
  }

  @Test
  public void testGetCommServer() throws ClassNotFoundException {
    Class c = Class.forName("org.daboodb.daboo.server.DirectCommServer");
    assertTrue(settings.getCommServer().getClass().equals(c));
  }

  @Test
  public void testGetDatabaseDirectory() {
    assertEquals("/tmp/daboodb/testdata/db", settings.getDatabaseDirectory());
  }
}