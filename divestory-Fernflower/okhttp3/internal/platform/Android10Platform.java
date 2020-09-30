package okhttp3.internal.platform;

import android.os.Build.VERSION;
import android.security.NetworkSecurityPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Protocol;
import okhttp3.internal.platform.android.Android10SocketAdapter;
import okhttp3.internal.platform.android.AndroidCertificateChainCleaner;
import okhttp3.internal.platform.android.AndroidSocketAdapter;
import okhttp3.internal.platform.android.BouncyCastleSocketAdapter;
import okhttp3.internal.platform.android.ConscryptSocketAdapter;
import okhttp3.internal.platform.android.DeferredSocketAdapter;
import okhttp3.internal.platform.android.SocketAdapter;
import okhttp3.internal.tls.CertificateChainCleaner;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J(\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u0004H\u0016J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u000fH\u0017J\u0012\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"},
   d2 = {"Lokhttp3/internal/platform/Android10Platform;", "Lokhttp3/internal/platform/Platform;", "()V", "socketAdapters", "", "Lokhttp3/internal/platform/android/SocketAdapter;", "buildCertificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "", "protocols", "Lokhttp3/Protocol;", "getSelectedProtocol", "isCleartextTrafficPermitted", "", "sslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Android10Platform extends Platform {
   public static final Android10Platform.Companion Companion = new Android10Platform.Companion((DefaultConstructorMarker)null);
   private static final boolean isSupported;
   private final List<SocketAdapter> socketAdapters;

   static {
      boolean var0;
      if (Platform.Companion.isAndroid() && VERSION.SDK_INT >= 29) {
         var0 = true;
      } else {
         var0 = false;
      }

      isSupported = var0;
   }

   public Android10Platform() {
      Iterable var1 = (Iterable)CollectionsKt.listOfNotNull(new SocketAdapter[]{Android10SocketAdapter.Companion.buildIfSupported(), (SocketAdapter)(new DeferredSocketAdapter(AndroidSocketAdapter.Companion.getPlayProviderFactory())), (SocketAdapter)(new DeferredSocketAdapter(ConscryptSocketAdapter.Companion.getFactory())), (SocketAdapter)(new DeferredSocketAdapter(BouncyCastleSocketAdapter.Companion.getFactory()))});
      Collection var2 = (Collection)(new ArrayList());
      Iterator var4 = var1.iterator();

      while(var4.hasNext()) {
         Object var3 = var4.next();
         if (((SocketAdapter)var3).isSupported()) {
            var2.add(var3);
         }
      }

      this.socketAdapters = (List)var2;
   }

   public CertificateChainCleaner buildCertificateChainCleaner(X509TrustManager var1) {
      Intrinsics.checkParameterIsNotNull(var1, "trustManager");
      AndroidCertificateChainCleaner var2 = AndroidCertificateChainCleaner.Companion.buildIfSupported(var1);
      CertificateChainCleaner var3;
      if (var2 != null) {
         var3 = (CertificateChainCleaner)var2;
      } else {
         var3 = super.buildCertificateChainCleaner(var1);
      }

      return var3;
   }

   public void configureTlsExtensions(SSLSocket var1, String var2, List<? extends Protocol> var3) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      Intrinsics.checkParameterIsNotNull(var3, "protocols");
      Iterator var4 = ((Iterable)this.socketAdapters).iterator();

      Object var5;
      do {
         if (!var4.hasNext()) {
            var5 = null;
            break;
         }

         var5 = var4.next();
      } while(!((SocketAdapter)var5).matchesSocket(var1));

      SocketAdapter var6 = (SocketAdapter)var5;
      if (var6 != null) {
         var6.configureTlsExtensions(var1, var2, var3);
      }

   }

   public String getSelectedProtocol(SSLSocket var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      Iterator var2 = ((Iterable)this.socketAdapters).iterator();

      Object var4;
      Object var5;
      do {
         boolean var3 = var2.hasNext();
         var4 = null;
         if (!var3) {
            var5 = null;
            break;
         }

         var5 = var2.next();
      } while(!((SocketAdapter)var5).matchesSocket(var1));

      SocketAdapter var7 = (SocketAdapter)var5;
      String var6 = (String)var4;
      if (var7 != null) {
         var6 = var7.getSelectedProtocol(var1);
      }

      return var6;
   }

   public boolean isCleartextTrafficPermitted(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "hostname");
      return NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted(var1);
   }

   public X509TrustManager trustManager(SSLSocketFactory var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
      Iterator var2 = ((Iterable)this.socketAdapters).iterator();

      Object var4;
      Object var5;
      do {
         boolean var3 = var2.hasNext();
         var4 = null;
         if (!var3) {
            var5 = null;
            break;
         }

         var5 = var2.next();
      } while(!((SocketAdapter)var5).matchesSocketFactory(var1));

      SocketAdapter var7 = (SocketAdapter)var5;
      X509TrustManager var6 = (X509TrustManager)var4;
      if (var7 != null) {
         var6 = var7.trustManager(var1);
      }

      return var6;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0005¨\u0006\b"},
      d2 = {"Lokhttp3/internal/platform/Android10Platform$Companion;", "", "()V", "isSupported", "", "()Z", "buildIfSupported", "Lokhttp3/internal/platform/Platform;", "okhttp"},
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

      public final Platform buildIfSupported() {
         Platform var1;
         if (((Android10Platform.Companion)this).isSupported()) {
            var1 = (Platform)(new Android10Platform());
         } else {
            var1 = null;
         }

         return var1;
      }

      public final boolean isSupported() {
         return Android10Platform.isSupported;
      }
   }
}
