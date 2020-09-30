package org.apache.http.impl.client;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import javax.net.ssl.SSLException;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;

public class DefaultHttpRequestRetryHandler implements HttpRequestRetryHandler {
   private final boolean requestSentRetryEnabled;
   private final int retryCount;

   public DefaultHttpRequestRetryHandler() {
      this(3, false);
   }

   public DefaultHttpRequestRetryHandler(int var1, boolean var2) {
      this.retryCount = var1;
      this.requestSentRetryEnabled = var2;
   }

   private boolean handleAsIdempotent(HttpRequest var1) {
      return var1 instanceof HttpEntityEnclosingRequest ^ true;
   }

   public int getRetryCount() {
      return this.retryCount;
   }

   public boolean isRequestSentRetryEnabled() {
      return this.requestSentRetryEnabled;
   }

   public boolean retryRequest(IOException var1, int var2, HttpContext var3) {
      if (var1 != null) {
         if (var3 == null) {
            throw new IllegalArgumentException("HTTP context may not be null");
         } else if (var2 > this.retryCount) {
            return false;
         } else if (var1 instanceof InterruptedIOException) {
            return false;
         } else if (var1 instanceof UnknownHostException) {
            return false;
         } else if (var1 instanceof ConnectException) {
            return false;
         } else if (var1 instanceof SSLException) {
            return false;
         } else if (this.handleAsIdempotent((HttpRequest)var3.getAttribute("http.request"))) {
            return true;
         } else {
            Boolean var4 = (Boolean)var3.getAttribute("http.request_sent");
            boolean var5;
            if (var4 != null && var4) {
               var5 = true;
            } else {
               var5 = false;
            }

            return !var5 || this.requestSentRetryEnabled;
         }
      } else {
         throw new IllegalArgumentException("Exception parameter may not be null");
      }
   }
}
