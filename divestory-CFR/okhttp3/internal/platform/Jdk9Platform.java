/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.platform;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Protocol;
import okhttp3.internal.platform.Platform;

@Metadata(bv={1, 0, 3}, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u0005\u00a2\u0006\u0002\u0010\u0002J-\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0011\u0010\t\u001a\r\u0012\t\u0012\u00070\u000b\u00a2\u0006\u0002\b\f0\nH\u0017J\u0012\u0010\r\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0017J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016\u00a8\u0006\u0013"}, d2={"Lokhttp3/internal/platform/Jdk9Platform;", "Lokhttp3/internal/platform/Platform;", "()V", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "Lkotlin/jvm/JvmSuppressWildcards;", "getSelectedProtocol", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "sslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public class Jdk9Platform
extends Platform {
    public static final Companion Companion;
    private static final boolean isAvailable;

    static {
        boolean bl;
        block3 : {
            block5 : {
                block4 : {
                    Integer n = null;
                    Companion = new Companion(null);
                    String string2 = System.getProperty("java.specification.version");
                    if (string2 != null) {
                        n = StringsKt.toIntOrNull(string2);
                    }
                    bl = true;
                    if (n == null) break block4;
                    if (n < 9) break block5;
                    break block3;
                }
                try {
                    SSLSocket.class.getMethod("getApplicationProtocol", new Class[0]);
                    break block3;
                }
                catch (NoSuchMethodException noSuchMethodException) {}
            }
            bl = false;
        }
        isAvailable = bl;
    }

    @Override
    public void configureTlsExtensions(SSLSocket sSLSocket, String object, List<Protocol> arrstring) {
        Intrinsics.checkParameterIsNotNull(sSLSocket, "sslSocket");
        Intrinsics.checkParameterIsNotNull(arrstring, "protocols");
        object = sSLSocket.getSSLParameters();
        arrstring = Platform.Companion.alpnProtocolNames((List<? extends Protocol>)arrstring);
        Intrinsics.checkExpressionValueIsNotNull(object, "sslParameters");
        arrstring = ((Collection)arrstring).toArray(new String[0]);
        if (arrstring == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        ((SSLParameters)object).setApplicationProtocols(arrstring);
        sSLSocket.setSSLParameters((SSLParameters)object);
    }

    @Override
    public String getSelectedProtocol(SSLSocket object) {
        Intrinsics.checkParameterIsNotNull(object, "sslSocket");
        Object var2_3 = null;
        try {
            object = ((SSLSocket)object).getApplicationProtocol();
            if (object == null) {
                return var2_3;
            }
            boolean bl = Intrinsics.areEqual(object, "");
            if (!bl) return object;
            return var2_3;
        }
        catch (UnsupportedOperationException unsupportedOperationException) {
            return var2_3;
        }
    }

    @Override
    public X509TrustManager trustManager(SSLSocketFactory sSLSocketFactory) {
        Intrinsics.checkParameterIsNotNull(sSLSocketFactory, "sslSocketFactory");
        throw (Throwable)new UnsupportedOperationException("clientBuilder.sslSocketFactory(SSLSocketFactory) not supported on JDK 9+");
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0005\u00a8\u0006\b"}, d2={"Lokhttp3/internal/platform/Jdk9Platform$Companion;", "", "()V", "isAvailable", "", "()Z", "buildIfSupported", "Lokhttp3/internal/platform/Jdk9Platform;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Jdk9Platform buildIfSupported() {
            if (!this.isAvailable()) return null;
            return new Jdk9Platform();
        }

        public final boolean isAvailable() {
            return isAvailable;
        }
    }

}

