package org.daboodb.daboo.testutil;

import org.daboodb.daboo.shared.AbstractDocument;
import org.daboodb.daboo.shared.Document;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

/**
 * A test document content class
 */
public class Company extends AbstractDocument {

  private String name;

  public Company(String name) {
    this.name = name;
  }

  public Company() {
  }

  @Override
  public String getDocumentType() {
    return "Company";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Company company = (Company) o;
    return Objects.equals(name, company.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  public void setName(String name) {
    this.name = name;
  }
}
