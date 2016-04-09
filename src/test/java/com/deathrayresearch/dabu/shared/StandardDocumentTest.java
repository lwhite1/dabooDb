package com.deathrayresearch.dabu.shared;

import com.deathrayresearch.dabu.testutil.Person;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 *
 */
public class StandardDocumentTest {

  private Person person = Person.createPeoples(1).get(0);
  private Document document = new StandardDocument(person);

  @Test
  public void testKey() {
    assertTrue(Arrays.equals(person.getKey(), document.key()));
  }

  @Test
  public void testGetContents() {
    //TODO(lwhite): implement
  }

  @Test
  public void testGetContentType() {
    assertEquals(person.getType(), document.getContentType());
  }

  @Test
  public void testSchemaVersion() {
    assertEquals(0, document.schemaVersion());
  }

  @Test
  public void testDocumentVersion() {
    assertEquals(0, document.documentVersion());
  }

  @Test
  public void testToString() {
    System.out.println(document.toString());
  }

  @Test
  public void testDocumentContents() {
    assertEquals(person, document.documentContents());
  }
}