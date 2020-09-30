package com.google.api.client.googleapis;

import com.google.api.client.http.EmptyContent;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.UrlEncodedContent;
import java.io.IOException;

public final class MethodOverride implements HttpExecuteInterceptor, HttpRequestInitializer {
   public static final String HEADER = "X-HTTP-Method-Override";
   static final int MAX_URL_LENGTH = 2048;
   private final boolean overrideAllMethods;

   public MethodOverride() {
      this(false);
   }

   MethodOverride(boolean var1) {
      this.overrideAllMethods = var1;
   }

   private boolean overrideThisMethod(HttpRequest var1) throws IOException {
      String var2 = var1.getRequestMethod();
      if (var2.equals("POST")) {
         return false;
      } else {
         if (var2.equals("GET")) {
            if (var1.getUrl().build().length() > 2048) {
               return true;
            }
         } else if (this.overrideAllMethods) {
            return true;
         }

         return var1.getTransport().supportsMethod(var2) ^ true;
      }
   }

   public void initialize(HttpRequest var1) {
      var1.setInterceptor(this);
   }

   public void intercept(HttpRequest var1) throws IOException {
      if (this.overrideThisMethod(var1)) {
         String var2 = var1.getRequestMethod();
         var1.setRequestMethod("POST");
         var1.getHeaders().set("X-HTTP-Method-Override", var2);
         if (var2.equals("GET")) {
            var1.setContent(new UrlEncodedContent(var1.getUrl().clone()));
            var1.getUrl().clear();
         } else if (var1.getContent() == null) {
            var1.setContent(new EmptyContent());
         }
      }

   }

   public static final class Builder {
      private boolean overrideAllMethods;

      public MethodOverride build() {
         return new MethodOverride(this.overrideAllMethods);
      }

      public boolean getOverrideAllMethods() {
         return this.overrideAllMethods;
      }

      public MethodOverride.Builder setOverrideAllMethods(boolean var1) {
         this.overrideAllMethods = var1;
         return this;
      }
   }
}
