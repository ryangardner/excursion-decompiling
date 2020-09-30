package org.apache.http.params;

public abstract class HttpAbstractParamBean {
   protected final HttpParams params;

   public HttpAbstractParamBean(HttpParams var1) {
      if (var1 != null) {
         this.params = var1;
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }
}
