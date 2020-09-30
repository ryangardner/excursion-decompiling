package org.apache.http;

import java.io.IOException;

public interface HttpConnection {
   void close() throws IOException;

   HttpConnectionMetrics getMetrics();

   int getSocketTimeout();

   boolean isOpen();

   boolean isStale();

   void setSocketTimeout(int var1);

   void shutdown() throws IOException;
}
