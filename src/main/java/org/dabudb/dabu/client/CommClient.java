package org.dabudb.dabu.client;

import static org.dabudb.dabu.generated.protobufs.Request.*;

/**
 * An object that handles the client side of communications with a database
 */
interface CommClient {

  WriteReply sendRequest(WriteRequest request);

  GetReply sendRequest(GetRequest request);

}
