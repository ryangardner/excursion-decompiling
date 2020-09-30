package org.apache.http.auth;

import java.util.Locale;
import org.apache.http.util.LangUtils;

public class AuthScope {
   public static final AuthScope ANY;
   public static final String ANY_HOST;
   public static final int ANY_PORT = -1;
   public static final String ANY_REALM;
   public static final String ANY_SCHEME;
   private final String host;
   private final int port;
   private final String realm;
   private final String scheme;

   static {
      ANY = new AuthScope(ANY_HOST, -1, ANY_REALM, ANY_SCHEME);
   }

   public AuthScope(String var1, int var2) {
      this(var1, var2, ANY_REALM, ANY_SCHEME);
   }

   public AuthScope(String var1, int var2, String var3) {
      this(var1, var2, var3, ANY_SCHEME);
   }

   public AuthScope(String var1, int var2, String var3, String var4) {
      if (var1 == null) {
         var1 = ANY_HOST;
      } else {
         var1 = var1.toLowerCase(Locale.ENGLISH);
      }

      this.host = var1;
      int var5 = var2;
      if (var2 < 0) {
         var5 = -1;
      }

      this.port = var5;
      var1 = var3;
      if (var3 == null) {
         var1 = ANY_REALM;
      }

      this.realm = var1;
      if (var4 == null) {
         var1 = ANY_SCHEME;
      } else {
         var1 = var4.toUpperCase(Locale.ENGLISH);
      }

      this.scheme = var1;
   }

   public AuthScope(AuthScope var1) {
      if (var1 != null) {
         this.host = var1.getHost();
         this.port = var1.getPort();
         this.realm = var1.getRealm();
         this.scheme = var1.getScheme();
      } else {
         throw new IllegalArgumentException("Scope may not be null");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = false;
      if (var1 == null) {
         return false;
      } else if (var1 == this) {
         return true;
      } else if (!(var1 instanceof AuthScope)) {
         return super.equals(var1);
      } else {
         AuthScope var4 = (AuthScope)var1;
         boolean var3 = var2;
         if (LangUtils.equals((Object)this.host, (Object)var4.host)) {
            var3 = var2;
            if (this.port == var4.port) {
               var3 = var2;
               if (LangUtils.equals((Object)this.realm, (Object)var4.realm)) {
                  var3 = var2;
                  if (LangUtils.equals((Object)this.scheme, (Object)var4.scheme)) {
                     var3 = true;
                  }
               }
            }
         }

         return var3;
      }
   }

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      return this.port;
   }

   public String getRealm() {
      return this.realm;
   }

   public String getScheme() {
      return this.scheme;
   }

   public int hashCode() {
      return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(17, this.host), this.port), this.realm), this.scheme);
   }

   public int match(AuthScope var1) {
      byte var2;
      String var3;
      String var4;
      if (LangUtils.equals((Object)this.scheme, (Object)var1.scheme)) {
         var2 = 1;
      } else {
         var3 = this.scheme;
         var4 = ANY_SCHEME;
         if (var3 != var4 && var1.scheme != var4) {
            return -1;
         }

         var2 = 0;
      }

      int var5;
      if (LangUtils.equals((Object)this.realm, (Object)var1.realm)) {
         var5 = var2 + 2;
      } else {
         var3 = this.realm;
         var4 = ANY_REALM;
         var5 = var2;
         if (var3 != var4) {
            var5 = var2;
            if (var1.realm != var4) {
               return -1;
            }
         }
      }

      int var6 = this.port;
      int var7 = var1.port;
      int var8;
      if (var6 == var7) {
         var8 = var5 + 4;
      } else {
         var8 = var5;
         if (var6 != -1) {
            var8 = var5;
            if (var7 != -1) {
               return -1;
            }
         }
      }

      if (LangUtils.equals((Object)this.host, (Object)var1.host)) {
         var5 = var8 + 8;
      } else {
         var4 = this.host;
         var3 = ANY_HOST;
         var5 = var8;
         if (var4 != var3) {
            var5 = var8;
            if (var1.host != var3) {
               return -1;
            }
         }
      }

      return var5;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.scheme;
      if (var2 != null) {
         var1.append(var2.toUpperCase(Locale.ENGLISH));
         var1.append(' ');
      }

      if (this.realm != null) {
         var1.append('\'');
         var1.append(this.realm);
         var1.append('\'');
      } else {
         var1.append("<any realm>");
      }

      if (this.host != null) {
         var1.append('@');
         var1.append(this.host);
         if (this.port >= 0) {
            var1.append(':');
            var1.append(this.port);
         }
      }

      return var1.toString();
   }
}
