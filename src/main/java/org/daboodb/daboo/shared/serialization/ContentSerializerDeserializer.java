package org.daboodb.daboo.shared.serialization;

import org.daboodb.daboo.shared.DocumentContents;

/**
 * General interface for serializing and deserializing contents to and from byte arrays
 */
public interface ContentSerializerDeserializer {

  byte[] serialize(DocumentContents contents);

  //TODO(lwhite): Can we improve this by specifying that clazz extends DocumentContents
  DocumentContents deserialize(Class clazz, byte[] contentBytes);
}
