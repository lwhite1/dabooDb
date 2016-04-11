package org.dabudb.dabu.server.io;

import org.dabudb.dabu.shared.Document;
import org.dabudb.dabu.shared.DocumentContents;
import org.dabudb.dabu.shared.DocumentUtils;
import org.dabudb.dabu.shared.StandardDocument;
import org.dabudb.dabu.shared.msg.MessageUtils;
import org.dabudb.dabu.shared.protobufs.Request;
import org.dabudb.dabu.testutil.Person;
import org.junit.Test;

/**
 *
 */
public class WriteLogTest {

  private DocumentContents contents = Person.createPeoples(1).get(0);
  private Document document = new StandardDocument(contents);
  Request.Document doc = DocumentUtils.getDocument(document);
  Request.Header header = MessageUtils.getHeader();
  Request.WriteRequestBody body = MessageUtils.getWriteRequestBody(doc);
  private final Request.WriteRequest writeRequest = MessageUtils.getWriteRequest(header, body);

  @Test
  public void testLogRequest() {

  }

  @Test
  public void testReplay() {

  }

  @Test
  public void testClose() {

  }

  @Test
  public void testHasNext() {

  }

  @Test
  public void testNext() {

  }
}