package org.apache.http.impl.client;

import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HttpContext;

public class DefaultConnectionKeepAliveStrategy implements ConnectionKeepAliveStrategy {
   public long getKeepAliveDuration(HttpResponse var1, HttpContext var2) {
      if (var1 == null) {
         throw new IllegalArgumentException("HTTP response may not be null");
      } else {
         BasicHeaderElementIterator var7 = new BasicHeaderElementIterator(var1.headerIterator("Keep-Alive"));

         while(true) {
            String var8;
            String var9;
            do {
               do {
                  if (!var7.hasNext()) {
                     return -1L;
                  }

                  HeaderElement var3 = var7.nextElement();
                  var8 = var3.getName();
                  var9 = var3.getValue();
               } while(var9 == null);
            } while(!var8.equalsIgnoreCase("timeout"));

            try {
               long var4 = Long.parseLong(var9);
               return var4 * 1000L;
            } catch (NumberFormatException var6) {
            }
         }
      }
   }
}
