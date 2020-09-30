package org.apache.http.impl;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.ProtocolVersion;
import org.apache.http.TokenIterator;
import org.apache.http.message.BasicTokenIterator;
import org.apache.http.protocol.HttpContext;

public class DefaultConnectionReuseStrategy implements ConnectionReuseStrategy {
   protected TokenIterator createTokenIterator(HeaderIterator var1) {
      return new BasicTokenIterator(var1);
   }

   public boolean keepAlive(HttpResponse var1, HttpContext var2) {
      if (var1 == null) {
         throw new IllegalArgumentException("HTTP response may not be null.");
      } else if (var2 != null) {
         HttpConnection var11 = (HttpConnection)var2.getAttribute("http.connection");
         if (var11 != null && !var11.isOpen()) {
            return false;
         } else {
            HttpEntity var12 = var1.getEntity();
            ProtocolVersion var3 = var1.getStatusLine().getProtocolVersion();
            if (var12 != null && var12.getContentLength() < 0L && (!var12.isChunked() || var3.lessEquals(HttpVersion.HTTP_1_0))) {
               return false;
            } else {
               HeaderIterator var4 = var1.headerIterator("Connection");
               HeaderIterator var13 = var4;
               if (!var4.hasNext()) {
                  var13 = var1.headerIterator("Proxy-Connection");
               }

               if (var13.hasNext()) {
                  boolean var5;
                  label100: {
                     TokenIterator var14;
                     boolean var10001;
                     try {
                        var14 = this.createTokenIterator(var13);
                     } catch (ParseException var9) {
                        var10001 = false;
                        return false;
                     }

                     var5 = false;

                     while(true) {
                        String var10;
                        try {
                           if (!var14.hasNext()) {
                              break label100;
                           }

                           var10 = var14.nextToken();
                           if ("Close".equalsIgnoreCase(var10)) {
                              return false;
                           }
                        } catch (ParseException var8) {
                           var10001 = false;
                           break;
                        }

                        boolean var6;
                        try {
                           var6 = "Keep-Alive".equalsIgnoreCase(var10);
                        } catch (ParseException var7) {
                           var10001 = false;
                           break;
                        }

                        if (var6) {
                           var5 = true;
                        }
                     }

                     return false;
                  }

                  if (var5) {
                     return true;
                  }
               }

               return var3.lessEquals(HttpVersion.HTTP_1_0) ^ true;
            }
         }
      } else {
         throw new IllegalArgumentException("HTTP context may not be null.");
      }
   }
}
