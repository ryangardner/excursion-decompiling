/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.platform.android;

import java.io.Serializable;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.platform.android.AndroidSocketAdapter;
import okhttp3.internal.platform.android.SocketAdapter;

@Metadata(bv={1, 0, 3}, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB1\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00040\u0003\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00060\u0003\u0012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u0003\u00a2\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0006H\u0016J\u0012\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000b\u001a\u00020\u0006H\u0016R\u0012\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00060\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2={"Lokhttp3/internal/platform/android/StandardAndroidSocketAdapter;", "Lokhttp3/internal/platform/android/AndroidSocketAdapter;", "sslSocketClass", "Ljava/lang/Class;", "Ljavax/net/ssl/SSLSocket;", "sslSocketFactoryClass", "Ljavax/net/ssl/SSLSocketFactory;", "paramClass", "(Ljava/lang/Class;Ljava/lang/Class;Ljava/lang/Class;)V", "matchesSocketFactory", "", "sslSocketFactory", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class StandardAndroidSocketAdapter
extends AndroidSocketAdapter {
    public static final Companion Companion = new Companion(null);
    private final Class<?> paramClass;
    private final Class<? super SSLSocketFactory> sslSocketFactoryClass;

    public StandardAndroidSocketAdapter(Class<? super SSLSocket> class_, Class<? super SSLSocketFactory> class_2, Class<?> class_3) {
        Intrinsics.checkParameterIsNotNull(class_, "sslSocketClass");
        Intrinsics.checkParameterIsNotNull(class_2, "sslSocketFactoryClass");
        Intrinsics.checkParameterIsNotNull(class_3, "paramClass");
        super(class_);
        this.sslSocketFactoryClass = class_2;
        this.paramClass = class_3;
    }

    @Override
    public boolean matchesSocketFactory(SSLSocketFactory sSLSocketFactory) {
        Intrinsics.checkParameterIsNotNull(sSLSocketFactory, "sslSocketFactory");
        return this.sslSocketFactoryClass.isInstance(sSLSocketFactory);
    }

    @Override
    public X509TrustManager trustManager(SSLSocketFactory object) {
        Intrinsics.checkParameterIsNotNull(object, "sslSocketFactory");
        Object obj = Util.readFieldOrNull(object, this.paramClass, "sslParameters");
        if (obj == null) {
            Intrinsics.throwNpe();
        }
        if ((object = Util.readFieldOrNull(obj, X509TrustManager.class, "x509TrustManager")) == null) return Util.readFieldOrNull(obj, X509TrustManager.class, "trustManager");
        return object;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2={"Lokhttp3/internal/platform/android/StandardAndroidSocketAdapter$Companion;", "", "()V", "buildIfSupported", "Lokhttp3/internal/platform/android/SocketAdapter;", "packageName", "", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ SocketAdapter buildIfSupported$default(Companion companion, String string2, int n, Object object) {
            if ((n & 1) == 0) return companion.buildIfSupported(string2);
            string2 = "com.android.org.conscrypt";
            return companion.buildIfSupported(string2);
        }

        public final SocketAdapter buildIfSupported(String object) {
            Intrinsics.checkParameterIsNotNull(object, "packageName");
            try {
                Serializable serializable = new StringBuilder();
                serializable.append((String)object);
                serializable.append(".OpenSSLSocketImpl");
                serializable = Class.forName(serializable.toString());
                if (serializable == null) {
                    object = new TypeCastException("null cannot be cast to non-null type java.lang.Class<in javax.net.ssl.SSLSocket>");
                    throw object;
                }
                Serializable serializable2 = new StringBuilder();
                serializable2.append((String)object);
                serializable2.append(".OpenSSLSocketFactoryImpl");
                serializable2 = Class.forName(serializable2.toString());
                if (serializable2 != null) {
                    Serializable serializable3 = new StringBuilder();
                    serializable3.append((String)object);
                    serializable3.append(".SSLParametersImpl");
                    serializable3 = Class.forName(serializable3.toString());
                    Intrinsics.checkExpressionValueIsNotNull(serializable3, "paramsClass");
                    object = new StandardAndroidSocketAdapter((Class<? super SSLSocket>)serializable, (Class<? super SSLSocketFactory>)serializable2, (Class<?>)serializable3);
                    return (SocketAdapter)object;
                }
                object = new TypeCastException("null cannot be cast to non-null type java.lang.Class<in javax.net.ssl.SSLSocketFactory>");
                throw object;
            }
            catch (Exception exception) {
                Platform.Companion.get().log("unable to load android socket classes", 5, exception);
                object = null;
            }
            return (SocketAdapter)object;
        }
    }

}

