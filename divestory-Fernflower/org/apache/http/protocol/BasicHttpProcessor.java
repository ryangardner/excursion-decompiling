package org.apache.http.protocol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;

public final class BasicHttpProcessor implements HttpProcessor, HttpRequestInterceptorList, HttpResponseInterceptorList, Cloneable {
   protected final List requestInterceptors = new ArrayList();
   protected final List responseInterceptors = new ArrayList();

   public final void addInterceptor(HttpRequestInterceptor var1) {
      this.addRequestInterceptor(var1);
   }

   public final void addInterceptor(HttpRequestInterceptor var1, int var2) {
      this.addRequestInterceptor(var1, var2);
   }

   public final void addInterceptor(HttpResponseInterceptor var1) {
      this.addResponseInterceptor(var1);
   }

   public final void addInterceptor(HttpResponseInterceptor var1, int var2) {
      this.addResponseInterceptor(var1, var2);
   }

   public void addRequestInterceptor(HttpRequestInterceptor var1) {
      if (var1 != null) {
         this.requestInterceptors.add(var1);
      }
   }

   public void addRequestInterceptor(HttpRequestInterceptor var1, int var2) {
      if (var1 != null) {
         this.requestInterceptors.add(var2, var1);
      }
   }

   public void addResponseInterceptor(HttpResponseInterceptor var1) {
      if (var1 != null) {
         this.responseInterceptors.add(var1);
      }
   }

   public void addResponseInterceptor(HttpResponseInterceptor var1, int var2) {
      if (var1 != null) {
         this.responseInterceptors.add(var2, var1);
      }
   }

   public void clearInterceptors() {
      this.clearRequestInterceptors();
      this.clearResponseInterceptors();
   }

   public void clearRequestInterceptors() {
      this.requestInterceptors.clear();
   }

   public void clearResponseInterceptors() {
      this.responseInterceptors.clear();
   }

   public Object clone() throws CloneNotSupportedException {
      BasicHttpProcessor var1 = (BasicHttpProcessor)super.clone();
      this.copyInterceptors(var1);
      return var1;
   }

   public BasicHttpProcessor copy() {
      BasicHttpProcessor var1 = new BasicHttpProcessor();
      this.copyInterceptors(var1);
      return var1;
   }

   protected void copyInterceptors(BasicHttpProcessor var1) {
      var1.requestInterceptors.clear();
      var1.requestInterceptors.addAll(this.requestInterceptors);
      var1.responseInterceptors.clear();
      var1.responseInterceptors.addAll(this.responseInterceptors);
   }

   public HttpRequestInterceptor getRequestInterceptor(int var1) {
      return var1 >= 0 && var1 < this.requestInterceptors.size() ? (HttpRequestInterceptor)this.requestInterceptors.get(var1) : null;
   }

   public int getRequestInterceptorCount() {
      return this.requestInterceptors.size();
   }

   public HttpResponseInterceptor getResponseInterceptor(int var1) {
      return var1 >= 0 && var1 < this.responseInterceptors.size() ? (HttpResponseInterceptor)this.responseInterceptors.get(var1) : null;
   }

   public int getResponseInterceptorCount() {
      return this.responseInterceptors.size();
   }

   public void process(HttpRequest var1, HttpContext var2) throws IOException, HttpException {
      for(int var3 = 0; var3 < this.requestInterceptors.size(); ++var3) {
         ((HttpRequestInterceptor)this.requestInterceptors.get(var3)).process(var1, var2);
      }

   }

   public void process(HttpResponse var1, HttpContext var2) throws IOException, HttpException {
      for(int var3 = 0; var3 < this.responseInterceptors.size(); ++var3) {
         ((HttpResponseInterceptor)this.responseInterceptors.get(var3)).process(var1, var2);
      }

   }

   public void removeRequestInterceptorByClass(Class var1) {
      Iterator var2 = this.requestInterceptors.iterator();

      while(var2.hasNext()) {
         if (var2.next().getClass().equals(var1)) {
            var2.remove();
         }
      }

   }

   public void removeResponseInterceptorByClass(Class var1) {
      Iterator var2 = this.responseInterceptors.iterator();

      while(var2.hasNext()) {
         if (var2.next().getClass().equals(var1)) {
            var2.remove();
         }
      }

   }

   public void setInterceptors(List var1) {
      if (var1 != null) {
         this.requestInterceptors.clear();
         this.responseInterceptors.clear();

         for(int var2 = 0; var2 < var1.size(); ++var2) {
            Object var3 = var1.get(var2);
            if (var3 instanceof HttpRequestInterceptor) {
               this.addInterceptor((HttpRequestInterceptor)var3);
            }

            if (var3 instanceof HttpResponseInterceptor) {
               this.addInterceptor((HttpResponseInterceptor)var3);
            }
         }

      } else {
         throw new IllegalArgumentException("List must not be null.");
      }
   }
}
