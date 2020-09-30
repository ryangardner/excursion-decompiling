package org.apache.http.protocol;

public final class DefaultedHttpContext implements HttpContext {
   private final HttpContext defaults;
   private final HttpContext local;

   public DefaultedHttpContext(HttpContext var1, HttpContext var2) {
      if (var1 != null) {
         this.local = var1;
         this.defaults = var2;
      } else {
         throw new IllegalArgumentException("HTTP context may not be null");
      }
   }

   public Object getAttribute(String var1) {
      Object var2 = this.local.getAttribute(var1);
      return var2 == null ? this.defaults.getAttribute(var1) : var2;
   }

   public HttpContext getDefaults() {
      return this.defaults;
   }

   public Object removeAttribute(String var1) {
      return this.local.removeAttribute(var1);
   }

   public void setAttribute(String var1, Object var2) {
      this.local.setAttribute(var1, var2);
   }
}
