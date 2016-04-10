package org.dabudb.dabu.shared.msg;

import org.dabudb.dabu.shared.Document;
import org.dabudb.dabu.shared.DocumentContents;
import org.dabudb.dabu.shared.StandardDocument;
import org.dabudb.dabu.shared.compression.CompressionType;
import org.dabudb.dabu.shared.msg.request.DocWriteRequest;
import org.dabudb.dabu.shared.msg.serialization.MessageSerializerType;
import org.dabudb.dabu.testutil.Company;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class MessagePipeTest {

  private final MessagePipe m1 = MessagePipe.create(CompressionType.NONE, MessageSerializerType.JSON);

  private final DocumentContents contents = new Company("testco");
  private final Document document = new StandardDocument(contents);
  private final DocWriteRequest writeRequest = new DocWriteRequest(document);

  @Test
  public void testConversion() {
    byte[] bytes = m1.messageToBytes(writeRequest);
    DocWriteRequest writeRequest1 = (DocWriteRequest) m1.bytesToMessage(DocWriteRequest.class, bytes);
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