/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okhttp3.internal.platform.android.AndroidSocketAdapter$Companion$factory
 */
package okhttp3.internal.platform.android;

import java.io.Serializable;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Protocol;
import okhttp3.internal.platform.AndroidPlatform;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.platform.android.AndroidSocketAdapter;
import okhttp3.internal.platform.android.DeferredSocketAdapter;
import okhttp3.internal.platform.android.SocketAdapter;

@Metadata(bv={1, 0, 3}, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0016\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J(\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00042\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0016J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u000e\u001a\u00020\u0004H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u000e\u001a\u00020\u0004H\u0016R\u0016\u0010\u0006\u001a\n \b*\u0004\u0018\u00010\u00070\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\n \b*\u0004\u0018\u00010\u00070\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n \b*\u0004\u0018\u00010\u00070\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0002\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00040\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2={"Lokhttp3/internal/platform/android/AndroidSocketAdapter;", "Lokhttp3/internal/platform/android/SocketAdapter;", "sslSocketClass", "Ljava/lang/Class;", "Ljavax/net/ssl/SSLSocket;", "(Ljava/lang/Class;)V", "getAlpnSelectedProtocol", "Ljava/lang/reflect/Method;", "kotlin.jvm.PlatformType", "setAlpnProtocols", "setHostname", "setUseSessionTickets", "configureTlsExtensions", "", "sslSocket", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "getSelectedProtocol", "isSupported", "", "matchesSocket", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public class AndroidSocketAdapter
implements SocketAdapter {
    public static final Companion Companion;
    private static final DeferredSocketAdapter.Factory playProviderFactory;
    private final Method getAlpnSelectedProtocol;
    private final Method setAlpnProtocols;
    private final Method setHostname;
    private final Method setUseSessionTickets;
    private final Class<? super SSLSocket> sslSocketClass;

    static {
        Companion companion;
        Companion = companion = new Companion(null);
        playProviderFactory = companion.factory("com.google.android.gms.org.conscrypt");
    }

    public AndroidSocketAdapter(Class<? super SSLSocket> genericDeclaration) {
        Intrinsics.checkParameterIsNotNull(genericDeclaration, "sslSocketClass");
        this.sslSocketClass = genericDeclaration;
        genericDeclaration = genericDeclaration.getDeclaredMethod("setUseSessionTickets", Boolean.TYPE);
        Intrinsics.checkExpressionValueIsNotNull(genericDeclaration, "sslSocketClass.getDeclar\u2026:class.javaPrimitiveType)");
        this.setUseSessionTickets = genericDeclaration;
        this.setHostname = this.sslSocketClass.getMethod("setHostname", String.class);
        this.getAlpnSelectedProtocol = this.sslSocketClass.getMethod("getAlpnSelectedProtocol", new Class[0]);
        this.setAlpnProtocols = this.sslSocketClass.getMethod("setAlpnProtocols", byte[].class);
    }

    @Override
    public void configureTlsExtensions(SSLSocket sSLSocket, String string2, List<? extends Protocol> list) {
        Intrinsics.checkParameterIsNotNull(sSLSocket, "sslSocket");
        Intrinsics.checkParameterIsNotNull(list, "protocols");
        if (!this.matchesSocket(sSLSocket)) return;
        try {
            this.setUseSessionTickets.invoke(sSLSocket, true);
            if (string2 != null) {
                this.setHostname.invoke(sSLSocket, string2);
            }
            this.setAlpnProtocols.invoke(sSLSocket, new Object[]{Platform.Companion.concatLengthPrefixed(list)});
            return;
        }
        catch (InvocationTargetException invocationTargetException) {
            throw (Throwable)((Object)new AssertionError(invocationTargetException));
        }
        catch (IllegalAccessException illegalAccessException) {
            throw (Throwable)((Object)new AssertionError(illegalAccessException));
        }
    }

    @Override
    public String getSelectedProtocol(SSLSocket object) {
        Intrinsics.checkParameterIsNotNull(object, "sslSocket");
        boolean bl = this.matchesSocket((SSLSocket)object);
        Object var3_6 = null;
        if (!bl) {
            return null;
        }
        try {
            byte[] arrby = (byte[])this.getAlpnSelectedProtocol.invoke(object, new Object[0]);
            object = var3_6;
            if (arrby == null) return object;
            Charset charset = StandardCharsets.UTF_8;
            Intrinsics.checkExpressionValueIsNotNull(charset, "StandardCharsets.UTF_8");
            return new String(arrby, charset);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw (Throwable)((Object)new AssertionError(invocationTargetException));
        }
        catch (IllegalAccessException illegalAccessException) {
            throw (Throwable)((Object)new AssertionError(illegalAccessException));
        }
        catch (NullPointerException nullPointerException) {
            if (!Intrinsics.areEqual(nullPointerException.getMessage(), "ssl == null")) throw (Throwable)nullPointerException;
            return var3_6;
        }
    }

    @Override
    public boolean isSupported() {
        return AndroidPlatform.Companion.isSupported();
    }

    @Override
    public boolean matchesSocket(SSLSocket sSLSocket) {
        Intrinsics.checkParameterIsNotNull(sSLSocket, "sslSocket");
        return this.sslSocketClass.isInstance(sSLSocket);
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

    @Metadata(bv={1, 0, 3}, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0007\u001a\u00020\b2\u000e\u0010\t\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u000b0\nH\u0002J\u000e\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u000f"}, d2={"Lokhttp3/internal/platform/android/AndroidSocketAdapter$Companion;", "", "()V", "playProviderFactory", "Lokhttp3/internal/platform/android/DeferredSocketAdapter$Factory;", "getPlayProviderFactory", "()Lokhttp3/internal/platform/android/DeferredSocketAdapter$Factory;", "build", "Lokhttp3/internal/platform/android/AndroidSocketAdapter;", "actualSSLSocketClass", "Ljava/lang/Class;", "Ljavax/net/ssl/SSLSocket;", "factory", "packageName", "", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final /* synthetic */ AndroidSocketAdapter access$build(Companion companion, Class class_) {
            return companion.build(class_);
        }

        private final AndroidSocketAdapter build(Class<? super SSLSocket> class_) {
            Serializable serializable = class_;
            while (serializable != null && Intrinsics.areEqual(((Class)serializable).getSimpleName(), "OpenSSLSocketImpl") ^ true) {
                if ((serializable = ((Class)serializable).getSuperclass()) != null) continue;
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("No OpenSSLSocketImpl superclass of socket of type ");
                ((StringBuilder)serializable).append(class_);
                throw (Throwable)((Object)new AssertionError((Object)((StringBuilder)serializable).toString()));
            }
            if (serializable != null) return new AndroidSocketAdapter((Class<? super SSLSocket>)serializable);
            Intrinsics.throwNpe();
            return new AndroidSocketAdapter((Class<? super SSLSocket>)serializable);
        }

        public final DeferredSocketAdapter.Factory factory(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "packageName");
            return new DeferredSocketAdapter.Factory(string2){
                final /* synthetic */ String $packageName;
                {
                    this.$packageName = string2;
                }

                public SocketAdapter create(SSLSocket sSLSocket) {
                    Intrinsics.checkParameterIsNotNull(sSLSocket, "sslSocket");
                    return Companion.access$build(AndroidSocketAdapter.Companion, sSLSocket.getClass());
                }

                public boolean matchesSocket(SSLSocket object) {
                    Intrinsics.checkParameterIsNotNull(object, "sslSocket");
                    String string2 = object.getClass().getName();
                    Intrinsics.checkExpressionValueIsNotNull(string2, "sslSocket.javaClass.name");
                    object = new StringBuilder();
                    ((StringBuilder)object).append(this.$packageName);
                    ((StringBuilder)object).append('.');
                    return kotlin.text.StringsKt.startsWith$default(string2, ((StringBuilder)object).toString(), false, 2, null);
                }
            };
        }

        public final DeferredSocketAdapter.Factory getPlayProviderFactory() {
            return playProviderFactory;
        }
    }

}

