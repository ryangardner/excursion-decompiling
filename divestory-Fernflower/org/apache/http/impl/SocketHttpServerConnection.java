package org.apache.http.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import org.apache.http.HttpInetConnection;
import org.apache.http.impl.io.SocketInputBuffer;
import org.apache.http.impl.io.SocketOutputBuffer;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class SocketHttpServerConnection extends AbstractHttpServerConnection implements HttpInetConnection {
   private volatile boolean open;
   private volatile Socket socket = null;

   protected void assertNotOpen() {
      if (this.open) {
         throw new IllegalStateException("Connection is already open");
      }
   }

   protected void assertOpen() {
      if (!this.open) {
         throw new IllegalStateException("Connection is not open");
      }
   }

   protected void bind(Socket var1, HttpParams var2) throws IOException {
      if (var1 != null) {
         if (var2 != null) {
            this.socket = var1;
            int var3 = HttpConnectionParams.getSocketBufferSize(var2);
            this.init(this.createHttpDataReceiver(var1, var3, var2), this.createHttpDataTransmitter(var1, var3, var2), var2);
            this.open = true;
         } else {
            throw new IllegalArgumentException("HTTP parameters may not be null");
         }
      } else {
         throw new IllegalArgumentException("Socket may not be null");
      }
   }

   public void close() throws IOException {
      // $FF: Couldn't be decompiled
   }

   protected SessionInputBuffer createHttpDataReceiver(Socket var1, int var2, HttpParams var3) throws IOException {
      return this.createSessionInputBuffer(var1, var2, var3);
   }

   protected SessionOutputBuffer createHttpDataTransmitter(Socket var1, int var2, HttpParams var3) throws IOException {
      return this.createSessionOutputBuffer(var1, var2, var3);
   }

   protected SessionInputBuffer createSessionInputBuffer(Socket var1, int var2, HttpParams var3) throws IOException {
      return new SocketInputBuffer(var1, var2, var3);
   }

   protected SessionOutputBuffer createSessionOutputBuffer(Socket var1, int var2, HttpParams var3) throws IOException {
      return new SocketOutputBuffer(var1, var2, var3);
   }

   public InetAddress getLocalAddress() {
      return this.socket != null ? this.socket.getLocalAddress() : null;
   }

   public int getLocalPort() {
      return this.socket != null ? this.socket.getLocalPort() : -1;
   }

   public InetAddress getRemoteAddress() {
      return this.socket != null ? this.socket.getInetAddress() : null;
   }

   public int getRemotePort() {
      return this.socket != null ? this.socket.getPort() : -1;
   }

   protected Socket getSocket() {
      return this.socket;
   }

   public int getSocketTimeout() {
      if (this.socket != null) {
         try {
            int var1 = this.socket.getSoTimeout();
            return var1;
         } catch (SocketException var3) {
         }
      }

      return -1;
   }

   public boolean isOpen() {
      return this.open;
   }

   public void setSocketTimeout(int var1) {
      this.assertOpen();
      if (this.socket != null) {
         try {
            this.socket.setSoTimeout(var1);
         } catch (SocketException var3) {
         }
      }

   }

   public void shutdown() throws IOException {
      this.open = false;
      Socket var1 = this.socket;
      if (var1 != null) {
         var1.close();
      }

   }
}
