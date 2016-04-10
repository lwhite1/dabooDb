package com.deathrayresearch.dabu.shared;

import com.deathrayresearch.dabu.shared.compression.CompressionType;
import com.deathrayresearch.dabu.shared.serialization.ContentSerializerType;
import com.deathrayresearch.dabu.shared.encryption.EncryptionType;
import com.deathrayresearch.dabu.testutil.Person;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class ContentsPipeTest {

  private Person person = Person.createPeoples(1).get(0);

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