/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okhttp3.internal.platform.ConscryptPlatform$platformTrustManager
 *  org.conscrypt.Conscrypt
 *  org.conscrypt.Conscrypt$ProviderBuilder
 *  org.conscrypt.Conscrypt$Version
 *  org.conscrypt.ConscryptHostnameVerifier
 */
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
import okhttp3.internal.platform.ConscryptPlatform;
import okhttp3.internal.platform.Platform;
import org.conscrypt.Conscrypt;
import org.conscrypt.ConscryptHostnameVerifier;

@Metadata(bv={1, 0, 3}, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J-\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0011\u0010\u000b\u001a\r\u0012\t\u0012\u00070\r\u00a2\u0006\u0002\b\u000e0\fH\u0016J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0015H\u0016J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0017\u001a\u00020\u0013H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2={"Lokhttp3/internal/platform/ConscryptPlatform;", "Lokhttp3/internal/platform/Platform;", "()V", "provider", "Ljava/security/Provider;", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "Lkotlin/jvm/JvmSuppressWildcards;", "getSelectedProtocol", "newSSLContext", "Ljavax/net/ssl/SSLContext;", "newSslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "platformTrustManager", "sslSocketFactory", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class ConscryptPlatform
extends Platform {
    public static final Companion Companion = var0 = new Companion(null);
    private static final boolean isSupported;
    private final Provider provider;

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    static {
        var1_2 = false;
        try {
            Class.forName("org.conscrypt.Conscrypt$Version", false, var0.getClass().getClassLoader());
            var2_3 = var1_2;
            if (Conscrypt.isAvailable()) {
                var3_4 = ConscryptPlatform.Companion.atLeastVersion(2, 1, 0);
                var2_3 = var1_2;
                if (var3_4) {
                    var2_3 = true;
                }
            }
lbl12: // 6 sources:
            do {
                ConscryptPlatform.isSupported = var2_3;
                return;
                break;
            } while (true);
        }
        catch (ClassNotFoundException | NoClassDefFoundError var0_1) {
            var2_3 = var1_2;
            ** continue;
        }
    }

    private ConscryptPlatform() {
        Provider provider = Conscrypt.newProviderBuilder().provideTrustManager(true).build();
        Intrinsics.checkExpressionValueIsNotNull(provider, "Conscrypt.newProviderBui\u2026rustManager(true).build()");
        this.provider = provider;
    }

    public /* synthetic */ ConscryptPlatform(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @Override
    public void configureTlsExtensions(SSLSocket sSLSocket, String arrstring, List<Protocol> list) {
        Intrinsics.checkParameterIsNotNull(sSLSocket, "sslSocket");
        Intrinsics.checkParameterIsNotNull(list, "protocols");
        if (Conscrypt.isConscrypt((SSLSocket)sSLSocket)) {
            Conscrypt.setUseSessionTickets((SSLSocket)sSLSocket, (boolean)true);
            arrstring = ((Collection)Platform.Companion.alpnProtocolNames(list)).toArray(new String[0]);
            if (arrstring == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            Conscrypt.setApplicationProtocols((SSLSocket)sSLSocket, (String[])arrstring);
            return;
        }
        super.configureTlsExtensions(sSLSocket, (String)arrstring, list);
    }

    @Override
    public String getSelectedProtocol(SSLSocket object) {
        Intrinsics.checkParameterIsNotNull(object, "sslSocket");
        if (!Conscrypt.isConscrypt((SSLSocket)object)) return super.getSelectedProtocol((SSLSocket)object);
        return Conscrypt.getApplicationProtocol((SSLSocket)object);
    }

    @Override
    public SSLContext newSSLContext() {
        SSLContext sSLContext = SSLContext.getInstance("TLS", this.provider);
        Intrinsics.checkExpressionValueIsNotNull(sSLContext, "SSLContext.getInstance(\"TLS\", provider)");
        return sSLContext;
    }

    @Override
    public SSLSocketFactory newSslSocketFactory(X509TrustManager object) {
        Intrinsics.checkParameterIsNotNull(object, "trustManager");
        SSLContext sSLContext = this.newSSLContext();
        sSLContext.init(null, new TrustManager[]{(TrustManager)object}, null);
        object = sSLContext.getSocketFactory();
        Conscrypt.setUseEngineSocket((SSLSocketFactory)object, (boolean)true);
        Intrinsics.checkExpressionValueIsNotNull(object, "newSSLContext().apply {\n\u2026ineSocket(it, true)\n    }");
        return object;
    }

    @Override
    public X509TrustManager platformTrustManager() {
        Object object = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        ((TrustManagerFactory)object).init((KeyStore)null);
        Intrinsics.checkExpressionValueIsNotNull(object, "TrustManagerFactory.getI\u2026(null as KeyStore?)\n    }");
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
            object = (X509TrustManager)object;
            Conscrypt.setHostnameVerifier((TrustManager)((TrustManager)object), (ConscryptHostnameVerifier)platformTrustManager.2.INSTANCE);
            return object;
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
        return null;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\"\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\bJ\b\u0010\u000b\u001a\u0004\u0018\u00010\fR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0005\u00a8\u0006\r"}, d2={"Lokhttp3/internal/platform/ConscryptPlatform$Companion;", "", "()V", "isSupported", "", "()Z", "atLeastVersion", "major", "", "minor", "patch", "buildIfSupported", "Lokhttp3/internal/platform/ConscryptPlatform;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ boolean atLeastVersion$default(Companion companion, int n, int n2, int n3, int n4, Object object) {
            if ((n4 & 2) != 0) {
                n2 = 0;
            }
            if ((n4 & 4) == 0) return companion.atLeastVersion(n, n2, n3);
            n3 = 0;
            return companion.atLeastVersion(n, n2, n3);
        }

        public final boolean atLeastVersion(int n, int n2, int n3) {
            Conscrypt.Version version = Conscrypt.version();
            int n4 = version.major();
            boolean bl = true;
            boolean bl2 = true;
            boolean bl3 = true;
            if (n4 != n) {
                if (version.major() <= n) return false;
                return bl3;
            }
            if (version.minor() != n2) {
                if (version.minor() <= n2) return false;
                return bl;
            }
            if (version.patch() < n3) return false;
            return bl2;
        }

        public final ConscryptPlatform buildIfSupported() {
            boolean bl = this.isSupported();
            ConscryptPlatform conscryptPlatform = null;
            if (!bl) return conscryptPlatform;
            return new ConscryptPlatform(null);
        }

        public final boolean isSupported() {
            return isSupported;
        }
    }

}

