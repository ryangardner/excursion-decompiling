package okhttp3.internal.platform;

import java.security.KeyStore;
import java.security.Provider;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Protocol;
import org.openjsse.net.ssl.OpenJSSE;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J-\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0011\u0010\u000b\u001a\r\u0012\t\u0012\u00070\r¢\u0006\u0002\b\u000e0\fH\u0016J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"},
   d2 = {"Lokhttp3/internal/platform/OpenJSSEPlatform;", "Lokhttp3/internal/platform/Platform;", "()V", "provider", "Ljava/security/Provider;", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "Lkotlin/jvm/JvmSuppressWildcards;", "getSelectedProtocol", "newSSLContext", "Ljavax/net/ssl/SSLContext;", "platformTrustManager", "Ljavax/net/ssl/X509TrustManager;", "trustManager", "sslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class OpenJSSEPlatform extends Platform {
   public static final OpenJSSEPlatform.Companion Companion;
   private static final boolean isSupported;
   private final Provider provider;

   static {
      OpenJSSEPlatform.Companion var0 = new OpenJSSEPlatform.Companion((DefaultConstructorMarker)null);
      Companion = var0;
      boolean var1 = false;

      label13: {
         try {
            Class.forName("org.openjsse.net.ssl.OpenJSSE", false, var0.getClass().getClassLoader());
         } catch (ClassNotFoundException var2) {
            break label13;
         }

         var1 = true;
      }

      isSupported = var1;
   }

   private OpenJSSEPlatform() {
      this.provider = (Provider)(new OpenJSSE());
   }

   // $FF: synthetic method
   public OpenJSSEPlatform(DefaultConstructorMarker var1) {
      this();
   }

   public void configureTlsExtensions(SSLSocket var1, String var2, List<Protocol> var3) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      Intrinsics.checkParameterIsNotNull(var3, "protocols");
      if (var1 instanceof org.openjsse.javax.net.ssl.SSLSocket) {
         org.openjsse.javax.net.ssl.SSLSocket var5 = (org.openjsse.javax.net.ssl.SSLSocket)var1;
         SSLParameters var6 = var5.getSSLParameters();
         if (var6 instanceof org.openjsse.javax.net.ssl.SSLParameters) {
            List var4 = Platform.Companion.alpnProtocolNames(var3);
            org.openjsse.javax.net.ssl.SSLParameters var7 = (org.openjsse.javax.net.ssl.SSLParameters)var6;
            Object[] var8 = ((Collection)var4).toArray(new String[0]);
            if (var8 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }

            var7.setApplicationProtocols((String[])var8);
            var5.setSSLParameters(var6);
         }
      } else {
         super.configureTlsExtensions(var1, var2, var3);
      }

   }

   public String getSelectedProtocol(SSLSocket var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      String var3;
      if (var1 instanceof org.openjsse.javax.net.ssl.SSLSocket) {
         String var2 = ((org.openjsse.javax.net.ssl.SSLSocket)var1).getApplicationProtocol();
         if (var2 != null) {
            var3 = var2;
            if (!Intrinsics.areEqual((Object)var2, (Object)"")) {
               return var3;
            }
         }

         var3 = null;
      } else {
         var3 = super.getSelectedProtocol(var1);
      }

      return var3;
   }

   public SSLContext newSSLContext() {
      SSLContext var1 = SSLContext.getInstance("TLSv1.3", this.provider);
      Intrinsics.checkExpressionValueIsNotNull(var1, "SSLContext.getInstance(\"TLSv1.3\", provider)");
      return var1;
   }

   public X509TrustManager platformTrustManager() {
      TrustManagerFactory var1 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm(), this.provider);
      var1.init((KeyStore)null);
      Intrinsics.checkExpressionValueIsNotNull(var1, "factory");
      TrustManager[] var2 = var1.getTrustManagers();
      if (var2 == null) {
         Intrinsics.throwNpe();
      }

      int var3 = var2.length;
      boolean var4 = true;
      if (var3 != 1 || !(var2[0] instanceof X509TrustManager)) {
         var4 = false;
      }

      if (var4) {
         TrustManager var6 = var2[0];
         if (var6 != null) {
            return (X509TrustManager)var6;
         } else {
            throw new TypeCastException("null cannot be cast to non-null type javax.net.ssl.X509TrustManager");
         }
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("Unexpected default trust managers: ");
         String var7 = Arrays.toString(var2);
         Intrinsics.checkExpressionValueIsNotNull(var7, "java.util.Arrays.toString(this)");
         var5.append(var7);
         throw (Throwable)(new IllegalStateException(var5.toString().toString()));
      }
   }

   public X509TrustManager trustManager(SSLSocketFactory var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
      throw (Throwable)(new UnsupportedOperationException("clientBuilder.sslSocketFactory(SSLSocketFactory) not supported with OpenJSSE"));
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0005¨\u0006\b"},
      d2 = {"Lokhttp3/internal/platform/OpenJSSEPlatform$Companion;", "", "()V", "isSupported", "", "()Z", "buildIfSupported", "Lokhttp3/internal/platform/OpenJSSEPlatform;", "okhttp"},
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

      public final OpenJSSEPlatform buildIfSupported() {
         boolean var1 = ((OpenJSSEPlatform.Companion)this).isSupported();
         OpenJSSEPlatform var2 = null;
         if (var1) {
            var2 = new OpenJSSEPlatform((DefaultConstructorMarker)null);
         }

         return var2;
      }

      public final boolean isSupported() {
         return OpenJSSEPlatform.isSupported;
      }
   }
}