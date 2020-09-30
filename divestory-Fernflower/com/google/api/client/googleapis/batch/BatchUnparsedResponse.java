package com.google.api.client.googleapis.batch;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.util.ByteStreams;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

final class BatchUnparsedResponse {
   private final String boundary;
   private int contentId = 0;
   boolean hasNext = true;
   private final InputStream inputStream;
   private final List<BatchRequest.RequestInfo<?, ?>> requestInfos;
   private final boolean retryAllowed;
   List<BatchRequest.RequestInfo<?, ?>> unsuccessfulRequestInfos = new ArrayList();

   BatchUnparsedResponse(InputStream var1, String var2, List<BatchRequest.RequestInfo<?, ?>> var3, boolean var4) throws IOException {
      this.boundary = var2;
      this.requestInfos = var3;
      this.retryAllowed = var4;
      this.inputStream = var1;
      this.checkForFinalBoundary(this.readLine());
   }

   private void checkForFinalBoundary(String var1) throws IOException {
      StringBuilder var2 = new StringBuilder();
      var2.append(this.boundary);
      var2.append("--");
      if (var1.equals(var2.toString())) {
         this.hasNext = false;
         this.inputStream.close();
      }

   }

   private HttpResponse getFakeResponse(int var1, InputStream var2, List<String> var3, List<String> var4) throws IOException {
      HttpRequest var5 = (new BatchUnparsedResponse.FakeResponseHttpTransport(var1, var2, var3, var4)).createRequestFactory().buildPostRequest(new GenericUrl("http://google.com/"), (HttpContent)null);
      var5.setLoggingEnabled(false);
      var5.setThrowExceptionOnExecuteError(false);
      return var5.execute();
   }

   private <A, T, E> A getParsedDataClass(Class<A> var1, HttpResponse var2, BatchRequest.RequestInfo<T, E> var3) throws IOException {
      return var1 == Void.class ? null : var3.request.getParser().parseAndClose(var2.getContent(), var2.getContentCharset(), var1);
   }

   private <T, E> void parseAndCallback(BatchRequest.RequestInfo<T, E> var1, int var2, HttpResponse var3) throws IOException {
      BatchCallback var4 = var1.callback;
      HttpHeaders var5 = var3.getHeaders();
      HttpUnsuccessfulResponseHandler var6 = var1.request.getUnsuccessfulResponseHandler();
      if (HttpStatusCodes.isSuccess(var2)) {
         if (var4 == null) {
            return;
         }

         var4.onSuccess(this.getParsedDataClass(var1.dataClass, var3, var1), var5);
      } else {
         HttpContent var7 = var1.request.getContent();
         boolean var8 = this.retryAllowed;
         boolean var10 = true;
         if (!var8 || var7 != null && !var7.retrySupported()) {
            var8 = false;
         } else {
            var8 = true;
         }

         boolean var9;
         if (var6 != null) {
            var9 = var6.handleResponse(var1.request, var3, var8);
         } else {
            var9 = false;
         }

         if (var9 || !var1.request.handleRedirect(var3.getStatusCode(), var3.getHeaders())) {
            var10 = false;
         }

         if (!var8 || !var9 && !var10) {
            if (var4 == null) {
               return;
            }

            var4.onFailure(this.getParsedDataClass(var1.errorClass, var3, var1), var5);
         } else {
            this.unsuccessfulRequestInfos.add(var1);
         }
      }

   }

   private String readLine() throws IOException {
      return trimCrlf(this.readRawLine());
   }

   private String readRawLine() throws IOException {
      int var1 = this.inputStream.read();
      if (var1 == -1) {
         return null;
      } else {
         StringBuilder var2;
         for(var2 = new StringBuilder(); var1 != -1; var1 = this.inputStream.read()) {
            var2.append((char)var1);
            if (var1 == 10) {
               break;
            }
         }

         return var2.toString();
      }
   }

   private static InputStream trimCrlf(byte[] var0) {
      int var1 = var0.length;
      int var2 = var1;
      if (var1 > 0) {
         var2 = var1;
         if (var0[var1 - 1] == 10) {
            var2 = var1 - 1;
         }
      }

      var1 = var2;
      if (var2 > 0) {
         var1 = var2;
         if (var0[var2 - 1] == 13) {
            var1 = var2 - 1;
         }
      }

      return new ByteArrayInputStream(var0, 0, var1);
   }

   private static String trimCrlf(String var0) {
      if (var0.endsWith("\r\n")) {
         return var0.substring(0, var0.length() - 2);
      } else {
         String var1 = var0;
         if (var0.endsWith("\n")) {
            var1 = var0.substring(0, var0.length() - 1);
         }

         return var1;
      }
   }

   void parseNextResponse() throws IOException {
      ++this.contentId;

      String var1;
      do {
         var1 = this.readLine();
      } while(var1 != null && !var1.equals(""));

      int var2 = Integer.parseInt(this.readLine().split(" ")[1]);
      ArrayList var3 = new ArrayList();
      ArrayList var4 = new ArrayList();
      long var5 = -1L;

      while(true) {
         var1 = this.readLine();
         if (var1 == null || var1.equals("")) {
            long var13;
            int var8 = (var13 = var5 - -1L) == 0L ? 0 : (var13 < 0L ? -1 : 1);
            Object var12;
            if (var8 == 0) {
               ByteArrayOutputStream var11 = new ByteArrayOutputStream();

               while(true) {
                  var1 = this.readRawLine();
                  if (var1 == null || var1.startsWith(this.boundary)) {
                     var12 = trimCrlf(var11.toByteArray());
                     var1 = trimCrlf(var1);
                     break;
                  }

                  var11.write(var1.getBytes("ISO-8859-1"));
               }
            } else {
               var12 = new FilterInputStream(ByteStreams.limit(this.inputStream, var5)) {
                  public void close() {
                  }
               };
            }

            HttpResponse var9 = this.getFakeResponse(var2, (InputStream)var12, var3, var4);
            this.parseAndCallback((BatchRequest.RequestInfo)this.requestInfos.get(this.contentId - 1), var2, var9);

            do {
               while(((InputStream)var12).skip(var5) > 0L) {
               }
            } while(((InputStream)var12).read() != -1);

            if (var8 != 0) {
               var1 = this.readLine();
            }

            while(var1 != null && var1.length() == 0) {
               var1 = this.readLine();
            }

            this.checkForFinalBoundary(var1);
            return;
         }

         String[] var7 = var1.split(": ", 2);
         var1 = var7[0];
         String var10 = var7[1];
         var3.add(var1);
         var4.add(var10);
         if ("Content-Length".equalsIgnoreCase(var1.trim())) {
            var5 = Long.parseLong(var10);
         }
      }
   }

   private static class FakeLowLevelHttpRequest extends LowLevelHttpRequest {
      private List<String> headerNames;
      private List<String> headerValues;
      private InputStream partContent;
      private int statusCode;

      FakeLowLevelHttpRequest(InputStream var1, int var2, List<String> var3, List<String> var4) {
         this.partContent = var1;
         this.statusCode = var2;
         this.headerNames = var3;
         this.headerValues = var4;
      }

      public void addHeader(String var1, String var2) {
      }

      public LowLevelHttpResponse execute() {
         return new BatchUnparsedResponse.FakeLowLevelHttpResponse(this.partContent, this.statusCode, this.headerNames, this.headerValues);
      }
   }

   private static class FakeLowLevelHttpResponse extends LowLevelHttpResponse {
      private List<String> headerNames = new ArrayList();
      private List<String> headerValues = new ArrayList();
      private InputStream partContent;
      private int statusCode;

      FakeLowLevelHttpResponse(InputStream var1, int var2, List<String> var3, List<String> var4) {
         this.partContent = var1;
         this.statusCode = var2;
         this.headerNames = var3;
         this.headerValues = var4;
      }

      public InputStream getContent() {
         return this.partContent;
      }

      public String getContentEncoding() {
         return null;
      }

      public long getContentLength() {
         return 0L;
      }

      public String getContentType() {
         return null;
      }

      public int getHeaderCount() {
         return this.headerNames.size();
      }

      public String getHeaderName(int var1) {
         return (String)this.headerNames.get(var1);
      }

      public String getHeaderValue(int var1) {
         return (String)this.headerValues.get(var1);
      }

      public String getReasonPhrase() {
         return null;
      }

      public int getStatusCode() {
         return this.statusCode;
      }

      public String getStatusLine() {
         return null;
      }
   }

   private static class FakeResponseHttpTransport extends HttpTransport {
      private List<String> headerNames;
      private List<String> headerValues;
      private InputStream partContent;
      private int statusCode;

      FakeResponseHttpTransport(int var1, InputStream var2, List<String> var3, List<String> var4) {
         this.statusCode = var1;
         this.partContent = var2;
         this.headerNames = var3;
         this.headerValues = var4;
      }

      protected LowLevelHttpRequest buildRequest(String var1, String var2) {
         return new BatchUnparsedResponse.FakeLowLevelHttpRequest(this.partContent, this.statusCode, this.headerNames, this.headerValues);
      }
   }
}
