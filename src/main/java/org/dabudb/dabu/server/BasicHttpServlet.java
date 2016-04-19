package org.dabudb.dabu.server;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.protobuf.CodedInputStream;
import org.dabudb.dabu.generated.protobufs.Request;
import org.dabudb.dabu.shared.exceptions.RuntimeDatastoreException;
import org.eclipse.jetty.http.HttpStatus;

public class BasicHttpServlet extends HttpServlet {

  private static final String GET = "/GET";
  private static final String GET_RANGE = "/GET_RANGE";
  private static final String WRITE = "/WRITE";

  private final Database database = Database.get();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    resp.setStatus(HttpStatus.OK_200);
    resp.getWriter().println("Server is ok");
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String servletPath = request.getServletPath();
    try {
      switch (servletPath) {
        case WRITE:
          handleWrite(request, response);
          break;
        case GET:
          handleGet(request, response);
          break;
        case GET_RANGE:
          handleGetRange(request, response);
          break;
        default:

          response.setStatus(HttpStatus.NOT_FOUND_404);
          break;
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeDatastoreException("IOException handling HTTP post", e);
    }
  }

  private void handleWrite(HttpServletRequest request, HttpServletResponse response) throws IOException {
    InputStream is = request.getInputStream();
    CodedInputStream cis = CodedInputStream.newInstance(is);
    Request.WriteRequest databaseRequest = Request.WriteRequest.parseFrom(cis);
    Request.WriteReply writeReply;
    writeReply = database.handleRequest(databaseRequest, databaseRequest.toByteArray());
    response.getOutputStream().write(writeReply.toByteArray());
    response.setStatus(HttpStatus.OK_200);
  }

  private void handleGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    InputStream is = request.getInputStream();
    CodedInputStream cis = CodedInputStream.newInstance(is);
    Request.WriteRequest databaseRequest = Request.WriteRequest.parseFrom(cis);
    Request.WriteReply writeReply;
    writeReply = database.handleRequest(databaseRequest, databaseRequest.toByteArray());
    response.getOutputStream().write(writeReply.toByteArray());
    response.setStatus(HttpStatus.OK_200);
  }

  private void handleGetRange(HttpServletRequest request, HttpServletResponse response) throws IOException {
    InputStream is = request.getInputStream();
    CodedInputStream cis = CodedInputStream.newInstance(is);
    Request.GetRangeRequest databaseRequest = Request.GetRangeRequest.parseFrom(cis);
    Request.GetReply getReply;
    getReply = database.handleRequest(databaseRequest);
    response.getOutputStream().write(getReply.toByteArray());
    response.setStatus(HttpStatus.OK_200);
  }
}

