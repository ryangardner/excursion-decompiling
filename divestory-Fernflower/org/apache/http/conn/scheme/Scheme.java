package org.apache.http.conn.scheme;

import java.util.Locale;
import org.apache.http.util.LangUtils;

public final class Scheme {
   private final int defaultPort;
   private final boolean layered;
   private final String name;
   private final SchemeSocketFactory socketFactory;
   private String stringRep;

   public Scheme(String var1, int var2, SchemeSocketFactory var3) {
      if (var1 != null) {
         if (var2 > 0 && var2 <= 65535) {
            if (var3 != null) {
               this.name = var1.toLowerCase(Locale.ENGLISH);
               this.socketFactory = var3;
               this.defaultPort = var2;
               this.layered = var3 instanceof LayeredSchemeSocketFactory;
            } else {
               throw new IllegalArgumentException("Socket factory may not be null");
            }
         } else {
            StringBuilder var4 = new StringBuilder();
            var4.append("Port is invalid: ");
            var4.append(var2);
            throw new IllegalArgumentException(var4.toString());
         }
      } else {
         throw new IllegalArgumentException("Scheme name may not be null");
      }
   }

   @Deprecated
   public Scheme(String var1, SocketFactory var2, int var3) {
      if (var1 != null) {
         if (var2 != null) {
            if (var3 > 0 && var3 <= 65535) {
               this.name = var1.toLowerCase(Locale.ENGLISH);
               if (var2 instanceof LayeredSocketFactory) {
                  this.socketFactory = new LayeredSchemeSocketFactoryAdaptor((LayeredSocketFactory)var2);
                  this.layered = true;
               } else {
                  this.socketFactory = new SchemeSocketFactoryAdaptor(var2);
                  this.layered = false;
               }

               this.defaultPort = var3;
            } else {
               StringBuilder var4 = new StringBuilder();
               var4.append("Port is invalid: ");
               var4.append(var3);
               throw new IllegalArgumentException(var4.toString());
            }
         } else {
            throw new IllegalArgumentException("Socket factory may not be null");
         }
      } else {
         throw new IllegalArgumentException("Scheme name may not be null");
      }
   }

   public final boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof Scheme)) {
         return false;
      } else {
         Scheme var3 = (Scheme)var1;
         if (!this.name.equals(var3.name) || this.defaultPort != var3.defaultPort || this.layered != var3.layered) {
            var2 = false;
         }

         return var2;
      }
   }

   public final int getDefaultPort() {
      return this.defaultPort;
   }

   public final String getName() {
      return this.name;
   }

   public final SchemeSocketFactory getSchemeSocketFactory() {
      return this.socketFactory;
   }

   @Deprecated
   public final SocketFactory getSocketFactory() {
      SchemeSocketFactory var1 = this.socketFactory;
      if (var1 instanceof SchemeSocketFactoryAdaptor) {
         return ((SchemeSocketFactoryAdaptor)var1).getFactory();
      } else {
         return (SocketFactory)(this.layered ? new LayeredSocketFactoryAdaptor((LayeredSchemeSocketFactory)this.socketFactory) : new SocketFactoryAdaptor(this.socketFactory));
      }
   }

   public int hashCode() {
      return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(17, this.defaultPort), this.name), this.layered);
   }

   public final boolean isLayered() {
      return this.layered;
   }

   public final int resolvePort(int var1) {
      int var2 = var1;
      if (var1 <= 0) {
         var2 = this.defaultPort;
      }

      return var2;
   }

   public final String toString() {
      if (this.stringRep == null) {
         StringBuilder var1 = new StringBuilder();
         var1.append(this.name);
         var1.append(':');
         var1.append(Integer.toString(this.defaultPort));
         this.stringRep = var1.toString();
      }

      return this.stringRep;
   }
}
