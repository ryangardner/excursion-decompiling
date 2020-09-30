package org.apache.http.impl.client;

import java.util.HashMap;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScheme;
import org.apache.http.client.AuthCache;

public class BasicAuthCache implements AuthCache {
   private final HashMap<HttpHost, AuthScheme> map = new HashMap();

   public void clear() {
      this.map.clear();
   }

   public AuthScheme get(HttpHost var1) {
      if (var1 != null) {
         return (AuthScheme)this.map.get(var1);
      } else {
         throw new IllegalArgumentException("HTTP host may not be null");
      }
   }

   public void put(HttpHost var1, AuthScheme var2) {
      if (var1 != null) {
         this.map.put(var1, var2);
      } else {
         throw new IllegalArgumentException("HTTP host may not be null");
      }
   }

   public void remove(HttpHost var1) {
      if (var1 != null) {
         this.map.remove(var1);
      } else {
         throw new IllegalArgumentException("HTTP host may not be null");
      }
   }

   public String toString() {
      return this.map.toString();
   }
}
