package com.deathrayresearch.dabu.client;

import com.deathrayresearch.dabu.shared.msg.GetReply;
import com.deathrayresearch.dabu.shared.msg.GetRequest;
import com.deathrayresearch.dabu.shared.msg.Reply;
import com.deathrayresearch.dabu.shared.msg.Request;

/**
 *
 */
public interface CommunicationClient {

  Reply sendRequest(Request request);

  GetReply sendRequest(GetRequest request);
}
