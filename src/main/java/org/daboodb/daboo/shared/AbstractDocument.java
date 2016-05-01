package org.daboodb.daboo.shared;

import org.daboodb.daboo.client.ClientSettings;
import org.daboodb.daboo.server.ServerSettings;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * A basic document implementation
 */
public class AbstractDocument implements Document {

  private volatile static ContentsPipe contentsPipe;

  private static ContentsPipe getContentsPipe() {
    if (contentsPipe == null) {
      contentsPipe = ClientSettings.getInstance().getContentsPipe();
    }
    return contentsPipe;
  }

  /**
   * An int that tracks the number of times this document has been saved. It is used in the implementation of optimistic locking.
   */
  private int instanceVersion;

  /**
   * The version of the global schema in use at the time this document was saved
   */
  private short schemaVersion = 0;

  /**
   * A byte array that uniquely identifies this document, amongst all documents in the store. There can only be one
   * document with a given key in the store at any time
   */
  private byte[] key;

  /**
   * The kind of document represented by the serialized
   */
  private String documentType;

  /**
   * The fully qualified class name of this document.
   */
  private String documentClass;

  /**
   * Constructs and returns a Document initialized with a random UUID key, and a documentType based on the class name
   */
  public AbstractDocument() {
    this.documentClass = this.getClass().getCanonicalName();
    this.documentType = this.getClass().getSimpleName();
    this.instanceVersion = 0;
    key = UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);
  }

  /**
   * Constructs and returns a Document initialized with a documentType based on the class name
   *
   * @param key a byte[] that uniquely identifies this document, amongst all the documents in the store
   */
  public AbstractDocument(byte[] key) {
    this.documentClass = this.getClass().getCanonicalName();
    this.documentType = this.getClass().getSimpleName();
    this.instanceVersion = 0;
    this.key = key;
  }

  /**
   * Constructs and returns a Document initialized with a random UUID key
   *
   * @param documentType A string that serves a purpose similar to a 'table name' in sql. In the future, it will allow
   *                     queries that restrict their results to a particular type of document, such as an "INVOICE"
   */
  public AbstractDocument(String documentType) {
    this.documentClass = this.getClass().getCanonicalName();
    this.documentType = documentType;
    this.instanceVersion = 0;
    key = UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public byte[] serialized() {
    return ServerSettings.getInstance().getDocumentSerializer().documentToBytes(this);
  }

  public String getDocumentClass() {
    return documentClass;
  }

  @Override
  public int schemaVersion() {
    return schemaVersion;
  }

  @Override
  public int instanceVersion() {
    return instanceVersion;
  }

  public void setInstanceVersion(int instanceVersion) {
    this.instanceVersion = instanceVersion;
  }

  @Override
  public void setKey(byte[] key) {
    this.key = key;
  }

  public void setDocumentType(String documentType) {
    this.documentType = documentType;
  }

  public void setDocumentClass(String documentClass) {
    this.documentClass = documentClass;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("AbstractDocument{");
    sb.append("instanceVersion=").append(instanceVersion);
    sb.append(", schemaVersion=").append(schemaVersion);
    sb.append(", documentClass=").append(documentClass);
    sb.append(", documentType='").append(documentType).append('\'');
    sb.append('}');
    return sb.toString();
  }

  /**
   * Sets the global json schema version that was in effect (and thus used to serialize the object) when this
   * document was saved
   *
   * Note: This should generally NOT be used by client code
   *
   * @param schemaVersion A number indicating what schema version was used to serialize the document
   */
  @Override
  public void setSchemaVersion(short schemaVersion) {
    this.schemaVersion = schemaVersion;
  }

  /**
   * Returns the key to be used as the primary database key for this object
   */
  @Override
  public byte[] getKey() {
    return key;
  }

  /**
   * Returns a string, analogous to a database table name, that describes what kind of object this is
   */
  public String getDocumentType() {
    return documentType;
  }
}
