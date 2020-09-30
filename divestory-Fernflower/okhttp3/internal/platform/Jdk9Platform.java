package okhttp3.internal.platform;

import java.util.Collection;
import java.util.List;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Protocol;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u0005¢\u0006\u0002\u0010\u0002J-\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0011\u0010\t\u001a\r\u0012\t\u0012\u00070\u000b¢\u0006\u0002\b\f0\nH\u0017J\u0012\u0010\r\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0017J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016¨\u0006\u0013"},
   d2 = {"Lokhttp3/internal/platform/Jdk9Platform;", "Lokhttp3/internal/platform/Platform;", "()V", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "Lkotlin/jvm/JvmSuppressWildcards;", "getSelectedProtocol", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "sslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public class Jdk9Platform extends Platform {
   public static final Jdk9Platform.Companion Companion;
   private static final boolean isAvailable;

   static {
      Integer var0 = null;
      Companion = new Jdk9Platform.Companion((DefaultConstructorMarker)null);
      String var1 = System.getProperty("java.specification.version");
      if (var1 != null) {
         var0 = StringsKt.toIntOrNull(var1);
      }

      boolean var2;
      label21: {
         var2 = true;
         if (var0 != null) {
            if (var0 >= 9) {
               break label21;
            }
         } else {
            try {
               SSLSocket.class.getMethod("getApplicationProtocol");
               break label21;
            } catch (NoSuchMethodException var3) {
            }
         }

         var2 = false;
      }

      isAvailable = var2;
   }

   public void configureTlsExtensions(SSLSocket var1, String var2, List<Protocol> var3) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      Intrinsics.checkParameterIsNotNull(var3, "protocols");
      SSLParameters var4 = var1.getSSLParameters();
      var3 = Platform.Companion.alpnProtocolNames(var3);
      Intrinsics.checkExpressionValueIsNotNull(var4, "sslParameters");
      Object[] var5 = ((Collection)var3).toArray(new String[0]);
      if (var5 != null) {
         var4.setApplicationProtocols((String[])var5);
         var1.setSSLParameters(var4);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      }
   }

   public String getSelectedProtocol(SSLSocket var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      Object var2 = null;

      String var6;
      label40: {
         boolean var10001;
         try {
            var6 = var1.getApplicationProtocol();
         } catch (UnsupportedOperationException var5) {
            var10001 = false;
            break label40;
         }

         if (var6 == null) {
            var6 = (String)var2;
            return var6;
         }

         boolean var3;
         try {
            var3 = Intrinsics.areEqual((Object)var6, (Object)"");
         } catch (UnsupportedOperationException var4) {
            var10001 = false;
            break label40;
         }

         if (var3) {
            var6 = (String)var2;
         }

         return var6;
      }

      var6 = (String)var2;
      return var6;
   }

   public X509TrustManager trustManager(SSLSocketFactory var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
      throw (Throwable)(new UnsupportedOperationException("clientBuilder.sslSocketFactory(SSLSocketFactory) not supported on JDK 9+"));
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0005¨\u0006\b"},
      d2 = {"Lokhttp3/internal/platform/Jdk9Platform$Companion;", "", "()V", "isAvailable", "", "()Z", "buildIfSupported", "Lokhttp3/internal/platform/Jdk9Platform;", "okhttp"},
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

      public final Jdk9Platform buildIfSupported() {
         Jdk9Platform var1;
         if (((Jdk9Platform.Companion)this).isAvailable()) {
            var1 = new Jdk9Platform();
         } else {
            var1 = null;
         }

         return var1;
      }

      public final boolean isAvailable() {
         return Jdk9Platform.isAvailable;
      }
   }
}
