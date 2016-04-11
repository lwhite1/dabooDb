package org.dabudb.dabu.shared.msg;

import org.dabudb.dabu.shared.Document;
import org.dabudb.dabu.shared.DocumentContents;
import org.dabudb.dabu.shared.DocumentUtils;
import org.dabudb.dabu.shared.StandardDocument;
import org.dabudb.dabu.shared.compression.CompressionType;
import org.dabudb.dabu.shared.msg.serialization.MessageSerializerType;
import org.dabudb.dabu.shared.protobufs.Request;
import org.dabudb.dabu.testutil.Company;
import org.junit.Test;

import java.util.Collections;

import static org.dabudb.dabu.shared.protobufs.Request.*;
import static org.junit.Assert.*;

/**
 *
 */
public class MessagePipeTest {

  private final MessagePipe m1 = MessagePipe.create(CompressionType.NONE, MessageSerializerType.JSON);

  private final DocumentContents contents = new Company("testco");
  private final Document document = new StandardDocument(contents);
  private final Request.Document doc = DocumentUtils.getDocument(document);
  Header header = MessageUtils.getHeader();
  WriteRequestBody body = MessageUtils.getWriteRequestBody(doc);
  private final WriteRequest writeRequest = MessageUtils.getWriteRequest(header, body);

  @Test
  public void testConversion() {
    byte[] bytes = m1.messageToBytes(writeRequest);
    WriteRequest writeRequest1 = m1.bytesToWriteRequst(bytes);
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