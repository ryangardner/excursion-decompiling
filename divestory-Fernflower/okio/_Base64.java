package okio;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\u001a\u000e\u0010\u0006\u001a\u0004\u0018\u00010\u0001*\u00020\u0007H\u0000\u001a\u0016\u0010\b\u001a\u00020\u0007*\u00020\u00012\b\b\u0002\u0010\t\u001a\u00020\u0001H\u0000\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0014\u0010\u0004\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0003¨\u0006\n"},
   d2 = {"BASE64", "", "getBASE64", "()[B", "BASE64_URL_SAFE", "getBASE64_URL_SAFE", "decodeBase64ToArray", "", "encodeBase64", "map", "okio"},
   k = 2,
   mv = {1, 1, 16}
)
public final class _Base64 {
   private static final byte[] BASE64;
   private static final byte[] BASE64_URL_SAFE;

   static {
      BASE64 = ByteString.Companion.encodeUtf8("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/").getData$okio();
      BASE64_URL_SAFE = ByteString.Companion.encodeUtf8("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_").getData$okio();
   }

   public static final byte[] decodeBase64ToArray(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$decodeBase64ToArray");

      int var1;
      for(var1 = var0.length(); var1 > 0; --var1) {
         char var2 = var0.charAt(var1 - 1);
         if (var2 != '=' && var2 != '\n' && var2 != '\r' && var2 != ' ' && var2 != '\t') {
            break;
         }
      }

      int var3 = (int)((long)var1 * 6L / 8L);
      byte[] var4 = new byte[var3];
      int var5 = 0;
      int var6 = 0;
      int var7 = 0;

      int var8;
      int var11;
      int var13;
      for(var8 = 0; var5 < var1; var8 = var11) {
         int var10;
         label113: {
            char var9 = var0.charAt(var5);
            if ('A' <= var9 && 'Z' >= var9) {
               var13 = var9 - 65;
            } else if ('a' <= var9 && 'z' >= var9) {
               var13 = var9 - 71;
            } else if ('0' <= var9 && '9' >= var9) {
               var13 = var9 + 4;
            } else if (var9 != '+' && var9 != '-') {
               if (var9 != '/' && var9 != '_') {
                  var13 = var6;
                  var10 = var7;
                  var11 = var8;
                  if (var9 != '\n') {
                     var13 = var6;
                     var10 = var7;
                     var11 = var8;
                     if (var9 != '\r') {
                        var13 = var6;
                        var10 = var7;
                        var11 = var8;
                        if (var9 != ' ') {
                           if (var9 != '\t') {
                              return null;
                           }

                           var13 = var6;
                           var10 = var7;
                           var11 = var8;
                        }
                     }
                  }
                  break label113;
               }

               var13 = 63;
            } else {
               var13 = 62;
            }

            var7 = var7 << 6 | var13;
            ++var6;
            var13 = var6;
            var10 = var7;
            var11 = var8;
            if (var6 % 4 == 0) {
               var13 = var8 + 1;
               var4[var8] = (byte)((byte)(var7 >> 16));
               var8 = var13 + 1;
               var4[var13] = (byte)((byte)(var7 >> 8));
               var4[var8] = (byte)((byte)var7);
               var11 = var8 + 1;
               var10 = var7;
               var13 = var6;
            }
         }

         ++var5;
         var6 = var13;
         var7 = var10;
      }

      var13 = var6 % 4;
      if (var13 != 1) {
         if (var13 != 2) {
            if (var13 == 3) {
               var1 = var7 << 6;
               var13 = var8 + 1;
               var4[var8] = (byte)((byte)(var1 >> 16));
               var8 = var13 + 1;
               var4[var13] = (byte)((byte)(var1 >> 8));
            }
         } else {
            var4[var8] = (byte)((byte)(var7 << 12 >> 16));
            ++var8;
         }

         if (var8 == var3) {
            return var4;
         } else {
            byte[] var12 = Arrays.copyOf(var4, var8);
            Intrinsics.checkExpressionValueIsNotNull(var12, "java.util.Arrays.copyOf(this, newSize)");
            return var12;
         }
      } else {
         return null;
      }
   }

   public static final String encodeBase64(byte[] var0, byte[] var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$encodeBase64");
      Intrinsics.checkParameterIsNotNull(var1, "map");
      byte[] var2 = new byte[(var0.length + 2) / 3 * 4];
      int var3 = var0.length - var0.length % 3;
      int var4 = 0;

      int var5;
      int var6;
      for(var5 = 0; var4 < var3; ++var4) {
         var6 = var4 + 1;
         byte var7 = var0[var4];
         var4 = var6 + 1;
         byte var8 = var0[var6];
         byte var12 = var0[var4];
         int var9 = var5 + 1;
         var2[var5] = (byte)var1[(var7 & 255) >> 2];
         var5 = var9 + 1;
         var2[var9] = (byte)var1[(var7 & 3) << 4 | (var8 & 255) >> 4];
         int var13 = var5 + 1;
         var2[var5] = (byte)var1[(var8 & 15) << 2 | (var12 & 255) >> 6];
         var5 = var13 + 1;
         var2[var13] = (byte)var1[var12 & 63];
      }

      var3 = var0.length - var3;
      byte var11;
      if (var3 != 1) {
         if (var3 == 2) {
            byte var10 = var0[var4];
            var11 = var0[var4 + 1];
            var6 = var5 + 1;
            var2[var5] = (byte)var1[(var10 & 255) >> 2];
            var5 = var6 + 1;
            var2[var6] = (byte)var1[(var10 & 3) << 4 | (var11 & 255) >> 4];
            var2[var5] = (byte)var1[(var11 & 15) << 2];
            var2[var5 + 1] = (byte)((byte)61);
         }
      } else {
         var11 = var0[var4];
         var3 = var5 + 1;
         var2[var5] = (byte)var1[(var11 & 255) >> 2];
         var5 = var3 + 1;
         var2[var3] = (byte)var1[(var11 & 3) << 4];
         var11 = (byte)61;
         var2[var5] = (byte)var11;
         var2[var5 + 1] = (byte)var11;
      }

      return _Platform.toUtf8String(var2);
   }

   // $FF: synthetic method
   public static String encodeBase64$default(byte[] var0, byte[] var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = BASE64;
      }

      return encodeBase64(var0, var1);
   }

   public static final byte[] getBASE64() {
      return BASE64;
   }

   public static final byte[] getBASE64_URL_SAFE() {
      return BASE64_URL_SAFE;
   }
}
