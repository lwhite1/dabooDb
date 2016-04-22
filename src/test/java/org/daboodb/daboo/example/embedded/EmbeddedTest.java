package org.daboodb.daboo.example.embedded;

import com.google.common.base.Stopwatch;
import org.daboodb.daboo.client.DbClient;
import org.daboodb.daboo.shared.Document;
import org.daboodb.daboo.shared.StandardDocument;
import org.daboodb.daboo.testutil.BasicTest;
import org.daboodb.daboo.testutil.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

/**
 * Executes a series of tests using embedded mode
 */
class EmbeddedTest extends BasicTest {

  public static void main(String[] args) throws Exception {

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
