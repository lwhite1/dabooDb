package org.dabudb.dabu.shared.msg;

import org.dabudb.dabu.shared.Document;
import org.dabudb.dabu.shared.DocumentContents;
import org.dabudb.dabu.shared.DocumentUtils;
import org.dabudb.dabu.shared.StandardDocument;
import org.dabudb.dabu.shared.compression.CompressionType;
import org.dabudb.dabu.shared.protobufs.Request;
import org.dabudb.dabu.testutil.Company;
import org.junit.Test;

import static org.dabudb.dabu.shared.protobufs.Request.*;
import static org.junit.Assert.*;

/**
 * Tests for MessagePipe
 */
public class MessagePipeTest {

  private final MessagePipe m1 = MessagePipe.create(CompressionType.NONE);

  private final DocumentContents contents = new Company("test co");
  private final Document document = new StandardDocument(contents);
  private final Request.Document doc = DocumentUtils.getDocument(document);
  private final Header header = MessageUtils.getHeader();
  private final WriteRequestBody body = MessageUtils.getWriteRequestBody(doc);
  private final WriteRequest writeRequest = MessageUtils.getWriteRequest(header, body);

  @Test
  public void testConversion() {
    WriteRequest writeRequest1 = m1.bytesToWriteRequest(writeRequest.toByteArray());
    assertEquals(writeRequest, writeRequest1);
  }

  @Test
  public void testGets() {

  }

  @Test
  public void testEquals() {

  }

  @Test
  public void testHashCode() {

  }

  @Test
  public void testToString() {

  }
}