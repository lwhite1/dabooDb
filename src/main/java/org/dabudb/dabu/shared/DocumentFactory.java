package org.dabudb.dabu.shared;

import javax.annotation.Nullable;

/**
 *
 */
public final class DocumentFactory {

  private DocumentFactory() {}

  @Nullable
  public static Document documentForClass(Class cls) {
    Document document = null;

    if (cls == StandardDocument.class) {
      document = new StandardDocument();
    }

    return document;
  }
}
