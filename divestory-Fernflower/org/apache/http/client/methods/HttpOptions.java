package org.apache.http.client.methods;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpResponse;

public class HttpOptions extends HttpRequestBase {
   public static final String METHOD_NAME = "OPTIONS";

   public HttpOptions() {
   }

   public HttpOptions(String var1) {
      this.setURI(URI.create(var1));
   }

   public HttpOptions(URI var1) {
      this.setURI(var1);
   }

   public Set<String> getAllowedMethods(HttpResponse var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("HTTP response may not be null");
      } else {
         HeaderIterator var6 = var1.headerIterator("Allow");
         HashSet var2 = new HashSet();

         while(var6.hasNext()) {
            HeaderElement[] var3 = var6.nextHeader().getElements();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               var2.add(var3[var5].getName());
            }
         }

         return var2;
      }
   }

   public String getMethod() {
      return "OPTIONS";
   }
}
