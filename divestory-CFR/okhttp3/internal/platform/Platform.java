/*
 * Decompiled with CFR <Could not determine version>.
 */
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
import okhttp3.internal.platform.Android10Platform;
import okhttp3.internal.platform.AndroidPlatform;
import okhttp3.internal.platform.BouncyCastlePlatform;
import okhttp3.internal.platform.ConscryptPlatform;
import okhttp3.internal.platform.Jdk8WithJettyBootPlatform;
import okhttp3.internal.platform.Jdk9Platform;
import okhttp3.internal.platform.OpenJSSEPlatform;
import okhttp3.internal.platform.android.AndroidLog;
import okhttp3.internal.tls.BasicCertificateChainCleaner;
import okhttp3.internal.tls.BasicTrustRootIndex;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.TrustRootIndex;
import okio.Buffer;

@Metadata(bv={1, 0, 3}, d1={"\u0000t\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u0000 /2\u00020\u0001:\u0001/B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\nH\u0016J-\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0011\u0010\u0010\u001a\r\u0012\t\u0012\u00070\u0012\u00a2\u0006\u0002\b\u00130\u0011H\u0016J \u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0006\u0010\u001b\u001a\u00020\u000fJ\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u001e\u001a\u00020\u000fH\u0016J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J&\u0010!\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020\u000f2\b\b\u0002\u0010#\u001a\u00020\u001a2\n\b\u0002\u0010$\u001a\u0004\u0018\u00010%H\u0016J\u001a\u0010&\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020\u000f2\b\u0010'\u001a\u0004\u0018\u00010\u0001H\u0016J\b\u0010(\u001a\u00020)H\u0016J\u0010\u0010*\u001a\u00020+2\u0006\u0010\t\u001a\u00020\nH\u0016J\b\u0010,\u001a\u00020\nH\u0016J\b\u0010-\u001a\u00020\u000fH\u0016J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010.\u001a\u00020+H\u0016\u00a8\u00060"}, d2={"Lokhttp3/internal/platform/Platform;", "", "()V", "afterHandshake", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "buildCertificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "buildTrustRootIndex", "Lokhttp3/internal/tls/TrustRootIndex;", "configureTlsExtensions", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "Lkotlin/jvm/JvmSuppressWildcards;", "connectSocket", "socket", "Ljava/net/Socket;", "address", "Ljava/net/InetSocketAddress;", "connectTimeout", "", "getPrefix", "getSelectedProtocol", "getStackTraceForCloseable", "closer", "isCleartextTrafficPermitted", "", "log", "message", "level", "t", "", "logCloseableLeak", "stackTrace", "newSSLContext", "Ljavax/net/ssl/SSLContext;", "newSslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "platformTrustManager", "toString", "sslSocketFactory", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public class Platform {
    public static final Companion Companion;
    public static final int INFO = 4;
    public static final int WARN = 5;
    private static final Logger logger;
    private static volatile Platform platform;

    static {
        Companion companion;
        Companion = companion = new Companion(null);
        platform = companion.findPlatform();
        logger = Logger.getLogger(OkHttpClient.class.getName());
    }

    public static final /* synthetic */ void access$setPlatform$cp(Platform platform) {
        Platform.platform = platform;
    }

    @JvmStatic
    public static final Platform get() {
        return Companion.get();
    }

    public static /* synthetic */ void log$default(Platform platform, String string2, int n, Throwable throwable, int n2, Object object) {
        if (object != null) throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: log");
        if ((n2 & 2) != 0) {
            n = 4;
        }
        if ((n2 & 4) != 0) {
            throwable = null;
        }
        platform.log(string2, n, throwable);
    }

    public void afterHandshake(SSLSocket sSLSocket) {
        Intrinsics.checkParameterIsNotNull(sSLSocket, "sslSocket");
    }

    public CertificateChainCleaner buildCertificateChainCleaner(X509TrustManager x509TrustManager) {
        Intrinsics.checkParameterIsNotNull(x509TrustManager, "trustManager");
        return new BasicCertificateChainCleaner(this.buildTrustRootIndex(x509TrustManager));
    }

    public TrustRootIndex buildTrustRootIndex(X509TrustManager arrx509Certificate) {
        Intrinsics.checkParameterIsNotNull(arrx509Certificate, "trustManager");
        arrx509Certificate = arrx509Certificate.getAcceptedIssuers();
        Intrinsics.checkExpressionValueIsNotNull(arrx509Certificate, "trustManager.acceptedIssuers");
        return new BasicTrustRootIndex(Arrays.copyOf(arrx509Certificate, arrx509Certificate.length));
    }

    public void configureTlsExtensions(SSLSocket sSLSocket, String string2, List<Protocol> list) {
        Intrinsics.checkParameterIsNotNull(sSLSocket, "sslSocket");
        Intrinsics.checkParameterIsNotNull(list, "protocols");
    }

    public void connectSocket(Socket socket, InetSocketAddress inetSocketAddress, int n) throws IOException {
        Intrinsics.checkParameterIsNotNull(socket, "socket");
        Intrinsics.checkParameterIsNotNull(inetSocketAddress, "address");
        socket.connect(inetSocketAddress, n);
    }

    public final String getPrefix() {
        return "OkHttp";
    }

    public String getSelectedProtocol(SSLSocket sSLSocket) {
        Intrinsics.checkParameterIsNotNull(sSLSocket, "sslSocket");
        return null;
    }

    public Object getStackTraceForCloseable(String object) {
        Intrinsics.checkParameterIsNotNull(object, "closer");
        if (!logger.isLoggable(Level.FINE)) return null;
        return new Throwable((String)object);
    }

    public boolean isCleartextTrafficPermitted(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "hostname");
        return true;
    }

    public void log(String string2, int n, Throwable throwable) {
        Intrinsics.checkParameterIsNotNull(string2, "message");
        Level level = n == 5 ? Level.WARNING : Level.INFO;
        logger.log(level, string2, throwable);
    }

    public void logCloseableLeak(String string2, Object object) {
        Intrinsics.checkParameterIsNotNull(string2, "message");
        CharSequence charSequence = string2;
        if (object == null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(" To see where this was allocated, set the OkHttpClient logger level to FINE: Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        this.log((String)charSequence, 5, (Throwable)object);
    }

    public SSLContext newSSLContext() {
        SSLContext sSLContext = SSLContext.getInstance("TLS");
        Intrinsics.checkExpressionValueIsNotNull(sSLContext, "SSLContext.getInstance(\"TLS\")");
        return sSLContext;
    }

    public SSLSocketFactory newSslSocketFactory(X509TrustManager object) {
        Intrinsics.checkParameterIsNotNull(object, "trustManager");
        try {
            SSLContext sSLContext = this.newSSLContext();
            sSLContext.init(null, new TrustManager[]{(TrustManager)object}, null);
            object = sSLContext.getSocketFactory();
            Intrinsics.checkExpressionValueIsNotNull(object, "newSSLContext().apply {\n\u2026ll)\n      }.socketFactory");
            return object;
        }
        catch (GeneralSecurityException generalSecurityException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No System TLS: ");
            stringBuilder.append(generalSecurityException);
            throw (Throwable)((Object)new AssertionError(stringBuilder.toString(), generalSecurityException));
        }
    }

    public X509TrustManager platformTrustManager() {
        Object object = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        ((TrustManagerFactory)object).init((KeyStore)null);
        Intrinsics.checkExpressionValueIsNotNull(object, "factory");
        Object object2 = ((TrustManagerFactory)object).getTrustManagers();
        if (object2 == null) {
            Intrinsics.throwNpe();
        }
        int n = ((TrustManager[])object2).length;
        boolean bl = true;
        if (n != 1 || !(object2[0] instanceof X509TrustManager)) {
            bl = false;
        }
        if (bl) {
            object = object2[0];
            if (object == null) throw new TypeCastException("null cannot be cast to non-null type javax.net.ssl.X509TrustManager");
            return (X509TrustManager)object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unexpected default trust managers: ");
        object2 = Arrays.toString(object2);
        Intrinsics.checkExpressionValueIsNotNull(object2, "java.util.Arrays.toString(this)");
        ((StringBuilder)object).append((String)object2);
        throw (Throwable)new IllegalStateException(((StringBuilder)object).toString().toString());
    }

    public String toString() {
        String string2 = this.getClass().getSimpleName();
        Intrinsics.checkExpressionValueIsNotNull(string2, "javaClass.simpleName");
        return string2;
    }

    public X509TrustManager trustManager(SSLSocketFactory object) {
        Intrinsics.checkParameterIsNotNull(object, "sslSocketFactory");
        Object var2_3 = null;
        try {
            Class<?> class_ = Class.forName("sun.security.ssl.SSLContextImpl");
            Intrinsics.checkExpressionValueIsNotNull(class_, "sslContextClass");
            class_ = Util.readFieldOrNull(object, class_, "context");
            object = var2_3;
            if (class_ == null) return object;
            return Util.readFieldOrNull(class_, X509TrustManager.class, "trustManager");
        }
        catch (ClassNotFoundException classNotFoundException) {
            return var2_3;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0012J\u0014\u0010\u0016\u001a\u00020\u00172\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0012J\b\u0010\u0018\u001a\u00020\u0010H\u0002J\b\u0010\u0019\u001a\u00020\u0010H\u0002J\b\u0010\u001a\u001a\u00020\u0010H\u0002J\b\u0010\u001b\u001a\u00020\u0010H\u0007J\u0010\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010\u000f\u001a\u00020\u0010R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u00078F\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\bR\u0014\u0010\t\u001a\u00020\u00078BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\bR\u0014\u0010\n\u001a\u00020\u00078BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\bR\u0014\u0010\u000b\u001a\u00020\u00078BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\bR\u0016\u0010\f\u001a\n \u000e*\u0004\u0018\u00010\r0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2={"Lokhttp3/internal/platform/Platform$Companion;", "", "()V", "INFO", "", "WARN", "isAndroid", "", "()Z", "isBouncyCastlePreferred", "isConscryptPreferred", "isOpenJSSEPreferred", "logger", "Ljava/util/logging/Logger;", "kotlin.jvm.PlatformType", "platform", "Lokhttp3/internal/platform/Platform;", "alpnProtocolNames", "", "", "protocols", "Lokhttp3/Protocol;", "concatLengthPrefixed", "", "findAndroidPlatform", "findJvmPlatform", "findPlatform", "get", "resetForTests", "", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final Platform findAndroidPlatform() {
            Platform platform;
            AndroidLog.INSTANCE.enable();
            Platform platform2 = Android10Platform.Companion.buildIfSupported();
            if (platform2 != null) {
                return platform2;
            }
            platform2 = platform = AndroidPlatform.Companion.buildIfSupported();
            if (platform != null) return platform2;
            Intrinsics.throwNpe();
            return platform;
        }

        private final Platform findJvmPlatform() {
            Platform platform;
            Object object = this;
            if (((Companion)object).isConscryptPreferred() && (platform = ConscryptPlatform.Companion.buildIfSupported()) != null) {
                return platform;
            }
            if (((Companion)object).isBouncyCastlePreferred() && (platform = BouncyCastlePlatform.Companion.buildIfSupported()) != null) {
                return platform;
            }
            if (((Companion)object).isOpenJSSEPreferred() && (object = OpenJSSEPlatform.Companion.buildIfSupported()) != null) {
                return (Platform)object;
            }
            object = Jdk9Platform.Companion.buildIfSupported();
            if (object != null) {
                return (Platform)object;
            }
            object = Jdk8WithJettyBootPlatform.Companion.buildIfSupported();
            if (object == null) return new Platform();
            return object;
        }

        private final Platform findPlatform() {
            Companion companion = this;
            if (!companion.isAndroid()) return companion.findJvmPlatform();
            return companion.findAndroidPlatform();
        }

        private final boolean isBouncyCastlePreferred() {
            Provider provider = Security.getProviders()[0];
            Intrinsics.checkExpressionValueIsNotNull(provider, "Security.getProviders()[0]");
            return Intrinsics.areEqual("BC", provider.getName());
        }

        private final boolean isConscryptPreferred() {
            Provider provider = Security.getProviders()[0];
            Intrinsics.checkExpressionValueIsNotNull(provider, "Security.getProviders()[0]");
            return Intrinsics.areEqual("Conscrypt", provider.getName());
        }

        private final boolean isOpenJSSEPreferred() {
            Provider provider = Security.getProviders()[0];
            Intrinsics.checkExpressionValueIsNotNull(provider, "Security.getProviders()[0]");
            return Intrinsics.areEqual("OpenJSSE", provider.getName());
        }

        public static /* synthetic */ void resetForTests$default(Companion companion, Platform platform, int n, Object object) {
            if ((n & 1) != 0) {
                platform = companion.findPlatform();
            }
            companion.resetForTests(platform);
        }

        public final List<String> alpnProtocolNames(List<? extends Protocol> collection) {
            Intrinsics.checkParameterIsNotNull(collection, "protocols");
            Iterator iterator2 = collection;
            collection = new ArrayList();
            iterator2 = iterator2.iterator();
            while (iterator2.hasNext()) {
                Object e = iterator2.next();
                boolean bl = (Protocol)((Object)e) != Protocol.HTTP_1_0;
                if (!bl) continue;
                collection.add((Protocol)((Object)e));
            }
            iterator2 = collection;
            collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterator2, 10));
            iterator2 = iterator2.iterator();
            while (iterator2.hasNext()) {
                collection.add((Protocol)((Object)((Protocol)((Object)iterator2.next())).toString()));
            }
            return collection;
        }

        public final byte[] concatLengthPrefixed(List<? extends Protocol> object) {
            Intrinsics.checkParameterIsNotNull(object, "protocols");
            Buffer buffer = new Buffer();
            Iterator<String> iterator2 = this.alpnProtocolNames((List<? extends Protocol>)object).iterator();
            while (iterator2.hasNext()) {
                object = iterator2.next();
                buffer.writeByte(((String)object).length());
                buffer.writeUtf8((String)object);
            }
            return buffer.readByteArray();
        }

        @JvmStatic
        public final Platform get() {
            return platform;
        }

        public final boolean isAndroid() {
            return Intrinsics.areEqual("Dalvik", System.getProperty("java.vm.name"));
        }

        public final void resetForTests(Platform platform) {
            Intrinsics.checkParameterIsNotNull(platform, "platform");
            Platform.access$setPlatform$cp(platform);
        }
    }

}

