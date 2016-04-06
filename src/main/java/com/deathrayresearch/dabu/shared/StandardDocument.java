package com.deathrayresearch.dabu.shared;

/**
 *
 */
public class StandardDocument implements Document {

  private final DocumentContents contents;
  private final int documentVersion;

  public StandardDocument(DocumentContents contents) {
    this.contents = contents;
    this.documentVersion = 0;
  }

  @Override
  public byte[] key() {
    return new byte[0];
  }

  @Override
  public DocumentContents getContents() {
    return contents;
  }

  @Override
  public int documentVersion() {
    return documentVersion;
  }
}
