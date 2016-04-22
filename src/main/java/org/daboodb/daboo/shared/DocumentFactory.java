package org.daboodb.daboo.shared;

import javax.annotation.Nullable;

/**
 * Handles instantiation of documents using an appropriate class
 */
final class DocumentFactory {

  private DocumentFactory() {}

  @Nullable
  static Document documentForClass(Class cls) {
    Document document = null;

    if (cls == StandardDocument.class) {
      document = new StandardDocument();
    }

    return document;
  }
}
