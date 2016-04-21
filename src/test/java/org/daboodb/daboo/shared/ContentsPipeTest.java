package org.daboodb.daboo.shared;

import org.daboodb.daboo.shared.compression.CompressionType;
import org.daboodb.daboo.shared.serialization.ContentSerializerType;
import org.daboodb.daboo.shared.encryption.EncryptionType;
import org.daboodb.daboo.testutil.Person;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for ContentsPipe
 */
public class ContentsPipeTest {

  private final Person person = Person.createPeoples(1).get(0);

  @Test
  public void testContentsToBytes() {

    ContentsPipeDefinition definition = new ContentsPipeDefinition(
        ContentSerializerType.JSON,
        CompressionType.NONE,
        EncryptionType.NONE);

    ContentsPipe pipe1 = ContentsPipe.create(definition, "");

    byte[] output = pipe1.contentsToBytes(person);
    Person person1 = (Person) pipe1.bytesToContents(Person.class, output);
    assertEquals(person, person1);
  }

  @Test
  public void testContentsToBytes1() {
    ContentsPipeDefinition definition = new ContentsPipeDefinition(
        ContentSerializerType.JSON,
        CompressionType.SNAPPY,
        EncryptionType.NONE);

    ContentsPipe pipe1 = ContentsPipe.create(definition, "");

    byte[] output = pipe1.contentsToBytes(person);
    Person person1 = (Person) pipe1.bytesToContents(Person.class, output);
    assertEquals(person, person1);
  }

  @Test
  public void testContentsToBytes2() {
    ContentsPipeDefinition definition = new ContentsPipeDefinition(
        ContentSerializerType.JSON,
        CompressionType.SNAPPY,
        EncryptionType.STANDARD);

    ContentsPipe pipe1 = ContentsPipe.create(definition, "password");

    byte[] output = pipe1.contentsToBytes(person);
    Person person1 = (Person) pipe1.bytesToContents(Person.class, output);
    assertEquals(person, person1);
  }
}