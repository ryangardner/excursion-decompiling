/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okhttp3.OkHttpClient$Builder$addInterceptor$
 *  okhttp3.OkHttpClient$Builder$addInterceptor$$inlined
 *  okhttp3.OkHttpClient$Builder$addInterceptor$$inlined$invoke
 *  okhttp3.OkHttpClient$Builder$addNetworkInterceptor$
 *  okhttp3.OkHttpClient$Builder$addNetworkInterceptor$$inlined
 *  okhttp3.OkHttpClient$Builder$addNetworkInterceptor$$inlined$invoke
 */
package okhttp3;

import java.net.Proxy;
import java.net.ProxySelector;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.CertificatePinner;
import okhttp3.ConnectionPool;
import okhttp3.ConnectionSpec;
import okhttp3.CookieJar;
import okhttp3.Dispatcher;
import okhttp3.Dns;
import okhttp3.EventListener;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient$Builder$addInterceptor$;
import okhttp3.OkHttpClient$Builder$addNetworkInterceptor$;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.internal.Util;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.connection.RealCall;
import okhttp3.internal.connection.RouteDatabase;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.proxy.NullProxySelector;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.OkHostnameVerifier;
import okhttp3.internal.ws.RealWebSocket;
import okhttp3.internal.ws.WebSocketExtensions;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000\u00ee\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0016\u0018\u0000 y2\u00020\u00012\u00020\u00022\u00020\u0003:\u0002xyB\u0007\b\u0016\u00a2\u0006\u0002\u0010\u0004B\u000f\b\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\r\u0010\b\u001a\u00020\tH\u0007\u00a2\u0006\u0002\bSJ\u000f\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007\u00a2\u0006\u0002\bTJ\r\u0010\u000e\u001a\u00020\u000fH\u0007\u00a2\u0006\u0002\bUJ\r\u0010\u0014\u001a\u00020\u0015H\u0007\u00a2\u0006\u0002\bVJ\r\u0010\u0017\u001a\u00020\u000fH\u0007\u00a2\u0006\u0002\bWJ\r\u0010\u0018\u001a\u00020\u0019H\u0007\u00a2\u0006\u0002\bXJ\u0013\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH\u0007\u00a2\u0006\u0002\bYJ\r\u0010\u001f\u001a\u00020 H\u0007\u00a2\u0006\u0002\bZJ\r\u0010\"\u001a\u00020#H\u0007\u00a2\u0006\u0002\b[J\r\u0010%\u001a\u00020&H\u0007\u00a2\u0006\u0002\b\\J\r\u0010(\u001a\u00020)H\u0007\u00a2\u0006\u0002\b]J\r\u0010+\u001a\u00020,H\u0007\u00a2\u0006\u0002\b^J\r\u0010.\u001a\u00020,H\u0007\u00a2\u0006\u0002\b_J\r\u0010/\u001a\u000200H\u0007\u00a2\u0006\u0002\b`J\u0013\u00102\u001a\b\u0012\u0004\u0012\u0002030\u001cH\u0007\u00a2\u0006\u0002\baJ\u0013\u00107\u001a\b\u0012\u0004\u0012\u0002030\u001cH\u0007\u00a2\u0006\u0002\bbJ\b\u0010c\u001a\u00020\u0006H\u0016J\u0010\u0010d\u001a\u00020e2\u0006\u0010f\u001a\u00020gH\u0016J\u0018\u0010h\u001a\u00020i2\u0006\u0010f\u001a\u00020g2\u0006\u0010j\u001a\u00020kH\u0016J\r\u00108\u001a\u00020\u000fH\u0007\u00a2\u0006\u0002\blJ\u0013\u00109\u001a\b\u0012\u0004\u0012\u00020:0\u001cH\u0007\u00a2\u0006\u0002\bmJ\u000f\u0010;\u001a\u0004\u0018\u00010<H\u0007\u00a2\u0006\u0002\bnJ\r\u0010>\u001a\u00020\tH\u0007\u00a2\u0006\u0002\boJ\r\u0010?\u001a\u00020@H\u0007\u00a2\u0006\u0002\bpJ\r\u0010B\u001a\u00020\u000fH\u0007\u00a2\u0006\u0002\bqJ\r\u0010C\u001a\u00020,H\u0007\u00a2\u0006\u0002\brJ\r\u0010H\u001a\u00020IH\u0007\u00a2\u0006\u0002\bsJ\r\u0010K\u001a\u00020LH\u0007\u00a2\u0006\u0002\btJ\b\u0010u\u001a\u00020vH\u0002J\r\u0010O\u001a\u00020\u000fH\u0007\u00a2\u0006\u0002\bwR\u0013\u0010\b\u001a\u00020\t8G\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\nR\u0015\u0010\u000b\u001a\u0004\u0018\u00010\f8G\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\rR\u0013\u0010\u000e\u001a\u00020\u000f8G\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0010R\u0015\u0010\u0011\u001a\u0004\u0018\u00010\u00128G\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0013R\u0013\u0010\u0014\u001a\u00020\u00158G\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0016R\u0013\u0010\u0017\u001a\u00020\u000f8G\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010R\u0013\u0010\u0018\u001a\u00020\u00198G\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u001aR\u0019\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c8G\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001eR\u0013\u0010\u001f\u001a\u00020 8G\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010!R\u0013\u0010\"\u001a\u00020#8G\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010$R\u0013\u0010%\u001a\u00020&8G\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010'R\u0013\u0010(\u001a\u00020)8G\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010*R\u0013\u0010+\u001a\u00020,8G\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010-R\u0013\u0010.\u001a\u00020,8G\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010-R\u0013\u0010/\u001a\u0002008G\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u00101R\u0019\u00102\u001a\b\u0012\u0004\u0012\u0002030\u001c8G\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010\u001eR\u0013\u00104\u001a\u0002058G\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u00106R\u0019\u00107\u001a\b\u0012\u0004\u0012\u0002030\u001c8G\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u0010\u001eR\u0013\u00108\u001a\u00020\u000f8G\u00a2\u0006\b\n\u0000\u001a\u0004\b8\u0010\u0010R\u0019\u00109\u001a\b\u0012\u0004\u0012\u00020:0\u001c8G\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u0010\u001eR\u0015\u0010;\u001a\u0004\u0018\u00010<8G\u00a2\u0006\b\n\u0000\u001a\u0004\b;\u0010=R\u0013\u0010>\u001a\u00020\t8G\u00a2\u0006\b\n\u0000\u001a\u0004\b>\u0010\nR\u0013\u0010?\u001a\u00020@8G\u00a2\u0006\b\n\u0000\u001a\u0004\b?\u0010AR\u0013\u0010B\u001a\u00020\u000f8G\u00a2\u0006\b\n\u0000\u001a\u0004\bB\u0010\u0010R\u0013\u0010C\u001a\u00020,8G\u00a2\u0006\b\n\u0000\u001a\u0004\bC\u0010-R\u0011\u0010D\u001a\u00020E\u00a2\u0006\b\n\u0000\u001a\u0004\bF\u0010GR\u0013\u0010H\u001a\u00020I8G\u00a2\u0006\b\n\u0000\u001a\u0004\bH\u0010JR\u0011\u0010K\u001a\u00020L8G\u00a2\u0006\u0006\u001a\u0004\bK\u0010MR\u0010\u0010N\u001a\u0004\u0018\u00010LX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0013\u0010O\u001a\u00020\u000f8G\u00a2\u0006\b\n\u0000\u001a\u0004\bO\u0010\u0010R\u0015\u0010P\u001a\u0004\u0018\u00010Q8G\u00a2\u0006\b\n\u0000\u001a\u0004\bP\u0010R\u00a8\u0006z"}, d2={"Lokhttp3/OkHttpClient;", "", "Lokhttp3/Call$Factory;", "Lokhttp3/WebSocket$Factory;", "()V", "builder", "Lokhttp3/OkHttpClient$Builder;", "(Lokhttp3/OkHttpClient$Builder;)V", "authenticator", "Lokhttp3/Authenticator;", "()Lokhttp3/Authenticator;", "cache", "Lokhttp3/Cache;", "()Lokhttp3/Cache;", "callTimeoutMillis", "", "()I", "certificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "()Lokhttp3/internal/tls/CertificateChainCleaner;", "certificatePinner", "Lokhttp3/CertificatePinner;", "()Lokhttp3/CertificatePinner;", "connectTimeoutMillis", "connectionPool", "Lokhttp3/ConnectionPool;", "()Lokhttp3/ConnectionPool;", "connectionSpecs", "", "Lokhttp3/ConnectionSpec;", "()Ljava/util/List;", "cookieJar", "Lokhttp3/CookieJar;", "()Lokhttp3/CookieJar;", "dispatcher", "Lokhttp3/Dispatcher;", "()Lokhttp3/Dispatcher;", "dns", "Lokhttp3/Dns;", "()Lokhttp3/Dns;", "eventListenerFactory", "Lokhttp3/EventListener$Factory;", "()Lokhttp3/EventListener$Factory;", "followRedirects", "", "()Z", "followSslRedirects", "hostnameVerifier", "Ljavax/net/ssl/HostnameVerifier;", "()Ljavax/net/ssl/HostnameVerifier;", "interceptors", "Lokhttp3/Interceptor;", "minWebSocketMessageToCompress", "", "()J", "networkInterceptors", "pingIntervalMillis", "protocols", "Lokhttp3/Protocol;", "proxy", "Ljava/net/Proxy;", "()Ljava/net/Proxy;", "proxyAuthenticator", "proxySelector", "Ljava/net/ProxySelector;", "()Ljava/net/ProxySelector;", "readTimeoutMillis", "retryOnConnectionFailure", "routeDatabase", "Lokhttp3/internal/connection/RouteDatabase;", "getRouteDatabase", "()Lokhttp3/internal/connection/RouteDatabase;", "socketFactory", "Ljavax/net/SocketFactory;", "()Ljavax/net/SocketFactory;", "sslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "()Ljavax/net/ssl/SSLSocketFactory;", "sslSocketFactoryOrNull", "writeTimeoutMillis", "x509TrustManager", "Ljavax/net/ssl/X509TrustManager;", "()Ljavax/net/ssl/X509TrustManager;", "-deprecated_authenticator", "-deprecated_cache", "-deprecated_callTimeoutMillis", "-deprecated_certificatePinner", "-deprecated_connectTimeoutMillis", "-deprecated_connectionPool", "-deprecated_connectionSpecs", "-deprecated_cookieJar", "-deprecated_dispatcher", "-deprecated_dns", "-deprecated_eventListenerFactory", "-deprecated_followRedirects", "-deprecated_followSslRedirects", "-deprecated_hostnameVerifier", "-deprecated_interceptors", "-deprecated_networkInterceptors", "newBuilder", "newCall", "Lokhttp3/Call;", "request", "Lokhttp3/Request;", "newWebSocket", "Lokhttp3/WebSocket;", "listener", "Lokhttp3/WebSocketListener;", "-deprecated_pingIntervalMillis", "-deprecated_protocols", "-deprecated_proxy", "-deprecated_proxyAuthenticator", "-deprecated_proxySelector", "-deprecated_readTimeoutMillis", "-deprecated_retryOnConnectionFailure", "-deprecated_socketFactory", "-deprecated_sslSocketFactory", "verifyClientState", "", "-deprecated_writeTimeoutMillis", "Builder", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public class OkHttpClient
implements Cloneable,
Call.Factory,
WebSocket.Factory {
    public static final Companion Companion = new Companion(null);
    private static final List<ConnectionSpec> DEFAULT_CONNECTION_SPECS;
    private static final List<Protocol> DEFAULT_PROTOCOLS;
    private final Authenticator authenticator;
    private final Cache cache;
    private final int callTimeoutMillis;
    private final CertificateChainCleaner certificateChainCleaner;
    private final CertificatePinner certificatePinner;
    private final int connectTimeoutMillis;
    private final ConnectionPool connectionPool;
    private final List<ConnectionSpec> connectionSpecs;
    private final CookieJar cookieJar;
    private final Dispatcher dispatcher;
    private final Dns dns;
    private final EventListener.Factory eventListenerFactory;
    private final boolean followRedirects;
    private final boolean followSslRedirects;
    private final HostnameVerifier hostnameVerifier;
    private final List<Interceptor> interceptors;
    private final long minWebSocketMessageToCompress;
    private final List<Interceptor> networkInterceptors;
    private final int pingIntervalMillis;
    private final List<Protocol> protocols;
    private final Proxy proxy;
    private final Authenticator proxyAuthenticator;
    private final ProxySelector proxySelector;
    private final int readTimeoutMillis;
    private final boolean retryOnConnectionFailure;
    private final RouteDatabase routeDatabase;
    private final SocketFactory socketFactory;
    private final SSLSocketFactory sslSocketFactoryOrNull;
    private final int writeTimeoutMillis;
    private final X509TrustManager x509TrustManager;

    static {
        DEFAULT_PROTOCOLS = Util.immutableListOf(new Protocol[]{Protocol.HTTP_2, Protocol.HTTP_1_1});
        DEFAULT_CONNECTION_SPECS = Util.immutableListOf(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT);
    }

    public OkHttpClient() {
        this(new Builder());
    }

    public OkHttpClient(Builder object) {
        Object object2;
        boolean bl;
        block18 : {
            Intrinsics.checkParameterIsNotNull(object, "builder");
            this.dispatcher = ((Builder)object).getDispatcher$okhttp();
            this.connectionPool = ((Builder)object).getConnectionPool$okhttp();
            this.interceptors = Util.toImmutableList(((Builder)object).getInterceptors$okhttp());
            this.networkInterceptors = Util.toImmutableList(((Builder)object).getNetworkInterceptors$okhttp());
            this.eventListenerFactory = ((Builder)object).getEventListenerFactory$okhttp();
            this.retryOnConnectionFailure = ((Builder)object).getRetryOnConnectionFailure$okhttp();
            this.authenticator = ((Builder)object).getAuthenticator$okhttp();
            this.followRedirects = ((Builder)object).getFollowRedirects$okhttp();
            this.followSslRedirects = ((Builder)object).getFollowSslRedirects$okhttp();
            this.cookieJar = ((Builder)object).getCookieJar$okhttp();
            this.cache = ((Builder)object).getCache$okhttp();
            this.dns = ((Builder)object).getDns$okhttp();
            this.proxy = ((Builder)object).getProxy$okhttp();
            if (((Builder)object).getProxy$okhttp() != null) {
                object2 = NullProxySelector.INSTANCE;
            } else {
                object2 = ((Builder)object).getProxySelector$okhttp();
                if (object2 == null) {
                    object2 = ProxySelector.getDefault();
                }
                if (object2 == null) {
                    object2 = NullProxySelector.INSTANCE;
                }
            }
            this.proxySelector = object2;
            this.proxyAuthenticator = ((Builder)object).getProxyAuthenticator$okhttp();
            this.socketFactory = ((Builder)object).getSocketFactory$okhttp();
            this.connectionSpecs = ((Builder)object).getConnectionSpecs$okhttp();
            this.protocols = ((Builder)object).getProtocols$okhttp();
            this.hostnameVerifier = ((Builder)object).getHostnameVerifier$okhttp();
            this.callTimeoutMillis = ((Builder)object).getCallTimeout$okhttp();
            this.connectTimeoutMillis = ((Builder)object).getConnectTimeout$okhttp();
            this.readTimeoutMillis = ((Builder)object).getReadTimeout$okhttp();
            this.writeTimeoutMillis = ((Builder)object).getWriteTimeout$okhttp();
            this.pingIntervalMillis = ((Builder)object).getPingInterval$okhttp();
            this.minWebSocketMessageToCompress = ((Builder)object).getMinWebSocketMessageToCompress$okhttp();
            object2 = ((Builder)object).getRouteDatabase$okhttp();
            if (object2 == null) {
                object2 = new RouteDatabase();
            }
            this.routeDatabase = object2;
            object2 = this.connectionSpecs;
            boolean bl2 = object2 instanceof Collection;
            boolean bl3 = true;
            if (bl2 && ((Collection)object2).isEmpty()) {
                bl = bl3;
            } else {
                object2 = object2.iterator();
                do {
                    bl = bl3;
                    if (!object2.hasNext()) break block18;
                } while (!((ConnectionSpec)object2.next()).isTls());
                bl = false;
            }
        }
        if (bl) {
            this.sslSocketFactoryOrNull = null;
            this.certificateChainCleaner = null;
            this.x509TrustManager = null;
            this.certificatePinner = CertificatePinner.DEFAULT;
        } else if (((Builder)object).getSslSocketFactoryOrNull$okhttp() != null) {
            this.sslSocketFactoryOrNull = ((Builder)object).getSslSocketFactoryOrNull$okhttp();
            object2 = ((Builder)object).getCertificateChainCleaner$okhttp();
            if (object2 == null) {
                Intrinsics.throwNpe();
            }
            this.certificateChainCleaner = object2;
            object2 = ((Builder)object).getX509TrustManagerOrNull$okhttp();
            if (object2 == null) {
                Intrinsics.throwNpe();
            }
            this.x509TrustManager = object2;
            object2 = ((Builder)object).getCertificatePinner$okhttp();
            object = this.certificateChainCleaner;
            if (object == null) {
                Intrinsics.throwNpe();
            }
            this.certificatePinner = ((CertificatePinner)object2).withCertificateChainCleaner$okhttp((CertificateChainCleaner)object);
        } else {
            this.x509TrustManager = Platform.Companion.get().platformTrustManager();
            object2 = Platform.Companion.get();
            Object object3 = this.x509TrustManager;
            if (object3 == null) {
                Intrinsics.throwNpe();
            }
            this.sslSocketFactoryOrNull = ((Platform)object2).newSslSocketFactory((X509TrustManager)object3);
            object3 = CertificateChainCleaner.Companion;
            object2 = this.x509TrustManager;
            if (object2 == null) {
                Intrinsics.throwNpe();
            }
            this.certificateChainCleaner = ((CertificateChainCleaner.Companion)object3).get((X509TrustManager)object2);
            object2 = ((Builder)object).getCertificatePinner$okhttp();
            object = this.certificateChainCleaner;
            if (object == null) {
                Intrinsics.throwNpe();
            }
            this.certificatePinner = ((CertificatePinner)object2).withCertificateChainCleaner$okhttp((CertificateChainCleaner)object);
        }
        this.verifyClientState();
    }

    private final void verifyClientState() {
        boolean bl;
        Iterable<Interceptor> iterable = this.interceptors;
        if (iterable == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.List<okhttp3.Interceptor?>");
        boolean bl2 = iterable.contains(null);
        boolean bl3 = true;
        if (!(bl2 ^ true)) {
            iterable = new StringBuilder();
            ((StringBuilder)((Object)iterable)).append("Null interceptor: ");
            ((StringBuilder)((Object)iterable)).append(this.interceptors);
            throw (Throwable)new IllegalStateException(((StringBuilder)((Object)iterable)).toString().toString());
        }
        iterable = this.networkInterceptors;
        if (iterable == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.List<okhttp3.Interceptor?>");
        if (!(iterable.contains(null) ^ true)) {
            iterable = new StringBuilder();
            ((StringBuilder)((Object)iterable)).append("Null network interceptor: ");
            ((StringBuilder)((Object)iterable)).append(this.networkInterceptors);
            throw (Throwable)new IllegalStateException(((StringBuilder)((Object)iterable)).toString().toString());
        }
        iterable = this.connectionSpecs;
        if (!(iterable instanceof Collection) || !((Collection)iterable).isEmpty()) {
            iterable = iterable.iterator();
            while (iterable.hasNext()) {
                if (!((ConnectionSpec)iterable.next()).isTls()) continue;
                bl = false;
                break;
            }
        } else {
            bl = true;
        }
        if (!bl) {
            if (this.sslSocketFactoryOrNull == null) throw (Throwable)new IllegalStateException("sslSocketFactory == null".toString());
            if (this.certificateChainCleaner == null) throw (Throwable)new IllegalStateException("certificateChainCleaner == null".toString());
            if (this.x509TrustManager == null) throw (Throwable)new IllegalStateException("x509TrustManager == null".toString());
            return;
        }
        bl = this.sslSocketFactoryOrNull == null;
        if (!bl) throw (Throwable)new IllegalStateException("Check failed.".toString());
        bl = this.certificateChainCleaner == null;
        if (!bl) throw (Throwable)new IllegalStateException("Check failed.".toString());
        bl = this.x509TrustManager == null ? bl3 : false;
        if (!bl) throw (Throwable)new IllegalStateException("Check failed.".toString());
        if (!Intrinsics.areEqual(this.certificatePinner, CertificatePinner.DEFAULT)) throw (Throwable)new IllegalStateException("Check failed.".toString());
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="authenticator", imports={}))
    public final Authenticator -deprecated_authenticator() {
        return this.authenticator;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="cache", imports={}))
    public final Cache -deprecated_cache() {
        return this.cache;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="callTimeoutMillis", imports={}))
    public final int -deprecated_callTimeoutMillis() {
        return this.callTimeoutMillis;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="certificatePinner", imports={}))
    public final CertificatePinner -deprecated_certificatePinner() {
        return this.certificatePinner;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="connectTimeoutMillis", imports={}))
    public final int -deprecated_connectTimeoutMillis() {
        return this.connectTimeoutMillis;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="connectionPool", imports={}))
    public final ConnectionPool -deprecated_connectionPool() {
        return this.connectionPool;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="connectionSpecs", imports={}))
    public final List<ConnectionSpec> -deprecated_connectionSpecs() {
        return this.connectionSpecs;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="cookieJar", imports={}))
    public final CookieJar -deprecated_cookieJar() {
        return this.cookieJar;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="dispatcher", imports={}))
    public final Dispatcher -deprecated_dispatcher() {
        return this.dispatcher;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="dns", imports={}))
    public final Dns -deprecated_dns() {
        return this.dns;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="eventListenerFactory", imports={}))
    public final EventListener.Factory -deprecated_eventListenerFactory() {
        return this.eventListenerFactory;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="followRedirects", imports={}))
    public final boolean -deprecated_followRedirects() {
        return this.followRedirects;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="followSslRedirects", imports={}))
    public final boolean -deprecated_followSslRedirects() {
        return this.followSslRedirects;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="hostnameVerifier", imports={}))
    public final HostnameVerifier -deprecated_hostnameVerifier() {
        return this.hostnameVerifier;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="interceptors", imports={}))
    public final List<Interceptor> -deprecated_interceptors() {
        return this.interceptors;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="networkInterceptors", imports={}))
    public final List<Interceptor> -deprecated_networkInterceptors() {
        return this.networkInterceptors;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="pingIntervalMillis", imports={}))
    public final int -deprecated_pingIntervalMillis() {
        return this.pingIntervalMillis;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="protocols", imports={}))
    public final List<Protocol> -deprecated_protocols() {
        return this.protocols;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="proxy", imports={}))
    public final Proxy -deprecated_proxy() {
        return this.proxy;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="proxyAuthenticator", imports={}))
    public final Authenticator -deprecated_proxyAuthenticator() {
        return this.proxyAuthenticator;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="proxySelector", imports={}))
    public final ProxySelector -deprecated_proxySelector() {
        return this.proxySelector;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="readTimeoutMillis", imports={}))
    public final int -deprecated_readTimeoutMillis() {
        return this.readTimeoutMillis;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="retryOnConnectionFailure", imports={}))
    public final boolean -deprecated_retryOnConnectionFailure() {
        return this.retryOnConnectionFailure;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="socketFactory", imports={}))
    public final SocketFactory -deprecated_socketFactory() {
        return this.socketFactory;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="sslSocketFactory", imports={}))
    public final SSLSocketFactory -deprecated_sslSocketFactory() {
        return this.sslSocketFactory();
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="writeTimeoutMillis", imports={}))
    public final int -deprecated_writeTimeoutMillis() {
        return this.writeTimeoutMillis;
    }

    public final Authenticator authenticator() {
        return this.authenticator;
    }

    public final Cache cache() {
        return this.cache;
    }

    public final int callTimeoutMillis() {
        return this.callTimeoutMillis;
    }

    public final CertificateChainCleaner certificateChainCleaner() {
        return this.certificateChainCleaner;
    }

    public final CertificatePinner certificatePinner() {
        return this.certificatePinner;
    }

    public Object clone() {
        return super.clone();
    }

    public final int connectTimeoutMillis() {
        return this.connectTimeoutMillis;
    }

    public final ConnectionPool connectionPool() {
        return this.connectionPool;
    }

    public final List<ConnectionSpec> connectionSpecs() {
        return this.connectionSpecs;
    }

    public final CookieJar cookieJar() {
        return this.cookieJar;
    }

    public final Dispatcher dispatcher() {
        return this.dispatcher;
    }

    public final Dns dns() {
        return this.dns;
    }

    public final EventListener.Factory eventListenerFactory() {
        return this.eventListenerFactory;
    }

    public final boolean followRedirects() {
        return this.followRedirects;
    }

    public final boolean followSslRedirects() {
        return this.followSslRedirects;
    }

    public final RouteDatabase getRouteDatabase() {
        return this.routeDatabase;
    }

    public final HostnameVerifier hostnameVerifier() {
        return this.hostnameVerifier;
    }

    public final List<Interceptor> interceptors() {
        return this.interceptors;
    }

    public final long minWebSocketMessageToCompress() {
        return this.minWebSocketMessageToCompress;
    }

    public final List<Interceptor> networkInterceptors() {
        return this.networkInterceptors;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    @Override
    public Call newCall(Request request) {
        Intrinsics.checkParameterIsNotNull(request, "request");
        return new RealCall(this, request, false);
    }

    @Override
    public WebSocket newWebSocket(Request object, WebSocketListener webSocketListener) {
        Intrinsics.checkParameterIsNotNull(object, "request");
        Intrinsics.checkParameterIsNotNull(webSocketListener, "listener");
        object = new RealWebSocket(TaskRunner.INSTANCE, (Request)object, webSocketListener, new Random(), this.pingIntervalMillis, null, this.minWebSocketMessageToCompress);
        ((RealWebSocket)object).connect(this);
        return (WebSocket)object;
    }

    public final int pingIntervalMillis() {
        return this.pingIntervalMillis;
    }

    public final List<Protocol> protocols() {
        return this.protocols;
    }

    public final Proxy proxy() {
        return this.proxy;
    }

    public final Authenticator proxyAuthenticator() {
        return this.proxyAuthenticator;
    }

    public final ProxySelector proxySelector() {
        return this.proxySelector;
    }

    public final int readTimeoutMillis() {
        return this.readTimeoutMillis;
    }

    public final boolean retryOnConnectionFailure() {
        return this.retryOnConnectionFailure;
    }

    public final SocketFactory socketFactory() {
        return this.socketFactory;
    }

    public final SSLSocketFactory sslSocketFactory() {
        SSLSocketFactory sSLSocketFactory = this.sslSocketFactoryOrNull;
        if (sSLSocketFactory == null) throw (Throwable)new IllegalStateException("CLEARTEXT-only client");
        return sSLSocketFactory;
    }

    public final int writeTimeoutMillis() {
        return this.writeTimeoutMillis;
    }

    public final X509TrustManager x509TrustManager() {
        return this.x509TrustManager;
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    @Metadata(bv={1, 0, 3}, d1={"\u0000\u00f8\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u000f\b\u0010\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004B\u0005\u00a2\u0006\u0002\u0010\u0005J<\u0010\u009e\u0001\u001a\u00020\u00002*\b\u0004\u0010\u009f\u0001\u001a#\u0012\u0017\u0012\u00150\u00a1\u0001\u00a2\u0006\u000f\b\u00a2\u0001\u0012\n\b\u00a3\u0001\u0012\u0005\b\b(\u00a4\u0001\u0012\u0005\u0012\u00030\u00a5\u00010\u00a0\u0001H\u0087\b\u00a2\u0006\u0003\b\u00a6\u0001J\u0010\u0010\u009e\u0001\u001a\u00020\u00002\u0007\u0010\u00a7\u0001\u001a\u00020]J<\u0010\u00a8\u0001\u001a\u00020\u00002*\b\u0004\u0010\u009f\u0001\u001a#\u0012\u0017\u0012\u00150\u00a1\u0001\u00a2\u0006\u000f\b\u00a2\u0001\u0012\n\b\u00a3\u0001\u0012\u0005\b\b(\u00a4\u0001\u0012\u0005\u0012\u00030\u00a5\u00010\u00a0\u0001H\u0087\b\u00a2\u0006\u0003\b\u00a9\u0001J\u0010\u0010\u00a8\u0001\u001a\u00020\u00002\u0007\u0010\u00a7\u0001\u001a\u00020]J\u000e\u0010\u0006\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0007J\u0007\u0010\u00aa\u0001\u001a\u00020\u0003J\u0010\u0010\f\u001a\u00020\u00002\b\u0010\f\u001a\u0004\u0018\u00010\rJ\u0012\u0010\u0012\u001a\u00020\u00002\b\u0010\u00ab\u0001\u001a\u00030\u00ac\u0001H\u0007J\u0019\u0010\u0012\u001a\u00020\u00002\u0007\u0010\u00ad\u0001\u001a\u00020`2\b\u0010\u00ae\u0001\u001a\u00030\u00af\u0001J\u000e\u0010\u001e\u001a\u00020\u00002\u0006\u0010\u001e\u001a\u00020\u001fJ\u0012\u0010$\u001a\u00020\u00002\b\u0010\u00ab\u0001\u001a\u00030\u00ac\u0001H\u0007J\u0019\u0010$\u001a\u00020\u00002\u0007\u0010\u00ad\u0001\u001a\u00020`2\b\u0010\u00ae\u0001\u001a\u00030\u00af\u0001J\u000e\u0010'\u001a\u00020\u00002\u0006\u0010'\u001a\u00020(J\u0014\u0010-\u001a\u00020\u00002\f\u0010-\u001a\b\u0012\u0004\u0012\u00020/0.J\u000e\u00104\u001a\u00020\u00002\u0006\u00104\u001a\u000205J\u000e\u0010:\u001a\u00020\u00002\u0006\u0010:\u001a\u00020;J\u000e\u0010@\u001a\u00020\u00002\u0006\u0010@\u001a\u00020AJ\u0011\u0010\u00b0\u0001\u001a\u00020\u00002\b\u0010\u00b0\u0001\u001a\u00030\u00b1\u0001J\u000e\u0010F\u001a\u00020\u00002\u0006\u0010F\u001a\u00020GJ\u000e\u0010L\u001a\u00020\u00002\u0006\u0010L\u001a\u00020MJ\u000f\u0010R\u001a\u00020\u00002\u0007\u0010\u00b2\u0001\u001a\u00020MJ\u000e\u0010U\u001a\u00020\u00002\u0006\u0010U\u001a\u00020VJ\f\u0010[\u001a\b\u0012\u0004\u0012\u00020]0\\J\u000f\u0010_\u001a\u00020\u00002\u0007\u0010\u00b3\u0001\u001a\u00020`J\f\u0010e\u001a\b\u0012\u0004\u0012\u00020]0\\J\u0012\u0010g\u001a\u00020\u00002\b\u0010\u00ab\u0001\u001a\u00030\u00ac\u0001H\u0007J\u0019\u0010g\u001a\u00020\u00002\u0007\u0010\u00b4\u0001\u001a\u00020`2\b\u0010\u00ae\u0001\u001a\u00030\u00af\u0001J\u0014\u0010j\u001a\u00020\u00002\f\u0010j\u001a\b\u0012\u0004\u0012\u00020k0.J\u0010\u0010n\u001a\u00020\u00002\b\u0010n\u001a\u0004\u0018\u00010oJ\u000e\u0010t\u001a\u00020\u00002\u0006\u0010t\u001a\u00020\u0007J\u000e\u0010w\u001a\u00020\u00002\u0006\u0010w\u001a\u00020xJ\u0012\u0010}\u001a\u00020\u00002\b\u0010\u00ab\u0001\u001a\u00030\u00ac\u0001H\u0007J\u0019\u0010}\u001a\u00020\u00002\u0007\u0010\u00ad\u0001\u001a\u00020`2\b\u0010\u00ae\u0001\u001a\u00030\u00af\u0001J\u0010\u0010\u0001\u001a\u00020\u00002\u0007\u0010\u0001\u001a\u00020MJ\u0011\u0010\u0089\u0001\u001a\u00020\u00002\b\u0010\u0089\u0001\u001a\u00030\u008a\u0001J\u0013\u0010\u00b5\u0001\u001a\u00020\u00002\b\u0010\u00b5\u0001\u001a\u00030\u0090\u0001H\u0007J\u001b\u0010\u00b5\u0001\u001a\u00020\u00002\b\u0010\u00b5\u0001\u001a\u00030\u0090\u00012\b\u0010\u00b6\u0001\u001a\u00030\u0099\u0001J\u0013\u0010\u0095\u0001\u001a\u00020\u00002\b\u0010\u00ab\u0001\u001a\u00030\u00ac\u0001H\u0007J\u001a\u0010\u0095\u0001\u001a\u00020\u00002\u0007\u0010\u00ad\u0001\u001a\u00020`2\b\u0010\u00ae\u0001\u001a\u00030\u00af\u0001R\u001a\u0010\u0006\u001a\u00020\u0007X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\u001fX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001a\u0010$\u001a\u00020\u0013X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0015\"\u0004\b&\u0010\u0017R\u001a\u0010'\u001a\u00020(X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R \u0010-\u001a\b\u0012\u0004\u0012\u00020/0.X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u001a\u00104\u001a\u000205X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u001a\u0010:\u001a\u00020;X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R\u001a\u0010@\u001a\u00020AX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u001a\u0010F\u001a\u00020GX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bH\u0010I\"\u0004\bJ\u0010KR\u001a\u0010L\u001a\u00020MX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bN\u0010O\"\u0004\bP\u0010QR\u001a\u0010R\u001a\u00020MX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bS\u0010O\"\u0004\bT\u0010QR\u001a\u0010U\u001a\u00020VX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bW\u0010X\"\u0004\bY\u0010ZR\u001a\u0010[\u001a\b\u0012\u0004\u0012\u00020]0\\X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b^\u00101R\u001a\u0010_\u001a\u00020`X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\ba\u0010b\"\u0004\bc\u0010dR\u001a\u0010e\u001a\b\u0012\u0004\u0012\u00020]0\\X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bf\u00101R\u001a\u0010g\u001a\u00020\u0013X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bh\u0010\u0015\"\u0004\bi\u0010\u0017R \u0010j\u001a\b\u0012\u0004\u0012\u00020k0.X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bl\u00101\"\u0004\bm\u00103R\u001c\u0010n\u001a\u0004\u0018\u00010oX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bp\u0010q\"\u0004\br\u0010sR\u001a\u0010t\u001a\u00020\u0007X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bu\u0010\t\"\u0004\bv\u0010\u000bR\u001c\u0010w\u001a\u0004\u0018\u00010xX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\by\u0010z\"\u0004\b{\u0010|R\u001a\u0010}\u001a\u00020\u0013X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b~\u0010\u0015\"\u0004\b\u0010\u0017R\u001d\u0010\u0001\u001a\u00020MX\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0081\u0001\u0010O\"\u0005\b\u0082\u0001\u0010QR\"\u0010\u0083\u0001\u001a\u0005\u0018\u00010\u0084\u0001X\u000e\u00a2\u0006\u0012\n\u0000\u001a\u0006\b\u0085\u0001\u0010\u0086\u0001\"\u0006\b\u0087\u0001\u0010\u0088\u0001R \u0010\u0089\u0001\u001a\u00030\u008a\u0001X\u000e\u00a2\u0006\u0012\n\u0000\u001a\u0006\b\u008b\u0001\u0010\u008c\u0001\"\u0006\b\u008d\u0001\u0010\u008e\u0001R\"\u0010\u008f\u0001\u001a\u0005\u0018\u00010\u0090\u0001X\u000e\u00a2\u0006\u0012\n\u0000\u001a\u0006\b\u0091\u0001\u0010\u0092\u0001\"\u0006\b\u0093\u0001\u0010\u0094\u0001R\u001d\u0010\u0095\u0001\u001a\u00020\u0013X\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0096\u0001\u0010\u0015\"\u0005\b\u0097\u0001\u0010\u0017R\"\u0010\u0098\u0001\u001a\u0005\u0018\u00010\u0099\u0001X\u000e\u00a2\u0006\u0012\n\u0000\u001a\u0006\b\u009a\u0001\u0010\u009b\u0001\"\u0006\b\u009c\u0001\u0010\u009d\u0001\u00a8\u0006\u00b7\u0001"}, d2={"Lokhttp3/OkHttpClient$Builder;", "", "okHttpClient", "Lokhttp3/OkHttpClient;", "(Lokhttp3/OkHttpClient;)V", "()V", "authenticator", "Lokhttp3/Authenticator;", "getAuthenticator$okhttp", "()Lokhttp3/Authenticator;", "setAuthenticator$okhttp", "(Lokhttp3/Authenticator;)V", "cache", "Lokhttp3/Cache;", "getCache$okhttp", "()Lokhttp3/Cache;", "setCache$okhttp", "(Lokhttp3/Cache;)V", "callTimeout", "", "getCallTimeout$okhttp", "()I", "setCallTimeout$okhttp", "(I)V", "certificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "getCertificateChainCleaner$okhttp", "()Lokhttp3/internal/tls/CertificateChainCleaner;", "setCertificateChainCleaner$okhttp", "(Lokhttp3/internal/tls/CertificateChainCleaner;)V", "certificatePinner", "Lokhttp3/CertificatePinner;", "getCertificatePinner$okhttp", "()Lokhttp3/CertificatePinner;", "setCertificatePinner$okhttp", "(Lokhttp3/CertificatePinner;)V", "connectTimeout", "getConnectTimeout$okhttp", "setConnectTimeout$okhttp", "connectionPool", "Lokhttp3/ConnectionPool;", "getConnectionPool$okhttp", "()Lokhttp3/ConnectionPool;", "setConnectionPool$okhttp", "(Lokhttp3/ConnectionPool;)V", "connectionSpecs", "", "Lokhttp3/ConnectionSpec;", "getConnectionSpecs$okhttp", "()Ljava/util/List;", "setConnectionSpecs$okhttp", "(Ljava/util/List;)V", "cookieJar", "Lokhttp3/CookieJar;", "getCookieJar$okhttp", "()Lokhttp3/CookieJar;", "setCookieJar$okhttp", "(Lokhttp3/CookieJar;)V", "dispatcher", "Lokhttp3/Dispatcher;", "getDispatcher$okhttp", "()Lokhttp3/Dispatcher;", "setDispatcher$okhttp", "(Lokhttp3/Dispatcher;)V", "dns", "Lokhttp3/Dns;", "getDns$okhttp", "()Lokhttp3/Dns;", "setDns$okhttp", "(Lokhttp3/Dns;)V", "eventListenerFactory", "Lokhttp3/EventListener$Factory;", "getEventListenerFactory$okhttp", "()Lokhttp3/EventListener$Factory;", "setEventListenerFactory$okhttp", "(Lokhttp3/EventListener$Factory;)V", "followRedirects", "", "getFollowRedirects$okhttp", "()Z", "setFollowRedirects$okhttp", "(Z)V", "followSslRedirects", "getFollowSslRedirects$okhttp", "setFollowSslRedirects$okhttp", "hostnameVerifier", "Ljavax/net/ssl/HostnameVerifier;", "getHostnameVerifier$okhttp", "()Ljavax/net/ssl/HostnameVerifier;", "setHostnameVerifier$okhttp", "(Ljavax/net/ssl/HostnameVerifier;)V", "interceptors", "", "Lokhttp3/Interceptor;", "getInterceptors$okhttp", "minWebSocketMessageToCompress", "", "getMinWebSocketMessageToCompress$okhttp", "()J", "setMinWebSocketMessageToCompress$okhttp", "(J)V", "networkInterceptors", "getNetworkInterceptors$okhttp", "pingInterval", "getPingInterval$okhttp", "setPingInterval$okhttp", "protocols", "Lokhttp3/Protocol;", "getProtocols$okhttp", "setProtocols$okhttp", "proxy", "Ljava/net/Proxy;", "getProxy$okhttp", "()Ljava/net/Proxy;", "setProxy$okhttp", "(Ljava/net/Proxy;)V", "proxyAuthenticator", "getProxyAuthenticator$okhttp", "setProxyAuthenticator$okhttp", "proxySelector", "Ljava/net/ProxySelector;", "getProxySelector$okhttp", "()Ljava/net/ProxySelector;", "setProxySelector$okhttp", "(Ljava/net/ProxySelector;)V", "readTimeout", "getReadTimeout$okhttp", "setReadTimeout$okhttp", "retryOnConnectionFailure", "getRetryOnConnectionFailure$okhttp", "setRetryOnConnectionFailure$okhttp", "routeDatabase", "Lokhttp3/internal/connection/RouteDatabase;", "getRouteDatabase$okhttp", "()Lokhttp3/internal/connection/RouteDatabase;", "setRouteDatabase$okhttp", "(Lokhttp3/internal/connection/RouteDatabase;)V", "socketFactory", "Ljavax/net/SocketFactory;", "getSocketFactory$okhttp", "()Ljavax/net/SocketFactory;", "setSocketFactory$okhttp", "(Ljavax/net/SocketFactory;)V", "sslSocketFactoryOrNull", "Ljavax/net/ssl/SSLSocketFactory;", "getSslSocketFactoryOrNull$okhttp", "()Ljavax/net/ssl/SSLSocketFactory;", "setSslSocketFactoryOrNull$okhttp", "(Ljavax/net/ssl/SSLSocketFactory;)V", "writeTimeout", "getWriteTimeout$okhttp", "setWriteTimeout$okhttp", "x509TrustManagerOrNull", "Ljavax/net/ssl/X509TrustManager;", "getX509TrustManagerOrNull$okhttp", "()Ljavax/net/ssl/X509TrustManager;", "setX509TrustManagerOrNull$okhttp", "(Ljavax/net/ssl/X509TrustManager;)V", "addInterceptor", "block", "Lkotlin/Function1;", "Lokhttp3/Interceptor$Chain;", "Lkotlin/ParameterName;", "name", "chain", "Lokhttp3/Response;", "-addInterceptor", "interceptor", "addNetworkInterceptor", "-addNetworkInterceptor", "build", "duration", "Ljava/time/Duration;", "timeout", "unit", "Ljava/util/concurrent/TimeUnit;", "eventListener", "Lokhttp3/EventListener;", "followProtocolRedirects", "bytes", "interval", "sslSocketFactory", "trustManager", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Builder {
        private Authenticator authenticator;
        private Cache cache;
        private int callTimeout;
        private CertificateChainCleaner certificateChainCleaner;
        private CertificatePinner certificatePinner;
        private int connectTimeout;
        private ConnectionPool connectionPool;
        private List<ConnectionSpec> connectionSpecs;
        private CookieJar cookieJar;
        private Dispatcher dispatcher;
        private Dns dns;
        private EventListener.Factory eventListenerFactory;
        private boolean followRedirects;
        private boolean followSslRedirects;
        private HostnameVerifier hostnameVerifier;
        private final List<Interceptor> interceptors;
        private long minWebSocketMessageToCompress;
        private final List<Interceptor> networkInterceptors;
        private int pingInterval;
        private List<? extends Protocol> protocols;
        private Proxy proxy;
        private Authenticator proxyAuthenticator;
        private ProxySelector proxySelector;
        private int readTimeout;
        private boolean retryOnConnectionFailure;
        private RouteDatabase routeDatabase;
        private SocketFactory socketFactory;
        private SSLSocketFactory sslSocketFactoryOrNull;
        private int writeTimeout;
        private X509TrustManager x509TrustManagerOrNull;

        public Builder() {
            this.dispatcher = new Dispatcher();
            this.connectionPool = new ConnectionPool();
            this.interceptors = new ArrayList();
            this.networkInterceptors = new ArrayList();
            this.eventListenerFactory = Util.asFactory(EventListener.NONE);
            this.retryOnConnectionFailure = true;
            this.authenticator = Authenticator.NONE;
            this.followRedirects = true;
            this.followSslRedirects = true;
            this.cookieJar = CookieJar.NO_COOKIES;
            this.dns = Dns.SYSTEM;
            this.proxyAuthenticator = Authenticator.NONE;
            SocketFactory socketFactory = SocketFactory.getDefault();
            Intrinsics.checkExpressionValueIsNotNull(socketFactory, "SocketFactory.getDefault()");
            this.socketFactory = socketFactory;
            this.connectionSpecs = Companion.getDEFAULT_CONNECTION_SPECS$okhttp();
            this.protocols = Companion.getDEFAULT_PROTOCOLS$okhttp();
            this.hostnameVerifier = OkHostnameVerifier.INSTANCE;
            this.certificatePinner = CertificatePinner.DEFAULT;
            this.connectTimeout = 10000;
            this.readTimeout = 10000;
            this.writeTimeout = 10000;
            this.minWebSocketMessageToCompress = 1024L;
        }

        public Builder(OkHttpClient okHttpClient) {
            Intrinsics.checkParameterIsNotNull(okHttpClient, "okHttpClient");
            this();
            this.dispatcher = okHttpClient.dispatcher();
            this.connectionPool = okHttpClient.connectionPool();
            CollectionsKt.addAll((Collection)this.interceptors, (Iterable)okHttpClient.interceptors());
            CollectionsKt.addAll((Collection)this.networkInterceptors, (Iterable)okHttpClient.networkInterceptors());
            this.eventListenerFactory = okHttpClient.eventListenerFactory();
            this.retryOnConnectionFailure = okHttpClient.retryOnConnectionFailure();
            this.authenticator = okHttpClient.authenticator();
            this.followRedirects = okHttpClient.followRedirects();
            this.followSslRedirects = okHttpClient.followSslRedirects();
            this.cookieJar = okHttpClient.cookieJar();
            this.cache = okHttpClient.cache();
            this.dns = okHttpClient.dns();
            this.proxy = okHttpClient.proxy();
            this.proxySelector = okHttpClient.proxySelector();
            this.proxyAuthenticator = okHttpClient.proxyAuthenticator();
            this.socketFactory = okHttpClient.socketFactory();
            this.sslSocketFactoryOrNull = okHttpClient.sslSocketFactoryOrNull;
            this.x509TrustManagerOrNull = okHttpClient.x509TrustManager();
            this.connectionSpecs = okHttpClient.connectionSpecs();
            this.protocols = okHttpClient.protocols();
            this.hostnameVerifier = okHttpClient.hostnameVerifier();
            this.certificatePinner = okHttpClient.certificatePinner();
            this.certificateChainCleaner = okHttpClient.certificateChainCleaner();
            this.callTimeout = okHttpClient.callTimeoutMillis();
            this.connectTimeout = okHttpClient.connectTimeoutMillis();
            this.readTimeout = okHttpClient.readTimeoutMillis();
            this.writeTimeout = okHttpClient.writeTimeoutMillis();
            this.pingInterval = okHttpClient.pingIntervalMillis();
            this.minWebSocketMessageToCompress = okHttpClient.minWebSocketMessageToCompress();
            this.routeDatabase = okHttpClient.getRouteDatabase();
        }

        public final Builder -addInterceptor(Function1<? super Interceptor.Chain, Response> function1) {
            Intrinsics.checkParameterIsNotNull(function1, "block");
            Interceptor.Companion companion = Interceptor.Companion;
            return this.addInterceptor(new Interceptor(function1){
                final /* synthetic */ Function1 $block$inlined;
                {
                    this.$block$inlined = function1;
                }

                public Response intercept(Interceptor.Chain chain) {
                    Intrinsics.checkParameterIsNotNull(chain, "chain");
                    return (Response)this.$block$inlined.invoke(chain);
                }
            });
        }

        public final Builder -addNetworkInterceptor(Function1<? super Interceptor.Chain, Response> function1) {
            Intrinsics.checkParameterIsNotNull(function1, "block");
            Interceptor.Companion companion = Interceptor.Companion;
            return this.addNetworkInterceptor(new Interceptor(function1){
                final /* synthetic */ Function1 $block$inlined;
                {
                    this.$block$inlined = function1;
                }

                public Response intercept(Interceptor.Chain chain) {
                    Intrinsics.checkParameterIsNotNull(chain, "chain");
                    return (Response)this.$block$inlined.invoke(chain);
                }
            });
        }

        public final Builder addInterceptor(Interceptor interceptor) {
            Intrinsics.checkParameterIsNotNull(interceptor, "interceptor");
            Builder builder = this;
            ((Collection)builder.interceptors).add(interceptor);
            return builder;
        }

        public final Builder addNetworkInterceptor(Interceptor interceptor) {
            Intrinsics.checkParameterIsNotNull(interceptor, "interceptor");
            Builder builder = this;
            ((Collection)builder.networkInterceptors).add(interceptor);
            return builder;
        }

        public final Builder authenticator(Authenticator authenticator) {
            Intrinsics.checkParameterIsNotNull(authenticator, "authenticator");
            Builder builder = this;
            builder.authenticator = authenticator;
            return builder;
        }

        public final OkHttpClient build() {
            return new OkHttpClient(this);
        }

        public final Builder cache(Cache cache) {
            Builder builder = this;
            builder.cache = cache;
            return builder;
        }

        public final Builder callTimeout(long l, TimeUnit timeUnit) {
            Intrinsics.checkParameterIsNotNull((Object)timeUnit, "unit");
            Builder builder = this;
            builder.callTimeout = Util.checkDuration("timeout", l, timeUnit);
            return builder;
        }

        public final Builder callTimeout(Duration duration) {
            Intrinsics.checkParameterIsNotNull(duration, "duration");
            Builder builder = this;
            builder.callTimeout(duration.toMillis(), TimeUnit.MILLISECONDS);
            return builder;
        }

        public final Builder certificatePinner(CertificatePinner certificatePinner) {
            Intrinsics.checkParameterIsNotNull(certificatePinner, "certificatePinner");
            Builder builder = this;
            if (Intrinsics.areEqual(certificatePinner, builder.certificatePinner) ^ true) {
                builder.routeDatabase = null;
            }
            builder.certificatePinner = certificatePinner;
            return builder;
        }

        public final Builder connectTimeout(long l, TimeUnit timeUnit) {
            Intrinsics.checkParameterIsNotNull((Object)timeUnit, "unit");
            Builder builder = this;
            builder.connectTimeout = Util.checkDuration("timeout", l, timeUnit);
            return builder;
        }

        public final Builder connectTimeout(Duration duration) {
            Intrinsics.checkParameterIsNotNull(duration, "duration");
            Builder builder = this;
            builder.connectTimeout(duration.toMillis(), TimeUnit.MILLISECONDS);
            return builder;
        }

        public final Builder connectionPool(ConnectionPool connectionPool) {
            Intrinsics.checkParameterIsNotNull(connectionPool, "connectionPool");
            Builder builder = this;
            builder.connectionPool = connectionPool;
            return builder;
        }

        public final Builder connectionSpecs(List<ConnectionSpec> list) {
            Intrinsics.checkParameterIsNotNull(list, "connectionSpecs");
            Builder builder = this;
            if (Intrinsics.areEqual(list, builder.connectionSpecs) ^ true) {
                builder.routeDatabase = null;
            }
            builder.connectionSpecs = Util.toImmutableList(list);
            return builder;
        }

        public final Builder cookieJar(CookieJar cookieJar) {
            Intrinsics.checkParameterIsNotNull(cookieJar, "cookieJar");
            Builder builder = this;
            builder.cookieJar = cookieJar;
            return builder;
        }

        public final Builder dispatcher(Dispatcher dispatcher) {
            Intrinsics.checkParameterIsNotNull(dispatcher, "dispatcher");
            Builder builder = this;
            builder.dispatcher = dispatcher;
            return builder;
        }

        public final Builder dns(Dns dns) {
            Intrinsics.checkParameterIsNotNull(dns, "dns");
            Builder builder = this;
            if (Intrinsics.areEqual(dns, builder.dns) ^ true) {
                builder.routeDatabase = null;
            }
            builder.dns = dns;
            return builder;
        }

        public final Builder eventListener(EventListener eventListener) {
            Intrinsics.checkParameterIsNotNull(eventListener, "eventListener");
            Builder builder = this;
            builder.eventListenerFactory = Util.asFactory(eventListener);
            return builder;
        }

        public final Builder eventListenerFactory(EventListener.Factory factory2) {
            Intrinsics.checkParameterIsNotNull(factory2, "eventListenerFactory");
            Builder builder = this;
            builder.eventListenerFactory = factory2;
            return builder;
        }

        public final Builder followRedirects(boolean bl) {
            Builder builder = this;
            builder.followRedirects = bl;
            return builder;
        }

        public final Builder followSslRedirects(boolean bl) {
            Builder builder = this;
            builder.followSslRedirects = bl;
            return builder;
        }

        public final Authenticator getAuthenticator$okhttp() {
            return this.authenticator;
        }

        public final Cache getCache$okhttp() {
            return this.cache;
        }

        public final int getCallTimeout$okhttp() {
            return this.callTimeout;
        }

        public final CertificateChainCleaner getCertificateChainCleaner$okhttp() {
            return this.certificateChainCleaner;
        }

        public final CertificatePinner getCertificatePinner$okhttp() {
            return this.certificatePinner;
        }

        public final int getConnectTimeout$okhttp() {
            return this.connectTimeout;
        }

        public final ConnectionPool getConnectionPool$okhttp() {
            return this.connectionPool;
        }

        public final List<ConnectionSpec> getConnectionSpecs$okhttp() {
            return this.connectionSpecs;
        }

        public final CookieJar getCookieJar$okhttp() {
            return this.cookieJar;
        }

        public final Dispatcher getDispatcher$okhttp() {
            return this.dispatcher;
        }

        public final Dns getDns$okhttp() {
            return this.dns;
        }

        public final EventListener.Factory getEventListenerFactory$okhttp() {
            return this.eventListenerFactory;
        }

        public final boolean getFollowRedirects$okhttp() {
            return this.followRedirects;
        }

        public final boolean getFollowSslRedirects$okhttp() {
            return this.followSslRedirects;
        }

        public final HostnameVerifier getHostnameVerifier$okhttp() {
            return this.hostnameVerifier;
        }

        public final List<Interceptor> getInterceptors$okhttp() {
            return this.interceptors;
        }

        public final long getMinWebSocketMessageToCompress$okhttp() {
            return this.minWebSocketMessageToCompress;
        }

        public final List<Interceptor> getNetworkInterceptors$okhttp() {
            return this.networkInterceptors;
        }

        public final int getPingInterval$okhttp() {
            return this.pingInterval;
        }

        public final List<Protocol> getProtocols$okhttp() {
            return this.protocols;
        }

        public final Proxy getProxy$okhttp() {
            return this.proxy;
        }

        public final Authenticator getProxyAuthenticator$okhttp() {
            return this.proxyAuthenticator;
        }

        public final ProxySelector getProxySelector$okhttp() {
            return this.proxySelector;
        }

        public final int getReadTimeout$okhttp() {
            return this.readTimeout;
        }

        public final boolean getRetryOnConnectionFailure$okhttp() {
            return this.retryOnConnectionFailure;
        }

        public final RouteDatabase getRouteDatabase$okhttp() {
            return this.routeDatabase;
        }

        public final SocketFactory getSocketFactory$okhttp() {
            return this.socketFactory;
        }

        public final SSLSocketFactory getSslSocketFactoryOrNull$okhttp() {
            return this.sslSocketFactoryOrNull;
        }

        public final int getWriteTimeout$okhttp() {
            return this.writeTimeout;
        }

        public final X509TrustManager getX509TrustManagerOrNull$okhttp() {
            return this.x509TrustManagerOrNull;
        }

        public final Builder hostnameVerifier(HostnameVerifier hostnameVerifier) {
            Intrinsics.checkParameterIsNotNull(hostnameVerifier, "hostnameVerifier");
            Builder builder = this;
            if (Intrinsics.areEqual(hostnameVerifier, builder.hostnameVerifier) ^ true) {
                builder.routeDatabase = null;
            }
            builder.hostnameVerifier = hostnameVerifier;
            return builder;
        }

        public final List<Interceptor> interceptors() {
            return this.interceptors;
        }

        public final Builder minWebSocketMessageToCompress(long l) {
            Object object = this;
            boolean bl = l >= 0L;
            if (bl) {
                ((Builder)object).minWebSocketMessageToCompress = l;
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("minWebSocketMessageToCompress must be positive: ");
            ((StringBuilder)object).append(l);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }

        public final List<Interceptor> networkInterceptors() {
            return this.networkInterceptors;
        }

        public final Builder pingInterval(long l, TimeUnit timeUnit) {
            Intrinsics.checkParameterIsNotNull((Object)timeUnit, "unit");
            Builder builder = this;
            builder.pingInterval = Util.checkDuration("interval", l, timeUnit);
            return builder;
        }

        public final Builder pingInterval(Duration duration) {
            Intrinsics.checkParameterIsNotNull(duration, "duration");
            Builder builder = this;
            builder.pingInterval(duration.toMillis(), TimeUnit.MILLISECONDS);
            return builder;
        }

        public final Builder protocols(List<? extends Protocol> list) {
            Object object;
            boolean bl;
            block8 : {
                block7 : {
                    Intrinsics.checkParameterIsNotNull(list, "protocols");
                    object = this;
                    list = CollectionsKt.toMutableList((Collection)list);
                    boolean bl2 = list.contains((Object)Protocol.H2_PRIOR_KNOWLEDGE);
                    boolean bl3 = false;
                    bl = bl2 || list.contains((Object)Protocol.HTTP_1_1);
                    if (!bl) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("protocols must contain h2_prior_knowledge or http/1.1: ");
                        ((StringBuilder)object).append(list);
                        throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
                    }
                    if (!list.contains((Object)Protocol.H2_PRIOR_KNOWLEDGE)) break block7;
                    bl = bl3;
                    if (list.size() > 1) break block8;
                }
                bl = true;
            }
            if (!bl) {
                object = new StringBuilder();
                ((StringBuilder)object).append("protocols containing h2_prior_knowledge cannot use other protocols: ");
                ((StringBuilder)object).append(list);
                throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
            }
            if (!(list.contains((Object)Protocol.HTTP_1_0) ^ true)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("protocols must not contain http/1.0: ");
                ((StringBuilder)object).append(list);
                throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
            }
            if (list == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.List<okhttp3.Protocol?>");
            if (!(list.contains(null) ^ true)) throw (Throwable)new IllegalArgumentException("protocols must not contain null".toString());
            list.remove((Object)Protocol.SPDY_3);
            if (Intrinsics.areEqual(list, ((Builder)object).protocols) ^ true) {
                ((Builder)object).routeDatabase = null;
            }
            list = Collections.unmodifiableList(list);
            Intrinsics.checkExpressionValueIsNotNull(list, "Collections.unmodifiableList(protocolsCopy)");
            ((Builder)object).protocols = list;
            return object;
        }

        public final Builder proxy(Proxy proxy) {
            Builder builder = this;
            if (Intrinsics.areEqual(proxy, builder.proxy) ^ true) {
                builder.routeDatabase = null;
            }
            builder.proxy = proxy;
            return builder;
        }

        public final Builder proxyAuthenticator(Authenticator authenticator) {
            Intrinsics.checkParameterIsNotNull(authenticator, "proxyAuthenticator");
            Builder builder = this;
            if (Intrinsics.areEqual(authenticator, builder.proxyAuthenticator) ^ true) {
                builder.routeDatabase = null;
            }
            builder.proxyAuthenticator = authenticator;
            return builder;
        }

        public final Builder proxySelector(ProxySelector proxySelector) {
            Intrinsics.checkParameterIsNotNull(proxySelector, "proxySelector");
            Builder builder = this;
            if (Intrinsics.areEqual(proxySelector, builder.proxySelector) ^ true) {
                builder.routeDatabase = null;
            }
            builder.proxySelector = proxySelector;
            return builder;
        }

        public final Builder readTimeout(long l, TimeUnit timeUnit) {
            Intrinsics.checkParameterIsNotNull((Object)timeUnit, "unit");
            Builder builder = this;
            builder.readTimeout = Util.checkDuration("timeout", l, timeUnit);
            return builder;
        }

        public final Builder readTimeout(Duration duration) {
            Intrinsics.checkParameterIsNotNull(duration, "duration");
            Builder builder = this;
            builder.readTimeout(duration.toMillis(), TimeUnit.MILLISECONDS);
            return builder;
        }

        public final Builder retryOnConnectionFailure(boolean bl) {
            Builder builder = this;
            builder.retryOnConnectionFailure = bl;
            return builder;
        }

        public final void setAuthenticator$okhttp(Authenticator authenticator) {
            Intrinsics.checkParameterIsNotNull(authenticator, "<set-?>");
            this.authenticator = authenticator;
        }

        public final void setCache$okhttp(Cache cache) {
            this.cache = cache;
        }

        public final void setCallTimeout$okhttp(int n) {
            this.callTimeout = n;
        }

        public final void setCertificateChainCleaner$okhttp(CertificateChainCleaner certificateChainCleaner) {
            this.certificateChainCleaner = certificateChainCleaner;
        }

        public final void setCertificatePinner$okhttp(CertificatePinner certificatePinner) {
            Intrinsics.checkParameterIsNotNull(certificatePinner, "<set-?>");
            this.certificatePinner = certificatePinner;
        }

        public final void setConnectTimeout$okhttp(int n) {
            this.connectTimeout = n;
        }

        public final void setConnectionPool$okhttp(ConnectionPool connectionPool) {
            Intrinsics.checkParameterIsNotNull(connectionPool, "<set-?>");
            this.connectionPool = connectionPool;
        }

        public final void setConnectionSpecs$okhttp(List<ConnectionSpec> list) {
            Intrinsics.checkParameterIsNotNull(list, "<set-?>");
            this.connectionSpecs = list;
        }

        public final void setCookieJar$okhttp(CookieJar cookieJar) {
            Intrinsics.checkParameterIsNotNull(cookieJar, "<set-?>");
            this.cookieJar = cookieJar;
        }

        public final void setDispatcher$okhttp(Dispatcher dispatcher) {
            Intrinsics.checkParameterIsNotNull(dispatcher, "<set-?>");
            this.dispatcher = dispatcher;
        }

        public final void setDns$okhttp(Dns dns) {
            Intrinsics.checkParameterIsNotNull(dns, "<set-?>");
            this.dns = dns;
        }

        public final void setEventListenerFactory$okhttp(EventListener.Factory factory2) {
            Intrinsics.checkParameterIsNotNull(factory2, "<set-?>");
            this.eventListenerFactory = factory2;
        }

        public final void setFollowRedirects$okhttp(boolean bl) {
            this.followRedirects = bl;
        }

        public final void setFollowSslRedirects$okhttp(boolean bl) {
            this.followSslRedirects = bl;
        }

        public final void setHostnameVerifier$okhttp(HostnameVerifier hostnameVerifier) {
            Intrinsics.checkParameterIsNotNull(hostnameVerifier, "<set-?>");
            this.hostnameVerifier = hostnameVerifier;
        }

        public final void setMinWebSocketMessageToCompress$okhttp(long l) {
            this.minWebSocketMessageToCompress = l;
        }

        public final void setPingInterval$okhttp(int n) {
            this.pingInterval = n;
        }

        public final void setProtocols$okhttp(List<? extends Protocol> list) {
            Intrinsics.checkParameterIsNotNull(list, "<set-?>");
            this.protocols = list;
        }

        public final void setProxy$okhttp(Proxy proxy) {
            this.proxy = proxy;
        }

        public final void setProxyAuthenticator$okhttp(Authenticator authenticator) {
            Intrinsics.checkParameterIsNotNull(authenticator, "<set-?>");
            this.proxyAuthenticator = authenticator;
        }

        public final void setProxySelector$okhttp(ProxySelector proxySelector) {
            this.proxySelector = proxySelector;
        }

        public final void setReadTimeout$okhttp(int n) {
            this.readTimeout = n;
        }

        public final void setRetryOnConnectionFailure$okhttp(boolean bl) {
            this.retryOnConnectionFailure = bl;
        }

        public final void setRouteDatabase$okhttp(RouteDatabase routeDatabase) {
            this.routeDatabase = routeDatabase;
        }

        public final void setSocketFactory$okhttp(SocketFactory socketFactory) {
            Intrinsics.checkParameterIsNotNull(socketFactory, "<set-?>");
            this.socketFactory = socketFactory;
        }

        public final void setSslSocketFactoryOrNull$okhttp(SSLSocketFactory sSLSocketFactory) {
            this.sslSocketFactoryOrNull = sSLSocketFactory;
        }

        public final void setWriteTimeout$okhttp(int n) {
            this.writeTimeout = n;
        }

        public final void setX509TrustManagerOrNull$okhttp(X509TrustManager x509TrustManager) {
            this.x509TrustManagerOrNull = x509TrustManager;
        }

        public final Builder socketFactory(SocketFactory socketFactory) {
            Intrinsics.checkParameterIsNotNull(socketFactory, "socketFactory");
            Builder builder = this;
            if (!(socketFactory instanceof SSLSocketFactory ^ true)) throw (Throwable)new IllegalArgumentException("socketFactory instanceof SSLSocketFactory".toString());
            if (Intrinsics.areEqual(socketFactory, builder.socketFactory) ^ true) {
                builder.routeDatabase = null;
            }
            builder.socketFactory = socketFactory;
            return builder;
        }

        @Deprecated(level=DeprecationLevel.ERROR, message="Use the sslSocketFactory overload that accepts a X509TrustManager.")
        public final Builder sslSocketFactory(SSLSocketFactory object) {
            Intrinsics.checkParameterIsNotNull(object, "sslSocketFactory");
            Object object2 = this;
            if (Intrinsics.areEqual(object, ((Builder)object2).sslSocketFactoryOrNull) ^ true) {
                ((Builder)object2).routeDatabase = null;
            }
            ((Builder)object2).sslSocketFactoryOrNull = object;
            X509TrustManager x509TrustManager = Platform.Companion.get().trustManager((SSLSocketFactory)object);
            if (x509TrustManager == null) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Unable to extract the trust manager on ");
                ((StringBuilder)object2).append(Platform.Companion.get());
                ((StringBuilder)object2).append(", ");
                ((StringBuilder)object2).append("sslSocketFactory is ");
                ((StringBuilder)object2).append(object.getClass());
                throw (Throwable)new IllegalStateException(((StringBuilder)object2).toString());
            }
            ((Builder)object2).x509TrustManagerOrNull = x509TrustManager;
            object = Platform.Companion.get();
            x509TrustManager = ((Builder)object2).x509TrustManagerOrNull;
            if (x509TrustManager == null) {
                Intrinsics.throwNpe();
            }
            ((Builder)object2).certificateChainCleaner = ((Platform)object).buildCertificateChainCleaner(x509TrustManager);
            return object2;
        }

        public final Builder sslSocketFactory(SSLSocketFactory sSLSocketFactory, X509TrustManager x509TrustManager) {
            Intrinsics.checkParameterIsNotNull(sSLSocketFactory, "sslSocketFactory");
            Intrinsics.checkParameterIsNotNull(x509TrustManager, "trustManager");
            Builder builder = this;
            if (Intrinsics.areEqual(sSLSocketFactory, builder.sslSocketFactoryOrNull) ^ true || Intrinsics.areEqual(x509TrustManager, builder.x509TrustManagerOrNull) ^ true) {
                builder.routeDatabase = null;
            }
            builder.sslSocketFactoryOrNull = sSLSocketFactory;
            builder.certificateChainCleaner = CertificateChainCleaner.Companion.get(x509TrustManager);
            builder.x509TrustManagerOrNull = x509TrustManager;
            return builder;
        }

        public final Builder writeTimeout(long l, TimeUnit timeUnit) {
            Intrinsics.checkParameterIsNotNull((Object)timeUnit, "unit");
            Builder builder = this;
            builder.writeTimeout = Util.checkDuration("timeout", l, timeUnit);
            return builder;
        }

        public final Builder writeTimeout(Duration duration) {
            Intrinsics.checkParameterIsNotNull(duration, "duration");
            Builder builder = this;
            builder.writeTimeout(duration.toMillis(), TimeUnit.MILLISECONDS);
            return builder;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0007\u00a8\u0006\u000b"}, d2={"Lokhttp3/OkHttpClient$Companion;", "", "()V", "DEFAULT_CONNECTION_SPECS", "", "Lokhttp3/ConnectionSpec;", "getDEFAULT_CONNECTION_SPECS$okhttp", "()Ljava/util/List;", "DEFAULT_PROTOCOLS", "Lokhttp3/Protocol;", "getDEFAULT_PROTOCOLS$okhttp", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final List<ConnectionSpec> getDEFAULT_CONNECTION_SPECS$okhttp() {
            return DEFAULT_CONNECTION_SPECS;
        }

        public final List<Protocol> getDEFAULT_PROTOCOLS$okhttp() {
            return DEFAULT_PROTOCOLS;
        }
    }

}

