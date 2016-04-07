package com.deathrayresearch.dabu.client;

import com.deathrayresearch.dabu.shared.Document;
import com.deathrayresearch.dabu.shared.StandardDocument;
import com.deathrayresearch.dabu.testutil.Company;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class DirectDbClientTest {


  DbClient client = new DirectDbClient();

  @Before
  public void setUp() throws Exception {

  }

  @Test
  public void testWriteDocument() {
    Company company = new Company("Drr");
    Document document = new StandardDocument(company);
    client.writeDoc(document);
    byte[] key = document.key();
    Document document1 = client.getDoc(key);
    System.out.println(document.toString());
  }

  @Test
  public void testGet() {

  }

  @Test
  public void testDelete() {

  }
}