package org.dabudb.dabu.example.embedded;

import com.google.common.base.Stopwatch;
import org.dabudb.dabu.client.DbClient;
import org.dabudb.dabu.shared.Document;
import org.dabudb.dabu.shared.StandardDocument;
import org.dabudb.dabu.testutil.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

/**
 *
 */
public class EmbeddedTest {

  public static void main(String[] args) {

    //ClientSettings clientSettings = new Properties();
    DbClient client = DbClient.get();

    int testCount = 1_000_000;

    List<Person> people = Person.createPeoples(testCount);
    List<Document> peopleDocs = new ArrayList<>();
    List<byte[]> keys = new ArrayList<>();
    for (Person person : people) {
      Document document = new StandardDocument(person);
      peopleDocs.add(document);
      keys.add(person.getKey());
    }

    System.out.println("Test data created");

    Stopwatch stopwatch = Stopwatch.createStarted();
    for (Document document : peopleDocs) {
      client.write(document);
    }
    System.out.println("Wrote " + testCount + " objects in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");

    stopwatch.reset().start();
    for (byte[] key : keys) {
      Document document = client.get(key);
      assertNotNull(document);
    }
    System.out.println("Read " + testCount + " objects in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");

  }
}
