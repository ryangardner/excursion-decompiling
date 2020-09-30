package org.apache.http.client.methods;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.client.utils.CloneUtils;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ConnectionReleaseTrigger;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicRequestLine;
import org.apache.http.message.HeaderGroup;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public abstract class HttpRequestBase extends AbstractHttpMessage implements HttpUriRequest, AbortableHttpRequest, Cloneable {
   private Lock abortLock = new ReentrantLock();
   private boolean aborted;
   private ClientConnectionRequest connRequest;
   private ConnectionReleaseTrigger releaseTrigger;
   private URI uri;

   public void abort() {
      this.abortLock.lock();

      Throwable var10000;
      label109: {
         boolean var1;
         boolean var10001;
         try {
            var1 = this.aborted;
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            break label109;
         }

         if (var1) {
            this.abortLock.unlock();
            return;
         }

         ConnectionReleaseTrigger var3;
         ClientConnectionRequest var13;
         try {
            this.aborted = true;
            var13 = this.connRequest;
            var3 = this.releaseTrigger;
         } catch (Throwable var11) {
            var10000 = var11;
            var10001 = false;
            break label109;
         }

         this.abortLock.unlock();
         if (var13 != null) {
            var13.abortRequest();
         }

         if (var3 != null) {
            try {
               var3.abortConnection();
            } catch (IOException var10) {
            }
         }

         return;
      }

      Throwable var2 = var10000;
      this.abortLock.unlock();
      throw var2;
   }

   public Object clone() throws CloneNotSupportedException {
      HttpRequestBase var1 = (HttpRequestBase)super.clone();
      var1.abortLock = new ReentrantLock();
      var1.aborted = false;
      var1.releaseTrigger = null;
      var1.connRequest = null;
      var1.headergroup = (HeaderGroup)CloneUtils.clone(this.headergroup);
      var1.params = (HttpParams)CloneUtils.clone(this.params);
      return var1;
   }

   public abstract String getMethod();

   public ProtocolVersion getProtocolVersion() {
      return HttpProtocolParams.getVersion(this.getParams());
   }

   public RequestLine getRequestLine() {
      String var1 = this.getMethod();
      ProtocolVersion var2 = this.getProtocolVersion();
      URI var3 = this.getURI();
      String var5;
      if (var3 != null) {
         var5 = var3.toASCIIString();
      } else {
         var5 = null;
      }

      String var4;
      if (var5 != null) {
         var4 = var5;
         if (var5.length() != 0) {
            return new BasicRequestLine(var1, var4, var2);
         }
      }

      var4 = "/";
      return new BasicRequestLine(var1, var4, var2);
   }

   public URI getURI() {
      return this.uri;
   }

   public boolean isAborted() {
      return this.aborted;
   }

   public void setConnectionRequest(ClientConnectionRequest var1) throws IOException {
      this.abortLock.lock();

      try {
         if (this.aborted) {
            IOException var4 = new IOException("Request already aborted");
            throw var4;
         }

         this.releaseTrigger = null;
         this.connRequest = var1;
      } finally {
         this.abortLock.unlock();
      }

   }

   public void setReleaseTrigger(ConnectionReleaseTrigger var1) throws IOException {
      this.abortLock.lock();

      try {
         if (this.aborted) {
            IOException var4 = new IOException("Request already aborted");
            throw var4;
         }

         this.connRequest = null;
         this.releaseTrigger = var1;
      } finally {
         this.abortLock.unlock();
      }

   }

   public void setURI(URI var1) {
      this.uri = var1;
   }
}
