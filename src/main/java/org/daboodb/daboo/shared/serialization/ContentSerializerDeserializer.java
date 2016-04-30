package org.daboodb.daboo.shared.serialization;

import org.daboodb.daboo.shared.Document;

/**
 * General interface for serializing and deserializing serialized to and from byte arrays
 */
public interface ContentSerializerDeserializer {

  byte[] serialize(Document document);

  Document deserialize(Class clazz, byte[] contentBytes);
}
