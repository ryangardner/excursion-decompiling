package org.apache.http.protocol;

import java.util.HashMap;
import java.util.Map;

public class BasicHttpContext implements HttpContext {
   private Map map;
   private final HttpContext parentContext;

   public BasicHttpContext() {
      this((HttpContext)null);
   }

   public BasicHttpContext(HttpContext var1) {
      this.map = null;
      this.parentContext = var1;
   }

   public Object getAttribute(String var1) {
      if (var1 != null) {
         Object var2 = null;
         Map var3 = this.map;
         if (var3 != null) {
            var2 = var3.get(var1);
         }

         Object var5 = var2;
         if (var2 == null) {
            HttpContext var4 = this.parentContext;
            var5 = var2;
            if (var4 != null) {
               var5 = var4.getAttribute(var1);
            }
         }

         return var5;
      } else {
         throw new IllegalArgumentException("Id may not be null");
      }
   }

   public Object removeAttribute(String var1) {
      if (var1 != null) {
         Map var2 = this.map;
         return var2 != null ? var2.remove(var1) : null;
      } else {
         throw new IllegalArgumentException("Id may not be null");
      }
   }

   public void setAttribute(String var1, Object var2) {
      if (var1 != null) {
         if (this.map == null) {
            this.map = new HashMap();
         }

         this.map.put(var1, var2);
      } else {
         throw new IllegalArgumentException("Id may not be null");
      }
   }
}
