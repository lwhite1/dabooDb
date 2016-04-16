package org.dabudb.dabu.server;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 *  Tests for ServerSettings
 */
public class ServerSettingsTest {

  @Before
  public void setUp() throws Exception {

    Properties serverProperties = new Properties();
    InputStream inputStream = new FileInputStream("src/main/resources/server.properties");
    serverProperties.load(inputStream);

    new ServerSettings(serverProperties);
  }

}