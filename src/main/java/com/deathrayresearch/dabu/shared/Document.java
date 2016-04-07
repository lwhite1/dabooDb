package com.deathrayresearch.dabu.shared;

import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;

/**
 *  A document is the basic unit of persistence
 */
public interface Document {

  static Gson GSON = new Gson();

  /**
   * Returns the primary key by which this document can be found in the db
   */
  byte[] key();

  /**
   * Returns an integer representing the number of times the document has been saved.
   * This value is used for optimistic lock checking
   */
  int documentVersion();

  /**
   * Returns the document's contents, a domain model object as a byte array
   */
  byte[] getContents();

  /**
   * Returns a String naming the kind of document represented by the content
   */
  String getContentType();

  int schemaVersion();

  default byte[] marshall() {
    return GSON.toJson(this).getBytes(StandardCharsets.UTF_8);
  }

}
