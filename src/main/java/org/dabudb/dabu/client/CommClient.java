package org.dabudb.dabu.client;

import org.dabudb.dabu.shared.msg.reply.DeleteReply;
import org.dabudb.dabu.shared.msg.request.DocDeleteRequest;
import org.dabudb.dabu.shared.msg.request.DocWriteRequest;
import org.dabudb.dabu.shared.msg.request.DocsDeleteRequest;
import org.dabudb.dabu.shared.msg.reply.DocsGetReply;
import org.dabudb.dabu.shared.msg.request.DocsGetRequest;
import org.dabudb.dabu.shared.msg.request.DocsWriteRequest;
import org.dabudb.dabu.shared.msg.reply.DocGetReply;
import org.dabudb.dabu.shared.msg.request.DocGetRequest;
import org.dabudb.dabu.shared.msg.reply.WriteReply;

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
