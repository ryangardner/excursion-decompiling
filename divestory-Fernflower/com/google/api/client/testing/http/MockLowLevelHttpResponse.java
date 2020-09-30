package com.google.api.client.testing.http;

import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.testing.util.TestableByteArrayInputStream;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MockLowLevelHttpResponse extends LowLevelHttpResponse {
   private InputStream content;
   private String contentEncoding;
   private long contentLength = -1L;
   private String contentType;
   private List<String> headerNames = new ArrayList();
   private List<String> headerValues = new ArrayList();
   private boolean isDisconnected;
   private String reasonPhrase;
   private int statusCode = 200;

   public MockLowLevelHttpResponse addHeader(String var1, String var2) {
      this.headerNames.add(Preconditions.checkNotNull(var1));
      this.headerValues.add(Preconditions.checkNotNull(var2));
      return this;
   }

   public void disconnect() throws IOException {
      this.isDisconnected = true;
      super.disconnect();
   }

   public InputStream getContent() throws IOException {
      return this.content;
   }

   public String getContentEncoding() {
      return this.contentEncoding;
   }

   public long getContentLength() {
      return this.contentLength;
   }

   public final String getContentType() {
      return this.contentType;
   }

   public int getHeaderCount() {
      return this.headerNames.size();
   }

   public String getHeaderName(int var1) {
      return (String)this.headerNames.get(var1);
   }

   public final List<String> getHeaderNames() {
      return this.headerNames;
   }

   public String getHeaderValue(int var1) {
      return (String)this.headerValues.get(var1);
   }

   public final List<String> getHeaderValues() {
      return this.headerValues;
   }

   public String getReasonPhrase() {
      return this.reasonPhrase;
   }

   public int getStatusCode() {
      return this.statusCode;
   }

   public String getStatusLine() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.statusCode);
      String var2 = this.reasonPhrase;
      if (var2 != null) {
         var1.append(var2);
      }

      return var1.toString();
   }

   public boolean isDisconnected() {
      return this.isDisconnected;
   }

   public MockLowLevelHttpResponse setContent(InputStream var1) {
      this.content = var1;
      return this;
   }

   public MockLowLevelHttpResponse setContent(String var1) {
      MockLowLevelHttpResponse var2;
      if (var1 == null) {
         var2 = this.setZeroContent();
      } else {
         var2 = this.setContent(StringUtils.getBytesUtf8(var1));
      }

      return var2;
   }

   public MockLowLevelHttpResponse setContent(byte[] var1) {
      if (var1 == null) {
         return this.setZeroContent();
      } else {
         this.content = new TestableByteArrayInputStream(var1);
         this.setContentLength((long)var1.length);
         return this;
      }
   }

   public MockLowLevelHttpResponse setContentEncoding(String var1) {
      this.contentEncoding = var1;
      return this;
   }

   public MockLowLevelHttpResponse setContentLength(long var1) {
      this.contentLength = var1;
      boolean var3;
      if (var1 >= -1L) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      return this;
   }

   public MockLowLevelHttpResponse setContentType(String var1) {
      this.contentType = var1;
      return this;
   }

   public MockLowLevelHttpResponse setHeaderNames(List<String> var1) {
      this.headerNames = (List)Preconditions.checkNotNull(var1);
      return this;
   }

   public MockLowLevelHttpResponse setHeaderValues(List<String> var1) {
      this.headerValues = (List)Preconditions.checkNotNull(var1);
      return this;
   }

   public MockLowLevelHttpResponse setReasonPhrase(String var1) {
      this.reasonPhrase = var1;
      return this;
   }

   public MockLowLevelHttpResponse setStatusCode(int var1) {
      this.statusCode = var1;
      return this;
   }

   public MockLowLevelHttpResponse setZeroContent() {
      this.content = null;
      this.setContentLength(0L);
      return this;
   }
}
