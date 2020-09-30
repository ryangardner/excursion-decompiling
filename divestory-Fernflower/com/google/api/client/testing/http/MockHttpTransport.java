package com.google.api.client.testing.http;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

public class MockHttpTransport extends HttpTransport {
   private MockLowLevelHttpRequest lowLevelHttpRequest;
   private MockLowLevelHttpResponse lowLevelHttpResponse;
   private Set<String> supportedMethods;

   public MockHttpTransport() {
   }

   protected MockHttpTransport(MockHttpTransport.Builder var1) {
      this.supportedMethods = var1.supportedMethods;
      this.lowLevelHttpRequest = var1.lowLevelHttpRequest;
      this.lowLevelHttpResponse = var1.lowLevelHttpResponse;
   }

   public LowLevelHttpRequest buildRequest(String var1, String var2) throws IOException {
      Preconditions.checkArgument(this.supportsMethod(var1), "HTTP method %s not supported", var1);
      MockLowLevelHttpRequest var3 = this.lowLevelHttpRequest;
      if (var3 != null) {
         return var3;
      } else {
         MockLowLevelHttpRequest var5 = new MockLowLevelHttpRequest(var2);
         this.lowLevelHttpRequest = var5;
         MockLowLevelHttpResponse var4 = this.lowLevelHttpResponse;
         if (var4 != null) {
            var5.setResponse(var4);
         }

         return this.lowLevelHttpRequest;
      }
   }

   public final MockLowLevelHttpRequest getLowLevelHttpRequest() {
      return this.lowLevelHttpRequest;
   }

   public final Set<String> getSupportedMethods() {
      Set var1 = this.supportedMethods;
      if (var1 == null) {
         var1 = null;
      } else {
         var1 = Collections.unmodifiableSet(var1);
      }

      return var1;
   }

   public boolean supportsMethod(String var1) throws IOException {
      Set var2 = this.supportedMethods;
      boolean var3;
      if (var2 != null && !var2.contains(var1)) {
         var3 = false;
      } else {
         var3 = true;
      }

      return var3;
   }

   public static class Builder {
      MockLowLevelHttpRequest lowLevelHttpRequest;
      MockLowLevelHttpResponse lowLevelHttpResponse;
      Set<String> supportedMethods;

      public MockHttpTransport build() {
         return new MockHttpTransport(this);
      }

      public final MockLowLevelHttpRequest getLowLevelHttpRequest() {
         return this.lowLevelHttpRequest;
      }

      MockLowLevelHttpResponse getLowLevelHttpResponse() {
         return this.lowLevelHttpResponse;
      }

      public final Set<String> getSupportedMethods() {
         return this.supportedMethods;
      }

      public final MockHttpTransport.Builder setLowLevelHttpRequest(MockLowLevelHttpRequest var1) {
         boolean var2;
         if (this.lowLevelHttpResponse == null) {
            var2 = true;
         } else {
            var2 = false;
         }

         Preconditions.checkState(var2, "Cannnot set a low level HTTP request when a low level HTTP response has been set.");
         this.lowLevelHttpRequest = var1;
         return this;
      }

      public final MockHttpTransport.Builder setLowLevelHttpResponse(MockLowLevelHttpResponse var1) {
         boolean var2;
         if (this.lowLevelHttpRequest == null) {
            var2 = true;
         } else {
            var2 = false;
         }

         Preconditions.checkState(var2, "Cannot set a low level HTTP response when a low level HTTP request has been set.");
         this.lowLevelHttpResponse = var1;
         return this;
      }

      public final MockHttpTransport.Builder setSupportedMethods(Set<String> var1) {
         this.supportedMethods = var1;
         return this;
      }
   }
}
