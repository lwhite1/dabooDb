package org.dabudb.dabu.client;

import org.dabudb.dabu.testutil.BasicTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Tests for ClientSettings (client)
 */
@SuppressWarnings("unused")
public class ClientSettingsTest extends BasicTest {

  @Before
  public void setUp() throws Exception {
    Properties clientProperties = new Properties();
    InputStream inputStream = new FileInputStream("src/main/resources/client.properties");
    clientProperties.load(inputStream);

    new ClientSettings(clientProperties);

    // System.out.println(new ClientJsonSettings().toJson());
  }

  @Override
  @After
  public void tearDown() throws Exception {
    super.tearDown();
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