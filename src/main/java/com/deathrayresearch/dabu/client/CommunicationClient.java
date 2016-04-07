package com.deathrayresearch.dabu.client;

import com.deathrayresearch.dabu.shared.msg.DocumentGetReply;
import com.deathrayresearch.dabu.shared.msg.DocumentGetRequest;
import com.deathrayresearch.dabu.shared.msg.Reply;
import com.deathrayresearch.dabu.shared.msg.Request;

/**
 *
 */
public interface CommunicationClient {

  Reply sendRequest(Request request);

  DocumentGetReply sendRequest(DocumentGetRequest request);
}
