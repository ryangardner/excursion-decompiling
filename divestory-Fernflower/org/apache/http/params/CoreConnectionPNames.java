package org.apache.http.params;

public interface CoreConnectionPNames {
   String CONNECTION_TIMEOUT = "http.connection.timeout";
   String MAX_HEADER_COUNT = "http.connection.max-header-count";
   String MAX_LINE_LENGTH = "http.connection.max-line-length";
   String MIN_CHUNK_LIMIT = "http.connection.min-chunk-limit";
   String SOCKET_BUFFER_SIZE = "http.socket.buffer-size";
   String SO_LINGER = "http.socket.linger";
   String SO_REUSEADDR = "http.socket.reuseaddr";
   String SO_TIMEOUT = "http.socket.timeout";
   String STALE_CONNECTION_CHECK = "http.connection.stalecheck";
   String TCP_NODELAY = "http.tcp.nodelay";
}
