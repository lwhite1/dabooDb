package org.daboodb.daboo.client;

import static org.daboodb.daboo.generated.protobufs.Request.*;

/**
 * An object that handles the client side of communications with a database
 */
interface CommClient {

  WriteReply sendRequest(WriteRequest request);

  GetReply sendRequest(GetRequest request);

  GetReply sendRequest(GetRangeRequest request);
}
