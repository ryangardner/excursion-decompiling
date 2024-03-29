package org.apache.http.conn;

import java.util.concurrent.TimeUnit;

public interface ClientConnectionRequest {
   void abortRequest();

   ManagedClientConnection getConnection(long var1, TimeUnit var3) throws InterruptedException, ConnectionPoolTimeoutException;
}
