package com.deathrayresearch.dabu.shared;

import com.deathrayresearch.dabu.testutil.Company;
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
    assertEquals(company.getClass().getCanonicalName(), company.getType());
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