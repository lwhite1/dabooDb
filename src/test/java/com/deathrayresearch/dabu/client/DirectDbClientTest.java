package com.deathrayresearch.dabu.client;

import com.deathrayresearch.dabu.shared.Document;
import com.deathrayresearch.dabu.shared.StandardDocument;
import com.deathrayresearch.dabu.testutil.Company;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

/**
 *
 */
public class DirectDbClientTest {


  DbClient client = new DirectDbClient();

  @Before
  public void setUp() throws Exception {

  }

  @Test
  public void testWriteKv() {
    byte[] key = "foo".getBytes(StandardCharsets.UTF_8);
    byte[] val = "bar".getBytes(StandardCharsets.UTF_8);
    client.write(key, val);
  }
  @Test
  public void testWriteDocument() {
    Company company = new Company("Drr");
    Document document = new StandardDocument(company);
    System.out.println(document.toString());
  }

  @Test
  public void testGet() {

  }

  @Test
  public void testDelete() {

  }
}