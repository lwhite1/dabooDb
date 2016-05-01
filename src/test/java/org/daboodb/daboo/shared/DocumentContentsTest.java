package org.daboodb.daboo.shared;

import org.daboodb.daboo.testutil.Company;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Tests for DocumentContents
 */
public class DocumentContentsTest {

  private final Company company = new Company("Standard Oil");

  @Test
  public void testGetType() {
    assertEquals("Company", company.getDocumentType());
  }

  @Test
  public void testGetKey() {
    assertNotNull(company.getKey());
    System.out.println(Arrays.toString(company.getKey()));
  }
}