package okhttp3.internal.platform.android;

import android.net.http.X509TrustManagerExtensions;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.tls.CertificateChainCleaner;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0000\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J$\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u000b\u001a\u00020\fH\u0017J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"},
   d2 = {"Lokhttp3/internal/platform/android/AndroidCertificateChainCleaner;", "Lokhttp3/internal/tls/CertificateChainCleaner;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "x509TrustManagerExtensions", "Landroid/net/http/X509TrustManagerExtensions;", "(Ljavax/net/ssl/X509TrustManager;Landroid/net/http/X509TrustManagerExtensions;)V", "clean", "", "Ljava/security/cert/Certificate;", "chain", "hostname", "", "equals", "", "other", "", "hashCode", "", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class AndroidCertificateChainCleaner extends CertificateChainCleaner {
   public static final AndroidCertificateChainCleaner.Companion Companion = new AndroidCertificateChainCleaner.Companion((DefaultConstructorMarker)null);
   private final X509TrustManager trustManager;
   private final X509TrustManagerExtensions x509TrustManagerExtensions;

   public AndroidCertificateChainCleaner(X509TrustManager var1, X509TrustManagerExtensions var2) {
      Intrinsics.checkParameterIsNotNull(var1, "trustManager");
      Intrinsics.checkParameterIsNotNull(var2, "x509TrustManagerExtensions");
      super();
      this.trustManager = var1;
      this.x509TrustManagerExtensions = var2;
   }

   public List<Certificate> clean(List<? extends Certificate> var1, String var2) throws SSLPeerUnverifiedException {
      Intrinsics.checkParameterIsNotNull(var1, "chain");
      Intrinsics.checkParameterIsNotNull(var2, "hostname");
      Object[] var4 = ((Collection)var1).toArray(new X509Certificate[0]);
      if (var4 != null) {
         X509Certificate[] var5 = (X509Certificate[])var4;

         try {
            var1 = this.x509TrustManagerExtensions.checkServerTrusted(var5, "RSA", var2);
            Intrinsics.checkExpressionValueIsNotNull(var1, "x509TrustManagerExtensio…ficates, \"RSA\", hostname)");
            return var1;
         } catch (CertificateException var3) {
            SSLPeerUnverifiedException var6 = new SSLPeerUnverifiedException(var3.getMessage());
            var6.initCause((Throwable)var3);
            throw (Throwable)var6;
         }
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      }
   }

   public boolean equals(Object var1) {
      boolean var2;
      if (var1 instanceof AndroidCertificateChainCleaner && ((AndroidCertificateChainCleaner)var1).trustManager == this.trustManager) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public int hashCode() {
      return System.identityHashCode(this.trustManager);
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"},
      d2 = {"Lokhttp3/internal/platform/android/AndroidCertificateChainCleaner$Companion;", "", "()V", "buildIfSupported", "Lokhttp3/internal/platform/android/AndroidCertificateChainCleaner;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "okhttp"},
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

      public final AndroidCertificateChainCleaner buildIfSupported(X509TrustManager var1) {
         Intrinsics.checkParameterIsNotNull(var1, "trustManager");
         AndroidCertificateChainCleaner var2 = null;

         X509TrustManagerExtensions var3;
         try {
            var3 = new X509TrustManagerExtensions(var1);
         } catch (IllegalArgumentException var4) {
            var3 = null;
         }

         if (var3 != null) {
            var2 = new AndroidCertificateChainCleaner(var1, var3);
         }

         return var2;
      }
   }
}
