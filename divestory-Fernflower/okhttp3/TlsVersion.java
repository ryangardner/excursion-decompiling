package okhttp3;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\b\u0086\u0001\u0018\u0000 \f2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\fB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\r\u0010\u0002\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\u0006R\u0013\u0010\u0002\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0005j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b¨\u0006\r"},
   d2 = {"Lokhttp3/TlsVersion;", "", "javaName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "()Ljava/lang/String;", "-deprecated_javaName", "TLS_1_3", "TLS_1_2", "TLS_1_1", "TLS_1_0", "SSL_3_0", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public enum TlsVersion {
   public static final TlsVersion.Companion Companion;
   SSL_3_0,
   TLS_1_0,
   TLS_1_1,
   TLS_1_2,
   TLS_1_3;

   private final String javaName;

   static {
      TlsVersion var0 = new TlsVersion("TLS_1_3", 0, "TLSv1.3");
      TLS_1_3 = var0;
      TlsVersion var1 = new TlsVersion("TLS_1_2", 1, "TLSv1.2");
      TLS_1_2 = var1;
      TlsVersion var2 = new TlsVersion("TLS_1_1", 2, "TLSv1.1");
      TLS_1_1 = var2;
      TlsVersion var3 = new TlsVersion("TLS_1_0", 3, "TLSv1");
      TLS_1_0 = var3;
      TlsVersion var4 = new TlsVersion("SSL_3_0", 4, "SSLv3");
      SSL_3_0 = var4;
      Companion = new TlsVersion.Companion((DefaultConstructorMarker)null);
   }

   private TlsVersion(String var3) {
      this.javaName = var3;
   }

   @JvmStatic
   public static final TlsVersion forJavaName(String var0) {
      return Companion.forJavaName(var0);
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "javaName",
   imports = {}
)
   )
   public final String _deprecated_javaName/* $FF was: -deprecated_javaName*/() {
      return this.javaName;
   }

   public final String javaName() {
      return this.javaName;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"},
      d2 = {"Lokhttp3/TlsVersion$Companion;", "", "()V", "forJavaName", "Lokhttp3/TlsVersion;", "javaName", "", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      @JvmStatic
      public final TlsVersion forJavaName(String var1) {
         label36: {
            Intrinsics.checkParameterIsNotNull(var1, "javaName");
            int var2 = var1.hashCode();
            TlsVersion var4;
            if (var2 != 79201641) {
               if (var2 != 79923350) {
                  switch(var2) {
                  case -503070503:
                     if (!var1.equals("TLSv1.1")) {
                        break label36;
                     }

                     var4 = TlsVersion.TLS_1_1;
                     break;
                  case -503070502:
                     if (!var1.equals("TLSv1.2")) {
                        break label36;
                     }

                     var4 = TlsVersion.TLS_1_2;
                     break;
                  case -503070501:
                     if (var1.equals("TLSv1.3")) {
                        var4 = TlsVersion.TLS_1_3;
                        break;
                     }
                  default:
                     break label36;
                  }
               } else {
                  if (!var1.equals("TLSv1")) {
                     break label36;
                  }

                  var4 = TlsVersion.TLS_1_0;
               }
            } else {
               if (!var1.equals("SSLv3")) {
                  break label36;
               }

               var4 = TlsVersion.SSL_3_0;
            }

            return var4;
         }

         StringBuilder var3 = new StringBuilder();
         var3.append("Unexpected TLS version: ");
         var3.append(var1);
         throw (Throwable)(new IllegalArgumentException(var3.toString()));
      }
   }
}
