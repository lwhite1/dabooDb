package com.deathrayresearch.dabu.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Starts the database server in client-server mode
 */
public class Main {

  public static void main(String[] args) throws IOException {

    // create and load default properties
    Properties serverProperties = new Properties();
    InputStream in = Main.class.getResourceAsStream("/server.properties");
    serverProperties.load(in);
    in.close();

    new Settings(serverProperties);
  }
}
