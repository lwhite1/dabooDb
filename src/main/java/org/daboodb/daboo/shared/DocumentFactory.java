package org.daboodb.daboo.shared;

import javax.annotation.Nullable;

/**
 * Handles instantiation of documents using an appropriate class
 */
final class DocumentFactory {

  private DocumentFactory() {}

  @Nullable
  static Document documentForClass(Class cls) throws IllegalAccessException, InstantiationException {
    Document document;

    if (cls == Document.class) {
      document = new AbstractDocument();
    } else {
      document = (Document) cls.newInstance();
    }

    return document;
  }
}
