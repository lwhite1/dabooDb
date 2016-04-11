package org.dabudb.dabu.client;

import static org.dabudb.dabu.shared.protobufs.Request.*;

/**
 * An object that handles the client side of communications with a database
 */
public interface CommClient {

  WriteReply sendRequest(WriteRequest request);

  GetReply sendRequest(GetRequest request);

  DeleteReply sendRequest(DeleteRequest request);

  QueryReply sendRequest(QueryRequest request);
}
