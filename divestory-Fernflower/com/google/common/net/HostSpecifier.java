package com.google.common.net;

import com.google.common.base.Preconditions;
import java.net.InetAddress;
import java.text.ParseException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class HostSpecifier {
   private final String canonicalForm;

   private HostSpecifier(String var1) {
      this.canonicalForm = var1;
   }

   public static HostSpecifier from(String var0) throws ParseException {
      try {
         HostSpecifier var1 = fromValid(var0);
         return var1;
      } catch (IllegalArgumentException var3) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Invalid host specifier: ");
         var2.append(var0);
         ParseException var4 = new ParseException(var2.toString(), 0);
         var4.initCause(var3);
         throw var4;
      }
   }

   public static HostSpecifier fromValid(String var0) {
      HostAndPort var4 = HostAndPort.fromString(var0);
      Preconditions.checkArgument(var4.hasPort() ^ true);
      String var1 = var4.getHost();
      InetAddress var5 = null;

      label21: {
         InetAddress var2;
         try {
            var2 = InetAddresses.forString(var1);
         } catch (IllegalArgumentException var3) {
            break label21;
         }

         var5 = var2;
      }

      if (var5 != null) {
         return new HostSpecifier(InetAddresses.toUriString(var5));
      } else {
         InternetDomainName var6 = InternetDomainName.from(var1);
         if (var6.hasPublicSuffix()) {
            return new HostSpecifier(var6.toString());
         } else {
            StringBuilder var7 = new StringBuilder();
            var7.append("Domain name does not have a recognized public suffix: ");
            var7.append(var1);
            throw new IllegalArgumentException(var7.toString());
         }
      }
   }

   public static boolean isValid(String var0) {
      try {
         fromValid(var0);
         return true;
      } catch (IllegalArgumentException var1) {
         return false;
      }
   }

   public boolean equals(@NullableDecl Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 instanceof HostSpecifier) {
         HostSpecifier var2 = (HostSpecifier)var1;
         return this.canonicalForm.equals(var2.canonicalForm);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.canonicalForm.hashCode();
   }

   public String toString() {
      return this.canonicalForm;
   }
}
