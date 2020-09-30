package okhttp3.internal.platform;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.internal.Util;
import okhttp3.internal.platform.android.AndroidLog;
import okhttp3.internal.tls.BasicCertificateChainCleaner;
import okhttp3.internal.tls.BasicTrustRootIndex;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.TrustRootIndex;
import okio.Buffer;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u0000 /2\u00020\u0001:\u0001/B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\nH\u0016J-\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0011\u0010\u0010\u001a\r\u0012\t\u0012\u00070\u0012¢\u0006\u0002\b\u00130\u0011H\u0016J \u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0006\u0010\u001b\u001a\u00020\u000fJ\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u001e\u001a\u00020\u000fH\u0016J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J&\u0010!\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020\u000f2\b\b\u0002\u0010#\u001a\u00020\u001a2\n\b\u0002\u0010$\u001a\u0004\u0018\u00010%H\u0016J\u001a\u0010&\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020\u000f2\b\u0010'\u001a\u0004\u0018\u00010\u0001H\u0016J\b\u0010(\u001a\u00020)H\u0016J\u0010\u0010*\u001a\u00020+2\u0006\u0010\t\u001a\u00020\nH\u0016J\b\u0010,\u001a\u00020\nH\u0016J\b\u0010-\u001a\u00020\u000fH\u0016J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010.\u001a\u00020+H\u0016¨\u00060"},
   d2 = {"Lokhttp3/internal/platform/Platform;", "", "()V", "afterHandshake", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "buildCertificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "buildTrustRootIndex", "Lokhttp3/internal/tls/TrustRootIndex;", "configureTlsExtensions", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "Lkotlin/jvm/JvmSuppressWildcards;", "connectSocket", "socket", "Ljava/net/Socket;", "address", "Ljava/net/InetSocketAddress;", "connectTimeout", "", "getPrefix", "getSelectedProtocol", "getStackTraceForCloseable", "closer", "isCleartextTrafficPermitted", "", "log", "message", "level", "t", "", "logCloseableLeak", "stackTrace", "newSSLContext", "Ljavax/net/ssl/SSLContext;", "newSslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "platformTrustManager", "toString", "sslSocketFactory", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public class Platform {
   public static final Platform.Companion Companion;
   public static final int INFO = 4;
   public static final int WARN = 5;
   private static final Logger logger;
   private static volatile Platform platform;

   static {
      Platform.Companion var0 = new Platform.Companion((DefaultConstructorMarker)null);
      Companion = var0;
      platform = var0.findPlatform();
      logger = Logger.getLogger(OkHttpClient.class.getName());
   }

   @JvmStatic
   public static final Platform get() {
      return Companion.get();
   }

   // $FF: synthetic method
   public static void log$default(Platform var0, String var1, int var2, Throwable var3, int var4, Object var5) {
      if (var5 == null) {
         if ((var4 & 2) != 0) {
            var2 = 4;
         }

         if ((var4 & 4) != 0) {
            var3 = (Throwable)null;
         }

         var0.log(var1, var2, var3);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: log");
      }
   }

   public void afterHandshake(SSLSocket var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
   }

   public CertificateChainCleaner buildCertificateChainCleaner(X509TrustManager var1) {
      Intrinsics.checkParameterIsNotNull(var1, "trustManager");
      return (CertificateChainCleaner)(new BasicCertificateChainCleaner(this.buildTrustRootIndex(var1)));
   }

   public TrustRootIndex buildTrustRootIndex(X509TrustManager var1) {
      Intrinsics.checkParameterIsNotNull(var1, "trustManager");
      X509Certificate[] var2 = var1.getAcceptedIssuers();
      Intrinsics.checkExpressionValueIsNotNull(var2, "trustManager.acceptedIssuers");
      return (TrustRootIndex)(new BasicTrustRootIndex((X509Certificate[])Arrays.copyOf(var2, var2.length)));
   }

   public void configureTlsExtensions(SSLSocket var1, String var2, List<Protocol> var3) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      Intrinsics.checkParameterIsNotNull(var3, "protocols");
   }

   public void connectSocket(Socket var1, InetSocketAddress var2, int var3) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "socket");
      Intrinsics.checkParameterIsNotNull(var2, "address");
      var1.connect((SocketAddress)var2, var3);
   }

   public final String getPrefix() {
      return "OkHttp";
   }

   public String getSelectedProtocol(SSLSocket var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      return null;
   }

   public Object getStackTraceForCloseable(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "closer");
      Throwable var2;
      if (logger.isLoggable(Level.FINE)) {
         var2 = new Throwable(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   public boolean isCleartextTrafficPermitted(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "hostname");
      return true;
   }

   public void log(String var1, int var2, Throwable var3) {
      Intrinsics.checkParameterIsNotNull(var1, "message");
      Level var4;
      if (var2 == 5) {
         var4 = Level.WARNING;
      } else {
         var4 = Level.INFO;
      }

      logger.log(var4, var1, var3);
   }

   public void logCloseableLeak(String var1, Object var2) {
      Intrinsics.checkParameterIsNotNull(var1, "message");
      String var3 = var1;
      if (var2 == null) {
         StringBuilder var4 = new StringBuilder();
         var4.append(var1);
         var4.append(" To see where this was allocated, set the OkHttpClient logger level to FINE: Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);");
         var3 = var4.toString();
      }

      this.log(var3, 5, (Throwable)var2);
   }

   public SSLContext newSSLContext() {
      SSLContext var1 = SSLContext.getInstance("TLS");
      Intrinsics.checkExpressionValueIsNotNull(var1, "SSLContext.getInstance(\"TLS\")");
      return var1;
   }

   public SSLSocketFactory newSslSocketFactory(X509TrustManager var1) {
      Intrinsics.checkParameterIsNotNull(var1, "trustManager");

      try {
         SSLContext var5 = this.newSSLContext();
         var5.init((KeyManager[])null, new TrustManager[]{(TrustManager)var1}, (SecureRandom)null);
         SSLSocketFactory var4 = var5.getSocketFactory();
         Intrinsics.checkExpressionValueIsNotNull(var4, "newSSLContext().apply {\n…ll)\n      }.socketFactory");
         return var4;
      } catch (GeneralSecurityException var3) {
         StringBuilder var2 = new StringBuilder();
         var2.append("No System TLS: ");
         var2.append(var3);
         throw (Throwable)(new AssertionError(var2.toString(), (Throwable)var3));
      }
   }

   public X509TrustManager platformTrustManager() {
      TrustManagerFactory var1 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
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

   public String toString() {
      String var1 = this.getClass().getSimpleName();
      Intrinsics.checkExpressionValueIsNotNull(var1, "javaClass.simpleName");
      return var1;
   }

   public X509TrustManager trustManager(SSLSocketFactory var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
      Object var2 = null;

      X509TrustManager var6;
      label27: {
         boolean var10001;
         Object var7;
         try {
            Class var3 = Class.forName("sun.security.ssl.SSLContextImpl");
            Intrinsics.checkExpressionValueIsNotNull(var3, "sslContextClass");
            var7 = Util.readFieldOrNull(var1, var3, "context");
         } catch (ClassNotFoundException var5) {
            var10001 = false;
            break label27;
         }

         var6 = (X509TrustManager)var2;
         if (var7 == null) {
            return var6;
         }

         try {
            var6 = (X509TrustManager)Util.readFieldOrNull(var7, X509TrustManager.class, "trustManager");
            return var6;
         } catch (ClassNotFoundException var4) {
            var10001 = false;
         }
      }

      var6 = (X509TrustManager)var2;
      return var6;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0012J\u0014\u0010\u0016\u001a\u00020\u00172\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0012J\b\u0010\u0018\u001a\u00020\u0010H\u0002J\b\u0010\u0019\u001a\u00020\u0010H\u0002J\b\u0010\u001a\u001a\u00020\u0010H\u0002J\b\u0010\u001b\u001a\u00020\u0010H\u0007J\u0010\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010\u000f\u001a\u00020\u0010R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\bR\u0014\u0010\t\u001a\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\bR\u0014\u0010\n\u001a\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\bR\u0014\u0010\u000b\u001a\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\bR\u0016\u0010\f\u001a\n \u000e*\u0004\u0018\u00010\r0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001e"},
      d2 = {"Lokhttp3/internal/platform/Platform$Companion;", "", "()V", "INFO", "", "WARN", "isAndroid", "", "()Z", "isBouncyCastlePreferred", "isConscryptPreferred", "isOpenJSSEPreferred", "logger", "Ljava/util/logging/Logger;", "kotlin.jvm.PlatformType", "platform", "Lokhttp3/internal/platform/Platform;", "alpnProtocolNames", "", "", "protocols", "Lokhttp3/Protocol;", "concatLengthPrefixed", "", "findAndroidPlatform", "findJvmPlatform", "findPlatform", "get", "resetForTests", "", "okhttp"},
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

      private final Platform findAndroidPlatform() {
         AndroidLog.INSTANCE.enable();
         Platform var1 = Android10Platform.Companion.buildIfSupported();
         if (var1 == null) {
            Platform var2 = AndroidPlatform.Companion.buildIfSupported();
            var1 = var2;
            if (var2 == null) {
               Intrinsics.throwNpe();
               var1 = var2;
            }
         }

         return var1;
      }

      private final Platform findJvmPlatform() {
         Platform.Companion var1 = (Platform.Companion)this;
         if (var1.isConscryptPreferred()) {
            ConscryptPlatform var2 = ConscryptPlatform.Companion.buildIfSupported();
            if (var2 != null) {
               return (Platform)var2;
            }
         }

         if (var1.isBouncyCastlePreferred()) {
            BouncyCastlePlatform var6 = BouncyCastlePlatform.Companion.buildIfSupported();
            if (var6 != null) {
               return (Platform)var6;
            }
         }

         if (var1.isOpenJSSEPreferred()) {
            OpenJSSEPlatform var3 = OpenJSSEPlatform.Companion.buildIfSupported();
            if (var3 != null) {
               return (Platform)var3;
            }
         }

         Jdk9Platform var4 = Jdk9Platform.Companion.buildIfSupported();
         if (var4 != null) {
            return (Platform)var4;
         } else {
            Platform var5 = Jdk8WithJettyBootPlatform.Companion.buildIfSupported();
            return var5 != null ? var5 : new Platform();
         }
      }

      private final Platform findPlatform() {
         Platform.Companion var1 = (Platform.Companion)this;
         Platform var2;
         if (var1.isAndroid()) {
            var2 = var1.findAndroidPlatform();
         } else {
            var2 = var1.findJvmPlatform();
         }

         return var2;
      }

      private final boolean isBouncyCastlePreferred() {
         Provider var1 = Security.getProviders()[0];
         Intrinsics.checkExpressionValueIsNotNull(var1, "Security.getProviders()[0]");
         return Intrinsics.areEqual((Object)"BC", (Object)var1.getName());
      }

      private final boolean isConscryptPreferred() {
         Provider var1 = Security.getProviders()[0];
         Intrinsics.checkExpressionValueIsNotNull(var1, "Security.getProviders()[0]");
         return Intrinsics.areEqual((Object)"Conscrypt", (Object)var1.getName());
      }

      private final boolean isOpenJSSEPreferred() {
         Provider var1 = Security.getProviders()[0];
         Intrinsics.checkExpressionValueIsNotNull(var1, "Security.getProviders()[0]");
         return Intrinsics.areEqual((Object)"OpenJSSE", (Object)var1.getName());
      }

      // $FF: synthetic method
      public static void resetForTests$default(Platform.Companion var0, Platform var1, int var2, Object var3) {
         if ((var2 & 1) != 0) {
            var1 = var0.findPlatform();
         }

         var0.resetForTests(var1);
      }

      public final List<String> alpnProtocolNames(List<? extends Protocol> var1) {
         Intrinsics.checkParameterIsNotNull(var1, "protocols");
         Iterable var2 = (Iterable)var1;
         Collection var5 = (Collection)(new ArrayList());
         Iterator var6 = var2.iterator();

         while(var6.hasNext()) {
            Object var3 = var6.next();
            boolean var4;
            if ((Protocol)var3 != Protocol.HTTP_1_0) {
               var4 = true;
            } else {
               var4 = false;
            }

            if (var4) {
               var5.add(var3);
            }
         }

         var2 = (Iterable)((List)var5);
         var5 = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(var2, 10)));
         var6 = var2.iterator();

         while(var6.hasNext()) {
            var5.add(((Protocol)var6.next()).toString());
         }

         return (List)var5;
      }

      public final byte[] concatLengthPrefixed(List<? extends Protocol> var1) {
         Intrinsics.checkParameterIsNotNull(var1, "protocols");
         Buffer var2 = new Buffer();
         Iterator var3 = ((Platform.Companion)this).alpnProtocolNames(var1).iterator();

         while(var3.hasNext()) {
            String var4 = (String)var3.next();
            var2.writeByte(var4.length());
            var2.writeUtf8(var4);
         }

         return var2.readByteArray();
      }

      @JvmStatic
      public final Platform get() {
         return Platform.platform;
      }

      public final boolean isAndroid() {
         return Intrinsics.areEqual((Object)"Dalvik", (Object)System.getProperty("java.vm.name"));
      }

      public final void resetForTests(Platform var1) {
         Intrinsics.checkParameterIsNotNull(var1, "platform");
         Platform.platform = var1;
      }
   }
}
