package org.daboodb.daboo.server.io;

import org.daboodb.daboo.shared.Document;
import org.daboodb.daboo.shared.DocumentContents;
import org.daboodb.daboo.shared.DocumentUtils;
import org.daboodb.daboo.shared.StandardDocument;
import org.daboodb.daboo.shared.RequestUtils;
import org.daboodb.daboo.generated.protobufs.Request;
import org.daboodb.daboo.testutil.BasicTest;
import org.daboodb.daboo.testutil.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 *  Tests for WriteLog
 */
public class WriteLogTest extends BasicTest {

  // setup data to create a request
  private final DocumentContents contents = Person.createPeoples(1).get(0);
  private final Document document = new StandardDocument(contents);
  private final Request.Document doc = DocumentUtils.getDocument(document);
  private final Request.Header header = RequestUtils.getHeader();
  private final Request.WriteRequestBody body = RequestUtils.getWriteRequestBody(doc);
  private final Request.WriteRequest writeRequest = RequestUtils.getWriteRequest(header, body);
  private WriteLog writeLog;

  @Before
  public void setUp() throws Exception {
    super.tearDown();
    writeLog = WriteLog.getInstance(Paths.get(TEST_DATA_FOLDER, "writeLogTest").toFile());
  }

  @Override
  @After
  public void tearDown() throws Exception {
    super.tearDown();
  }

  @Test
  public void testLogRequest() throws Exception {
    writeLog.clear();
    writeLog.log(writeRequest.toByteArray());

    int count = 0;
    while (writeLog.hasNext()) {
      byte[] bytes = writeLog.next();
      Request.WriteRequest fromLog = Request.WriteRequest.parseFrom(bytes);
      assertNotNull(fromLog);
      count++;
    }
    assertEquals(1, count);
    writeLog.close();

    writeLog.clear();
    assertFalse(writeLog.hasNext());
  }
}