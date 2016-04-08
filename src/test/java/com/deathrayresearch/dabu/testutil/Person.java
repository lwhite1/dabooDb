package com.deathrayresearch.dabu.testutil;

import com.deathrayresearch.dabu.shared.DocumentContents;
import io.codearte.jfairy.Fairy;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 *  A class representing a person that can be used as a test content for the db
 */
public class Person implements DocumentContents {

  private byte[] key = UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);
  private String fName;
  private String lName;
  private String city;
  private String state;
  private String zip;
  private LocalDate birthday;
  private int height;
  private int weight;
  private char gender;

  public static List<Person> createPeoples(int quantity) {

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
    return people;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return height == person.height &&
        weight == person.weight &&
        gender == person.gender &&
        Arrays.equals(key, person.key) &&
        Objects.equals(fName, person.fName) &&
        Objects.equals(lName, person.lName) &&
        Objects.equals(city, person.city) &&
        Objects.equals(state, person.state) &&
        Objects.equals(zip, person.zip) &&
        Objects.equals(birthday, person.birthday);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, fName, lName, city, state, zip, birthday, height, weight, gender);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Person{");
    sb.append("key=").append(Arrays.toString(key));
    sb.append(", fName='").append(fName).append('\'');
    sb.append(", lName='").append(lName).append('\'');
    sb.append(", city='").append(city).append('\'');
    sb.append(", state='").append(state).append('\'');
    sb.append(", zip='").append(zip).append('\'');
    sb.append(", birthday=").append(birthday);
    sb.append(", height=").append(height);
    sb.append(", weight=").append(weight);
    sb.append(", gender=").append(gender);
    sb.append('}');
    return sb.toString();
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

  public String getfName() {
    return fName;
  }

  public String getlName() {
    return lName;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getZip() {
    return zip;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public int getHeight() {
    return height;
  }

  public int getWeight() {
    return weight;
  }

  public char getGender() {
    return gender;
  }

  public static String[] getUsStateArray() {
    return usStateArray;
  }

  public static List<String> getUsStates() {
    return usStates;
  }
}
