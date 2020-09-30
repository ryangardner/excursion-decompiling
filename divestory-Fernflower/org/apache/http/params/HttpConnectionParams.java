package org.apache.http.params;

public final class HttpConnectionParams implements CoreConnectionPNames {
   private HttpConnectionParams() {
   }

   public static int getConnectionTimeout(HttpParams var0) {
      if (var0 != null) {
         return var0.getIntParameter("http.connection.timeout", 0);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static int getLinger(HttpParams var0) {
      if (var0 != null) {
         return var0.getIntParameter("http.socket.linger", -1);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static boolean getSoReuseaddr(HttpParams var0) {
      if (var0 != null) {
         return var0.getBooleanParameter("http.socket.reuseaddr", false);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static int getSoTimeout(HttpParams var0) {
      if (var0 != null) {
         return var0.getIntParameter("http.socket.timeout", 0);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static int getSocketBufferSize(HttpParams var0) {
      if (var0 != null) {
         return var0.getIntParameter("http.socket.buffer-size", -1);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static boolean getTcpNoDelay(HttpParams var0) {
      if (var0 != null) {
         return var0.getBooleanParameter("http.tcp.nodelay", true);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static boolean isStaleCheckingEnabled(HttpParams var0) {
      if (var0 != null) {
         return var0.getBooleanParameter("http.connection.stalecheck", true);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static void setConnectionTimeout(HttpParams var0, int var1) {
      if (var0 != null) {
         var0.setIntParameter("http.connection.timeout", var1);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static void setLinger(HttpParams var0, int var1) {
      if (var0 != null) {
         var0.setIntParameter("http.socket.linger", var1);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static void setSoReuseaddr(HttpParams var0, boolean var1) {
      if (var0 != null) {
         var0.setBooleanParameter("http.socket.reuseaddr", var1);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static void setSoTimeout(HttpParams var0, int var1) {
      if (var0 != null) {
         var0.setIntParameter("http.socket.timeout", var1);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static void setSocketBufferSize(HttpParams var0, int var1) {
      if (var0 != null) {
         var0.setIntParameter("http.socket.buffer-size", var1);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static void setStaleCheckingEnabled(HttpParams var0, boolean var1) {
      if (var0 != null) {
         var0.setBooleanParameter("http.connection.stalecheck", var1);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   public static void setTcpNoDelay(HttpParams var0, boolean var1) {
      if (var0 != null) {
         var0.setBooleanParameter("http.tcp.nodelay", var1);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }
}
