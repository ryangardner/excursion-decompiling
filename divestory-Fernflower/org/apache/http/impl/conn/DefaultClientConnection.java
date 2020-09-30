package org.apache.http.impl.conn;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.impl.SocketHttpClientConnection;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.message.LineParser;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;

public class DefaultClientConnection extends SocketHttpClientConnection implements OperatedClientConnection, HttpContext {
   private final Map<String, Object> attributes = new HashMap();
   private boolean connSecure;
   private final Log headerLog = LogFactory.getLog("org.apache.http.headers");
   private final Log log = LogFactory.getLog(this.getClass());
   private volatile boolean shutdown;
   private volatile Socket socket;
   private HttpHost targetHost;
   private final Log wireLog = LogFactory.getLog("org.apache.http.wire");

   public void close() throws IOException {
      try {
         super.close();
         this.log.debug("Connection closed");
      } catch (IOException var2) {
         this.log.debug("I/O error closing connection", var2);
      }

   }

   protected HttpMessageParser createResponseParser(SessionInputBuffer var1, HttpResponseFactory var2, HttpParams var3) {
      return new DefaultResponseParser(var1, (LineParser)null, var2, var3);
   }

   protected SessionInputBuffer createSessionInputBuffer(Socket var1, int var2, HttpParams var3) throws IOException {
      int var4 = var2;
      if (var2 == -1) {
         var4 = 8192;
      }

      SessionInputBuffer var5 = super.createSessionInputBuffer(var1, var4, var3);
      Object var6 = var5;
      if (this.wireLog.isDebugEnabled()) {
         var6 = new LoggingSessionInputBuffer(var5, new Wire(this.wireLog), HttpProtocolParams.getHttpElementCharset(var3));
      }

      return (SessionInputBuffer)var6;
   }

   protected SessionOutputBuffer createSessionOutputBuffer(Socket var1, int var2, HttpParams var3) throws IOException {
      int var4 = var2;
      if (var2 == -1) {
         var4 = 8192;
      }

      SessionOutputBuffer var5 = super.createSessionOutputBuffer(var1, var4, var3);
      Object var6 = var5;
      if (this.wireLog.isDebugEnabled()) {
         var6 = new LoggingSessionOutputBuffer(var5, new Wire(this.wireLog), HttpProtocolParams.getHttpElementCharset(var3));
      }

      return (SessionOutputBuffer)var6;
   }

   public Object getAttribute(String var1) {
      return this.attributes.get(var1);
   }

   public final Socket getSocket() {
      return this.socket;
   }

   public final HttpHost getTargetHost() {
      return this.targetHost;
   }

   public final boolean isSecure() {
      return this.connSecure;
   }

   public void openCompleted(boolean var1, HttpParams var2) throws IOException {
      this.assertNotOpen();
      if (var2 != null) {
         this.connSecure = var1;
         this.bind(this.socket, var2);
      } else {
         throw new IllegalArgumentException("Parameters must not be null.");
      }
   }

   public void opening(Socket var1, HttpHost var2) throws IOException {
      this.assertNotOpen();
      this.socket = var1;
      this.targetHost = var2;
      if (this.shutdown) {
         var1.close();
         throw new IOException("Connection already shutdown");
      }
   }

   public HttpResponse receiveResponseHeader() throws HttpException, IOException {
      HttpResponse var1 = super.receiveResponseHeader();
      StringBuilder var3;
      if (this.log.isDebugEnabled()) {
         Log var2 = this.log;
         var3 = new StringBuilder();
         var3.append("Receiving response: ");
         var3.append(var1.getStatusLine());
         var2.debug(var3.toString());
      }

      if (this.headerLog.isDebugEnabled()) {
         Log var10 = this.headerLog;
         StringBuilder var8 = new StringBuilder();
         var8.append("<< ");
         var8.append(var1.getStatusLine().toString());
         var10.debug(var8.toString());
         Header[] var4 = var1.getAllHeaders();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Header var9 = var4[var6];
            Log var7 = this.headerLog;
            var3 = new StringBuilder();
            var3.append("<< ");
            var3.append(var9.toString());
            var7.debug(var3.toString());
         }
      }

      return var1;
   }

   public Object removeAttribute(String var1) {
      return this.attributes.remove(var1);
   }

   public void sendRequestHeader(HttpRequest var1) throws HttpException, IOException {
      Log var2;
      StringBuilder var3;
      if (this.log.isDebugEnabled()) {
         var2 = this.log;
         var3 = new StringBuilder();
         var3.append("Sending request: ");
         var3.append(var1.getRequestLine());
         var2.debug(var3.toString());
      }

      super.sendRequestHeader(var1);
      if (this.headerLog.isDebugEnabled()) {
         var2 = this.headerLog;
         var3 = new StringBuilder();
         var3.append(">> ");
         var3.append(var1.getRequestLine().toString());
         var2.debug(var3.toString());
         Header[] var7 = var1.getAllHeaders();
         int var4 = var7.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Header var8 = var7[var5];
            var2 = this.headerLog;
            StringBuilder var6 = new StringBuilder();
            var6.append(">> ");
            var6.append(var8.toString());
            var2.debug(var6.toString());
         }
      }

   }

   public void setAttribute(String var1, Object var2) {
      this.attributes.put(var1, var2);
   }

   public void shutdown() throws IOException {
      this.shutdown = true;

      IOException var10000;
      label28: {
         Socket var1;
         boolean var10001;
         try {
            super.shutdown();
            this.log.debug("Connection shut down");
            var1 = this.socket;
         } catch (IOException var3) {
            var10000 = var3;
            var10001 = false;
            break label28;
         }

         if (var1 == null) {
            return;
         }

         try {
            var1.close();
            return;
         } catch (IOException var2) {
            var10000 = var2;
            var10001 = false;
         }
      }

      IOException var4 = var10000;
      this.log.debug("I/O error shutting down connection", var4);
   }

   public void update(Socket var1, HttpHost var2, boolean var3, HttpParams var4) throws IOException {
      this.assertOpen();
      if (var2 != null) {
         if (var4 != null) {
            if (var1 != null) {
               this.socket = var1;
               this.bind(var1, var4);
            }

            this.targetHost = var2;
            this.connSecure = var3;
         } else {
            throw new IllegalArgumentException("Parameters must not be null.");
         }
      } else {
         throw new IllegalArgumentException("Target host must not be null.");
      }
   }
}
