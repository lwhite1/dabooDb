package org.dabudb.dabu.client;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Tests for ClientSettings (client)
 */
public class ClientSettingsTest {

  @Before
  public void setUp() throws Exception {
    Properties clientProperties = new Properties();
    InputStream inputStream = new FileInputStream("src/main/resources/client.properties");
    clientProperties.load(inputStream);

    new ClientSettings(clientProperties);

    // System.out.println(new ClientJsonSettings().toJson());
  }

  @Test
  public void testLoading() {

  }

  @Test
  public void testGetInstance() {

  }

  @Test
  public void testGetContentsPipe() {

  }

  @Test
  public void testGetDocumentClass() {

  }

  @Test
  public void testGetCommClient() {

  }
}