package com.deathrayresearch.dabu.client;

import com.deathrayresearch.dabu.shared.msg.DeleteReply;
import com.deathrayresearch.dabu.shared.msg.DocDeleteRequest;
import com.deathrayresearch.dabu.shared.msg.DocWriteRequest;
import com.deathrayresearch.dabu.shared.msg.DocsDeleteRequest;
import com.deathrayresearch.dabu.shared.msg.DocsGetRequest;
import com.deathrayresearch.dabu.shared.msg.DocsWriteRequest;
import com.deathrayresearch.dabu.shared.msg.GetReply;
import com.deathrayresearch.dabu.shared.msg.DocGetRequest;
import com.deathrayresearch.dabu.shared.msg.Reply;
import com.deathrayresearch.dabu.shared.msg.Request;
import com.deathrayresearch.dabu.shared.msg.WriteReply;

/**
 *
 */
public interface CommClient {

  GetReply sendRequest(DocGetRequest request);

  GetReply sendRequest(DocsGetRequest request);

  DeleteReply sendRequest(DocDeleteRequest request);

  DeleteReply sendRequest(DocsDeleteRequest request);

  WriteReply sendRequest(DocWriteRequest request);

  WriteReply sendRequest(DocsWriteRequest request);
}
