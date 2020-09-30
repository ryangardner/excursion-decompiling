package org.apache.http.conn;

import java.io.IOException;
import java.io.InputStream;

public class BasicEofSensorWatcher implements EofSensorWatcher {
   protected final boolean attemptReuse;
   protected final ManagedClientConnection managedConn;

   public BasicEofSensorWatcher(ManagedClientConnection var1, boolean var2) {
      if (var1 != null) {
         this.managedConn = var1;
         this.attemptReuse = var2;
      } else {
         throw new IllegalArgumentException("Connection may not be null.");
      }
   }

   public boolean eofDetected(InputStream var1) throws IOException {
      try {
         if (this.attemptReuse) {
            var1.close();
            this.managedConn.markReusable();
         }
      } finally {
         this.managedConn.releaseConnection();
      }

      return false;
   }

   public boolean streamAbort(InputStream var1) throws IOException {
      this.managedConn.abortConnection();
      return false;
   }

   public boolean streamClosed(InputStream var1) throws IOException {
      try {
         if (this.attemptReuse) {
            var1.close();
            this.managedConn.markReusable();
         }
      } finally {
         this.managedConn.releaseConnection();
      }

      return false;
   }
}
