package com.deathrayresearch.dabu.client;

import com.deathrayresearch.dabu.shared.msg.Reply;
import com.deathrayresearch.dabu.shared.msg.Request;

/**
 *
 */
public interface CommunicationClient {

  Reply sendRequest(Request request);
}
