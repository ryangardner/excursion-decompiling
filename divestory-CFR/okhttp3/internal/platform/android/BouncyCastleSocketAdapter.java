/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okhttp3.internal.platform.android.BouncyCastleSocketAdapter$Companion$factory
 *  org.bouncycastle.jsse.BCSSLParameters
 *  org.bouncycastle.jsse.BCSSLSocket
 */
package okhttp3.internal.platform.android;

import java.util.Collection;
import java.util.List;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Protocol;
import okhttp3.internal.platform.BouncyCastlePlatform;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.platform.android.BouncyCastleSocketAdapter;
import okhttp3.internal.platform.android.DeferredSocketAdapter;
import okhttp3.internal.platform.android.SocketAdapter;
import org.bouncycastle.jsse.BCSSLParameters;
import org.bouncycastle.jsse.BCSSLSocket;

@Metadata(bv={1, 0, 3}, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u0005\u00a2\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016J\u0012\u0010\f\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016\u00a8\u0006\u0011"}, d2={"Lokhttp3/internal/platform/android/BouncyCastleSocketAdapter;", "Lokhttp3/internal/platform/android/SocketAdapter;", "()V", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "getSelectedProtocol", "isSupported", "", "matchesSocket", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class BouncyCastleSocketAdapter
implements SocketAdapter {
    public static final Companion Companion = new Companion(null);
    private static final DeferredSocketAdapter.Factory factory = new DeferredSocketAdapter.Factory(){

        public SocketAdapter create(SSLSocket sSLSocket) {
            Intrinsics.checkParameterIsNotNull(sSLSocket, "sslSocket");
            return new BouncyCastleSocketAdapter();
        }

        public boolean matchesSocket(SSLSocket sSLSocket) {
            Intrinsics.checkParameterIsNotNull(sSLSocket, "sslSocket");
            if (!BouncyCastlePlatform.Companion.isSupported()) return false;
            if (!(sSLSocket instanceof BCSSLSocket)) return false;
            return true;
        }
    };

    @Override
    public void configureTlsExtensions(SSLSocket sSLSocket, String string2, List<? extends Protocol> arrstring) {
        Intrinsics.checkParameterIsNotNull(sSLSocket, "sslSocket");
        Intrinsics.checkParameterIsNotNull(arrstring, "protocols");
        if (!this.matchesSocket(sSLSocket)) return;
        sSLSocket = (BCSSLSocket)sSLSocket;
        string2 = sSLSocket.getParameters();
        Intrinsics.checkExpressionValueIsNotNull(string2, "sslParameters");
        arrstring = ((Collection)Platform.Companion.alpnProtocolNames((List<? extends Protocol>)arrstring)).toArray(new String[0]);
        if (arrstring == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        string2.setApplicationProtocols(arrstring);
        sSLSocket.setParameters((BCSSLParameters)string2);
    }

    @Override
    public String getSelectedProtocol(SSLSocket object) {
        Intrinsics.checkParameterIsNotNull(object, "sslSocket");
        String string2 = ((BCSSLSocket)object).getApplicationProtocol();
        if (string2 == null) return null;
        object = string2;
        if (!Intrinsics.areEqual(string2, "")) return object;
        return null;
    }

    @Override
    public boolean isSupported() {
        return BouncyCastlePlatform.Companion.isSupported();
    }

    @Override
    public boolean matchesSocket(SSLSocket sSLSocket) {
        Intrinsics.checkParameterIsNotNull(sSLSocket, "sslSocket");
        return sSLSocket instanceof BCSSLSocket;
    }

    @Override
    public boolean matchesSocketFactory(SSLSocketFactory sSLSocketFactory) {
        Intrinsics.checkParameterIsNotNull(sSLSocketFactory, "sslSocketFactory");
        return SocketAdapter.DefaultImpls.matchesSocketFactory(this, sSLSocketFactory);
    }

    @Override
    public X509TrustManager trustManager(SSLSocketFactory sSLSocketFactory) {
        Intrinsics.checkParameterIsNotNull(sSLSocketFactory, "sslSocketFactory");
        return SocketAdapter.DefaultImpls.trustManager(this, sSLSocketFactory);
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2={"Lokhttp3/internal/platform/android/BouncyCastleSocketAdapter$Companion;", "", "()V", "factory", "Lokhttp3/internal/platform/android/DeferredSocketAdapter$Factory;", "getFactory", "()Lokhttp3/internal/platform/android/DeferredSocketAdapter$Factory;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final DeferredSocketAdapter.Factory getFactory() {
            return factory;
        }
    }

}
