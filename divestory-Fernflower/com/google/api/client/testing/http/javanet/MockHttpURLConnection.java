package com.google.api.client.testing.http.javanet;

import com.google.api.client.util.Preconditions;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MockHttpURLConnection extends HttpURLConnection {
   @Deprecated
   public static final byte[] ERROR_BUF = new byte[5];
   @Deprecated
   public static final byte[] INPUT_BUF = new byte[1];
   private boolean doOutputCalled;
   private InputStream errorStream = null;
   private Map<String, List<String>> headers = new LinkedHashMap();
   private InputStream inputStream = null;
   private OutputStream outputStream = new ByteArrayOutputStream(0);

   public MockHttpURLConnection(URL var1) {
      super(var1);
   }

   public MockHttpURLConnection addHeader(String var1, String var2) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      if (this.headers.containsKey(var1)) {
         ((List)this.headers.get(var1)).add(var2);
      } else {
         ArrayList var3 = new ArrayList();
         var3.add(var2);
         this.headers.put(var1, var3);
      }

      return this;
   }

   public void connect() throws IOException {
   }

   public void disconnect() {
   }

   public final boolean doOutputCalled() {
      return this.doOutputCalled;
   }

   public int getChunkLength() {
      return this.chunkLength;
   }

   public InputStream getErrorStream() {
      return this.errorStream;
   }

   public String getHeaderField(String var1) {
      List var2 = (List)this.headers.get(var1);
      if (var2 == null) {
         var1 = null;
      } else {
         var1 = (String)var2.get(0);
      }

      return var1;
   }

   public Map<String, List<String>> getHeaderFields() {
      return this.headers;
   }

   public InputStream getInputStream() throws IOException {
      if (this.responseCode < 400) {
         return this.inputStream;
      } else {
         throw new IOException();
      }
   }

   public OutputStream getOutputStream() throws IOException {
      OutputStream var1 = this.outputStream;
      return var1 != null ? var1 : super.getOutputStream();
   }

   public int getResponseCode() throws IOException {
      return this.responseCode;
   }

   public void setDoOutput(boolean var1) {
      this.doOutputCalled = true;
   }

   public MockHttpURLConnection setErrorStream(InputStream var1) {
      Preconditions.checkNotNull(var1);
      if (this.errorStream == null) {
         this.errorStream = var1;
      }

      return this;
   }

   public MockHttpURLConnection setInputStream(InputStream var1) {
      Preconditions.checkNotNull(var1);
      if (this.inputStream == null) {
         this.inputStream = var1;
      }

      return this;
   }

   public MockHttpURLConnection setOutputStream(OutputStream var1) {
      this.outputStream = var1;
      return this;
   }

   public MockHttpURLConnection setResponseCode(int var1) {
      boolean var2;
      if (var1 >= -1) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2);
      this.responseCode = var1;
      return this;
   }

   public boolean usingProxy() {
      return false;
   }
}
