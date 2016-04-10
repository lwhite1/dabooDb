package org.dabudb.dabu.client;

import org.dabudb.dabu.shared.msg.DeleteReply;
import org.dabudb.dabu.shared.msg.DocDeleteRequest;
import org.dabudb.dabu.shared.msg.DocWriteRequest;
import org.dabudb.dabu.shared.msg.DocsDeleteRequest;
import org.dabudb.dabu.shared.msg.DocsGetReply;
import org.dabudb.dabu.shared.msg.DocsGetRequest;
import org.dabudb.dabu.shared.msg.DocsWriteRequest;
import org.dabudb.dabu.shared.msg.DocGetReply;
import org.dabudb.dabu.shared.msg.DocGetRequest;
import org.dabudb.dabu.shared.msg.WriteReply;

/**
 *
 */
public interface CommClient {

  DocGetReply sendRequest(DocGetRequest request);

  DocsGetReply sendRequest(DocsGetRequest request);

  DeleteReply sendRequest(DocDeleteRequest request);

  DeleteReply sendRequest(DocsDeleteRequest request);

  WriteReply sendRequest(DocWriteRequest request);

  WriteReply sendRequest(DocsWriteRequest request);
}
