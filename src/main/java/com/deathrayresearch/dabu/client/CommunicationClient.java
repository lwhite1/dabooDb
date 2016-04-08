package com.deathrayresearch.dabu.client;

import com.deathrayresearch.dabu.shared.msg.GetReply;
import com.deathrayresearch.dabu.shared.msg.DocGetRequest;
import com.deathrayresearch.dabu.shared.msg.Reply;
import com.deathrayresearch.dabu.shared.msg.Request;

/**
 *
 */
public interface CommunicationClient {

  Reply sendRequest(Request request);

  GetReply sendRequest(DocGetRequest request);
}
