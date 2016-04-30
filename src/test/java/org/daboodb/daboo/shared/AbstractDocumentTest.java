package org.daboodb.daboo.shared;

import org.daboodb.daboo.client.DbClient;
import org.junit.Test;

/**
 *
 */
public class AbstractDocumentTest {

  @Test
  public void testBasic() throws Exception {
    DbClient client = DbClient.get();
    AbstractDocument abstractDocument = new TestDocument("larry", 55);
    client.write(abstractDocument);
    System.out.println(abstractDocument);
  }

  static class TestDocument extends AbstractDocument {
    private final String name;
    private final int age;

    public TestDocument(String name, int age) {
      this.name = name;
      this.age = age;
    }

    public String getName() {
      return name;
    }

    public int getAge() {
      return age;
    }
  }
}