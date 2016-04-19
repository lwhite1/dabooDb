package org.dabudb.dabu.server;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Tests for ServerSettings
 */
public class ServerSettingsTest {

  private ServerSettings settings;

  @Before
  public void setUp() throws Exception {

    Properties serverProperties = new Properties();
    serverProperties.put("document.class", "org.dabudb.dabu.shared.StandardDocument");
    serverProperties.put("document.serializer.class", "org.dabudb.dabu.shared.serialization.DocumentJsonSerializer");

    serverProperties.put("comm.server.class", "org.dabudb.dabu.server.DirectCommServer");

    serverProperties.put("db.class", "org.dabudb.dabu.server.db.OnHeapRBTreeDb");
    serverProperties.put("db.folderName", "/tmp/dabudb/testdata/db");
    serverProperties.put("db.write_ahead_log.class", "org.dabudb.dabu.server.io.WriteLog");
    serverProperties.put("db.write_ahead_log.folderName", "wal");

    settings = ServerSettings.create(serverProperties);
  }

  @Test
  public void testGetDocumentClass() throws Exception {
    assertEquals(Class.forName("org.dabudb.dabu.shared.StandardDocument"), settings.getDocumentClass());
  }

  @Test
  public void testGetDocumentSerializer() throws Exception {
    Class c = Class.forName("org.dabudb.dabu.shared.serialization.DocumentJsonSerializer");
    assertTrue(settings.getDocumentSerializer().getClass().equals(c));
  }

  @Test
  public void testGetDb() throws Exception {
    Class c =  Class.forName("org.dabudb.dabu.server.db.OnHeapRBTreeDb");
    assertTrue(settings.getDb().getClass().equals(c));
  }

  @Test
  public void testGetWriteAheadLog() throws Exception {
    Class c = Class.forName("org.dabudb.dabu.server.io.WriteLog");
    assertTrue(settings.getWriteAheadLog().getClass().equals(c));
  }

  @Test
  public void testGetCommServer() throws ClassNotFoundException {
    Class c = Class.forName("org.dabudb.dabu.server.DirectCommServer");
    assertTrue(settings.getCommServer().getClass().equals(c));
  }

  @Test
  public void testGetDatabaseDirectory() {
    assertEquals("/tmp/dabudb/testdata/db", settings.getDatabaseDirectory());
  }
}