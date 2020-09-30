package org.apache.http.params;

public final class DefaultedHttpParams extends AbstractHttpParams {
   private final HttpParams defaults;
   private final HttpParams local;

   public DefaultedHttpParams(HttpParams var1, HttpParams var2) {
      if (var1 != null) {
         this.local = var1;
         this.defaults = var2;
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public HttpParams copy() {
      return new DefaultedHttpParams(this.local.copy(), this.defaults);
   }

   public HttpParams getDefaults() {
      return this.defaults;
   }

   public Object getParameter(String var1) {
      Object var2 = this.local.getParameter(var1);
      Object var3 = var2;
      if (var2 == null) {
         HttpParams var4 = this.defaults;
         var3 = var2;
         if (var4 != null) {
            var3 = var4.getParameter(var1);
         }
      }

      return var3;
   }

   public boolean removeParameter(String var1) {
      return this.local.removeParameter(var1);
   }

   public HttpParams setParameter(String var1, Object var2) {
      return this.local.setParameter(var1, var2);
   }
}
