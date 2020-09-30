/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.jsse.BCSSLParameters
 *  org.bouncycastle.jsse.BCSSLSocket
 *  org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
 */
package okhttp3.internal.platform;

import java.security.KeyStore;
import java.security.Provider;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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
import okhttp3.internal.platform.Platform;
import org.bouncycastle.jsse.BCSSLParameters;
import org.bouncycastle.jsse.BCSSLSocket;
import org.bouncycastle.jsse.provider.BouncyCastleJsseProvider;

@Metadata(bv={1, 0, 3}, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J-\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0011\u0010\u000b\u001a\r\u0012\t\u0012\u00070\r\u00a2\u0006\u0002\b\u000e0\fH\u0016J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2={"Lokhttp3/internal/platform/BouncyCastlePlatform;", "Lokhttp3/internal/platform/Platform;", "()V", "provider", "Ljava/security/Provider;", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "Lkotlin/jvm/JvmSuppressWildcards;", "getSelectedProtocol", "newSSLContext", "Ljavax/net/ssl/SSLContext;", "platformTrustManager", "Ljavax/net/ssl/X509TrustManager;", "trustManager", "sslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class BouncyCastlePlatform
extends Platform {
    public static final Companion Companion = var0 = new Companion(null);
    private static final boolean isSupported;
    private final Provider provider = (Provider)new BouncyCastleJsseProvider();

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    static {
        var1_2 = false;
        try {
            Class.forName("org.bouncycastle.jsse.provider.BouncyCastleJsseProvider", false, var0.getClass().getClassLoader());
            var1_2 = true;
lbl7: // 2 sources:
            do {
                BouncyCastlePlatform.isSupported = var1_2;
                return;
                break;
            } while (true);
        }
        catch (ClassNotFoundException var0_1) {
            ** continue;
        }
    }

    private BouncyCastlePlatform() {
    }

    public /* synthetic */ BouncyCastlePlatform(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @Override
    public void configureTlsExtensions(SSLSocket sSLSocket, String string2, List<Protocol> arrstring) {
        Intrinsics.checkParameterIsNotNull(sSLSocket, "sslSocket");
        Intrinsics.checkParameterIsNotNull(arrstring, "protocols");
        if (sSLSocket instanceof BCSSLSocket) {
            sSLSocket = (BCSSLSocket)sSLSocket;
            string2 = sSLSocket.getParameters();
            arrstring = Platform.Companion.alpnProtocolNames((List<? extends Protocol>)arrstring);
            Intrinsics.checkExpressionValueIsNotNull(string2, "sslParameters");
            arrstring = ((Collection)arrstring).toArray(new String[0]);
            if (arrstring == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            string2.setApplicationProtocols(arrstring);
            sSLSocket.setParameters((BCSSLParameters)string2);
            return;
        }
        super.configureTlsExtensions(sSLSocket, string2, (List<Protocol>)arrstring);
    }

    @Override
    public String getSelectedProtocol(SSLSocket object) {
        Intrinsics.checkParameterIsNotNull(object, "sslSocket");
        if (!(object instanceof BCSSLSocket)) {
            return super.getSelectedProtocol((SSLSocket)object);
        }
        String string2 = ((BCSSLSocket)object).getApplicationProtocol();
        if (string2 == null) return null;
        object = string2;
        if (!Intrinsics.areEqual(string2, "")) return object;
        return null;
    }

    @Override
    public SSLContext newSSLContext() {
        SSLContext sSLContext = SSLContext.getInstance("TLS", this.provider);
        Intrinsics.checkExpressionValueIsNotNull(sSLContext, "SSLContext.getInstance(\"TLS\", provider)");
        return sSLContext;
    }

    @Override
    public X509TrustManager platformTrustManager() {
        Object object = TrustManagerFactory.getInstance("PKIX", "BCJSSE");
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

    @Override
    public X509TrustManager trustManager(SSLSocketFactory sSLSocketFactory) {
        Intrinsics.checkParameterIsNotNull(sSLSocketFactory, "sslSocketFactory");
        throw (Throwable)new UnsupportedOperationException("clientBuilder.sslSocketFactory(SSLSocketFactory) not supported with BouncyCastle");
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0005\u00a8\u0006\b"}, d2={"Lokhttp3/internal/platform/BouncyCastlePlatform$Companion;", "", "()V", "isSupported", "", "()Z", "buildIfSupported", "Lokhttp3/internal/platform/BouncyCastlePlatform;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final BouncyCastlePlatform buildIfSupported() {
            boolean bl = this.isSupported();
            BouncyCastlePlatform bouncyCastlePlatform = null;
            if (!bl) return bouncyCastlePlatform;
            return new BouncyCastlePlatform(null);
        }

        public final boolean isSupported() {
            return isSupported;
        }
    }

}

