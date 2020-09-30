package org.apache.http.impl.conn;

import java.io.IOException;
import java.net.Socket;
import org.apache.http.HttpHost;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.RouteTracker;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public abstract class AbstractPoolEntry {
   protected final ClientConnectionOperator connOperator;
   protected final OperatedClientConnection connection;
   protected volatile HttpRoute route;
   protected volatile Object state;
   protected volatile RouteTracker tracker;

   protected AbstractPoolEntry(ClientConnectionOperator var1, HttpRoute var2) {
      if (var1 != null) {
         this.connOperator = var1;
         this.connection = var1.createConnection();
         this.route = var2;
         this.tracker = null;
      } else {
         throw new IllegalArgumentException("Connection operator may not be null");
      }
   }

   public Object getState() {
      return this.state;
   }

   public void layerProtocol(HttpContext var1, HttpParams var2) throws IOException {
      if (var2 != null) {
         if (this.tracker != null && this.tracker.isConnected()) {
            if (this.tracker.isTunnelled()) {
               if (!this.tracker.isLayered()) {
                  HttpHost var3 = this.tracker.getTargetHost();
                  this.connOperator.updateSecureConnection(this.connection, var3, var1, var2);
                  this.tracker.layerProtocol(this.connection.isSecure());
               } else {
                  throw new IllegalStateException("Multiple protocol layering not supported.");
               }
            } else {
               throw new IllegalStateException("Protocol layering without a tunnel not supported.");
            }
         } else {
            throw new IllegalStateException("Connection not open.");
         }
      } else {
         throw new IllegalArgumentException("Parameters must not be null.");
      }
   }

   public void open(HttpRoute var1, HttpContext var2, HttpParams var3) throws IOException {
      if (var1 != null) {
         if (var3 != null) {
            if (this.tracker != null && this.tracker.isConnected()) {
               throw new IllegalStateException("Connection already open.");
            } else {
               this.tracker = new RouteTracker(var1);
               HttpHost var4 = var1.getProxyHost();
               ClientConnectionOperator var5 = this.connOperator;
               OperatedClientConnection var6 = this.connection;
               HttpHost var7;
               if (var4 != null) {
                  var7 = var4;
               } else {
                  var7 = var1.getTargetHost();
               }

               var5.openConnection(var6, var7, var1.getLocalAddress(), var2, var3);
               RouteTracker var8 = this.tracker;
               if (var8 != null) {
                  if (var4 == null) {
                     var8.connectTarget(this.connection.isSecure());
                  } else {
                     var8.connectProxy(var4, this.connection.isSecure());
                  }

               } else {
                  throw new IOException("Request aborted");
               }
            }
         } else {
            throw new IllegalArgumentException("Parameters must not be null.");
         }
      } else {
         throw new IllegalArgumentException("Route must not be null.");
      }
   }

   public void setState(Object var1) {
      this.state = var1;
   }

   protected void shutdownEntry() {
      this.tracker = null;
      this.state = null;
   }

   public void tunnelProxy(HttpHost var1, boolean var2, HttpParams var3) throws IOException {
      if (var1 != null) {
         if (var3 != null) {
            if (this.tracker != null && this.tracker.isConnected()) {
               this.connection.update((Socket)null, var1, var2, var3);
               this.tracker.tunnelProxy(var1, var2);
            } else {
               throw new IllegalStateException("Connection not open.");
            }
         } else {
            throw new IllegalArgumentException("Parameters must not be null.");
         }
      } else {
         throw new IllegalArgumentException("Next proxy must not be null.");
      }
   }

   public void tunnelTarget(boolean var1, HttpParams var2) throws IOException {
      if (var2 != null) {
         if (this.tracker != null && this.tracker.isConnected()) {
            if (!this.tracker.isTunnelled()) {
               this.connection.update((Socket)null, this.tracker.getTargetHost(), var1, var2);
               this.tracker.tunnelTarget(var1);
            } else {
               throw new IllegalStateException("Connection is already tunnelled.");
            }
         } else {
            throw new IllegalStateException("Connection not open.");
         }
      } else {
         throw new IllegalArgumentException("Parameters must not be null.");
      }
   }
}
