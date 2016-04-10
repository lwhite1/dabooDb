package org.dabudb.dabu.server.io;

import org.dabudb.dabu.shared.Document;
import org.dabudb.dabu.shared.DocumentContents;
import org.dabudb.dabu.shared.StandardDocument;
import org.dabudb.dabu.shared.msg.DocWriteRequest;
import org.dabudb.dabu.shared.msg.Request;
import org.dabudb.dabu.testutil.Person;
import org.junit.Test;

/**
 *
 */
public class WriteLogTest {

  private DocumentContents contents = Person.createPeoples(1).get(0);
  private Document document = new StandardDocument(contents);
  private Request request = new DocWriteRequest(document);

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