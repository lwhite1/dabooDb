package org.dabudb.dabu.shared;

import org.dabudb.dabu.testutil.Company;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Tests for DocumentContents
 */
public class DocumentContentsTest {

  Company company = new Company("Standard Oil");

  @Test
  public void testGetType() {
    assertEquals("Company", company.getContentType());
  }

  @Test
  public void testMarshall() {
    byte[] bytes = company.marshall();
  }

  @Test
  public void testGetKey() {
    assertNotNull(company.getKey());
    System.out.println(Arrays.toString(company.getKey()));
  }
}