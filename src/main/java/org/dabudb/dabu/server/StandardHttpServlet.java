package org.dabudb.dabu.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import org.dabudb.dabu.generated.protobufs.Request;
import org.dabudb.dabu.shared.exceptions.RuntimeDatastoreException;
import org.eclipse.jetty.http.HttpStatus;

/**
 *
 */
class StandardHttpServlet extends HttpServlet {

  private final Database database = Database.get();
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    resp.setStatus(HttpStatus.OK_200);
    resp.getWriter().println("EmbeddedJetty");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {
      InputStream is = request.getInputStream();
      CodedInputStream cis = CodedInputStream.newInstance(is);
      Request.WriteRequest databaseRequest = Request.WriteRequest.parseFrom(cis);
      Request.WriteReply writeReply;
      writeReply = database.handleRequest(databaseRequest, databaseRequest.toByteArray());
      response.getOutputStream().write(writeReply.toByteArray());

    } catch (IOException e) {
      throw new RuntimeDatastoreException("IOException handling HTTP post", e);
    }
  }
}

