package org.apache.http.conn;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.util.EntityUtils;

public class BasicManagedEntity extends HttpEntityWrapper implements ConnectionReleaseTrigger, EofSensorWatcher {
   protected final boolean attemptReuse;
   protected ManagedClientConnection managedConn;

   public BasicManagedEntity(HttpEntity var1, ManagedClientConnection var2, boolean var3) {
      super(var1);
      if (var2 != null) {
         this.managedConn = var2;
         this.attemptReuse = var3;
      } else {
         throw new IllegalArgumentException("Connection may not be null.");
      }
   }

   private void ensureConsumed() throws IOException {
      if (this.managedConn != null) {
         try {
            if (this.attemptReuse) {
               EntityUtils.consume(this.wrappedEntity);
               this.managedConn.markReusable();
            }
         } finally {
            this.releaseManagedConnection();
         }

      }
   }

   public void abortConnection() throws IOException {
      ManagedClientConnection var1 = this.managedConn;
      if (var1 != null) {
         try {
            var1.abortConnection();
         } finally {
            this.managedConn = null;
         }
      }

   }

   @Deprecated
   public void consumeContent() throws IOException {
      this.ensureConsumed();
   }

   public boolean eofDetected(InputStream var1) throws IOException {
      try {
         if (this.attemptReuse && this.managedConn != null) {
            var1.close();
            this.managedConn.markReusable();
         }
      } finally {
         this.releaseManagedConnection();
      }

      return false;
   }

   public InputStream getContent() throws IOException {
      return new EofSensorInputStream(this.wrappedEntity.getContent(), this);
   }

   public boolean isRepeatable() {
      return false;
   }

   public void releaseConnection() throws IOException {
      this.ensureConsumed();
   }

   protected void releaseManagedConnection() throws IOException {
      ManagedClientConnection var1 = this.managedConn;
      if (var1 != null) {
         try {
            var1.releaseConnection();
         } finally {
            this.managedConn = null;
         }
      }

   }

   public boolean streamAbort(InputStream var1) throws IOException {
      ManagedClientConnection var2 = this.managedConn;
      if (var2 != null) {
         var2.abortConnection();
      }

      return false;
   }

   public boolean streamClosed(InputStream var1) throws IOException {
      try {
         if (this.attemptReuse && this.managedConn != null) {
            var1.close();
            this.managedConn.markReusable();
         }
      } finally {
         this.releaseManagedConnection();
      }

      return false;
   }

   public void writeTo(OutputStream var1) throws IOException {
      super.writeTo(var1);
      this.ensureConsumed();
   }
}
