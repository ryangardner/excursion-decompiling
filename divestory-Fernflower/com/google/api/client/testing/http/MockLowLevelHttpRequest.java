package com.google.api.client.testing.http;

import com.google.api.client.http.HttpMediaType;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.util.Charsets;
import com.google.api.client.util.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class MockLowLevelHttpRequest extends LowLevelHttpRequest {
   private final Map<String, List<String>> headersMap = new HashMap();
   private MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
   private String url;

   public MockLowLevelHttpRequest() {
   }

   public MockLowLevelHttpRequest(String var1) {
      this.url = var1;
   }

   public void addHeader(String var1, String var2) throws IOException {
      String var3 = var1.toLowerCase(Locale.US);
      List var4 = (List)this.headersMap.get(var3);
      Object var5 = var4;
      if (var4 == null) {
         var5 = new ArrayList();
         this.headersMap.put(var3, var5);
      }

      ((List)var5).add(var2);
   }

   public LowLevelHttpResponse execute() throws IOException {
      return this.response;
   }

   public String getContentAsString() throws IOException {
      if (this.getStreamingContent() == null) {
         return "";
      } else {
         ByteArrayOutputStream var1 = new ByteArrayOutputStream();
         this.getStreamingContent().writeTo(var1);
         String var2 = this.getContentEncoding();
         ByteArrayOutputStream var3 = var1;
         if (var2 != null) {
            var3 = var1;
            if (var2.contains("gzip")) {
               GZIPInputStream var4 = new GZIPInputStream(new ByteArrayInputStream(var1.toByteArray()));
               var3 = new ByteArrayOutputStream();
               IOUtils.copy(var4, var3);
            }
         }

         String var5 = this.getContentType();
         HttpMediaType var6;
         if (var5 != null) {
            var6 = new HttpMediaType(var5);
         } else {
            var6 = null;
         }

         Charset var7;
         if (var6 != null && var6.getCharsetParameter() != null) {
            var7 = var6.getCharsetParameter();
         } else {
            var7 = Charsets.ISO_8859_1;
         }

         return var3.toString(var7.name());
      }
   }

   public String getFirstHeaderValue(String var1) {
      List var2 = (List)this.headersMap.get(var1.toLowerCase(Locale.US));
      if (var2 == null) {
         var1 = null;
      } else {
         var1 = (String)var2.get(0);
      }

      return var1;
   }

   public List<String> getHeaderValues(String var1) {
      List var2 = (List)this.headersMap.get(var1.toLowerCase(Locale.US));
      if (var2 == null) {
         var2 = Collections.emptyList();
      } else {
         var2 = Collections.unmodifiableList(var2);
      }

      return var2;
   }

   public Map<String, List<String>> getHeaders() {
      return Collections.unmodifiableMap(this.headersMap);
   }

   public MockLowLevelHttpResponse getResponse() {
      return this.response;
   }

   public String getUrl() {
      return this.url;
   }

   public MockLowLevelHttpRequest setResponse(MockLowLevelHttpResponse var1) {
      this.response = var1;
      return this;
   }

   public MockLowLevelHttpRequest setUrl(String var1) {
      this.url = var1;
      return this;
   }
}
