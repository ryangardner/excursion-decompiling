package org.apache.http.impl;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;

public class NoConnectionReuseStrategy implements ConnectionReuseStrategy {
   public boolean keepAlive(HttpResponse var1, HttpContext var2) {
      if (var1 != null) {
         if (var2 != null) {
            return false;
         } else {
            throw new IllegalArgumentException("HTTP context may not be null");
         }
      } else {
         throw new IllegalArgumentException("HTTP response may not be null");
      }
   }
}
