package org.apache.http.params;

import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;

public final class HttpProtocolParams implements CoreProtocolPNames {
   private HttpProtocolParams() {
   }

   public static String getContentCharset(HttpParams var0) {
      if (var0 != null) {
         String var1 = (String)var0.getParameter("http.protocol.content-charset");
         String var2 = var1;
         if (var1 == null) {
            var2 = "ISO-8859-1";
         }

         return var2;
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static String getHttpElementCharset(HttpParams var0) {
      if (var0 != null) {
         String var1 = (String)var0.getParameter("http.protocol.element-charset");
         String var2 = var1;
         if (var1 == null) {
            var2 = "US-ASCII";
         }

         return var2;
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static String getUserAgent(HttpParams var0) {
      if (var0 != null) {
         return (String)var0.getParameter("http.useragent");
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static ProtocolVersion getVersion(HttpParams var0) {
      if (var0 != null) {
         Object var1 = var0.getParameter("http.protocol.version");
         return (ProtocolVersion)(var1 == null ? HttpVersion.HTTP_1_1 : (ProtocolVersion)var1);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static void setContentCharset(HttpParams var0, String var1) {
      if (var0 != null) {
         var0.setParameter("http.protocol.content-charset", var1);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static void setHttpElementCharset(HttpParams var0, String var1) {
      if (var0 != null) {
         var0.setParameter("http.protocol.element-charset", var1);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static void setUseExpectContinue(HttpParams var0, boolean var1) {
      if (var0 != null) {
         var0.setBooleanParameter("http.protocol.expect-continue", var1);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static void setUserAgent(HttpParams var0, String var1) {
      if (var0 != null) {
         var0.setParameter("http.useragent", var1);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static void setVersion(HttpParams var0, ProtocolVersion var1) {
      if (var0 != null) {
         var0.setParameter("http.protocol.version", var1);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static boolean useExpectContinue(HttpParams var0) {
      if (var0 != null) {
         return var0.getBooleanParameter("http.protocol.expect-continue", false);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }
}
