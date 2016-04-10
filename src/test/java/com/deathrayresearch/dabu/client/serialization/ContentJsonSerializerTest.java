package com.deathrayresearch.dabu.client.serialization;

import com.deathrayresearch.dabu.shared.serialization.ContentJsonSerializer;
import com.deathrayresearch.dabu.testutil.Person;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class ContentJsonSerializerTest {

  @Test
  public void testSerialize() {
    Person person = Person.createPeoples(1).get(0);

    byte[] serializedPerson = ContentJsonSerializer.get().serialize(person);
    assertEquals(person, ContentJsonSerializer.get().deserialize(Person.class, serializedPerson));
  }
}