package org.apache.http.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.params.HttpParams;

public final class AuthSchemeRegistry {
   private final ConcurrentHashMap<String, AuthSchemeFactory> registeredSchemes = new ConcurrentHashMap();

   public AuthScheme getAuthScheme(String var1, HttpParams var2) throws IllegalStateException {
      if (var1 != null) {
         AuthSchemeFactory var3 = (AuthSchemeFactory)this.registeredSchemes.get(var1.toLowerCase(Locale.ENGLISH));
         if (var3 != null) {
            return var3.newInstance(var2);
         } else {
            StringBuilder var4 = new StringBuilder();
            var4.append("Unsupported authentication scheme: ");
            var4.append(var1);
            throw new IllegalStateException(var4.toString());
         }
      } else {
         throw new IllegalArgumentException("Name may not be null");
      }
   }

   public List<String> getSchemeNames() {
      return new ArrayList(this.registeredSchemes.keySet());
   }

   public void register(String var1, AuthSchemeFactory var2) {
      if (var1 != null) {
         if (var2 != null) {
            this.registeredSchemes.put(var1.toLowerCase(Locale.ENGLISH), var2);
         } else {
            throw new IllegalArgumentException("Authentication scheme factory may not be null");
         }
      } else {
         throw new IllegalArgumentException("Name may not be null");
      }
   }

   public void setItems(Map<String, AuthSchemeFactory> var1) {
      if (var1 != null) {
         this.registeredSchemes.clear();
         this.registeredSchemes.putAll(var1);
      }
   }

   public void unregister(String var1) {
      if (var1 != null) {
         this.registeredSchemes.remove(var1.toLowerCase(Locale.ENGLISH));
      } else {
         throw new IllegalArgumentException("Name may not be null");
      }
   }
}
