package com.google.api.client.http.javanet;

import com.google.api.client.http.LowLevelHttpResponse;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

final class NetHttpResponse extends LowLevelHttpResponse {
   private final HttpURLConnection connection;
   private final ArrayList<String> headerNames = new ArrayList();
   private final ArrayList<String> headerValues = new ArrayList();
   private final int responseCode;
   private final String responseMessage;

   NetHttpResponse(HttpURLConnection var1) throws IOException {
      this.connection = var1;
      int var2 = var1.getResponseCode();
      int var3 = var2;
      if (var2 == -1) {
         var3 = 0;
      }

      this.responseCode = var3;
      this.responseMessage = var1.getResponseMessage();
      ArrayList var4 = this.headerNames;
      ArrayList var5 = this.headerValues;
      Iterator var9 = var1.getHeaderFields().entrySet().iterator();

      while(true) {
         Entry var6;
         String var7;
         do {
            if (!var9.hasNext()) {
               return;
            }

            var6 = (Entry)var9.next();
            var7 = (String)var6.getKey();
         } while(var7 == null);

         Iterator var10 = ((List)var6.getValue()).iterator();

         while(var10.hasNext()) {
            String var8 = (String)var10.next();
            if (var8 != null) {
               var4.add(var7);
               var5.add(var8);
            }
         }
      }
   }

   public void disconnect() {
      this.connection.disconnect();
   }

   public InputStream getContent() throws IOException {
      InputStream var1;
      try {
         var1 = this.connection.getInputStream();
      } catch (IOException var2) {
         var1 = this.connection.getErrorStream();
      }

      NetHttpResponse.SizeValidatingInputStream var3;
      if (var1 == null) {
         var3 = null;
      } else {
         var3 = new NetHttpResponse.SizeValidatingInputStream(var1);
      }

      return var3;
   }

   public String getContentEncoding() {
      return this.connection.getContentEncoding();
   }

   public long getContentLength() {
      String var1 = this.connection.getHeaderField("Content-Length");
      long var2;
      if (var1 == null) {
         var2 = -1L;
      } else {
         var2 = Long.parseLong(var1);
      }

      return var2;
   }

   public String getContentType() {
      return this.connection.getHeaderField("Content-Type");
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
      return this.responseMessage;
   }

   public int getStatusCode() {
      return this.responseCode;
   }

   public String getStatusLine() {
      String var1 = this.connection.getHeaderField(0);
      if (var1 == null || !var1.startsWith("HTTP/1.")) {
         var1 = null;
      }

      return var1;
   }

   private final class SizeValidatingInputStream extends FilterInputStream {
      private long bytesRead = 0L;

      public SizeValidatingInputStream(InputStream var2) {
         super(var2);
      }

      private void throwIfFalseEOF() throws IOException {
         long var1 = NetHttpResponse.this.getContentLength();
         if (var1 != -1L) {
            long var3 = this.bytesRead;
            if (var3 != 0L && var3 < var1) {
               StringBuilder var5 = new StringBuilder();
               var5.append("Connection closed prematurely: bytesRead = ");
               var5.append(this.bytesRead);
               var5.append(", Content-Length = ");
               var5.append(var1);
               throw new IOException(var5.toString());
            }
         }
      }

      public int read() throws IOException {
         int var1 = this.in.read();
         if (var1 == -1) {
            this.throwIfFalseEOF();
         } else {
            ++this.bytesRead;
         }

         return var1;
      }

      public int read(byte[] var1, int var2, int var3) throws IOException {
         var2 = this.in.read(var1, var2, var3);
         if (var2 == -1) {
            this.throwIfFalseEOF();
         } else {
            this.bytesRead += (long)var2;
         }

         return var2;
      }

      public long skip(long var1) throws IOException {
         var1 = this.in.skip(var1);
         this.bytesRead += var1;
         return var1;
      }
   }
}
