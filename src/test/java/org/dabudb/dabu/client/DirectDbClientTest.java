package org.dabudb.dabu.client;

import org.dabudb.dabu.shared.Document;
import org.dabudb.dabu.shared.StandardDocument;
import org.dabudb.dabu.testutil.Company;
import org.dabudb.dabu.testutil.Person;
import com.google.common.base.Stopwatch;
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

  DbClient client = DbClient.get();

  @Test
  public void testWriteDocument() {
    Company company = new Company("Drr");
    Document document = new StandardDocument(company);
    Stopwatch stopwatch = Stopwatch.createStarted();
    client.write(document);
    byte[] key = document.key();
    Document document1 = client.get(key);
    assertTrue(document1 != null);
    Company company1 = (Company) document1.documentContents();
    assertEquals(company, company1);
    System.out.println("Round trip: " + stopwatch.elapsed(TimeUnit.MICROSECONDS));
    System.out.println(document.toString());
  }

  @Test
  public void testWriteDocument2() {
    Company company = new Company("Drr");
    Document document = new StandardDocument(company);
    client.write(document);
  }

  @Test
  public void testWriteDocuments() {
    List<Person> people = Person.createPeoples(6_000);
    List<Document> peopleDocs = new ArrayList<>();
    for (Person person : people) {
      Document document = new StandardDocument(person);
      peopleDocs.add(document);
    }

    Stopwatch stopwatch = Stopwatch.createStarted();
    for (Document document : peopleDocs) {
      client.write(document);
    }
    System.out.println("Write " + 6_000 + " objects in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
  }

  @Test
  public void testWriteDocuments2() {
    List<Person> people = Person.createPeoples(6_000);
    List<Document> peopleDocs = new ArrayList<>();
    for (Person person : people) {
      Document document = new StandardDocument(person);
      peopleDocs.add(document);
    }

    Stopwatch stopwatch = Stopwatch.createStarted();
    client.write(peopleDocs);
    System.out.println("Write: " + stopwatch.elapsed(TimeUnit.MILLISECONDS));
  }

  @Test
  public void testGet() {

  }

  @Test
  public void testDelete() {

  }
}