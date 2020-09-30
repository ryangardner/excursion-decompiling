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
import okhttp3.internal.platform.Platform;
import okhttp3.internal.platform.android.Android10SocketAdapter;
import okhttp3.internal.platform.android.AndroidCertificateChainCleaner;
import okhttp3.internal.platform.android.AndroidSocketAdapter;
import okhttp3.internal.platform.android.BouncyCastleSocketAdapter;
import okhttp3.internal.platform.android.ConscryptSocketAdapter;
import okhttp3.internal.platform.android.DeferredSocketAdapter;
import okhttp3.internal.platform.android.SocketAdapter;
import okhttp3.internal.tls.CertificateChainCleaner;

@Metadata(bv={1, 0, 3}, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J(\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u0004H\u0016J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u000fH\u0017J\u0012\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2={"Lokhttp3/internal/platform/Android10Platform;", "Lokhttp3/internal/platform/Platform;", "()V", "socketAdapters", "", "Lokhttp3/internal/platform/android/SocketAdapter;", "buildCertificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "", "protocols", "Lokhttp3/Protocol;", "getSelectedProtocol", "isCleartextTrafficPermitted", "", "sslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class Android10Platform
extends Platform {
    public static final Companion Companion = new Companion(null);
    private static final boolean isSupported;
    private final List<SocketAdapter> socketAdapters;

    static {
        boolean bl = Platform.Companion.isAndroid() && Build.VERSION.SDK_INT >= 29;
        isSupported = bl;
    }

    public Android10Platform() {
        Object object = CollectionsKt.listOfNotNull(Android10SocketAdapter.Companion.buildIfSupported(), new DeferredSocketAdapter(AndroidSocketAdapter.Companion.getPlayProviderFactory()), new DeferredSocketAdapter(ConscryptSocketAdapter.Companion.getFactory()), new DeferredSocketAdapter(BouncyCastleSocketAdapter.Companion.getFactory()));
        Collection collection = new ArrayList();
        object = object.iterator();
        do {
            if (!object.hasNext()) {
                this.socketAdapters = (List)collection;
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

    public void configureTlsExtensions(SSLSocket sSLSocket, String string2, List<? extends Protocol> list) {
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
    public boolean isCleartextTrafficPermitted(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "hostname");
        return NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted(string2);
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

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0005\u00a8\u0006\b"}, d2={"Lokhttp3/internal/platform/Android10Platform$Companion;", "", "()V", "isSupported", "", "()Z", "buildIfSupported", "Lokhttp3/internal/platform/Platform;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Platform buildIfSupported() {
            if (!this.isSupported()) return null;
            return new Android10Platform();
        }

        public final boolean isSupported() {
            return isSupported;
        }
    }

}

