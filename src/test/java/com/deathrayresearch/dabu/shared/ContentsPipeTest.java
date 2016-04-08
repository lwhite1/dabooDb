package com.deathrayresearch.dabu.shared;

import com.deathrayresearch.dabu.client.serialization.JsonSerializerDeserializer;
import com.deathrayresearch.dabu.shared.compression.NullCompressor;
import com.deathrayresearch.dabu.shared.compression.SnappyCompressor;
import com.deathrayresearch.dabu.shared.encryption.NullEncryptor;
import com.deathrayresearch.dabu.shared.encryption.StandardTextEncyptor;
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
    ContentsPipe pipe1 = new ContentsPipe(
        NullCompressor.get(),
        NullEncryptor.get(),
        JsonSerializerDeserializer.get());

    byte[] output = pipe1.contentsToBytes(person);
    Person person1 = (Person) pipe1.bytesToContents(Person.class, output);
    assertEquals(person, person1);
  }

  @Test
  public void testContentsToBytes1() {
    ContentsPipe pipe1 = new ContentsPipe(
        SnappyCompressor.get(),
        NullEncryptor.get(),
        JsonSerializerDeserializer.get());

    byte[] output = pipe1.contentsToBytes(person);
    Person person1 = (Person) pipe1.bytesToContents(Person.class, output);
    assertEquals(person, person1);
  }

  @Test
  public void testContentsToBytes2() {
    ContentsPipe pipe1 = new ContentsPipe(
        SnappyCompressor.get(),
        new StandardTextEncyptor("password"),
        JsonSerializerDeserializer.get());

    byte[] output = pipe1.contentsToBytes(person);
    Person person1 = (Person) pipe1.bytesToContents(Person.class, output);
    assertEquals(person, person1);
  }
}