package com.deathrayresearch.dabu.shared;

import java.util.Arrays;

/**
 * A basic document implementation
 */
public class StandardDocument implements Document {

  private final byte[] contents;

  private final int documentVersion;

  private final short schemaVersion = 0;

  private boolean deleted = false;

  /** The kind of document represented by the contents */
  private final String contentType;

  public StandardDocument(DocumentContents contents) {
    this.contents = contents.marshall();
    this.documentVersion = 0;
    this.contentType = contents.getType();
  }

  public StandardDocument(Document other) {
    this.contents = other.getContents();

    //TODO(lwhite): Thread saftey
    this.documentVersion = other.documentVersion() + 1;

    this.contentType = other.getContentType();
  }

  @Override
  public byte[] key() {
    return new byte[0];
  }

  @Override
  public byte[] getContents() {
    return contents;
  }

  @Override
  public String getContentType() {
    return contentType;
  }

  @Override
  public int schemaVersion() {
    return schemaVersion;
  }

  @Override
  public int documentVersion() {
    return documentVersion;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("StandardDocument{");
    sb.append("contents=").append(Arrays.toString(contents));
    sb.append(", documentVersion=").append(documentVersion);
    sb.append(", schemaVersion=").append(schemaVersion);
    sb.append(", deleted=").append(deleted);
    sb.append(", contentType='").append(contentType).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
