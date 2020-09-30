package org.apache.http.cookie;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.params.HttpParams;

public final class CookieSpecRegistry {
   private final ConcurrentHashMap<String, CookieSpecFactory> registeredSpecs = new ConcurrentHashMap();

   public CookieSpec getCookieSpec(String var1) throws IllegalStateException {
      return this.getCookieSpec(var1, (HttpParams)null);
   }

   public CookieSpec getCookieSpec(String var1, HttpParams var2) throws IllegalStateException {
      if (var1 != null) {
         CookieSpecFactory var3 = (CookieSpecFactory)this.registeredSpecs.get(var1.toLowerCase(Locale.ENGLISH));
         if (var3 != null) {
            return var3.newInstance(var2);
         } else {
            StringBuilder var4 = new StringBuilder();
            var4.append("Unsupported cookie spec: ");
            var4.append(var1);
            throw new IllegalStateException(var4.toString());
         }
      } else {
         throw new IllegalArgumentException("Name may not be null");
      }
   }

   public List<String> getSpecNames() {
      return new ArrayList(this.registeredSpecs.keySet());
   }

   public void register(String var1, CookieSpecFactory var2) {
      if (var1 != null) {
         if (var2 != null) {
            this.registeredSpecs.put(var1.toLowerCase(Locale.ENGLISH), var2);
         } else {
            throw new IllegalArgumentException("Cookie spec factory may not be null");
         }
      } else {
         throw new IllegalArgumentException("Name may not be null");
      }
   }

   public void setItems(Map<String, CookieSpecFactory> var1) {
      if (var1 != null) {
         this.registeredSpecs.clear();
         this.registeredSpecs.putAll(var1);
      }
   }

   public void unregister(String var1) {
      if (var1 != null) {
         this.registeredSpecs.remove(var1.toLowerCase(Locale.ENGLISH));
      } else {
         throw new IllegalArgumentException("Id may not be null");
      }
   }
}
