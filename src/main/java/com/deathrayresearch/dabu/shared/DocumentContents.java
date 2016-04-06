package com.deathrayresearch.dabu.shared;

/**
 *
 */
public interface DocumentContents {

  String getType();

  byte[] marshall();

  byte[] getKey();
}
