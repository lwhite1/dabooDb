package com.deathrayresearch.dabu.client;

import com.deathrayresearch.dabu.shared.Document;
import com.deathrayresearch.dabu.shared.StandardDocument;
import com.deathrayresearch.dabu.testutil.Company;
import com.deathrayresearch.dabu.testutil.Person;
import com.google.common.base.Stopwatch;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    Stopwatch stopwatch = Stopwatch.createStarted();
    client.writeDoc(document);
    byte[] key = document.key();
    Document document1 = client.getDoc(key);
    assertTrue(document1 != null);
    Company company1 = (Company) document1.documentContents();
    assertEquals(company, company1);
    System.out.println("Round trip: " + stopwatch.elapsed(TimeUnit.MICROSECONDS));
    System.out.println(document.toString());
  }

  @Test
  public void testWriteDocuments() {
    List<Person> people = Person.createPeoples(600_000);
    List<Document> peopleDocs = new ArrayList<>();
    for (Person person : people) {
      Document document = new StandardDocument(person);
      peopleDocs.add(document);
    }

    Stopwatch stopwatch = Stopwatch.createStarted();
    for (Document document : peopleDocs) {
      client.writeDoc(document);
    }
    System.out.println("Write: " + stopwatch.elapsed(TimeUnit.MILLISECONDS));
  }

  @Test
  public void testWriteDocuments2() {
    List<Person> people = Person.createPeoples(600_000);
    List<Document> peopleDocs = new ArrayList<>();
    for (Person person : people) {
      Document document = new StandardDocument(person);
      peopleDocs.add(document);
    }

    Stopwatch stopwatch = Stopwatch.createStarted();
    client.writeDocs(peopleDocs);
    System.out.println("Write: " + stopwatch.elapsed(TimeUnit.MILLISECONDS));
  }

  @Test
  public void testGet() {

  }

  @Test
  public void testDelete() {

  }
}