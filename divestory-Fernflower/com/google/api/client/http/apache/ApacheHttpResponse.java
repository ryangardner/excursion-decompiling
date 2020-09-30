package com.google.api.client.http.apache;

import com.google.api.client.http.LowLevelHttpResponse;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpRequestBase;

final class ApacheHttpResponse extends LowLevelHttpResponse {
   private final Header[] allHeaders;
   private final HttpRequestBase request;
   private final HttpResponse response;

   ApacheHttpResponse(HttpRequestBase var1, HttpResponse var2) {
      this.request = var1;
      this.response = var2;
      this.allHeaders = var2.getAllHeaders();
   }

   public void disconnect() {
      this.request.abort();
   }

   public InputStream getContent() throws IOException {
      HttpEntity var1 = this.response.getEntity();
      InputStream var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = var1.getContent();
      }

      return var2;
   }

   public String getContentEncoding() {
      HttpEntity var1 = this.response.getEntity();
      if (var1 != null) {
         Header var2 = var1.getContentEncoding();
         if (var2 != null) {
            return var2.getValue();
         }
      }

      return null;
   }

   public long getContentLength() {
      HttpEntity var1 = this.response.getEntity();
      long var2;
      if (var1 == null) {
         var2 = -1L;
      } else {
         var2 = var1.getContentLength();
      }

      return var2;
   }

   public String getContentType() {
      HttpEntity var1 = this.response.getEntity();
      if (var1 != null) {
         Header var2 = var1.getContentType();
         if (var2 != null) {
            return var2.getValue();
         }
      }

      return null;
   }

   public int getHeaderCount() {
      return this.allHeaders.length;
   }

   public String getHeaderName(int var1) {
      return this.allHeaders[var1].getName();
   }

   public String getHeaderValue(int var1) {
      return this.allHeaders[var1].getValue();
   }

   public String getHeaderValue(String var1) {
      return this.response.getLastHeader(var1).getValue();
   }

   public String getReasonPhrase() {
      StatusLine var1 = this.response.getStatusLine();
      String var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = var1.getReasonPhrase();
      }

      return var2;
   }

   public int getStatusCode() {
      StatusLine var1 = this.response.getStatusLine();
      int var2;
      if (var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.getStatusCode();
      }

      return var2;
   }

   public String getStatusLine() {
      StatusLine var1 = this.response.getStatusLine();
      String var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = var1.toString();
      }

      return var2;
   }
}
