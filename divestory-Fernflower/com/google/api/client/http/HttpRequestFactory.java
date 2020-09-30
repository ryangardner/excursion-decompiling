package com.google.api.client.http;

import java.io.IOException;

public final class HttpRequestFactory {
   private final HttpRequestInitializer initializer;
   private final HttpTransport transport;

   HttpRequestFactory(HttpTransport var1, HttpRequestInitializer var2) {
      this.transport = var1;
      this.initializer = var2;
   }

   public HttpRequest buildDeleteRequest(GenericUrl var1) throws IOException {
      return this.buildRequest("DELETE", var1, (HttpContent)null);
   }

   public HttpRequest buildGetRequest(GenericUrl var1) throws IOException {
      return this.buildRequest("GET", var1, (HttpContent)null);
   }

   public HttpRequest buildHeadRequest(GenericUrl var1) throws IOException {
      return this.buildRequest("HEAD", var1, (HttpContent)null);
   }

   public HttpRequest buildPatchRequest(GenericUrl var1, HttpContent var2) throws IOException {
      return this.buildRequest("PATCH", var1, var2);
   }

   public HttpRequest buildPostRequest(GenericUrl var1, HttpContent var2) throws IOException {
      return this.buildRequest("POST", var1, var2);
   }

   public HttpRequest buildPutRequest(GenericUrl var1, HttpContent var2) throws IOException {
      return this.buildRequest("PUT", var1, var2);
   }

   public HttpRequest buildRequest(String var1, GenericUrl var2, HttpContent var3) throws IOException {
      HttpRequest var4 = this.transport.buildRequest();
      HttpRequestInitializer var5 = this.initializer;
      if (var5 != null) {
         var5.initialize(var4);
      }

      var4.setRequestMethod(var1);
      if (var2 != null) {
         var4.setUrl(var2);
      }

      if (var3 != null) {
         var4.setContent(var3);
      }

      return var4;
   }

   public HttpRequestInitializer getInitializer() {
      return this.initializer;
   }

   public HttpTransport getTransport() {
      return this.transport;
   }
}
