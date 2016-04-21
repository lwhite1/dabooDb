package org.daboodb.daboo.testutil;

import org.daboodb.daboo.shared.DocumentContents;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

/**
 * A test document content class
 */
public class Company implements DocumentContents {

  private String name;
  private final byte[] key;

  public Company(String name) {
    this.name = name;
    key = UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public byte[] getKey() {
    return key;
  }

  @Override
  public String getContentType() {
    return "Company";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Company company = (Company) o;
    return Objects.equals(name, company.name) &&
        Arrays.equals(key, company.key);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, key);
  }

  public void setName(String name) {
    this.name = name;
  }
}
