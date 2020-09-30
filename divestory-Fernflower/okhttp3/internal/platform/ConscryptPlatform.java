package okhttp3.internal.platform;

import java.security.KeyStore;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
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
import org.conscrypt.Conscrypt;
import org.conscrypt.ConscryptHostnameVerifier;
import org.conscrypt.Conscrypt.Version;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J-\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0011\u0010\u000b\u001a\r\u0012\t\u0012\u00070\r¢\u0006\u0002\b\u000e0\fH\u0016J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0015H\u0016J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0017\u001a\u00020\u0013H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"},
   d2 = {"Lokhttp3/internal/platform/ConscryptPlatform;", "Lokhttp3/internal/platform/Platform;", "()V", "provider", "Ljava/security/Provider;", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "Lkotlin/jvm/JvmSuppressWildcards;", "getSelectedProtocol", "newSSLContext", "Ljavax/net/ssl/SSLContext;", "newSslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "platformTrustManager", "sslSocketFactory", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class ConscryptPlatform extends Platform {
   public static final ConscryptPlatform.Companion Companion;
   private static final boolean isSupported;
   private final Provider provider;

   static {
      ConscryptPlatform.Companion var0 = new ConscryptPlatform.Companion((DefaultConstructorMarker)null);
      Companion = var0;
      boolean var1 = false;

      boolean var2;
      label30: {
         boolean var3;
         label29: {
            label34: {
               boolean var10001;
               try {
                  Class.forName("org.conscrypt.Conscrypt$Version", false, var0.getClass().getClassLoader());
               } catch (ClassNotFoundException | NoClassDefFoundError var5) {
                  var10001 = false;
                  break label34;
               }

               var2 = var1;

               try {
                  if (!Conscrypt.isAvailable()) {
                     break label30;
                  }

                  var3 = Companion.atLeastVersion(2, 1, 0);
                  break label29;
               } catch (ClassNotFoundException | NoClassDefFoundError var4) {
                  var10001 = false;
               }
            }

            var2 = var1;
            break label30;
         }

         var2 = var1;
         if (var3) {
            var2 = true;
         }
      }

      isSupported = var2;
   }

   private ConscryptPlatform() {
      Provider var1 = Conscrypt.newProviderBuilder().provideTrustManager(true).build();
      Intrinsics.checkExpressionValueIsNotNull(var1, "Conscrypt.newProviderBui…rustManager(true).build()");
      this.provider = var1;
   }

   // $FF: synthetic method
   public ConscryptPlatform(DefaultConstructorMarker var1) {
      this();
   }

   public void configureTlsExtensions(SSLSocket var1, String var2, List<Protocol> var3) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      Intrinsics.checkParameterIsNotNull(var3, "protocols");
      if (Conscrypt.isConscrypt(var1)) {
         Conscrypt.setUseSessionTickets(var1, true);
         Object[] var4 = ((Collection)Platform.Companion.alpnProtocolNames(var3)).toArray(new String[0]);
         if (var4 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
         }

         Conscrypt.setApplicationProtocols(var1, (String[])var4);
      } else {
         super.configureTlsExtensions(var1, var2, var3);
      }

   }

   public String getSelectedProtocol(SSLSocket var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      String var2;
      if (Conscrypt.isConscrypt(var1)) {
         var2 = Conscrypt.getApplicationProtocol(var1);
      } else {
         var2 = super.getSelectedProtocol(var1);
      }

      return var2;
   }

   public SSLContext newSSLContext() {
      SSLContext var1 = SSLContext.getInstance("TLS", this.provider);
      Intrinsics.checkExpressionValueIsNotNull(var1, "SSLContext.getInstance(\"TLS\", provider)");
      return var1;
   }

   public SSLSocketFactory newSslSocketFactory(X509TrustManager var1) {
      Intrinsics.checkParameterIsNotNull(var1, "trustManager");
      SSLContext var2 = this.newSSLContext();
      var2.init((KeyManager[])null, new TrustManager[]{(TrustManager)var1}, (SecureRandom)null);
      SSLSocketFactory var3 = var2.getSocketFactory();
      Conscrypt.setUseEngineSocket(var3, true);
      Intrinsics.checkExpressionValueIsNotNull(var3, "newSSLContext().apply {\n…ineSocket(it, true)\n    }");
      return var3;
   }

   public X509TrustManager platformTrustManager() {
      TrustManagerFactory var1 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      var1.init((KeyStore)null);
      Intrinsics.checkExpressionValueIsNotNull(var1, "TrustManagerFactory.getI…(null as KeyStore?)\n    }");
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
            X509TrustManager var7 = (X509TrustManager)var6;
            Conscrypt.setHostnameVerifier((TrustManager)var7, (ConscryptHostnameVerifier)null.INSTANCE);
            return var7;
         } else {
            throw new TypeCastException("null cannot be cast to non-null type javax.net.ssl.X509TrustManager");
         }
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("Unexpected default trust managers: ");
         String var8 = Arrays.toString(var2);
         Intrinsics.checkExpressionValueIsNotNull(var8, "java.util.Arrays.toString(this)");
         var5.append(var8);
         throw (Throwable)(new IllegalStateException(var5.toString().toString()));
      }
   }

   public X509TrustManager trustManager(SSLSocketFactory var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
      return null;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\bJ\b\u0010\u000b\u001a\u0004\u0018\u00010\fR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0005¨\u0006\r"},
      d2 = {"Lokhttp3/internal/platform/ConscryptPlatform$Companion;", "", "()V", "isSupported", "", "()Z", "atLeastVersion", "major", "", "minor", "patch", "buildIfSupported", "Lokhttp3/internal/platform/ConscryptPlatform;", "okhttp"},
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
      public static boolean atLeastVersion$default(ConscryptPlatform.Companion var0, int var1, int var2, int var3, int var4, Object var5) {
         if ((var4 & 2) != 0) {
            var2 = 0;
         }

         if ((var4 & 4) != 0) {
            var3 = 0;
         }

         return var0.atLeastVersion(var1, var2, var3);
      }

      public final boolean atLeastVersion(int var1, int var2, int var3) {
         Version var4 = Conscrypt.version();
         int var5 = var4.major();
         boolean var6 = true;
         boolean var7 = true;
         boolean var8 = true;
         if (var5 != var1) {
            if (var4.major() <= var1) {
               var8 = false;
            }

            return var8;
         } else if (var4.minor() != var2) {
            if (var4.minor() > var2) {
               var8 = var6;
            } else {
               var8 = false;
            }

            return var8;
         } else {
            if (var4.patch() >= var3) {
               var8 = var7;
            } else {
               var8 = false;
            }

            return var8;
         }
      }

      public final ConscryptPlatform buildIfSupported() {
         boolean var1 = ((ConscryptPlatform.Companion)this).isSupported();
         ConscryptPlatform var2 = null;
         if (var1) {
            var2 = new ConscryptPlatform((DefaultConstructorMarker)null);
         }

         return var2;
      }

      public final boolean isSupported() {
         return ConscryptPlatform.isSupported;
      }
   }
}
