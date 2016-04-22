package org.daboodb.daboo.shared;

import org.daboodb.daboo.shared.compression.CompressionType;
import org.daboodb.daboo.shared.encryption.EncryptionType;
import org.daboodb.daboo.shared.serialization.ContentSerializerType;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for ContentsPipeDefinition
 */
public class ContentsPipeDefinitionTest {

  private final ContentsPipeDefinition d1 = new ContentsPipeDefinition(
      ContentSerializerType.NONE,
      CompressionType.NONE,
      EncryptionType.NONE
  );

  private final ContentsPipeDefinition d1Alt = new ContentsPipeDefinition(
      ContentSerializerType.NONE,
      CompressionType.NONE,
      EncryptionType.NONE
  );

  private final ContentsPipeDefinition d2 = new ContentsPipeDefinition(
      ContentSerializerType.JSON,
      CompressionType.SNAPPY,
      EncryptionType.STANDARD
  );

  @Test
  public void testGetTypeInfo() {
    assertEquals(ContentSerializerType.NONE, d1.getContentSerializerType());
    assertEquals(CompressionType.NONE, d1.getCompressionType());
    assertEquals(EncryptionType.NONE, d1.getEncryptionType());
  }

  @Test
  public void testEquals() {
    assertEquals(d1, d1);
    assertEquals(d1, d1Alt);
    assertEquals(d2, d2);
    assertNotEquals(d1, d2);
  }

  @Test
  public void testHashCode() {
    assertEquals(d1.hashCode(), d1.hashCode());
    assertEquals(d1.hashCode(), d1Alt.hashCode());
    assertEquals(d2.hashCode(), d2.hashCode());
    assertNotEquals(d1.hashCode(), d2.hashCode());
  }

  @Test
  public void testToString() {
    String output =
        "ContentsPipeDefinition{contentSerializerType=NONE, compressionType=NONE, encryptionType=NONE}";
    assertEquals(output, d1.toString());
  }
}