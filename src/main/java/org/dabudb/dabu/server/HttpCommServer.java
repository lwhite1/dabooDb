package org.dabudb.dabu.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * A server that handles http requests that hold database requests in their payloads
 */
public class HttpCommServer implements CommServer {

  public static void main(String[] args) throws Exception {

    Server server = new Server(7070);
    ServletContextHandler handler = new ServletContextHandler(server, "/example");
    handler.addServlet(StandardHttpServlet.class, "/");
    server.start();
  }
}
