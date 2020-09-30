package org.apache.http.impl;

import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.RequestLine;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicHttpRequest;

public class DefaultHttpRequestFactory implements HttpRequestFactory {
   private static final String[] RFC2616_COMMON_METHODS = new String[]{"GET"};
   private static final String[] RFC2616_ENTITY_ENC_METHODS = new String[]{"POST", "PUT"};
   private static final String[] RFC2616_SPECIAL_METHODS = new String[]{"HEAD", "OPTIONS", "DELETE", "TRACE", "CONNECT"};

   private static boolean isOneOf(String[] var0, String var1) {
      for(int var2 = 0; var2 < var0.length; ++var2) {
         if (var0[var2].equalsIgnoreCase(var1)) {
            return true;
         }
      }

      return false;
   }

   public HttpRequest newHttpRequest(String var1, String var2) throws MethodNotSupportedException {
      if (isOneOf(RFC2616_COMMON_METHODS, var1)) {
         return new BasicHttpRequest(var1, var2);
      } else if (isOneOf(RFC2616_ENTITY_ENC_METHODS, var1)) {
         return new BasicHttpEntityEnclosingRequest(var1, var2);
      } else if (isOneOf(RFC2616_SPECIAL_METHODS, var1)) {
         return new BasicHttpRequest(var1, var2);
      } else {
         StringBuffer var3 = new StringBuffer();
         var3.append(var1);
         var3.append(" method not supported");
         throw new MethodNotSupportedException(var3.toString());
      }
   }

   public HttpRequest newHttpRequest(RequestLine var1) throws MethodNotSupportedException {
      if (var1 != null) {
         String var2 = var1.getMethod();
         if (isOneOf(RFC2616_COMMON_METHODS, var2)) {
            return new BasicHttpRequest(var1);
         } else if (isOneOf(RFC2616_ENTITY_ENC_METHODS, var2)) {
            return new BasicHttpEntityEnclosingRequest(var1);
         } else if (isOneOf(RFC2616_SPECIAL_METHODS, var2)) {
            return new BasicHttpRequest(var1);
         } else {
            StringBuffer var3 = new StringBuffer();
            var3.append(var2);
            var3.append(" method not supported");
            throw new MethodNotSupportedException(var3.toString());
         }
      } else {
         throw new IllegalArgumentException("Request line may not be null");
      }
   }
}
