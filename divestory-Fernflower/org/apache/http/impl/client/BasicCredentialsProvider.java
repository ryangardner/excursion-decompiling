package org.apache.http.impl.client;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;

public class BasicCredentialsProvider implements CredentialsProvider {
   private final ConcurrentHashMap<AuthScope, Credentials> credMap = new ConcurrentHashMap();

   private static Credentials matchCredentials(Map<AuthScope, Credentials> var0, AuthScope var1) {
      Credentials var2 = (Credentials)var0.get(var1);
      Credentials var3 = var2;
      if (var2 == null) {
         int var4 = -1;
         AuthScope var5 = null;
         Iterator var6 = var0.keySet().iterator();

         while(var6.hasNext()) {
            AuthScope var8 = (AuthScope)var6.next();
            int var7 = var1.match(var8);
            if (var7 > var4) {
               var5 = var8;
               var4 = var7;
            }
         }

         var3 = var2;
         if (var5 != null) {
            var3 = (Credentials)var0.get(var5);
         }
      }

      return var3;
   }

   public void clear() {
      this.credMap.clear();
   }

   public Credentials getCredentials(AuthScope var1) {
      if (var1 != null) {
         return matchCredentials(this.credMap, var1);
      } else {
         throw new IllegalArgumentException("Authentication scope may not be null");
      }
   }

   public void setCredentials(AuthScope var1, Credentials var2) {
      if (var1 != null) {
         this.credMap.put(var1, var2);
      } else {
         throw new IllegalArgumentException("Authentication scope may not be null");
      }
   }

   public String toString() {
      return this.credMap.toString();
   }
}
