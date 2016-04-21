package org.daboodb.daboo.server;

/**
 * Abstracts away the communication interface for the server. Every database needs at least one. An embedded database
 * would use the DirectCommServer.
 * <p/>
 * All implementations will, necessarily, provide a different api depending on the communications technology used.
 */
public interface CommServer {
}
