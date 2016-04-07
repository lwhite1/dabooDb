package com.deathrayresearch.dabu.testutil;

import com.deathrayresearch.dabu.shared.DocumentContents;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * A test document content class
 */
public class Company implements DocumentContents {

  String name;
  byte[] key;

  public Company(String name) {
    this.name = name;
    key = UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public String getType() {
    return getClass().getCanonicalName();
  }

  @Override
  public byte[] getKey() {
    return key;
  }
}
