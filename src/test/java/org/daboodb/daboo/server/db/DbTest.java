package org.daboodb.daboo.server.db;

import com.google.common.primitives.SignedBytes;
import com.google.protobuf.ByteString;
import org.daboodb.daboo.generated.protobufs.Request;
import org.daboodb.daboo.shared.Document;
import org.daboodb.daboo.testutil.BasicTest;
import org.daboodb.daboo.testutil.Person;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

/**
 * Tests for all implementations of Db
 */
public class DbTest extends BasicTest {

  private final Map<byte[], byte[]> documentMap = new TreeMap<>(SignedBytes.lexicographicalComparator());
  private final List<ByteString> keys = new ArrayList<>();
  private final List<Request.Document> documentList = new ArrayList<>();
  private Db db;

  @Override
  @After
  public void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Tests for the concurrent SkipList-backed db
   */
  @Test
  public void testBatchWriteGetDelete1() throws Exception {
    setupPeople(10);

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
      assertNotNull(doc);
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
    setupPeople(10);

    db = new OnHeapRBTreeDb();
    List<ByteString> docs;

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
      assertNotNull(doc);
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
    setupPeople(10);
    // setup db and write docs
    db = new OffHeapBTreeDb();
    db.write(documentMap);
    // assert that the db has the right number of records
    assertEquals(documentMap.size(), db.size());

    // get all the docs in one request
    List<ByteString> docs;
    docs = db.get(keys);
    // assert we got the right number of records
    assertEquals(keys.size(), docs.size());

    // make sure they came back correctly and aren't corrupted
    for (ByteString value : docs) {
      Request.Document doc = Request.Document.parseFrom(value);
      assertNotNull(doc);
    }

    // delete them from the db
    db.delete(documentList);

    // assert that the db is now empty
    assertEquals(0, db.size());
  }


  @Test
  public void testImportExport1() {

    setupPeople(100);
    db = new OffHeapBTreeDb();
    db.write(documentMap);
    File file = Paths.get("/tmp/daboodb/testdata/exportTest").toFile();
    db.exportDocuments(file);
    assertTrue(file.exists());

    File file2 = Paths.get("/tmp/daboodb/testdata/exportTest").toFile();
    assertTrue(file.exists());

    Db db2 = new OffHeapBTreeDb();
    db2.importDocuments(file2);
    assertEquals(db.size(), db2.size());
    assertEquals(100, db2.size());
  }

  @Test
  public void testImportExport2() {
    setupPeople(100);
    db = new OnHeapRBTreeDb();
    db.write(documentMap);
    File file = Paths.get("/tmp/daboodb/testdata/exportTest").toFile();
    db.exportDocuments(file);
    assertTrue(file.exists());

    File file2 = Paths.get("/tmp/daboodb/testdata/exportTest").toFile();
    assertTrue(file.exists());

    Db db2 = new OnHeapRBTreeDb();
    db2.importDocuments(file2);
    assertEquals(db.size(), db2.size());
    assertEquals(100, db2.size());
  }

  @Test
  public void testImportExport3() {
    setupPeople(100);
    db = new OnHeapConcurrentSkipListDb();
    db.write(documentMap);
    File file = Paths.get("/tmp/daboodb/testdata/exportTest").toFile();
    db.exportDocuments(file);
    assertTrue(file.exists());

    File file2 = Paths.get("/tmp/daboodb/testdata/exportTest").toFile();
    assertTrue(file.exists());

    Db db2 = new OnHeapConcurrentSkipListDb();
    db2.importDocuments(file2);
    assertEquals(db.size(), db2.size());
    assertEquals(100, db2.size());
  }

  private void setupPeople(int count) {
    List<Person> people = Person.createPeoples(count);
    keys.clear();
    documentList.clear();
    documentMap.clear();

    for (Person person : people) {
      Request.Document doc = Request.Document.newBuilder()
          .setContentBytes(ByteString.copyFrom(person.serialized()))
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
}