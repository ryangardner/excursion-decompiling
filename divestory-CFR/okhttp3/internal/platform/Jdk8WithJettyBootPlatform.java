/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.platform;

import java.io.Serializable;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLSocket;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Protocol;
import okhttp3.internal.platform.Platform;

@Metadata(bv={1, 0, 3}, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00162\u00020\u0001:\u0002\u0015\u0016B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007\u0012\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\u0007\u00a2\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J(\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0016J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00102\u0006\u0010\f\u001a\u00020\rH\u0016R\u0012\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u0006\u0012\u0002\b\u00030\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2={"Lokhttp3/internal/platform/Jdk8WithJettyBootPlatform;", "Lokhttp3/internal/platform/Platform;", "putMethod", "Ljava/lang/reflect/Method;", "getMethod", "removeMethod", "clientProviderClass", "Ljava/lang/Class;", "serverProviderClass", "(Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;Ljava/lang/Class;Ljava/lang/Class;)V", "afterHandshake", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "configureTlsExtensions", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "getSelectedProtocol", "AlpnProvider", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class Jdk8WithJettyBootPlatform
extends Platform {
    public static final Companion Companion = new Companion(null);
    private final Class<?> clientProviderClass;
    private final Method getMethod;
    private final Method putMethod;
    private final Method removeMethod;
    private final Class<?> serverProviderClass;

    public Jdk8WithJettyBootPlatform(Method method, Method method2, Method method3, Class<?> class_, Class<?> class_2) {
        Intrinsics.checkParameterIsNotNull(method, "putMethod");
        Intrinsics.checkParameterIsNotNull(method2, "getMethod");
        Intrinsics.checkParameterIsNotNull(method3, "removeMethod");
        Intrinsics.checkParameterIsNotNull(class_, "clientProviderClass");
        Intrinsics.checkParameterIsNotNull(class_2, "serverProviderClass");
        this.putMethod = method;
        this.getMethod = method2;
        this.removeMethod = method3;
        this.clientProviderClass = class_;
        this.serverProviderClass = class_2;
    }

    @Override
    public void afterHandshake(SSLSocket sSLSocket) {
        Intrinsics.checkParameterIsNotNull(sSLSocket, "sslSocket");
        try {
            this.removeMethod.invoke(null, sSLSocket);
            return;
        }
        catch (InvocationTargetException invocationTargetException) {
            throw (Throwable)((Object)new AssertionError("failed to remove ALPN", invocationTargetException));
        }
        catch (IllegalAccessException illegalAccessException) {
            throw (Throwable)((Object)new AssertionError("failed to remove ALPN", illegalAccessException));
        }
    }

    public void configureTlsExtensions(SSLSocket sSLSocket, String object, List<? extends Protocol> object2) {
        Intrinsics.checkParameterIsNotNull(sSLSocket, "sslSocket");
        Intrinsics.checkParameterIsNotNull(object2, "protocols");
        List<String> list = Platform.Companion.alpnProtocolNames((List<? extends Protocol>)object2);
        try {
            object = Platform.class.getClassLoader();
            object2 = this.clientProviderClass;
            Class<?> class_ = this.serverProviderClass;
            InvocationHandler invocationHandler = new AlpnProvider(list);
            invocationHandler = invocationHandler;
            object = Proxy.newProxyInstance((ClassLoader)object, new Class[]{object2, class_}, invocationHandler);
            this.putMethod.invoke(null, sSLSocket, object);
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            throw (Throwable)((Object)new AssertionError("failed to set ALPN", illegalAccessException));
        }
        catch (InvocationTargetException invocationTargetException) {
            throw (Throwable)((Object)new AssertionError("failed to set ALPN", invocationTargetException));
        }
    }

    @Override
    public String getSelectedProtocol(SSLSocket object) {
        Intrinsics.checkParameterIsNotNull(object, "sslSocket");
        try {
            Method method = this.getMethod;
            Object var3_5 = null;
            object = Proxy.getInvocationHandler(method.invoke(null, object));
            if (object == null) {
                object = new TypeCastException("null cannot be cast to non-null type okhttp3.internal.platform.Jdk8WithJettyBootPlatform.AlpnProvider");
                throw object;
            }
            if (!((AlpnProvider)(object = (AlpnProvider)object)).getUnsupported$okhttp() && ((AlpnProvider)object).getSelected$okhttp() == null) {
                Platform.log$default(this, "ALPN callback dropped: HTTP/2 is disabled. Is alpn-boot on the boot class path?", 0, null, 6, null);
                return null;
            }
            if (!((AlpnProvider)object).getUnsupported$okhttp()) return ((AlpnProvider)object).getSelected$okhttp();
            return var3_5;
        }
        catch (IllegalAccessException illegalAccessException) {
            throw (Throwable)((Object)new AssertionError("failed to get ALPN selected protocol", illegalAccessException));
        }
        catch (InvocationTargetException invocationTargetException) {
            throw (Throwable)((Object)new AssertionError("failed to get ALPN selected protocol", invocationTargetException));
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\b\u0000\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J0\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00152\u000e\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u0017H\u0096\u0002\u00a2\u0006\u0002\u0010\u0018R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0004X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0019"}, d2={"Lokhttp3/internal/platform/Jdk8WithJettyBootPlatform$AlpnProvider;", "Ljava/lang/reflect/InvocationHandler;", "protocols", "", "", "(Ljava/util/List;)V", "selected", "getSelected$okhttp", "()Ljava/lang/String;", "setSelected$okhttp", "(Ljava/lang/String;)V", "unsupported", "", "getUnsupported$okhttp", "()Z", "setUnsupported$okhttp", "(Z)V", "invoke", "", "proxy", "method", "Ljava/lang/reflect/Method;", "args", "", "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;", "okhttp"}, k=1, mv={1, 1, 16})
    private static final class AlpnProvider
    implements InvocationHandler {
        private final List<String> protocols;
        private String selected;
        private boolean unsupported;

        public AlpnProvider(List<String> list) {
            Intrinsics.checkParameterIsNotNull(list, "protocols");
            this.protocols = list;
        }

        public final String getSelected$okhttp() {
            return this.selected;
        }

        public final boolean getUnsupported$okhttp() {
            return this.unsupported;
        }

        @Override
        public Object invoke(Object object, Method object2, Object[] arrobject) throws Throwable {
            block8 : {
                int n;
                int n2;
                block10 : {
                    block9 : {
                        Intrinsics.checkParameterIsNotNull(object, "proxy");
                        Intrinsics.checkParameterIsNotNull(object2, "method");
                        if (arrobject == null) {
                            arrobject = new Object[]{};
                        }
                        object = ((Method)object2).getName();
                        Class<?> class_ = ((Method)object2).getReturnType();
                        if (Intrinsics.areEqual(object, "supports") && Intrinsics.areEqual(Boolean.TYPE, class_)) {
                            return true;
                        }
                        if (Intrinsics.areEqual(object, "unsupported") && Intrinsics.areEqual(Void.TYPE, class_)) {
                            this.unsupported = true;
                            return null;
                        }
                        if (Intrinsics.areEqual(object, "protocols") && (n2 = arrobject.length == 0 ? 1 : 0) != 0) {
                            return this.protocols;
                        }
                        if (!Intrinsics.areEqual(object, "selectProtocol") && !Intrinsics.areEqual(object, "select") || !Intrinsics.areEqual(String.class, class_) || arrobject.length != 1 || !(arrobject[0] instanceof List)) break block9;
                        object = arrobject[0];
                        if (object == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.List<*>");
                        n = (object = (List)object).size();
                        if (n < 0) break block8;
                        break block10;
                    }
                    if (!Intrinsics.areEqual(object, "protocolSelected")) {
                        if (!Intrinsics.areEqual(object, "selected")) return ((Method)object2).invoke(this, Arrays.copyOf(arrobject, arrobject.length));
                    }
                    if (arrobject.length != 1) return ((Method)object2).invoke(this, Arrays.copyOf(arrobject, arrobject.length));
                    object = arrobject[0];
                    if (object == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
                    this.selected = (String)object;
                    return null;
                }
                n2 = 0;
                while ((object2 = object.get(n2)) != null) {
                    if (this.protocols.contains(object2 = (String)object2)) {
                        this.selected = object2;
                        return object2;
                    }
                    if (n2 != n) {
                        ++n2;
                        continue;
                    }
                    break block8;
                }
                throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
            }
            this.selected = object = this.protocols.get(0);
            return object;
        }

        public final void setSelected$okhttp(String string2) {
            this.selected = string2;
        }

        public final void setUnsupported$okhttp(boolean bl) {
            this.unsupported = bl;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u00a8\u0006\u0005"}, d2={"Lokhttp3/internal/platform/Jdk8WithJettyBootPlatform$Companion;", "", "()V", "buildIfSupported", "Lokhttp3/internal/platform/Platform;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Platform buildIfSupported() {
            Object object = System.getProperty("java.specification.version", "unknown");
            try {
                Intrinsics.checkExpressionValueIsNotNull(object, "jvmVersion");
                int n = Integer.parseInt((String)object);
                if (n >= 9) {
                    return null;
                }
            }
            catch (NumberFormatException numberFormatException) {}
            try {
                object = Class.forName("org.eclipse.jetty.alpn.ALPN", true, null);
                Serializable serializable = new StringBuilder();
                serializable.append("org.eclipse.jetty.alpn.ALPN");
                serializable.append("$Provider");
                GenericDeclaration genericDeclaration = Class.forName(serializable.toString(), true, null);
                serializable = new StringBuilder();
                serializable.append("org.eclipse.jetty.alpn.ALPN");
                serializable.append("$ClientProvider");
                serializable = Class.forName(serializable.toString(), true, null);
                Serializable serializable2 = new StringBuilder();
                serializable2.append("org.eclipse.jetty.alpn.ALPN");
                serializable2.append("$ServerProvider");
                serializable2 = Class.forName(serializable2.toString(), true, null);
                genericDeclaration = ((Class)object).getMethod("put", new Class[]{SSLSocket.class, genericDeclaration});
                Method method = ((Class)object).getMethod("get", SSLSocket.class);
                object = ((Class)object).getMethod("remove", SSLSocket.class);
                Intrinsics.checkExpressionValueIsNotNull(genericDeclaration, "putMethod");
                Intrinsics.checkExpressionValueIsNotNull(method, "getMethod");
                Intrinsics.checkExpressionValueIsNotNull(object, "removeMethod");
                Intrinsics.checkExpressionValueIsNotNull(serializable, "clientProviderClass");
                Intrinsics.checkExpressionValueIsNotNull(serializable2, "serverProviderClass");
                Jdk8WithJettyBootPlatform jdk8WithJettyBootPlatform = new Jdk8WithJettyBootPlatform((Method)genericDeclaration, method, (Method)object, (Class<?>)serializable, (Class<?>)serializable2);
                return jdk8WithJettyBootPlatform;
            }
            catch (ClassNotFoundException | NoSuchMethodException reflectiveOperationException) {
                return null;
            }
        }
    }

}

