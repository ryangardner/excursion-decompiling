package org.apache.http.impl.cookie;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieSpec;

public abstract class AbstractCookieSpec implements CookieSpec {
   private final Map<String, CookieAttributeHandler> attribHandlerMap = new HashMap(10);

   protected CookieAttributeHandler findAttribHandler(String var1) {
      return (CookieAttributeHandler)this.attribHandlerMap.get(var1);
   }

   protected CookieAttributeHandler getAttribHandler(String var1) {
      CookieAttributeHandler var2 = this.findAttribHandler(var1);
      if (var2 != null) {
         return var2;
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Handler not registered for ");
         var3.append(var1);
         var3.append(" attribute.");
         throw new IllegalStateException(var3.toString());
      }
   }

   protected Collection<CookieAttributeHandler> getAttribHandlers() {
      return this.attribHandlerMap.values();
   }

   public void registerAttribHandler(String var1, CookieAttributeHandler var2) {
      if (var1 != null) {
         if (var2 != null) {
            this.attribHandlerMap.put(var1, var2);
         } else {
            throw new IllegalArgumentException("Attribute handler may not be null");
         }
      } else {
         throw new IllegalArgumentException("Attribute name may not be null");
      }
   }
}
