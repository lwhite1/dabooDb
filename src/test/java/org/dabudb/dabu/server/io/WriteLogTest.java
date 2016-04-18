package org.dabudb.dabu.server.io;

import org.dabudb.dabu.shared.Document;
import org.dabudb.dabu.shared.DocumentContents;
import org.dabudb.dabu.shared.DocumentUtils;
import org.dabudb.dabu.shared.StandardDocument;
import org.dabudb.dabu.shared.RequestUtils;
import org.dabudb.dabu.generated.protobufs.Request;
import org.dabudb.dabu.testutil.BasicTest;
import org.dabudb.dabu.testutil.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
      count++;
    }
    assertEquals(1, count);
    writeLog.close();

    writeLog.clear();
    assertFalse(writeLog.hasNext());
  }
}