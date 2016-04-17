package org.dabudb.dabu.server.io;

import com.google.protobuf.ByteString;
import org.dabudb.dabu.generated.protobufs.Request;
import org.dabudb.dabu.server.DbServer;
import org.dabudb.dabu.shared.Document;
import org.dabudb.dabu.shared.DocumentUtils;
import org.dabudb.dabu.shared.StandardDocument;
import org.dabudb.dabu.testutil.BasicTest;
import org.dabudb.dabu.testutil.Person;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.dabudb.dabu.shared.msg.MessageUtils.*;
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
    DbServer server = DbServer.get();
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

    server = DbServer.get();

    backupFile = Paths.get(BasicTest.TEST_DATA_FOLDER, "backupTest").toFile();

    server.recoverFromBackup(backupFile);
    assertEquals(2, server.size());

  }
}
