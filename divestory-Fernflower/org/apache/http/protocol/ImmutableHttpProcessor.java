package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;

public final class ImmutableHttpProcessor implements HttpProcessor {
   private final HttpRequestInterceptor[] requestInterceptors;
   private final HttpResponseInterceptor[] responseInterceptors;

   public ImmutableHttpProcessor(HttpRequestInterceptorList var1, HttpResponseInterceptorList var2) {
      byte var3 = 0;
      int var4;
      int var5;
      if (var1 != null) {
         var4 = var1.getRequestInterceptorCount();
         this.requestInterceptors = new HttpRequestInterceptor[var4];

         for(var5 = 0; var5 < var4; ++var5) {
            this.requestInterceptors[var5] = var1.getRequestInterceptor(var5);
         }
      } else {
         this.requestInterceptors = new HttpRequestInterceptor[0];
      }

      if (var2 != null) {
         var4 = var2.getResponseInterceptorCount();
         this.responseInterceptors = new HttpResponseInterceptor[var4];

         for(var5 = var3; var5 < var4; ++var5) {
            this.responseInterceptors[var5] = var2.getResponseInterceptor(var5);
         }
      } else {
         this.responseInterceptors = new HttpResponseInterceptor[0];
      }

   }

   public ImmutableHttpProcessor(HttpRequestInterceptor[] var1) {
      this((HttpRequestInterceptor[])var1, (HttpResponseInterceptor[])null);
   }

   public ImmutableHttpProcessor(HttpRequestInterceptor[] var1, HttpResponseInterceptor[] var2) {
      byte var3 = 0;
      int var4;
      int var5;
      if (var1 != null) {
         var4 = var1.length;
         this.requestInterceptors = new HttpRequestInterceptor[var4];

         for(var5 = 0; var5 < var4; ++var5) {
            this.requestInterceptors[var5] = var1[var5];
         }
      } else {
         this.requestInterceptors = new HttpRequestInterceptor[0];
      }

      if (var2 != null) {
         var4 = var2.length;
         this.responseInterceptors = new HttpResponseInterceptor[var4];

         for(var5 = var3; var5 < var4; ++var5) {
            this.responseInterceptors[var5] = var2[var5];
         }
      } else {
         this.responseInterceptors = new HttpResponseInterceptor[0];
      }

   }

   public ImmutableHttpProcessor(HttpResponseInterceptor[] var1) {
      this((HttpRequestInterceptor[])null, (HttpResponseInterceptor[])var1);
   }

   public void process(HttpRequest var1, HttpContext var2) throws IOException, HttpException {
      int var3 = 0;

      while(true) {
         HttpRequestInterceptor[] var4 = this.requestInterceptors;
         if (var3 >= var4.length) {
            return;
         }

         var4[var3].process(var1, var2);
         ++var3;
      }
   }

   public void process(HttpResponse var1, HttpContext var2) throws IOException, HttpException {
      int var3 = 0;

      while(true) {
         HttpResponseInterceptor[] var4 = this.responseInterceptors;
         if (var3 >= var4.length) {
            return;
         }

         var4[var3].process(var1, var2);
         ++var3;
      }
   }
}
