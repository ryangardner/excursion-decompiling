package okhttp3.internal;

import java.net.IDN;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okio.Buffer;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000&\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a0\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005H\u0002\u001a\"\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0002\u001a\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\f\u0010\r\u001a\u00020\u0001*\u00020\u0003H\u0002\u001a\f\u0010\u000e\u001a\u0004\u0018\u00010\u0003*\u00020\u0003¨\u0006\u000f"},
   d2 = {"decodeIpv4Suffix", "", "input", "", "pos", "", "limit", "address", "", "addressOffset", "decodeIpv6", "Ljava/net/InetAddress;", "inet6AddressToAscii", "containsInvalidHostnameAsciiCodes", "toCanonicalHost", "okhttp"},
   k = 2,
   mv = {1, 1, 16}
)
public final class HostnamesKt {
   private static final boolean containsInvalidHostnameAsciiCodes(String var0) {
      int var1 = var0.length();

      for(int var2 = 0; var2 < var1; ++var2) {
         char var3 = var0.charAt(var2);
         if (var3 <= 31 || var3 >= 127) {
            return true;
         }

         if (StringsKt.indexOf$default((CharSequence)" #%/:?@[\\]", var3, 0, false, 6, (Object)null) != -1) {
            return true;
         }
      }

      return false;
   }

   private static final boolean decodeIpv4Suffix(String var0, int var1, int var2, byte[] var3, int var4) {
      int var5 = var4;
      int var6 = var1;

      while(true) {
         boolean var7 = false;
         if (var6 >= var2) {
            if (var5 == var4 + 4) {
               var7 = true;
            }

            return var7;
         }

         if (var5 == var3.length) {
            return false;
         }

         var1 = var6;
         if (var5 != var4) {
            if (var0.charAt(var6) != '.') {
               return false;
            }

            var1 = var6 + 1;
         }

         var6 = var1;

         int var8;
         for(var8 = 0; var6 < var2; ++var6) {
            char var9 = var0.charAt(var6);
            if (var9 < '0' || var9 > '9') {
               break;
            }

            if (var8 == 0 && var1 != var6) {
               return false;
            }

            var8 = var8 * 10 + var9 - 48;
            if (var8 > 255) {
               return false;
            }
         }

         if (var6 - var1 == 0) {
            return false;
         }

         var3[var5] = (byte)((byte)var8);
         ++var5;
      }
   }

   private static final InetAddress decodeIpv6(String var0, int var1, int var2) {
      byte[] var3 = new byte[16];
      int var4 = 0;
      int var5 = -1;
      int var6 = -1;
      int var7 = var1;

      int var8;
      while(true) {
         var1 = var4;
         var8 = var5;
         if (var7 >= var2) {
            break;
         }

         if (var4 == 16) {
            return null;
         }

         var8 = var7 + 2;
         if (var8 <= var2 && StringsKt.startsWith$default(var0, "::", var7, false, 4, (Object)null)) {
            if (var5 != -1) {
               return null;
            }

            var1 = var4 + 2;
            if (var8 == var2) {
               var8 = var1;
               break;
            }

            var5 = var1;
            var4 = var1;
            var1 = var8;
         } else {
            var1 = var7;
            if (var4 != 0) {
               if (!StringsKt.startsWith$default(var0, ":", var7, false, 4, (Object)null)) {
                  if (!StringsKt.startsWith$default(var0, ".", var7, false, 4, (Object)null)) {
                     return null;
                  }

                  if (!decodeIpv4Suffix(var0, var6, var2, var3, var4 - 2)) {
                     return null;
                  }

                  var1 = var4 + 2;
                  var8 = var5;
                  break;
               }

               var1 = var7 + 1;
            }
         }

         var7 = var1;

         for(var6 = 0; var7 < var2; ++var7) {
            var8 = Util.parseHexDigit(var0.charAt(var7));
            if (var8 == -1) {
               break;
            }

            var6 = (var6 << 4) + var8;
         }

         var8 = var7 - var1;
         if (var8 == 0 || var8 > 4) {
            return null;
         }

         var8 = var4 + 1;
         var3[var4] = (byte)((byte)(var6 >>> 8 & 255));
         var4 = var8 + 1;
         var3[var8] = (byte)((byte)(var6 & 255));
         var6 = var1;
      }

      if (var1 != 16) {
         if (var8 == -1) {
            return null;
         }

         var2 = var1 - var8;
         System.arraycopy(var3, var8, var3, 16 - var2, var2);
         Arrays.fill(var3, var8, 16 - var1 + var8, (byte)0);
      }

      return InetAddress.getByAddress(var3);
   }

   private static final String inet6AddressToAscii(byte[] var0) {
      byte var1 = 0;
      int var2 = -1;
      int var3 = 0;

      int var4;
      int var5;
      int var8;
      for(var4 = 0; var3 < var0.length; var4 = var8) {
         for(var5 = var3; var5 < 16 && var0[var5] == 0 && var0[var5 + 1] == 0; var5 += 2) {
         }

         int var6 = var5 - var3;
         int var7 = var2;
         var8 = var4;
         if (var6 > var4) {
            var7 = var2;
            var8 = var4;
            if (var6 >= 4) {
               var8 = var6;
               var7 = var3;
            }
         }

         var3 = var5 + 2;
         var2 = var7;
      }

      Buffer var9 = new Buffer();
      var3 = var1;

      while(var3 < var0.length) {
         if (var3 == var2) {
            var9.writeByte(58);
            var5 = var3 + var4;
            var3 = var5;
            if (var5 == 16) {
               var9.writeByte(58);
               var3 = var5;
            }
         } else {
            if (var3 > 0) {
               var9.writeByte(58);
            }

            var9.writeHexadecimalUnsignedLong((long)(Util.and((byte)var0[var3], 255) << 8 | Util.and((byte)var0[var3 + 1], 255)));
            var3 += 2;
         }
      }

      return var9.readUtf8();
   }

   public static final String toCanonicalHost(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toCanonicalHost");
      CharSequence var1 = (CharSequence)var0;
      CharSequence var2 = (CharSequence)":";
      boolean var3 = false;
      InetAddress var4 = null;
      if (StringsKt.contains$default(var1, var2, false, 2, (Object)null)) {
         if (StringsKt.startsWith$default(var0, "[", false, 2, (Object)null) && StringsKt.endsWith$default(var0, "]", false, 2, (Object)null)) {
            var4 = decodeIpv6(var0, 1, var0.length() - 1);
         } else {
            var4 = decodeIpv6(var0, 0, var0.length());
         }

         if (var4 != null) {
            byte[] var12 = var4.getAddress();
            if (var12.length == 16) {
               Intrinsics.checkExpressionValueIsNotNull(var12, "address");
               return inet6AddressToAscii(var12);
            } else if (var12.length == 4) {
               return var4.getHostAddress();
            } else {
               StringBuilder var14 = new StringBuilder();
               var14.append("Invalid IPv6 address: '");
               var14.append(var0);
               var14.append('\'');
               throw (Throwable)(new AssertionError(var14.toString()));
            }
         } else {
            return null;
         }
      } else {
         boolean var10001;
         Locale var10;
         String var11;
         try {
            var11 = IDN.toASCII(var0);
            Intrinsics.checkExpressionValueIsNotNull(var11, "IDN.toASCII(host)");
            var10 = Locale.US;
            Intrinsics.checkExpressionValueIsNotNull(var10, "Locale.US");
         } catch (IllegalArgumentException var9) {
            var10001 = false;
            return null;
         }

         if (var11 != null) {
            try {
               var0 = var11.toLowerCase(var10);
               Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.String).toLowerCase(locale)");
            } catch (IllegalArgumentException var7) {
               var10001 = false;
               return null;
            }

            label63: {
               try {
                  if (((CharSequence)var0).length() != 0) {
                     break label63;
                  }
               } catch (IllegalArgumentException var6) {
                  var10001 = false;
                  return null;
               }

               var3 = true;
            }

            if (var3) {
               return null;
            } else {
               try {
                  if (!containsInvalidHostnameAsciiCodes(var0)) {
                     return var0;
                  }
               } catch (IllegalArgumentException var5) {
                  var10001 = false;
                  return null;
               }

               var0 = var4;
               return var0;
            }
         } else {
            try {
               TypeCastException var13 = new TypeCastException("null cannot be cast to non-null type java.lang.String");
               throw var13;
            } catch (IllegalArgumentException var8) {
               var10001 = false;
               return null;
            }
         }
      }
   }
}
