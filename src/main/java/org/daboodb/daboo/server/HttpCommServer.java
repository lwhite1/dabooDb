package org.daboodb.daboo.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * A server that handles http requests that hold database requests in their payloads
 */
public class HttpCommServer implements CommServer {

  public static void main(String[] args) throws Exception {

    Server server = new Server(7070);
    ServletContextHandler handler = new ServletContextHandler(server, "/");

    ServletHolder servlet = new ServletHolder(new BasicHttpServlet());
    handler.addServlet(servlet, "/");

    try {
      server.start();
      server.join();
    } finally {
      server.destroy();
    }
  }
}
