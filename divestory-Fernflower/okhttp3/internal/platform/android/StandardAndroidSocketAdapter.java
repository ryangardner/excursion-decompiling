package okhttp3.internal.platform.android;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import okhttp3.internal.platform.Platform;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB1\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00040\u0003\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00060\u0003\u0012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0006H\u0016J\u0012\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000b\u001a\u00020\u0006H\u0016R\u0012\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00060\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"},
   d2 = {"Lokhttp3/internal/platform/android/StandardAndroidSocketAdapter;", "Lokhttp3/internal/platform/android/AndroidSocketAdapter;", "sslSocketClass", "Ljava/lang/Class;", "Ljavax/net/ssl/SSLSocket;", "sslSocketFactoryClass", "Ljavax/net/ssl/SSLSocketFactory;", "paramClass", "(Ljava/lang/Class;Ljava/lang/Class;Ljava/lang/Class;)V", "matchesSocketFactory", "", "sslSocketFactory", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class StandardAndroidSocketAdapter extends AndroidSocketAdapter {
   public static final StandardAndroidSocketAdapter.Companion Companion = new StandardAndroidSocketAdapter.Companion((DefaultConstructorMarker)null);
   private final Class<?> paramClass;
   private final Class<? super SSLSocketFactory> sslSocketFactoryClass;

   public StandardAndroidSocketAdapter(Class<? super SSLSocket> var1, Class<? super SSLSocketFactory> var2, Class<?> var3) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocketClass");
      Intrinsics.checkParameterIsNotNull(var2, "sslSocketFactoryClass");
      Intrinsics.checkParameterIsNotNull(var3, "paramClass");
      super(var1);
      this.sslSocketFactoryClass = var2;
      this.paramClass = var3;
   }

   public boolean matchesSocketFactory(SSLSocketFactory var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
      return this.sslSocketFactoryClass.isInstance(var1);
   }

   public X509TrustManager trustManager(SSLSocketFactory var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
      Object var2 = Util.readFieldOrNull(var1, this.paramClass, "sslParameters");
      if (var2 == null) {
         Intrinsics.throwNpe();
      }

      X509TrustManager var3 = (X509TrustManager)Util.readFieldOrNull(var2, X509TrustManager.class, "x509TrustManager");
      if (var3 == null) {
         var3 = (X509TrustManager)Util.readFieldOrNull(var2, X509TrustManager.class, "trustManager");
      }

      return var3;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"},
      d2 = {"Lokhttp3/internal/platform/android/StandardAndroidSocketAdapter$Companion;", "", "()V", "buildIfSupported", "Lokhttp3/internal/platform/android/SocketAdapter;", "packageName", "", "okhttp"},
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

      // $FF: synthetic method
      public static SocketAdapter buildIfSupported$default(StandardAndroidSocketAdapter.Companion var0, String var1, int var2, Object var3) {
         if ((var2 & 1) != 0) {
            var1 = "com.android.org.conscrypt";
         }

         return var0.buildIfSupported(var1);
      }

      public final SocketAdapter buildIfSupported(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "packageName");

         StandardAndroidSocketAdapter var10;
         Exception var10000;
         label41: {
            Class var13;
            boolean var10001;
            try {
               StringBuilder var2 = new StringBuilder();
               var2.append(var1);
               var2.append(".OpenSSLSocketImpl");
               var13 = Class.forName(var2.toString());
            } catch (Exception var9) {
               var10000 = var9;
               var10001 = false;
               break label41;
            }

            TypeCastException var11;
            if (var13 != null) {
               label37: {
                  Class var14;
                  try {
                     StringBuilder var3 = new StringBuilder();
                     var3.append(var1);
                     var3.append(".OpenSSLSocketFactoryImpl");
                     var14 = Class.forName(var3.toString());
                  } catch (Exception var8) {
                     var10000 = var8;
                     var10001 = false;
                     break label37;
                  }

                  if (var14 != null) {
                     try {
                        StringBuilder var4 = new StringBuilder();
                        var4.append(var1);
                        var4.append(".SSLParametersImpl");
                        Class var15 = Class.forName(var4.toString());
                        Intrinsics.checkExpressionValueIsNotNull(var15, "paramsClass");
                        var10 = new StandardAndroidSocketAdapter(var13, var14, var15);
                        return (SocketAdapter)var10;
                     } catch (Exception var7) {
                        var10000 = var7;
                        var10001 = false;
                     }
                  } else {
                     try {
                        var11 = new TypeCastException("null cannot be cast to non-null type java.lang.Class<in javax.net.ssl.SSLSocketFactory>");
                        throw var11;
                     } catch (Exception var5) {
                        var10000 = var5;
                        var10001 = false;
                     }
                  }
               }
            } else {
               try {
                  var11 = new TypeCastException("null cannot be cast to non-null type java.lang.Class<in javax.net.ssl.SSLSocket>");
                  throw var11;
               } catch (Exception var6) {
                  var10000 = var6;
                  var10001 = false;
               }
            }
         }

         Exception var12 = var10000;
         Platform.Companion.get().log("unable to load android socket classes", 5, (Throwable)var12);
         var10 = null;
         return (SocketAdapter)var10;
      }
   }
}
