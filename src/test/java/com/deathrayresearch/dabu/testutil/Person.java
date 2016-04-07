package com.deathrayresearch.dabu.testutil;

import com.deathrayresearch.dabu.shared.DocumentContents;
import com.google.common.base.Stopwatch;
import io.codearte.jfairy.Fairy;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class Person implements DocumentContents {
  byte[] key = UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);
  String fName;
  String lName;
  String city;
  String state;
  String zip;
  LocalDate birthday;
  int height;
  int weight;
  char gender;

  public static List<Person> createPeoples(int quantity) {

    Stopwatch stopwatch = Stopwatch.createStarted();

    Person person = new Person();
    Fairy fairy = Fairy.create();

    io.codearte.jfairy.producer.person.Person p;

    List<Person> people = new ArrayList<>();
    for (int r = 0; r < quantity; r++) {
      p = fairy.person();
      person.fName = p.firstName();
      person.lName = p.lastName();
      person.birthday = LocalDate.parse(p.dateOfBirth().toLocalDate().toString());
      person.city = p.getAddress().getCity();
      person.zip = p.getAddress().getPostalCode();
      person.state = fairy.baseProducer().randomElement(usStateArray);
      person.weight = fairy.baseProducer().randomBetween(65, 280);
      person.height = fairy.baseProducer().randomBetween(64, 78);
      person.gender = p.sex().name().charAt(0);
      people.add(person);
    }
    System.out.println("Time to generate " + stopwatch.elapsed(TimeUnit.SECONDS));
    return people;
  }

  private static String[] usStateArray = {"Alabama","Alaska","Arizona","Arkansas","California","Colorado","Connecticut",
      "Delaware","District Of Columbia","Florida","Georgia","Hawaii","Idaho","Illinois","Indiana","Iowa","Kansas",
      "Kentucky","Louisiana","Maine","Maryland","Massachusetts","Michigan","Minnesota","Mississippi","Missouri",
      "Montana","Nebraska","Nevada","New Hampshire","New Jersey","New Mexico","New York","North Carolina",
      "North Dakota","Ohio","Oklahoma","Oregon","Pennsylvania","Rhode Island","South Carolina","South Dakota",
      "Tennessee","Texas","Utah","Vermont","Virginia","Washington","West Virginia","Wisconsin","Wyoming"};

  private static List<String> usStates = Arrays.asList(usStateArray);

  @Override
  public byte[] getKey() {
    return new byte[0];
  }
}
