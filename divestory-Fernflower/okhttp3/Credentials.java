package okhttp3;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import okio.ByteString;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007¨\u0006\t"},
   d2 = {"Lokhttp3/Credentials;", "", "()V", "basic", "", "username", "password", "charset", "Ljava/nio/charset/Charset;", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Credentials {
   public static final Credentials INSTANCE = new Credentials();

   private Credentials() {
   }

   @JvmStatic
   public static final String basic(String var0, String var1) {
      return basic$default(var0, var1, (Charset)null, 4, (Object)null);
   }

   @JvmStatic
   public static final String basic(String var0, String var1, Charset var2) {
      Intrinsics.checkParameterIsNotNull(var0, "username");
      Intrinsics.checkParameterIsNotNull(var1, "password");
      Intrinsics.checkParameterIsNotNull(var2, "charset");
      StringBuilder var3 = new StringBuilder();
      var3.append(var0);
      var3.append(':');
      var3.append(var1);
      var0 = var3.toString();
      var0 = ByteString.Companion.encodeString(var0, var2).base64();
      StringBuilder var4 = new StringBuilder();
      var4.append("Basic ");
      var4.append(var0);
      return var4.toString();
   }

   // $FF: synthetic method
   public static String basic$default(String var0, String var1, Charset var2, int var3, Object var4) {
      if ((var3 & 4) != 0) {
         var2 = StandardCharsets.ISO_8859_1;
         Intrinsics.checkExpressionValueIsNotNull(var2, "ISO_8859_1");
      }

      return basic(var0, var1, var2);
   }
}
