package com.deathrayresearch.dabu.server.io;

import com.deathrayresearch.dabu.shared.Document;
import com.deathrayresearch.dabu.shared.DocumentContents;
import com.deathrayresearch.dabu.shared.StandardDocument;
import com.deathrayresearch.dabu.shared.msg.DocWriteRequest;
import com.deathrayresearch.dabu.shared.msg.Request;
import com.deathrayresearch.dabu.testutil.Person;
import org.junit.Test;

import static org.junit.Assert.*;

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