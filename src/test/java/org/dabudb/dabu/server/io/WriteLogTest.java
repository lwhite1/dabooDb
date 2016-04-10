package org.dabudb.dabu.server.io;

import org.dabudb.dabu.shared.Document;
import org.dabudb.dabu.shared.DocumentContents;
import org.dabudb.dabu.shared.StandardDocument;
import org.dabudb.dabu.shared.msg.request.DocsWriteRequest;
import org.dabudb.dabu.shared.msg.request.Request;
import org.dabudb.dabu.testutil.Person;
import org.junit.Test;

import java.util.Collections;

/**
 *
 */
public class WriteLogTest {

  private DocumentContents contents = Person.createPeoples(1).get(0);
  private Document document = new StandardDocument(contents);
  private Request request = new DocsWriteRequest(Collections.singletonList(document));

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