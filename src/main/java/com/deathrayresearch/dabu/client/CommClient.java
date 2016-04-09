package com.deathrayresearch.dabu.client;

import com.deathrayresearch.dabu.shared.msg.DeleteReply;
import com.deathrayresearch.dabu.shared.msg.DocDeleteRequest;
import com.deathrayresearch.dabu.shared.msg.DocWriteRequest;
import com.deathrayresearch.dabu.shared.msg.DocsDeleteRequest;
import com.deathrayresearch.dabu.shared.msg.DocsGetReply;
import com.deathrayresearch.dabu.shared.msg.DocsGetRequest;
import com.deathrayresearch.dabu.shared.msg.DocsWriteRequest;
import com.deathrayresearch.dabu.shared.msg.DocGetReply;
import com.deathrayresearch.dabu.shared.msg.DocGetRequest;
import com.deathrayresearch.dabu.shared.msg.WriteReply;

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
