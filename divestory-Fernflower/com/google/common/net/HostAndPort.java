package com.google.common.net;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
public final class HostAndPort implements Serializable {
   private static final int NO_PORT = -1;
   private static final long serialVersionUID = 0L;
   private final boolean hasBracketlessColons;
   private final String host;
   private final int port;

   private HostAndPort(String var1, int var2, boolean var3) {
      this.host = var1;
      this.port = var2;
      this.hasBracketlessColons = var3;
   }

   public static HostAndPort fromHost(String var0) {
      HostAndPort var1 = fromString(var0);
      Preconditions.checkArgument(var1.hasPort() ^ true, "Host has a port: %s", (Object)var0);
      return var1;
   }

   public static HostAndPort fromParts(String var0, int var1) {
      Preconditions.checkArgument(isValidPort(var1), "Port out of range: %s", var1);
      HostAndPort var2 = fromString(var0);
      Preconditions.checkArgument(var2.hasPort() ^ true, "Host has a port: %s", (Object)var0);
      return new HostAndPort(var2.host, var1, var2.hasBracketlessColons);
   }

   public static HostAndPort fromString(String var0) {
      Preconditions.checkNotNull(var0);
      boolean var1 = var0.startsWith("[");
      int var2 = -1;
      boolean var3 = false;
      boolean var4 = false;
      String var6;
      String var10;
      if (var1) {
         String[] var5 = getHostAndPortFromBracketedHost(var0);
         var6 = var5[0];
         var10 = var5[1];
         var4 = var3;
      } else {
         label34: {
            int var7 = var0.indexOf(58);
            if (var7 >= 0) {
               int var8 = var7 + 1;
               if (var0.indexOf(58, var8) == -1) {
                  var6 = var0.substring(0, var7);
                  var10 = var0.substring(var8);
                  var4 = var3;
                  break label34;
               }
            }

            if (var7 >= 0) {
               var4 = true;
            }

            var10 = null;
            var6 = var0;
         }
      }

      if (!Strings.isNullOrEmpty(var10)) {
         Preconditions.checkArgument(var10.startsWith("+") ^ true, "Unparseable port number: %s", (Object)var0);

         try {
            var2 = Integer.parseInt(var10);
         } catch (NumberFormatException var9) {
            StringBuilder var11 = new StringBuilder();
            var11.append("Unparseable port number: ");
            var11.append(var0);
            throw new IllegalArgumentException(var11.toString());
         }

         Preconditions.checkArgument(isValidPort(var2), "Port number out of range: %s", (Object)var0);
      }

      return new HostAndPort(var6, var2, var4);
   }

   private static String[] getHostAndPortFromBracketedHost(String var0) {
      boolean var1;
      if (var0.charAt(0) == '[') {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkArgument(var1, "Bracketed host-port string must start with a bracket: %s", (Object)var0);
      int var2 = var0.indexOf(58);
      int var3 = var0.lastIndexOf(93);
      if (var2 > -1 && var3 > var2) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkArgument(var1, "Invalid bracketed host/port: %s", (Object)var0);
      String var4 = var0.substring(1, var3);
      var2 = var3 + 1;
      if (var2 == var0.length()) {
         return new String[]{var4, ""};
      } else {
         if (var0.charAt(var2) == ':') {
            var1 = true;
         } else {
            var1 = false;
         }

         Preconditions.checkArgument(var1, "Only a colon may follow a close bracket: %s", (Object)var0);
         var2 = var3 + 2;

         for(var3 = var2; var3 < var0.length(); ++var3) {
            Preconditions.checkArgument(Character.isDigit(var0.charAt(var3)), "Port must be numeric: %s", (Object)var0);
         }

         return new String[]{var4, var0.substring(var2)};
      }
   }

   private static boolean isValidPort(int var0) {
      boolean var1;
      if (var0 >= 0 && var0 <= 65535) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean equals(@NullableDecl Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof HostAndPort)) {
         return false;
      } else {
         HostAndPort var3 = (HostAndPort)var1;
         if (!Objects.equal(this.host, var3.host) || this.port != var3.port) {
            var2 = false;
         }

         return var2;
      }
   }

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      Preconditions.checkState(this.hasPort());
      return this.port;
   }

   public int getPortOrDefault(int var1) {
      if (this.hasPort()) {
         var1 = this.port;
      }

      return var1;
   }

   public boolean hasPort() {
      boolean var1;
      if (this.port >= 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public int hashCode() {
      return Objects.hashCode(this.host, this.port);
   }

   public HostAndPort requireBracketsForIPv6() {
      Preconditions.checkArgument(this.hasBracketlessColons ^ true, "Possible bracketless IPv6 literal: %s", (Object)this.host);
      return this;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(this.host.length() + 8);
      if (this.host.indexOf(58) >= 0) {
         var1.append('[');
         var1.append(this.host);
         var1.append(']');
      } else {
         var1.append(this.host);
      }

      if (this.hasPort()) {
         var1.append(':');
         var1.append(this.port);
      }

      return var1.toString();
   }

   public HostAndPort withDefaultPort(int var1) {
      Preconditions.checkArgument(isValidPort(var1));
      return this.hasPort() ? this : new HostAndPort(this.host, var1, this.hasBracketlessColons);
   }
}
