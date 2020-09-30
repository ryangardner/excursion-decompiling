package org.apache.http.impl.conn;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.params.HttpParams;

public class SingleClientConnManager implements ClientConnectionManager {
   public static final String MISUSE_MESSAGE = "Invalid use of SingleClientConnManager: connection still allocated.\nMake sure to release the connection before allocating another one.";
   protected final boolean alwaysShutDown;
   protected final ClientConnectionOperator connOperator;
   protected long connectionExpiresTime;
   protected volatile boolean isShutDown;
   protected long lastReleaseTime;
   private final Log log;
   protected SingleClientConnManager.ConnAdapter managedConn;
   protected final SchemeRegistry schemeRegistry;
   protected SingleClientConnManager.PoolEntry uniquePoolEntry;

   public SingleClientConnManager() {
      this(SchemeRegistryFactory.createDefault());
   }

   public SingleClientConnManager(SchemeRegistry var1) {
      this.log = LogFactory.getLog(this.getClass());
      if (var1 != null) {
         this.schemeRegistry = var1;
         this.connOperator = this.createConnectionOperator(var1);
         this.uniquePoolEntry = new SingleClientConnManager.PoolEntry();
         this.managedConn = null;
         this.lastReleaseTime = -1L;
         this.alwaysShutDown = false;
         this.isShutDown = false;
      } else {
         throw new IllegalArgumentException("Scheme registry must not be null.");
      }
   }

   @Deprecated
   public SingleClientConnManager(HttpParams var1, SchemeRegistry var2) {
      this(var2);
   }

   protected final void assertStillUp() throws IllegalStateException {
      if (this.isShutDown) {
         throw new IllegalStateException("Manager is shut down.");
      }
   }

   public void closeExpiredConnections() {
      synchronized(this){}

      try {
         if (System.currentTimeMillis() >= this.connectionExpiresTime) {
            this.closeIdleConnections(0L, TimeUnit.MILLISECONDS);
         }
      } finally {
         ;
      }

   }

   public void closeIdleConnections(long param1, TimeUnit param3) {
      // $FF: Couldn't be decompiled
   }

   protected ClientConnectionOperator createConnectionOperator(SchemeRegistry var1) {
      return new DefaultClientConnectionOperator(var1);
   }

   protected void finalize() throws Throwable {
      try {
         this.shutdown();
      } finally {
         super.finalize();
      }

   }

   public ManagedClientConnection getConnection(HttpRoute param1, Object param2) {
      // $FF: Couldn't be decompiled
   }

   public SchemeRegistry getSchemeRegistry() {
      return this.schemeRegistry;
   }

   public void releaseConnection(ManagedClientConnection param1, long param2, TimeUnit param4) {
      // $FF: Couldn't be decompiled
   }

   public final ClientConnectionRequest requestConnection(final HttpRoute var1, final Object var2) {
      return new ClientConnectionRequest() {
         public void abortRequest() {
         }

         public ManagedClientConnection getConnection(long var1x, TimeUnit var3) {
            return SingleClientConnManager.this.getConnection(var1, var2);
         }
      };
   }

   @Deprecated
   protected void revokeConnection() {
      // $FF: Couldn't be decompiled
   }

   public void shutdown() {
      synchronized(this){}

      Throwable var10000;
      Throwable var38;
      label308: {
         boolean var10001;
         try {
            this.isShutDown = true;
            if (this.managedConn != null) {
               this.managedConn.detach();
            }
         } catch (Throwable var37) {
            var10000 = var37;
            var10001 = false;
            break label308;
         }

         label309: {
            label298: {
               IOException var1;
               try {
                  try {
                     if (this.uniquePoolEntry != null) {
                        this.uniquePoolEntry.shutdown();
                     }
                     break label309;
                  } catch (IOException var35) {
                     var1 = var35;
                  }
               } catch (Throwable var36) {
                  var10000 = var36;
                  var10001 = false;
                  break label298;
               }

               label293:
               try {
                  this.log.debug("Problem while shutting down manager.", var1);
                  break label309;
               } catch (Throwable var34) {
                  var10000 = var34;
                  var10001 = false;
                  break label293;
               }
            }

            var38 = var10000;

            try {
               this.uniquePoolEntry = null;
               throw var38;
            } catch (Throwable var32) {
               var10000 = var32;
               var10001 = false;
               break label308;
            }
         }

         label289:
         try {
            this.uniquePoolEntry = null;
            return;
         } catch (Throwable var33) {
            var10000 = var33;
            var10001 = false;
            break label289;
         }
      }

      var38 = var10000;
      throw var38;
   }

   protected class ConnAdapter extends AbstractPooledConnAdapter {
      protected ConnAdapter(SingleClientConnManager.PoolEntry var2, HttpRoute var3) {
         super(SingleClientConnManager.this, var2);
         this.markReusable();
         var2.route = var3;
      }
   }

   protected class PoolEntry extends AbstractPoolEntry {
      protected PoolEntry() {
         super(SingleClientConnManager.this.connOperator, (HttpRoute)null);
      }

      protected void close() throws IOException {
         this.shutdownEntry();
         if (this.connection.isOpen()) {
            this.connection.close();
         }

      }

      protected void shutdown() throws IOException {
         this.shutdownEntry();
         if (this.connection.isOpen()) {
            this.connection.shutdown();
         }

      }
   }
}
