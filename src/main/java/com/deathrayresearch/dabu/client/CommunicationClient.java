package com.deathrayresearch.dabu.client;

import com.deathrayresearch.dabu.shared.Reply;
import com.deathrayresearch.dabu.shared.Request;

/**
 *
 */
public interface CommunicationClient {

  Reply sendRequest(Request request);
}
