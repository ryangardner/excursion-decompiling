package okhttp3.internal.platform;

import android.os.Build.VERSION;
import android.security.NetworkSecurityPolicy;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Protocol;
import okhttp3.internal.platform.android.AndroidCertificateChainCleaner;
import okhttp3.internal.platform.android.AndroidSocketAdapter;
import okhttp3.internal.platform.android.BouncyCastleSocketAdapter;
import okhttp3.internal.platform.android.CloseGuard;
import okhttp3.internal.platform.android.ConscryptSocketAdapter;
import okhttp3.internal.platform.android.DeferredSocketAdapter;
import okhttp3.internal.platform.android.SocketAdapter;
import okhttp3.internal.platform.android.StandardAndroidSocketAdapter;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.TrustRootIndex;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 )2\u00020\u0001:\u0002)*B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000bH\u0016J-\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0011\u0010\u0014\u001a\r\u0012\t\u0012\u00070\u0015¢\u0006\u0002\b\u00160\u0006H\u0016J \u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0012\u0010\u001e\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0012\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010!\u001a\u00020\u0013H\u0016J\u0010\u0010\"\u001a\u00020#2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u001a\u0010$\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020\u00132\b\u0010&\u001a\u0004\u0018\u00010 H\u0016J\u0012\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010'\u001a\u00020(H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006+"},
   d2 = {"Lokhttp3/internal/platform/AndroidPlatform;", "Lokhttp3/internal/platform/Platform;", "()V", "closeGuard", "Lokhttp3/internal/platform/android/CloseGuard;", "socketAdapters", "", "Lokhttp3/internal/platform/android/SocketAdapter;", "buildCertificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "buildTrustRootIndex", "Lokhttp3/internal/tls/TrustRootIndex;", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "", "protocols", "Lokhttp3/Protocol;", "Lkotlin/jvm/JvmSuppressWildcards;", "connectSocket", "socket", "Ljava/net/Socket;", "address", "Ljava/net/InetSocketAddress;", "connectTimeout", "", "getSelectedProtocol", "getStackTraceForCloseable", "", "closer", "isCleartextTrafficPermitted", "", "logCloseableLeak", "message", "stackTrace", "sslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "Companion", "CustomTrustRootIndex", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class AndroidPlatform extends Platform {
   public static final AndroidPlatform.Companion Companion = new AndroidPlatform.Companion((DefaultConstructorMarker)null);
   private static final boolean isSupported;
   private final CloseGuard closeGuard;
   private final List<SocketAdapter> socketAdapters;

   static {
      boolean var0 = Platform.Companion.isAndroid();
      boolean var1 = true;
      boolean var2 = false;
      if (var0 && VERSION.SDK_INT < 30) {
         if (VERSION.SDK_INT >= 21) {
            var2 = true;
         }

         if (!var2) {
            StringBuilder var3 = new StringBuilder();
            var3.append("Expected Android API level 21+ but was ");
            var3.append(VERSION.SDK_INT);
            throw (Throwable)(new IllegalStateException(var3.toString().toString()));
         }
      } else {
         var1 = false;
      }

      isSupported = var1;
   }

   public AndroidPlatform() {
      Iterable var1 = (Iterable)CollectionsKt.listOfNotNull(new SocketAdapter[]{StandardAndroidSocketAdapter.Companion.buildIfSupported$default(StandardAndroidSocketAdapter.Companion, (String)null, 1, (Object)null), (SocketAdapter)(new DeferredSocketAdapter(AndroidSocketAdapter.Companion.getPlayProviderFactory())), (SocketAdapter)(new DeferredSocketAdapter(ConscryptSocketAdapter.Companion.getFactory())), (SocketAdapter)(new DeferredSocketAdapter(BouncyCastleSocketAdapter.Companion.getFactory()))});
      Collection var2 = (Collection)(new ArrayList());
      Iterator var4 = var1.iterator();

      while(var4.hasNext()) {
         Object var3 = var4.next();
         if (((SocketAdapter)var3).isSupported()) {
            var2.add(var3);
         }
      }

      this.socketAdapters = (List)var2;
      this.closeGuard = CloseGuard.Companion.get();
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

   public TrustRootIndex buildTrustRootIndex(X509TrustManager var1) {
      Intrinsics.checkParameterIsNotNull(var1, "trustManager");

      TrustRootIndex var5;
      TrustRootIndex var6;
      try {
         Method var2 = var1.getClass().getDeclaredMethod("findTrustAnchorByIssuerAndSignature", X509Certificate.class);
         Intrinsics.checkExpressionValueIsNotNull(var2, "method");
         var2.setAccessible(true);
         AndroidPlatform.CustomTrustRootIndex var3 = new AndroidPlatform.CustomTrustRootIndex(var1, var2);
         var6 = (TrustRootIndex)var3;
      } catch (NoSuchMethodException var4) {
         var5 = super.buildTrustRootIndex(var1);
         return var5;
      }

      var5 = var6;
      return var5;
   }

   public void configureTlsExtensions(SSLSocket var1, String var2, List<Protocol> var3) {
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

   public void connectSocket(Socket var1, InetSocketAddress var2, int var3) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "socket");
      Intrinsics.checkParameterIsNotNull(var2, "address");

      try {
         var1.connect((SocketAddress)var2, var3);
      } catch (ClassCastException var4) {
         if (VERSION.SDK_INT == 26) {
            throw (Throwable)(new IOException("Exception in connect", (Throwable)var4));
         } else {
            throw (Throwable)var4;
         }
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

   public Object getStackTraceForCloseable(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "closer");
      return this.closeGuard.createAndOpen(var1);
   }

   public boolean isCleartextTrafficPermitted(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "hostname");
      boolean var2;
      if (VERSION.SDK_INT >= 24) {
         var2 = NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted(var1);
      } else if (VERSION.SDK_INT >= 23) {
         NetworkSecurityPolicy var3 = NetworkSecurityPolicy.getInstance();
         Intrinsics.checkExpressionValueIsNotNull(var3, "NetworkSecurityPolicy.getInstance()");
         var2 = var3.isCleartextTrafficPermitted();
      } else {
         var2 = true;
      }

      return var2;
   }

   public void logCloseableLeak(String var1, Object var2) {
      Intrinsics.checkParameterIsNotNull(var1, "message");
      if (!this.closeGuard.warnIfOpen(var2)) {
         Platform.log$default(this, var1, 5, (Throwable)null, 4, (Object)null);
      }

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
      d2 = {"Lokhttp3/internal/platform/AndroidPlatform$Companion;", "", "()V", "isSupported", "", "()Z", "buildIfSupported", "Lokhttp3/internal/platform/Platform;", "okhttp"},
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
         if (((AndroidPlatform.Companion)this).isSupported()) {
            var1 = (Platform)(new AndroidPlatform());
         } else {
            var1 = null;
         }

         return var1;
      }

      public final boolean isSupported() {
         return AndroidPlatform.isSupported;
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u0007\u001a\u00020\u0003HÂ\u0003J\t\u0010\b\u001a\u00020\u0005HÂ\u0003J\u001d\u0010\t\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rHÖ\u0003J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"},
      d2 = {"Lokhttp3/internal/platform/AndroidPlatform$CustomTrustRootIndex;", "Lokhttp3/internal/tls/TrustRootIndex;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "findByIssuerAndSignatureMethod", "Ljava/lang/reflect/Method;", "(Ljavax/net/ssl/X509TrustManager;Ljava/lang/reflect/Method;)V", "component1", "component2", "copy", "equals", "", "other", "", "findByIssuerAndSignature", "Ljava/security/cert/X509Certificate;", "cert", "hashCode", "", "toString", "", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class CustomTrustRootIndex implements TrustRootIndex {
      private final Method findByIssuerAndSignatureMethod;
      private final X509TrustManager trustManager;

      public CustomTrustRootIndex(X509TrustManager var1, Method var2) {
         Intrinsics.checkParameterIsNotNull(var1, "trustManager");
         Intrinsics.checkParameterIsNotNull(var2, "findByIssuerAndSignatureMethod");
         super();
         this.trustManager = var1;
         this.findByIssuerAndSignatureMethod = var2;
      }

      private final X509TrustManager component1() {
         return this.trustManager;
      }

      private final Method component2() {
         return this.findByIssuerAndSignatureMethod;
      }

      // $FF: synthetic method
      public static AndroidPlatform.CustomTrustRootIndex copy$default(AndroidPlatform.CustomTrustRootIndex var0, X509TrustManager var1, Method var2, int var3, Object var4) {
         if ((var3 & 1) != 0) {
            var1 = var0.trustManager;
         }

         if ((var3 & 2) != 0) {
            var2 = var0.findByIssuerAndSignatureMethod;
         }

         return var0.copy(var1, var2);
      }

      public final AndroidPlatform.CustomTrustRootIndex copy(X509TrustManager var1, Method var2) {
         Intrinsics.checkParameterIsNotNull(var1, "trustManager");
         Intrinsics.checkParameterIsNotNull(var2, "findByIssuerAndSignatureMethod");
         return new AndroidPlatform.CustomTrustRootIndex(var1, var2);
      }

      public boolean equals(Object var1) {
         if (this != var1) {
            if (!(var1 instanceof AndroidPlatform.CustomTrustRootIndex)) {
               return false;
            }

            AndroidPlatform.CustomTrustRootIndex var2 = (AndroidPlatform.CustomTrustRootIndex)var1;
            if (!Intrinsics.areEqual((Object)this.trustManager, (Object)var2.trustManager) || !Intrinsics.areEqual((Object)this.findByIssuerAndSignatureMethod, (Object)var2.findByIssuerAndSignatureMethod)) {
               return false;
            }
         }

         return true;
      }

      public X509Certificate findByIssuerAndSignature(X509Certificate var1) {
         Intrinsics.checkParameterIsNotNull(var1, "cert");

         IllegalAccessException var10000;
         label48: {
            label36: {
               boolean var10001;
               Object var8;
               try {
                  var8 = this.findByIssuerAndSignatureMethod.invoke(this.trustManager, var1);
               } catch (IllegalAccessException var6) {
                  var10000 = var6;
                  var10001 = false;
                  break label48;
               } catch (InvocationTargetException var7) {
                  var10001 = false;
                  break label36;
               }

               if (var8 != null) {
                  try {
                     var1 = ((TrustAnchor)var8).getTrustedCert();
                     return var1;
                  } catch (IllegalAccessException var4) {
                     var10000 = var4;
                     var10001 = false;
                     break label48;
                  } catch (InvocationTargetException var5) {
                     var10001 = false;
                  }
               } else {
                  try {
                     TypeCastException var10 = new TypeCastException("null cannot be cast to non-null type java.security.cert.TrustAnchor");
                     throw var10;
                  } catch (IllegalAccessException var2) {
                     var10000 = var2;
                     var10001 = false;
                     break label48;
                  } catch (InvocationTargetException var3) {
                     var10001 = false;
                  }
               }
            }

            var1 = null;
            return var1;
         }

         IllegalAccessException var9 = var10000;
         throw (Throwable)(new AssertionError("unable to get issues and signature", (Throwable)var9));
      }

      public int hashCode() {
         X509TrustManager var1 = this.trustManager;
         int var2 = 0;
         int var3;
         if (var1 != null) {
            var3 = var1.hashCode();
         } else {
            var3 = 0;
         }

         Method var4 = this.findByIssuerAndSignatureMethod;
         if (var4 != null) {
            var2 = var4.hashCode();
         }

         return var3 * 31 + var2;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("CustomTrustRootIndex(trustManager=");
         var1.append(this.trustManager);
         var1.append(", findByIssuerAndSignatureMethod=");
         var1.append(this.findByIssuerAndSignatureMethod);
         var1.append(")");
         return var1.toString();
      }
   }
}
