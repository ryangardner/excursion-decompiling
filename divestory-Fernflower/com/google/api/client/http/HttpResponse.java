package com.google.api.client.http;

import com.google.api.client.util.Charsets;
import com.google.api.client.util.IOUtils;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StringUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class HttpResponse {
   private static final String CONTENT_ENCODING_GZIP = "gzip";
   private static final String CONTENT_ENCODING_XGZIP = "x-gzip";
   private InputStream content;
   private final String contentEncoding;
   private int contentLoggingLimit;
   private boolean contentRead;
   private final String contentType;
   private boolean loggingEnabled;
   private final HttpMediaType mediaType;
   private final HttpRequest request;
   LowLevelHttpResponse response;
   private final boolean returnRawInputStream;
   private final int statusCode;
   private final String statusMessage;

   HttpResponse(HttpRequest var1, LowLevelHttpResponse var2) throws IOException {
      this.request = var1;
      this.returnRawInputStream = var1.getResponseReturnRawInputStream();
      this.contentLoggingLimit = var1.getContentLoggingLimit();
      this.loggingEnabled = var1.isLoggingEnabled();
      this.response = var2;
      this.contentEncoding = var2.getContentEncoding();
      int var3 = var2.getStatusCode();
      boolean var4 = false;
      int var5 = var3;
      if (var3 < 0) {
         var5 = 0;
      }

      this.statusCode = var5;
      String var6 = var2.getReasonPhrase();
      this.statusMessage = var6;
      Logger var7 = HttpTransport.LOGGER;
      boolean var12 = var4;
      if (this.loggingEnabled) {
         var12 = var4;
         if (var7.isLoggable(Level.CONFIG)) {
            var12 = true;
         }
      }

      StringBuilder var8 = null;
      StringBuilder var9;
      if (var12) {
         var9 = new StringBuilder();
         var9.append("-------------- RESPONSE --------------");
         var9.append(StringUtils.LINE_SEPARATOR);
         String var10 = var2.getStatusLine();
         if (var10 != null) {
            var9.append(var10);
         } else {
            var9.append(this.statusCode);
            if (var6 != null) {
               var9.append(' ');
               var9.append(var6);
            }
         }

         var9.append(StringUtils.LINE_SEPARATOR);
      } else {
         var9 = null;
      }

      HttpHeaders var13 = var1.getResponseHeaders();
      if (var12) {
         var8 = var9;
      }

      var13.fromHttpResponse(var2, var8);
      String var14 = var2.getContentType();
      String var11 = var14;
      if (var14 == null) {
         var11 = var1.getResponseHeaders().getContentType();
      }

      this.contentType = var11;
      this.mediaType = parseMediaType(var11);
      if (var12) {
         var7.config(var9.toString());
      }

   }

   private boolean hasMessageBody() throws IOException {
      int var1 = this.getStatusCode();
      if (!this.getRequest().getRequestMethod().equals("HEAD") && var1 / 100 != 1 && var1 != 204 && var1 != 304) {
         return true;
      } else {
         this.ignore();
         return false;
      }
   }

   private static HttpMediaType parseMediaType(String var0) {
      if (var0 == null) {
         return null;
      } else {
         try {
            HttpMediaType var2 = new HttpMediaType(var0);
            return var2;
         } catch (IllegalArgumentException var1) {
            return null;
         }
      }
   }

   public void disconnect() throws IOException {
      this.ignore();
      this.response.disconnect();
   }

   public void download(OutputStream var1) throws IOException {
      IOUtils.copy(this.getContent(), var1);
   }

   public InputStream getContent() throws IOException {
      // $FF: Couldn't be decompiled
   }

   public Charset getContentCharset() {
      HttpMediaType var1 = this.mediaType;
      Charset var2;
      if (var1 != null && var1.getCharsetParameter() != null) {
         var2 = this.mediaType.getCharsetParameter();
      } else {
         var2 = Charsets.ISO_8859_1;
      }

      return var2;
   }

   public String getContentEncoding() {
      return this.contentEncoding;
   }

   public int getContentLoggingLimit() {
      return this.contentLoggingLimit;
   }

   public String getContentType() {
      return this.contentType;
   }

   public HttpHeaders getHeaders() {
      return this.request.getResponseHeaders();
   }

   public HttpMediaType getMediaType() {
      return this.mediaType;
   }

   public HttpRequest getRequest() {
      return this.request;
   }

   public int getStatusCode() {
      return this.statusCode;
   }

   public String getStatusMessage() {
      return this.statusMessage;
   }

   public HttpTransport getTransport() {
      return this.request.getTransport();
   }

   public void ignore() throws IOException {
      InputStream var1 = this.getContent();
      if (var1 != null) {
         var1.close();
      }

   }

   public boolean isLoggingEnabled() {
      return this.loggingEnabled;
   }

   public boolean isSuccessStatusCode() {
      return HttpStatusCodes.isSuccess(this.statusCode);
   }

   public <T> T parseAs(Class<T> var1) throws IOException {
      return !this.hasMessageBody() ? null : this.request.getParser().parseAndClose(this.getContent(), this.getContentCharset(), var1);
   }

   public Object parseAs(Type var1) throws IOException {
      return !this.hasMessageBody() ? null : this.request.getParser().parseAndClose(this.getContent(), this.getContentCharset(), var1);
   }

   public String parseAsString() throws IOException {
      InputStream var1 = this.getContent();
      if (var1 == null) {
         return "";
      } else {
         ByteArrayOutputStream var2 = new ByteArrayOutputStream();
         IOUtils.copy(var1, var2);
         return var2.toString(this.getContentCharset().name());
      }
   }

   public HttpResponse setContentLoggingLimit(int var1) {
      boolean var2;
      if (var1 >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "The content logging limit must be non-negative.");
      this.contentLoggingLimit = var1;
      return this;
   }

   public HttpResponse setLoggingEnabled(boolean var1) {
      this.loggingEnabled = var1;
      return this;
   }
}
