/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.security.NetworkSecurityPolicy
 */
package okhttp3.internal.platform;

import android.os.Build;
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
import okhttp3.internal.platform.Platform;
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

@Metadata(bv={1, 0, 3}, d1={"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 )2\u00020\u0001:\u0002)*B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000bH\u0016J-\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0011\u0010\u0014\u001a\r\u0012\t\u0012\u00070\u0015\u00a2\u0006\u0002\b\u00160\u0006H\u0016J \u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0012\u0010\u001e\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0012\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010!\u001a\u00020\u0013H\u0016J\u0010\u0010\"\u001a\u00020#2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u001a\u0010$\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020\u00132\b\u0010&\u001a\u0004\u0018\u00010 H\u0016J\u0012\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010'\u001a\u00020(H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006+"}, d2={"Lokhttp3/internal/platform/AndroidPlatform;", "Lokhttp3/internal/platform/Platform;", "()V", "closeGuard", "Lokhttp3/internal/platform/android/CloseGuard;", "socketAdapters", "", "Lokhttp3/internal/platform/android/SocketAdapter;", "buildCertificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "buildTrustRootIndex", "Lokhttp3/internal/tls/TrustRootIndex;", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "", "protocols", "Lokhttp3/Protocol;", "Lkotlin/jvm/JvmSuppressWildcards;", "connectSocket", "socket", "Ljava/net/Socket;", "address", "Ljava/net/InetSocketAddress;", "connectTimeout", "", "getSelectedProtocol", "getStackTraceForCloseable", "", "closer", "isCleartextTrafficPermitted", "", "logCloseableLeak", "message", "stackTrace", "sslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "Companion", "CustomTrustRootIndex", "okhttp"}, k=1, mv={1, 1, 16})
public final class AndroidPlatform
extends Platform {
    public static final Companion Companion = new Companion(null);
    private static final boolean isSupported;
    private final CloseGuard closeGuard;
    private final List<SocketAdapter> socketAdapters;

    static {
        boolean bl = Platform.Companion.isAndroid();
        boolean bl2 = true;
        boolean bl3 = false;
        if (!bl || Build.VERSION.SDK_INT >= 30) {
            bl2 = false;
        } else {
            if (Build.VERSION.SDK_INT >= 21) {
                bl3 = true;
            }
            if (!bl3) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Expected Android API level 21+ but was ");
                stringBuilder.append(Build.VERSION.SDK_INT);
                throw (Throwable)new IllegalStateException(stringBuilder.toString().toString());
            }
        }
        isSupported = bl2;
    }

    public AndroidPlatform() {
        Object object = CollectionsKt.listOfNotNull(StandardAndroidSocketAdapter.Companion.buildIfSupported$default(StandardAndroidSocketAdapter.Companion, null, 1, null), new DeferredSocketAdapter(AndroidSocketAdapter.Companion.getPlayProviderFactory()), new DeferredSocketAdapter(ConscryptSocketAdapter.Companion.getFactory()), new DeferredSocketAdapter(BouncyCastleSocketAdapter.Companion.getFactory()));
        Collection collection = new ArrayList();
        object = object.iterator();
        do {
            if (!object.hasNext()) {
                this.socketAdapters = (List)collection;
                this.closeGuard = CloseGuard.Companion.get();
                return;
            }
            Object e = object.next();
            if (!((SocketAdapter)e).isSupported()) continue;
            collection.add(e);
        } while (true);
    }

    @Override
    public CertificateChainCleaner buildCertificateChainCleaner(X509TrustManager object) {
        Intrinsics.checkParameterIsNotNull(object, "trustManager");
        AndroidCertificateChainCleaner androidCertificateChainCleaner = AndroidCertificateChainCleaner.Companion.buildIfSupported((X509TrustManager)object);
        if (androidCertificateChainCleaner == null) return super.buildCertificateChainCleaner((X509TrustManager)object);
        return androidCertificateChainCleaner;
    }

    @Override
    public TrustRootIndex buildTrustRootIndex(X509TrustManager object) {
        Intrinsics.checkParameterIsNotNull(object, "trustManager");
        try {
            Object object2 = object.getClass().getDeclaredMethod("findTrustAnchorByIssuerAndSignature", X509Certificate.class);
            Intrinsics.checkExpressionValueIsNotNull(object2, "method");
            ((Method)object2).setAccessible(true);
            CustomTrustRootIndex customTrustRootIndex = new CustomTrustRootIndex((X509TrustManager)object, (Method)object2);
            object2 = customTrustRootIndex;
            return object2;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return super.buildTrustRootIndex((X509TrustManager)object);
        }
    }

    @Override
    public void configureTlsExtensions(SSLSocket sSLSocket, String string2, List<Protocol> list) {
        SocketAdapter socketAdapter2;
        block1 : {
            Intrinsics.checkParameterIsNotNull(sSLSocket, "sslSocket");
            Intrinsics.checkParameterIsNotNull(list, "protocols");
            for (SocketAdapter socketAdapter2 : (Iterable)this.socketAdapters) {
                if (!((SocketAdapter)socketAdapter2).matchesSocket(sSLSocket)) continue;
                break block1;
            }
            socketAdapter2 = null;
        }
        socketAdapter2 = socketAdapter2;
        if (socketAdapter2 == null) return;
        socketAdapter2.configureTlsExtensions(sSLSocket, string2, list);
    }

    @Override
    public void connectSocket(Socket socket, InetSocketAddress inetSocketAddress, int n) throws IOException {
        Intrinsics.checkParameterIsNotNull(socket, "socket");
        Intrinsics.checkParameterIsNotNull(inetSocketAddress, "address");
        try {
            socket.connect(inetSocketAddress, n);
            return;
        }
        catch (ClassCastException classCastException) {
            if (Build.VERSION.SDK_INT != 26) throw (Throwable)classCastException;
            throw (Throwable)new IOException("Exception in connect", classCastException);
        }
    }

    @Override
    public String getSelectedProtocol(SSLSocket sSLSocket) {
        String string2;
        Object object;
        String string3;
        block2 : {
            block1 : {
                Intrinsics.checkParameterIsNotNull(sSLSocket, "sslSocket");
                object = ((Iterable)this.socketAdapters).iterator();
                do {
                    boolean bl = object.hasNext();
                    string2 = null;
                    if (!bl) break block1;
                } while (!((SocketAdapter)((Object)(string3 = (String)object.next()))).matchesSocket(sSLSocket));
                break block2;
            }
            string3 = null;
        }
        object = (SocketAdapter)((Object)string3);
        string3 = string2;
        if (object == null) return string3;
        return object.getSelectedProtocol(sSLSocket);
    }

    @Override
    public Object getStackTraceForCloseable(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "closer");
        return this.closeGuard.createAndOpen(string2);
    }

    @Override
    public boolean isCleartextTrafficPermitted(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "hostname");
        if (Build.VERSION.SDK_INT >= 24) {
            return NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted(string2);
        }
        if (Build.VERSION.SDK_INT < 23) return true;
        string2 = NetworkSecurityPolicy.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(string2, "NetworkSecurityPolicy.getInstance()");
        return string2.isCleartextTrafficPermitted();
    }

    @Override
    public void logCloseableLeak(String string2, Object object) {
        Intrinsics.checkParameterIsNotNull(string2, "message");
        if (this.closeGuard.warnIfOpen(object)) return;
        Platform.log$default(this, string2, 5, null, 4, null);
    }

    @Override
    public X509TrustManager trustManager(SSLSocketFactory sSLSocketFactory) {
        Object object;
        X509TrustManager x509TrustManager;
        X509TrustManager x509TrustManager2;
        block2 : {
            block1 : {
                Intrinsics.checkParameterIsNotNull(sSLSocketFactory, "sslSocketFactory");
                object = ((Iterable)this.socketAdapters).iterator();
                do {
                    boolean bl = object.hasNext();
                    x509TrustManager = null;
                    if (!bl) break block1;
                } while (!((SocketAdapter)((Object)(x509TrustManager2 = (X509TrustManager)object.next()))).matchesSocketFactory(sSLSocketFactory));
                break block2;
            }
            x509TrustManager2 = null;
        }
        object = (SocketAdapter)((Object)x509TrustManager2);
        x509TrustManager2 = x509TrustManager;
        if (object == null) return x509TrustManager2;
        return object.trustManager(sSLSocketFactory);
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0005\u00a8\u0006\b"}, d2={"Lokhttp3/internal/platform/AndroidPlatform$Companion;", "", "()V", "isSupported", "", "()Z", "buildIfSupported", "Lokhttp3/internal/platform/Platform;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Platform buildIfSupported() {
            if (!this.isSupported()) return null;
            return new AndroidPlatform();
        }

        public final boolean isSupported() {
            return isSupported;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u0007\u001a\u00020\u0003H\u00c2\u0003J\t\u0010\b\u001a\u00020\u0005H\u00c2\u0003J\u001d\u0010\t\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u00d6\u0003J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2={"Lokhttp3/internal/platform/AndroidPlatform$CustomTrustRootIndex;", "Lokhttp3/internal/tls/TrustRootIndex;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "findByIssuerAndSignatureMethod", "Ljava/lang/reflect/Method;", "(Ljavax/net/ssl/X509TrustManager;Ljava/lang/reflect/Method;)V", "component1", "component2", "copy", "equals", "", "other", "", "findByIssuerAndSignature", "Ljava/security/cert/X509Certificate;", "cert", "hashCode", "", "toString", "", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class CustomTrustRootIndex
    implements TrustRootIndex {
        private final Method findByIssuerAndSignatureMethod;
        private final X509TrustManager trustManager;

        public CustomTrustRootIndex(X509TrustManager x509TrustManager, Method method) {
            Intrinsics.checkParameterIsNotNull(x509TrustManager, "trustManager");
            Intrinsics.checkParameterIsNotNull(method, "findByIssuerAndSignatureMethod");
            this.trustManager = x509TrustManager;
            this.findByIssuerAndSignatureMethod = method;
        }

        private final X509TrustManager component1() {
            return this.trustManager;
        }

        private final Method component2() {
            return this.findByIssuerAndSignatureMethod;
        }

        public static /* synthetic */ CustomTrustRootIndex copy$default(CustomTrustRootIndex customTrustRootIndex, X509TrustManager x509TrustManager, Method method, int n, Object object) {
            if ((n & 1) != 0) {
                x509TrustManager = customTrustRootIndex.trustManager;
            }
            if ((n & 2) == 0) return customTrustRootIndex.copy(x509TrustManager, method);
            method = customTrustRootIndex.findByIssuerAndSignatureMethod;
            return customTrustRootIndex.copy(x509TrustManager, method);
        }

        public final CustomTrustRootIndex copy(X509TrustManager x509TrustManager, Method method) {
            Intrinsics.checkParameterIsNotNull(x509TrustManager, "trustManager");
            Intrinsics.checkParameterIsNotNull(method, "findByIssuerAndSignatureMethod");
            return new CustomTrustRootIndex(x509TrustManager, method);
        }

        public boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof CustomTrustRootIndex)) return false;
            object = (CustomTrustRootIndex)object;
            if (!Intrinsics.areEqual(this.trustManager, ((CustomTrustRootIndex)object).trustManager)) return false;
            if (!Intrinsics.areEqual(this.findByIssuerAndSignatureMethod, ((CustomTrustRootIndex)object).findByIssuerAndSignatureMethod)) return false;
            return true;
        }

        @Override
        public X509Certificate findByIssuerAndSignature(X509Certificate object) {
            Intrinsics.checkParameterIsNotNull(object, "cert");
            try {
                object = this.findByIssuerAndSignatureMethod.invoke(this.trustManager, object);
                if (object != null) {
                    return ((TrustAnchor)object).getTrustedCert();
                }
                object = new TypeCastException("null cannot be cast to non-null type java.security.cert.TrustAnchor");
                throw object;
            }
            catch (InvocationTargetException invocationTargetException) {
                return null;
            }
            catch (IllegalAccessException illegalAccessException) {
                throw (Throwable)((Object)new AssertionError("unable to get issues and signature", illegalAccessException));
            }
        }

        public int hashCode() {
            Object object = this.trustManager;
            int n = 0;
            int n2 = object != null ? object.hashCode() : 0;
            object = this.findByIssuerAndSignatureMethod;
            if (object == null) return n2 * 31 + n;
            n = object.hashCode();
            return n2 * 31 + n;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CustomTrustRootIndex(trustManager=");
            stringBuilder.append(this.trustManager);
            stringBuilder.append(", findByIssuerAndSignatureMethod=");
            stringBuilder.append(this.findByIssuerAndSignatureMethod);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

}

