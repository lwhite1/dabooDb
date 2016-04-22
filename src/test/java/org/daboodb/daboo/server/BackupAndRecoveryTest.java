package org.daboodb.daboo.server;

import org.daboodb.daboo.generated.protobufs.Request;
import org.daboodb.daboo.shared.DocumentUtils;
import org.daboodb.daboo.shared.StandardDocument;
import org.daboodb.daboo.testutil.BasicTest;
import org.daboodb.daboo.testutil.Person;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.daboodb.daboo.shared.RequestUtils.*;
import static org.junit.Assert.assertEquals;

/**
 * Tests db backup and recovery
 */
public class BackupAndRecoveryTest extends BasicTest {

  @BeforeClass
  public static void runOnce() throws Exception {
    removeRecursive(Paths.get(TEST_DATA_FOLDER));
  }

  @Test
  public void testBackupAndRecovery() {

    // create a db
    Database server = Database.get();
    server.clear();

    // Create some data and process the write
    List<Request.Document> documentList = new ArrayList<>();
    List<Person> people = Person.createPeoples(2);
    Person person = people.get(0);
    StandardDocument standardDocument = new StandardDocument(person);
    documentList.add(DocumentUtils.getDocument(standardDocument));

    Request.Header header = getHeader();
    Request.WriteRequestBody body = getWriteRequestBody(documentList);
    Request.WriteRequest writeRequest = getWriteRequest(header, body);
    server.handleRequest(writeRequest, writeRequest.toByteArray());

    // make a backup
    File backupFile = Paths.get(BasicTest.TEST_DATA_FOLDER, "backupTest").toFile();
    server.backup(backupFile);

    // add more data
    Person person2 = people.get(1);
    StandardDocument standardDocument2 = new StandardDocument(person2);
    documentList.clear();
    documentList.add(DocumentUtils.getDocument(standardDocument2));

    body = getWriteRequestBody(documentList);
    writeRequest = getWriteRequest(header, body);
    server.handleRequest(writeRequest, writeRequest.toByteArray());

    server = Database.get();

    backupFile = Paths.get(BasicTest.TEST_DATA_FOLDER, "backupTest").toFile();

    server.recoverFromBackup(backupFile);
    assertEquals(2, server.size());

  }
}
