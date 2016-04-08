package com.deathrayresearch.dabu.client.serialization;

import com.deathrayresearch.dabu.testutil.Person;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class JsonSerializerDeserializerTest {

  @Test
  public void testSerialize() {
    Person person = Person.createPeoples(1).get(0);

    byte[] serializedPerson = JsonSerializerDeserializer.get().serialize(person);
    assertEquals(person, JsonSerializerDeserializer.get().deserialize(Person.class, serializedPerson));
  }
}