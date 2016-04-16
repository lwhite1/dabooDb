package org.dabudb.dabu.server.db;

import com.google.common.primitives.SignedBytes;
import com.google.protobuf.ByteString;
import org.dabudb.dabu.generated.protobufs.Request;
import org.dabudb.dabu.shared.Document;
import org.dabudb.dabu.shared.DocumentContents;
import org.dabudb.dabu.shared.StandardDocument;
import org.dabudb.dabu.testutil.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

/**
 * Tests for all implementations of Db
 */
public class DbTest {

  private Map<byte[], byte[]> documentMap;
  private final List<ByteString> keys = new ArrayList<>();
  private final List<Request.Document> documentList = new ArrayList<>();
  private Db db;

  @Before
  public void setUp() throws Exception {

    documentMap = new TreeMap<>(SignedBytes.lexicographicalComparator());

    List<Person> people = Person.createPeoples(10);

    for (Person person : people) {
      Document document = new StandardDocument(person);
      Request.Document doc = Request.Document.newBuilder()
          .setContentBytes(ByteString.copyFrom(document.contents()))
          .setContentClass(person.getClass().getCanonicalName())
          .setContentType(person.getContentType())
          .setKey(ByteString.copyFrom(person.getKey()))
          .setInstanceVersion(0)
          .setSchemaVersion(1)
          .build();

      documentMap.put(doc.getKey().toByteArray(), doc.toByteArray());
      keys.add(doc.getKey());
      documentList.add(doc);
    }
  }

  /**
   * Tests for the concurrent SkipList-backed db
   */
  @Test
  public void testBatchWriteGetDelete1() throws Exception {

    // Create the db and write a batch of documents
    db = new OnHeapConcurrentSkipListDb();
    db.write(documentMap);

    // assert that the database has the right number of records
    assertEquals(documentMap.size(), db.size());

    // get the docs and assert that we got the right number
    List<ByteString> docs = db.get(keys);
    assertEquals(keys.size(), docs.size());

    // confirm that what we got are real, uncorrupted documents
    for (ByteString value : docs) {
      Request.Document doc = Request.Document.parseFrom(value);
    }

    // delete them all and assert the db is empty
    db.delete(documentList);
    assertEquals(0, db.size());
  }

  /**
   * Tests for the standard TreeMap-backed db
   */
  @Test
  public void testBatchWriteGetDelete2() throws Exception {

    db = new OnHeapRBTreeDb();
    List<ByteString> docs = db.get(keys);

    db = new OnHeapRBTreeDb();
    db.write(documentMap);

    // make sure the db has the right number of documents
    assertEquals(documentMap.size(), db.size());

    // get the records and make sure you get the right number of results
    docs = db.get(keys);
    assertEquals(keys.size(), docs.size());

    // makes sure none of the returned values is corrupted
    for (ByteString value : docs) {
      Request.Document doc = Request.Document.parseFrom(value);
    }

    // delete all the docs
    db.delete(documentList);

    // assert that the db is now empty
    assertEquals(0, db.size());
  }

  /**
   * Tests using the off-heap btree-backed db
   */
  @Test
  public void testBatchWriteGetDelete3() throws Exception {

    // setup db and write docs
    db = new OffHeapBTreeDb();
    db.write(documentMap);
    // assert that the db has the right number of records
    assertEquals(documentMap.size(), db.size());

    // get all the docs in one request
    List<ByteString> docs = db.get(keys);
    docs = db.get(keys);
    // assert we got the right number of records
    assertEquals(keys.size(), docs.size());

    // make sure they came back correctly and aren't corrupted
    for (ByteString value : docs) {
      Request.Document doc = Request.Document.parseFrom(value);
    }

    // delete them from the db
    db.delete(documentList);

    // assert that the db is now empty
    assertEquals(0, db.size());
  }


  @Test
  public void testExport() {

  }
}