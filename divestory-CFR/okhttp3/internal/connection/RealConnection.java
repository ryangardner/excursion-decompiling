/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okhttp3.internal.connection.RealConnection$connectTls
 *  okhttp3.internal.connection.RealConnection$newWebSocketStreams
 */
package okhttp3.internal.connection;

import java.io.Closeable;
import java.io.IOException;
import java.lang.ref.Reference;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.UnknownServiceException;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Address;
import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.CertificatePinner;
import okhttp3.CipherSuite;
import okhttp3.Connection;
import okhttp3.ConnectionSpec;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Route;
import okhttp3.TlsVersion;
import okhttp3.internal.Util;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.connection.ConnectionSpecSelector;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.connection.RealCall;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.RealConnection$WhenMappings;
import okhttp3.internal.connection.RealConnectionPool;
import okhttp3.internal.connection.RouteDatabase;
import okhttp3.internal.connection.RouteException;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http1.Http1ExchangeCodec;
import okhttp3.internal.http2.ConnectionShutdownException;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.Http2Connection;
import okhttp3.internal.http2.Http2ExchangeCodec;
import okhttp3.internal.http2.Http2Stream;
import okhttp3.internal.http2.Settings;
import okhttp3.internal.http2.StreamResetException;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.tls.OkHostnameVerifier;
import okhttp3.internal.ws.RealWebSocket;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u00ec\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000 {2\u00020\u00012\u00020\u0002:\u0001{B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u0006\u00105\u001a\u000206J\u0018\u00107\u001a\u00020\u001d2\u0006\u00108\u001a\u0002092\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J>\u0010:\u001a\u0002062\u0006\u0010;\u001a\u00020\t2\u0006\u0010<\u001a\u00020\t2\u0006\u0010=\u001a\u00020\t2\u0006\u0010>\u001a\u00020\t2\u0006\u0010?\u001a\u00020\u001d2\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020CJ%\u0010D\u001a\u0002062\u0006\u0010E\u001a\u00020F2\u0006\u0010G\u001a\u00020\u00062\u0006\u0010H\u001a\u00020IH\u0000\u00a2\u0006\u0002\bJJ(\u0010K\u001a\u0002062\u0006\u0010;\u001a\u00020\t2\u0006\u0010<\u001a\u00020\t2\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020CH\u0002J\u0010\u0010L\u001a\u0002062\u0006\u0010M\u001a\u00020NH\u0002J0\u0010O\u001a\u0002062\u0006\u0010;\u001a\u00020\t2\u0006\u0010<\u001a\u00020\t2\u0006\u0010=\u001a\u00020\t2\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020CH\u0002J*\u0010P\u001a\u0004\u0018\u00010Q2\u0006\u0010<\u001a\u00020\t2\u0006\u0010=\u001a\u00020\t2\u0006\u0010R\u001a\u00020Q2\u0006\u00108\u001a\u000209H\u0002J\b\u0010S\u001a\u00020QH\u0002J(\u0010T\u001a\u0002062\u0006\u0010M\u001a\u00020N2\u0006\u0010>\u001a\u00020\t2\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020CH\u0002J\n\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J\r\u0010U\u001a\u000206H\u0000\u00a2\u0006\u0002\bVJ%\u0010W\u001a\u00020\u001d2\u0006\u0010X\u001a\u00020Y2\u000e\u0010Z\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010[H\u0000\u00a2\u0006\u0002\b\\J\u000e\u0010]\u001a\u00020\u001d2\u0006\u0010^\u001a\u00020\u001dJ\u001d\u0010_\u001a\u00020`2\u0006\u0010E\u001a\u00020F2\u0006\u0010a\u001a\u00020bH\u0000\u00a2\u0006\u0002\bcJ\u0015\u0010d\u001a\u00020e2\u0006\u0010f\u001a\u00020gH\u0000\u00a2\u0006\u0002\bhJ\r\u0010 \u001a\u000206H\u0000\u00a2\u0006\u0002\biJ\r\u0010!\u001a\u000206H\u0000\u00a2\u0006\u0002\bjJ\u0018\u0010k\u001a\u0002062\u0006\u0010l\u001a\u00020\u00152\u0006\u0010m\u001a\u00020nH\u0016J\u0010\u0010o\u001a\u0002062\u0006\u0010p\u001a\u00020qH\u0016J\b\u0010%\u001a\u00020&H\u0016J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0016\u0010r\u001a\u00020\u001d2\f\u0010s\u001a\b\u0012\u0004\u0012\u00020\u00060[H\u0002J\b\u00101\u001a\u00020(H\u0016J\u0010\u0010t\u001a\u0002062\u0006\u0010>\u001a\u00020\tH\u0002J\u0010\u0010u\u001a\u00020\u001d2\u0006\u00108\u001a\u000209H\u0002J\b\u0010v\u001a\u00020wH\u0016J\u001f\u0010x\u001a\u0002062\u0006\u0010@\u001a\u00020\r2\b\u0010y\u001a\u0004\u0018\u00010IH\u0000\u00a2\u0006\u0002\bzR\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001d\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\u00020\u0017X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001c\u001a\u00020\u001d8@X\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u000e\u0010 \u001a\u00020\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010!\u001a\u00020\u001dX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u001f\"\u0004\b#\u0010$R\u0010\u0010%\u001a\u0004\u0018\u00010&X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010'\u001a\u0004\u0018\u00010(X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010*\u001a\u00020\tX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u0010\u0010/\u001a\u0004\u0018\u000100X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00101\u001a\u0004\u0018\u00010(X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00102\u001a\u0004\u0018\u000103X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006|"}, d2={"Lokhttp3/internal/connection/RealConnection;", "Lokhttp3/internal/http2/Http2Connection$Listener;", "Lokhttp3/Connection;", "connectionPool", "Lokhttp3/internal/connection/RealConnectionPool;", "route", "Lokhttp3/Route;", "(Lokhttp3/internal/connection/RealConnectionPool;Lokhttp3/Route;)V", "allocationLimit", "", "calls", "", "Ljava/lang/ref/Reference;", "Lokhttp3/internal/connection/RealCall;", "getCalls", "()Ljava/util/List;", "getConnectionPool", "()Lokhttp3/internal/connection/RealConnectionPool;", "handshake", "Lokhttp3/Handshake;", "http2Connection", "Lokhttp3/internal/http2/Http2Connection;", "idleAtNs", "", "getIdleAtNs$okhttp", "()J", "setIdleAtNs$okhttp", "(J)V", "isMultiplexed", "", "isMultiplexed$okhttp", "()Z", "noCoalescedConnections", "noNewExchanges", "getNoNewExchanges", "setNoNewExchanges", "(Z)V", "protocol", "Lokhttp3/Protocol;", "rawSocket", "Ljava/net/Socket;", "refusedStreamCount", "routeFailureCount", "getRouteFailureCount$okhttp", "()I", "setRouteFailureCount$okhttp", "(I)V", "sink", "Lokio/BufferedSink;", "socket", "source", "Lokio/BufferedSource;", "successCount", "cancel", "", "certificateSupportHost", "url", "Lokhttp3/HttpUrl;", "connect", "connectTimeout", "readTimeout", "writeTimeout", "pingIntervalMillis", "connectionRetryEnabled", "call", "Lokhttp3/Call;", "eventListener", "Lokhttp3/EventListener;", "connectFailed", "client", "Lokhttp3/OkHttpClient;", "failedRoute", "failure", "Ljava/io/IOException;", "connectFailed$okhttp", "connectSocket", "connectTls", "connectionSpecSelector", "Lokhttp3/internal/connection/ConnectionSpecSelector;", "connectTunnel", "createTunnel", "Lokhttp3/Request;", "tunnelRequest", "createTunnelRequest", "establishProtocol", "incrementSuccessCount", "incrementSuccessCount$okhttp", "isEligible", "address", "Lokhttp3/Address;", "routes", "", "isEligible$okhttp", "isHealthy", "doExtensiveChecks", "newCodec", "Lokhttp3/internal/http/ExchangeCodec;", "chain", "Lokhttp3/internal/http/RealInterceptorChain;", "newCodec$okhttp", "newWebSocketStreams", "Lokhttp3/internal/ws/RealWebSocket$Streams;", "exchange", "Lokhttp3/internal/connection/Exchange;", "newWebSocketStreams$okhttp", "noCoalescedConnections$okhttp", "noNewExchanges$okhttp", "onSettings", "connection", "settings", "Lokhttp3/internal/http2/Settings;", "onStream", "stream", "Lokhttp3/internal/http2/Http2Stream;", "routeMatchesAny", "candidates", "startHttp2", "supportsUrl", "toString", "", "trackFailure", "e", "trackFailure$okhttp", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class RealConnection
extends Http2Connection.Listener
implements Connection {
    public static final Companion Companion = new Companion(null);
    public static final long IDLE_CONNECTION_HEALTHY_NS = 10000000000L;
    private static final int MAX_TUNNEL_ATTEMPTS = 21;
    private static final String NPE_THROW_WITH_NULL = "throw with null exception";
    private int allocationLimit;
    private final List<Reference<RealCall>> calls;
    private final RealConnectionPool connectionPool;
    private Handshake handshake;
    private Http2Connection http2Connection;
    private long idleAtNs;
    private boolean noCoalescedConnections;
    private boolean noNewExchanges;
    private Protocol protocol;
    private Socket rawSocket;
    private int refusedStreamCount;
    private final Route route;
    private int routeFailureCount;
    private BufferedSink sink;
    private Socket socket;
    private BufferedSource source;
    private int successCount;

    public RealConnection(RealConnectionPool realConnectionPool, Route route) {
        Intrinsics.checkParameterIsNotNull(realConnectionPool, "connectionPool");
        Intrinsics.checkParameterIsNotNull(route, "route");
        this.connectionPool = realConnectionPool;
        this.route = route;
        this.allocationLimit = 1;
        this.calls = new ArrayList();
        this.idleAtNs = Long.MAX_VALUE;
    }

    public static final /* synthetic */ Handshake access$getHandshake$p(RealConnection realConnection) {
        return realConnection.handshake;
    }

    public static final /* synthetic */ Socket access$getSocket$p(RealConnection realConnection) {
        return realConnection.socket;
    }

    public static final /* synthetic */ void access$setHandshake$p(RealConnection realConnection, Handshake handshake2) {
        realConnection.handshake = handshake2;
    }

    public static final /* synthetic */ void access$setSocket$p(RealConnection realConnection, Socket socket) {
        realConnection.socket = socket;
    }

    private final boolean certificateSupportHost(HttpUrl object, Handshake object2) {
        Object object3 = ((Handshake)object2).peerCertificates();
        boolean bl = ((Collection)object3).isEmpty();
        boolean bl2 = true;
        if (!(bl ^ true)) return false;
        object2 = OkHostnameVerifier.INSTANCE;
        object = ((HttpUrl)object).host();
        if ((object3 = object3.get(0)) == null) throw new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
        if (!((OkHostnameVerifier)object2).verify((String)object, (X509Certificate)object3)) return false;
        return bl2;
    }

    private final void connectSocket(int n, int n2, Call call, EventListener object) throws IOException {
        int n3;
        Proxy proxy = this.route.proxy();
        Object object2 = this.route.address();
        Object object3 = proxy.type();
        if (object3 == null || (n3 = RealConnection$WhenMappings.$EnumSwitchMapping$0[object3.ordinal()]) != 1 && n3 != 2) {
            object3 = new Socket(proxy);
        } else {
            object2 = ((Address)object2).socketFactory().createSocket();
            object3 = object2;
            if (object2 == null) {
                Intrinsics.throwNpe();
                object3 = object2;
            }
        }
        this.rawSocket = object3;
        ((EventListener)object).connectStart(call, this.route.socketAddress(), proxy);
        ((Socket)object3).setSoTimeout(n2);
        try {
            Platform.Companion.get().connectSocket((Socket)object3, this.route.socketAddress(), n);
        }
        catch (ConnectException connectException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed to connect to ");
            ((StringBuilder)object).append(this.route.socketAddress());
            object = new ConnectException(((StringBuilder)object).toString());
            ((Throwable)object).initCause(connectException);
            throw (Throwable)object;
        }
        try {
            this.source = Okio.buffer(Okio.source((Socket)object3));
            this.sink = Okio.buffer(Okio.sink((Socket)object3));
            return;
        }
        catch (NullPointerException nullPointerException) {
            if (Intrinsics.areEqual(nullPointerException.getMessage(), NPE_THROW_WITH_NULL)) throw (Throwable)new IOException(nullPointerException);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private final void connectTls(ConnectionSpecSelector var1_1) throws IOException {
        block16 : {
            block15 : {
                var2_19 = this.route.address();
                var3_20 = var2_19.sslSocketFactory();
                var4_21 = null;
                var5_22 = null;
                if (var3_20 != null) ** GOTO lbl8
                Intrinsics.throwNpe();
lbl8: // 2 sources:
                if ((var3_20 = var3_20.createSocket(this.rawSocket, var2_19.url().host(), var2_19.url().port(), true)) == null) break block15;
                var3_20 = (SSLSocket)var3_20;
                try {
                    var5_22 = var1_1.configureSecureSocket((SSLSocket)var3_20);
                    if (var5_22.supportsTlsExtensions()) {
                        Platform.Companion.get().configureTlsExtensions((SSLSocket)var3_20, var2_19.url().host(), var2_19.protocols());
                    }
                    var3_20.startHandshake();
                    var1_2 = var3_20.getSession();
                    var6_23 = Handshake.Companion;
                    Intrinsics.checkExpressionValueIsNotNull(var1_2, "sslSocketSession");
                    var6_23 = var6_23.get(var1_2);
                    var7_24 = var2_19.hostnameVerifier();
                    if (var7_24 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (!var7_24.verify(var2_19.url().host(), var1_2)) {
                        var1_3 = var6_23.peerCertificates();
                        if (!(((Collection)var1_3).isEmpty() ^ true)) {
                            var4_21 = new StringBuilder();
                            var4_21.append("Hostname ");
                            var4_21.append(var2_19.url().host());
                            var4_21.append(" not verified (no certificates)");
                            var1_7 = new SSLPeerUnverifiedException(var4_21.toString());
                            throw (Throwable)var1_7;
                        }
                        var1_4 = var1_3.get(0);
                        if (var1_4 == null) {
                            var1_5 = new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
                            throw var1_5;
                        }
                        var1_6 = (X509Certificate)var1_4;
                        var4_21 = new StringBuilder();
                        var4_21.append("\n              |Hostname ");
                        var4_21.append(var2_19.url().host());
                        var4_21.append(" not verified:\n              |    certificate: ");
                        var4_21.append(CertificatePinner.Companion.pin(var1_6));
                        var4_21.append("\n              |    DN: ");
                        var2_19 = var1_6.getSubjectDN();
                        Intrinsics.checkExpressionValueIsNotNull(var2_19, "cert.subjectDN");
                        var4_21.append(var2_19.getName());
                        var4_21.append("\n              |    subjectAltNames: ");
                        var4_21.append(OkHostnameVerifier.INSTANCE.allSubjectAltNames(var1_6));
                        var4_21.append("\n              ");
                        var5_22 = new SSLPeerUnverifiedException(StringsKt.trimMargin$default(var4_21.toString(), null, 1, null));
                        throw (Throwable)var5_22;
                    }
                    var1_8 = var2_19.certificatePinner();
                    if (var1_8 == null) {
                        Intrinsics.throwNpe();
                    }
                    var9_26 = var6_23.tlsVersion();
                    var7_24 = var6_23.cipherSuite();
                    var10_27 = var6_23.localCertificates();
                    var11_28 = new Function0<List<? extends Certificate>>(var1_8, (Handshake)var6_23, (Address)var2_19){
                        final /* synthetic */ Address $address;
                        final /* synthetic */ CertificatePinner $certificatePinner;
                        final /* synthetic */ Handshake $unverifiedHandshake;
                        {
                            this.$certificatePinner = certificatePinner;
                            this.$unverifiedHandshake = handshake2;
                            this.$address = address;
                            super(0);
                        }

                        public final List<Certificate> invoke() {
                            okhttp3.internal.tls.CertificateChainCleaner certificateChainCleaner = this.$certificatePinner.getCertificateChainCleaner$okhttp();
                            if (certificateChainCleaner != null) return certificateChainCleaner.clean(this.$unverifiedHandshake.peerCertificates(), this.$address.url().host());
                            Intrinsics.throwNpe();
                            return certificateChainCleaner.clean(this.$unverifiedHandshake.peerCertificates(), this.$address.url().host());
                        }
                    };
                    this.handshake = var8_25 = new Handshake(var9_26, (CipherSuite)var7_24, var10_27, (Function0<? extends List<? extends Certificate>>)var11_28);
                    var6_23 = var2_19.url().host();
                    var2_19 = new Function0<List<? extends X509Certificate>>(this){
                        final /* synthetic */ RealConnection this$0;
                        {
                            this.this$0 = realConnection;
                            super(0);
                        }

                        public final List<X509Certificate> invoke() {
                            Object object = RealConnection.access$getHandshake$p(this.this$0);
                            if (object == null) {
                                Intrinsics.throwNpe();
                            }
                            Object object2 = ((Handshake)object).peerCertificates();
                            object = new ArrayList<E>(kotlin.collections.CollectionsKt.collectionSizeOrDefault(object2, 10));
                            object2 = object2.iterator();
                            while (object2.hasNext()) {
                                Certificate certificate = (Certificate)object2.next();
                                if (certificate == null) throw new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
                                object.add((X509Certificate)certificate);
                            }
                            return (List)object;
                        }
                    };
                    var1_8.check$okhttp((String)var6_23, (Function0<? extends List<? extends X509Certificate>>)var2_19);
                    var1_9 = var4_21;
                    if (var5_22.supportsTlsExtensions()) {
                        var1_10 = Platform.Companion.get().getSelectedProtocol((SSLSocket)var3_20);
                    }
                    this.socket = (Socket)var3_20;
                    this.source = Okio.buffer(Okio.source((Socket)var3_20));
                    this.sink = Okio.buffer(Okio.sink((Socket)var3_20));
                    if (var1_11 != null) {
                        var1_12 = Protocol.Companion.get((String)var1_11);
                    } else {
                        var1_13 = Protocol.HTTP_1_1;
                    }
                    this.protocol = var1_14;
                    if (var3_20 == null) return;
                    Platform.Companion.get().afterHandshake((SSLSocket)var3_20);
                    return;
                }
                catch (Throwable var1_15) {}
                break block16;
            }
            try {
                var1_16 = new TypeCastException("null cannot be cast to non-null type javax.net.ssl.SSLSocket");
                throw var1_16;
            }
            catch (Throwable var1_17) {
                var3_20 = var5_22;
            }
        }
        if (var3_20 != null) {
            Platform.Companion.get().afterHandshake((SSLSocket)var3_20);
        }
        if (var3_20 == null) throw var1_18;
        Util.closeQuietly((Socket)var3_20);
        throw var1_18;
    }

    private final void connectTunnel(int n, int n2, int n3, Call call, EventListener eventListener) throws IOException {
        Request request = this.createTunnelRequest();
        HttpUrl httpUrl = request.url();
        int n4 = 0;
        while (n4 < 21) {
            this.connectSocket(n, n2, call, eventListener);
            request = this.createTunnel(n2, n3, request, httpUrl);
            if (request == null) return;
            Socket socket = this.rawSocket;
            if (socket != null) {
                Util.closeQuietly(socket);
            }
            this.rawSocket = null;
            this.sink = null;
            this.source = null;
            eventListener.connectEnd(call, this.route.socketAddress(), this.route.proxy(), null);
            ++n4;
        }
    }

    private final Request createTunnel(int n, int n2, Request object, HttpUrl object2) throws IOException {
        Object object3;
        Object object4 = new StringBuilder();
        ((StringBuilder)object4).append("CONNECT ");
        ((StringBuilder)object4).append(Util.toHostHeader((HttpUrl)object2, true));
        ((StringBuilder)object4).append(" HTTP/1.1");
        object2 = ((StringBuilder)object4).toString();
        do {
            BufferedSink bufferedSink;
            BufferedSource bufferedSource;
            if ((bufferedSource = this.source) == null) {
                Intrinsics.throwNpe();
            }
            if ((bufferedSink = this.sink) == null) {
                Intrinsics.throwNpe();
            }
            object4 = new Http1ExchangeCodec(null, this, bufferedSource, bufferedSink);
            bufferedSource.timeout().timeout(n, TimeUnit.MILLISECONDS);
            bufferedSink.timeout().timeout(n2, TimeUnit.MILLISECONDS);
            ((Http1ExchangeCodec)object4).writeRequest(((Request)object).headers(), (String)object2);
            ((Http1ExchangeCodec)object4).finishRequest();
            object3 = ((Http1ExchangeCodec)object4).readResponseHeaders(false);
            if (object3 == null) {
                Intrinsics.throwNpe();
            }
            object3 = ((Response.Builder)object3).request((Request)object).build();
            ((Http1ExchangeCodec)object4).skipConnectBody((Response)object3);
            int n3 = ((Response)object3).code();
            if (n3 == 200) {
                if (!bufferedSource.getBuffer().exhausted()) throw (Throwable)new IOException("TLS tunnel buffered too many bytes!");
                if (!bufferedSink.getBuffer().exhausted()) throw (Throwable)new IOException("TLS tunnel buffered too many bytes!");
                return null;
            }
            if (n3 != 407) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unexpected response code for CONNECT: ");
                ((StringBuilder)object).append(((Response)object3).code());
                throw (Throwable)new IOException(((StringBuilder)object).toString());
            }
            object = this.route.address().proxyAuthenticator().authenticate(this.route, (Response)object3);
            if (object == null) throw (Throwable)new IOException("Failed to authenticate with proxy");
        } while (!StringsKt.equals("close", Response.header$default((Response)object3, "Connection", null, 2, null), true));
        return object;
    }

    private final Request createTunnelRequest() throws IOException {
        Request request = new Request.Builder().url(this.route.address().url()).method("CONNECT", null).header("Host", Util.toHostHeader(this.route.address().url(), true)).header("Proxy-Connection", "Keep-Alive").header("User-Agent", "okhttp/4.8.1").build();
        Object object = new Response.Builder().request(request).protocol(Protocol.HTTP_1_1).code(407).message("Preemptive Authenticate").body(Util.EMPTY_RESPONSE).sentRequestAtMillis(-1L).receivedResponseAtMillis(-1L).header("Proxy-Authenticate", "OkHttp-Preemptive").build();
        object = this.route.address().proxyAuthenticator().authenticate(this.route, (Response)object);
        if (object == null) return request;
        return object;
    }

    private final void establishProtocol(ConnectionSpecSelector connectionSpecSelector, int n, Call call, EventListener eventListener) throws IOException {
        if (this.route.address().sslSocketFactory() != null) {
            eventListener.secureConnectStart(call);
            this.connectTls(connectionSpecSelector);
            eventListener.secureConnectEnd(call, this.handshake);
            if (this.protocol != Protocol.HTTP_2) return;
            this.startHttp2(n);
            return;
        }
        if (this.route.address().protocols().contains((Object)Protocol.H2_PRIOR_KNOWLEDGE)) {
            this.socket = this.rawSocket;
            this.protocol = Protocol.H2_PRIOR_KNOWLEDGE;
            this.startHttp2(n);
            return;
        }
        this.socket = this.rawSocket;
        this.protocol = Protocol.HTTP_1_1;
    }

    private final boolean routeMatchesAny(List<Route> object) {
        boolean bl;
        object = (Iterable)object;
        boolean bl2 = object instanceof Collection;
        boolean bl3 = true;
        if (bl2) {
            if (((Collection)object).isEmpty()) return false;
        }
        Iterator<Route> iterator2 = object.iterator();
        do {
            if (iterator2.hasNext()) continue;
            return false;
        } while (!(bl = ((Route)(object = iterator2.next())).proxy().type() == Proxy.Type.DIRECT && this.route.proxy().type() == Proxy.Type.DIRECT && Intrinsics.areEqual(this.route.socketAddress(), ((Route)object).socketAddress())));
        return bl3;
    }

    private final void startHttp2(int n) throws IOException {
        BufferedSource bufferedSource;
        Closeable closeable;
        Socket socket = this.socket;
        if (socket == null) {
            Intrinsics.throwNpe();
        }
        if ((bufferedSource = this.source) == null) {
            Intrinsics.throwNpe();
        }
        if ((closeable = this.sink) == null) {
            Intrinsics.throwNpe();
        }
        socket.setSoTimeout(0);
        closeable = new Http2Connection.Builder(true, TaskRunner.INSTANCE).socket(socket, this.route.address().url().host(), bufferedSource, (BufferedSink)closeable).listener(this).pingIntervalMillis(n).build();
        this.http2Connection = closeable;
        this.allocationLimit = Http2Connection.Companion.getDEFAULT_SETTINGS().getMaxConcurrentStreams();
        Http2Connection.start$default((Http2Connection)closeable, false, null, 3, null);
    }

    private final boolean supportsUrl(HttpUrl object) {
        if (Util.assertionsEnabled && !Thread.holdsLock(this)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Thread ");
            object = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(object, "Thread.currentThread()");
            stringBuilder.append(((Thread)object).getName());
            stringBuilder.append(" MUST hold lock on ");
            stringBuilder.append(this);
            throw (Throwable)((Object)new AssertionError((Object)stringBuilder.toString()));
        }
        Object object2 = this.route.address().url();
        int n = ((HttpUrl)object).port();
        int n2 = ((HttpUrl)object2).port();
        boolean bl = false;
        if (n != n2) {
            return false;
        }
        if (Intrinsics.areEqual(((HttpUrl)object).host(), ((HttpUrl)object2).host())) {
            return true;
        }
        boolean bl2 = bl;
        if (this.noCoalescedConnections) return bl2;
        object2 = this.handshake;
        bl2 = bl;
        if (object2 == null) return bl2;
        if (object2 == null) {
            Intrinsics.throwNpe();
        }
        bl2 = bl;
        if (!this.certificateSupportHost((HttpUrl)object, (Handshake)object2)) return bl2;
        return true;
    }

    public final void cancel() {
        Socket socket = this.rawSocket;
        if (socket == null) return;
        Util.closeQuietly(socket);
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public final void connect(int n, int n2, int n3, int n4, boolean bl, Call object, EventListener eventListener) {
        Intrinsics.checkParameterIsNotNull(object, "call");
        Intrinsics.checkParameterIsNotNull(eventListener, "eventListener");
        boolean bl2 = this.protocol == null;
        if (!bl2) throw (Throwable)new IllegalStateException("already connected".toString());
        RouteException routeException = null;
        Object object2 = this.route.address().connectionSpecs();
        ConnectionSpecSelector connectionSpecSelector = new ConnectionSpecSelector((List<ConnectionSpec>)object2);
        if (this.route.address().sslSocketFactory() == null) {
            if (!object2.contains(ConnectionSpec.CLEARTEXT)) throw (Throwable)new RouteException(new UnknownServiceException("CLEARTEXT communication not enabled for client"));
            object2 = this.route.address().url().host();
            if (!Platform.Companion.get().isCleartextTrafficPermitted((String)object2)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("CLEARTEXT communication to ");
                ((StringBuilder)object).append((String)object2);
                ((StringBuilder)object).append(" not permitted by network security policy");
                throw (Throwable)new RouteException(new UnknownServiceException(((StringBuilder)object).toString()));
            }
        } else if (this.route.address().protocols().contains((Object)Protocol.H2_PRIOR_KNOWLEDGE)) throw (Throwable)new RouteException(new UnknownServiceException("H2_PRIOR_KNOWLEDGE cannot be used with HTTPS"));
        do {
            block18 : {
                block17 : {
                    block16 : {
                        block15 : {
                            if (!this.route.requiresTunnel()) break block15;
                            this.connectTunnel(n, n2, n3, (Call)object, eventListener);
                            object2 = this.rawSocket;
                            if (object2 != null) break block16;
                            break block17;
                        }
                        try {
                            this.connectSocket(n, n2, (Call)object, eventListener);
                        }
                        catch (IOException iOException) {
                            break block18;
                        }
                    }
                    try {
                        this.establishProtocol(connectionSpecSelector, n4, (Call)object, eventListener);
                        eventListener.connectEnd((Call)object, this.route.socketAddress(), this.route.proxy(), this.protocol);
                    }
                    catch (IOException iOException) {}
                }
                if (this.route.requiresTunnel()) {
                    if (this.rawSocket == null) throw (Throwable)new RouteException(new ProtocolException("Too many tunnel connections attempted: 21"));
                }
                this.idleAtNs = System.nanoTime();
                return;
                break block18;
                catch (IOException iOException) {
                    // empty catch block
                }
            }
            Socket socket = this.socket;
            if (socket != null) {
                Util.closeQuietly(socket);
            }
            if ((socket = this.rawSocket) != null) {
                Util.closeQuietly(socket);
            }
            this.socket = socket = (Socket)null;
            this.rawSocket = socket;
            this.source = null;
            this.sink = null;
            this.handshake = null;
            this.protocol = null;
            this.http2Connection = null;
            this.allocationLimit = 1;
            eventListener.connectFailed((Call)object, this.route.socketAddress(), this.route.proxy(), null, (IOException)object2);
            if (routeException == null) {
                routeException = new RouteException((IOException)object2);
            } else {
                routeException.addConnectException((IOException)object2);
            }
            if (!bl) throw (Throwable)routeException;
            if (!connectionSpecSelector.connectionFailed((IOException)object2)) throw (Throwable)routeException;
        } while (true);
    }

    public final void connectFailed$okhttp(OkHttpClient okHttpClient, Route route, IOException iOException) {
        Intrinsics.checkParameterIsNotNull(okHttpClient, "client");
        Intrinsics.checkParameterIsNotNull(route, "failedRoute");
        Intrinsics.checkParameterIsNotNull(iOException, "failure");
        if (route.proxy().type() != Proxy.Type.DIRECT) {
            Address address = route.address();
            address.proxySelector().connectFailed(address.url().uri(), route.proxy().address(), iOException);
        }
        okHttpClient.getRouteDatabase().failed(route);
    }

    public final List<Reference<RealCall>> getCalls() {
        return this.calls;
    }

    public final RealConnectionPool getConnectionPool() {
        return this.connectionPool;
    }

    public final long getIdleAtNs$okhttp() {
        return this.idleAtNs;
    }

    public final boolean getNoNewExchanges() {
        return this.noNewExchanges;
    }

    public final int getRouteFailureCount$okhttp() {
        return this.routeFailureCount;
    }

    @Override
    public Handshake handshake() {
        return this.handshake;
    }

    public final void incrementSuccessCount$okhttp() {
        synchronized (this) {
            ++this.successCount;
            return;
        }
    }

    public final boolean isEligible$okhttp(Address object, List<Route> object2) {
        Intrinsics.checkParameterIsNotNull(object, "address");
        if (Util.assertionsEnabled && !Thread.holdsLock(this)) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Thread ");
            object = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(object, "Thread.currentThread()");
            ((StringBuilder)object2).append(((Thread)object).getName());
            ((StringBuilder)object2).append(" MUST hold lock on ");
            ((StringBuilder)object2).append(this);
            throw (Throwable)((Object)new AssertionError((Object)((StringBuilder)object2).toString()));
        }
        if (this.calls.size() >= this.allocationLimit) return false;
        if (this.noNewExchanges) {
            return false;
        }
        if (!this.route.address().equalsNonHost$okhttp((Address)object)) {
            return false;
        }
        if (Intrinsics.areEqual(((Address)object).url().host(), this.route().address().url().host())) {
            return true;
        }
        if (this.http2Connection == null) {
            return false;
        }
        if (object2 == null) return false;
        if (!this.routeMatchesAny((List<Route>)object2)) {
            return false;
        }
        if (((Address)object).hostnameVerifier() != OkHostnameVerifier.INSTANCE) {
            return false;
        }
        if (!this.supportsUrl(((Address)object).url())) {
            return false;
        }
        try {
            object2 = ((Address)object).certificatePinner();
            if (object2 == null) {
                Intrinsics.throwNpe();
            }
            object = ((Address)object).url().host();
            Handshake handshake2 = this.handshake();
            if (handshake2 == null) {
                Intrinsics.throwNpe();
            }
            ((CertificatePinner)object2).check((String)object, handshake2.peerCertificates());
            return true;
        }
        catch (SSLPeerUnverifiedException sSLPeerUnverifiedException) {
            return false;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final boolean isHealthy(boolean bl) {
        BufferedSource bufferedSource;
        Socket socket;
        long l;
        if (Util.assertionsEnabled && Thread.holdsLock(this)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Thread ");
            Thread thread2 = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(thread2, "Thread.currentThread()");
            stringBuilder.append(thread2.getName());
            stringBuilder.append(" MUST NOT hold lock on ");
            stringBuilder.append(this);
            throw (Throwable)((Object)new AssertionError((Object)stringBuilder.toString()));
        }
        long l2 = System.nanoTime();
        Closeable closeable = this.rawSocket;
        if (closeable == null) {
            Intrinsics.throwNpe();
        }
        if ((socket = this.socket) == null) {
            Intrinsics.throwNpe();
        }
        if ((bufferedSource = this.source) == null) {
            Intrinsics.throwNpe();
        }
        if (((Socket)closeable).isClosed()) return false;
        if (socket.isClosed()) return false;
        if (socket.isInputShutdown()) return false;
        if (socket.isOutputShutdown()) {
            return false;
        }
        closeable = this.http2Connection;
        if (closeable != null) {
            return ((Http2Connection)closeable).isHealthy(l2);
        }
        synchronized (this) {
            l = this.idleAtNs;
        }
        if (l2 - l < 10000000000L) return true;
        if (!bl) return true;
        return Util.isHealthy(socket, bufferedSource);
    }

    public final boolean isMultiplexed$okhttp() {
        if (this.http2Connection == null) return false;
        return true;
    }

    public final ExchangeCodec newCodec$okhttp(OkHttpClient object, RealInterceptorChain realInterceptorChain) throws SocketException {
        Http2Connection http2Connection;
        BufferedSource bufferedSource;
        BufferedSink bufferedSink;
        Intrinsics.checkParameterIsNotNull(object, "client");
        Intrinsics.checkParameterIsNotNull(realInterceptorChain, "chain");
        Socket socket = this.socket;
        if (socket == null) {
            Intrinsics.throwNpe();
        }
        if ((bufferedSource = this.source) == null) {
            Intrinsics.throwNpe();
        }
        if ((bufferedSink = this.sink) == null) {
            Intrinsics.throwNpe();
        }
        if ((http2Connection = this.http2Connection) != null) {
            return new Http2ExchangeCodec((OkHttpClient)object, this, realInterceptorChain, http2Connection);
        }
        socket.setSoTimeout(realInterceptorChain.readTimeoutMillis());
        bufferedSource.timeout().timeout(realInterceptorChain.getReadTimeoutMillis$okhttp(), TimeUnit.MILLISECONDS);
        bufferedSink.timeout().timeout(realInterceptorChain.getWriteTimeoutMillis$okhttp(), TimeUnit.MILLISECONDS);
        return new Http1ExchangeCodec((OkHttpClient)object, this, bufferedSource, bufferedSink);
    }

    public final RealWebSocket.Streams newWebSocketStreams$okhttp(Exchange exchange) throws SocketException {
        BufferedSource bufferedSource;
        BufferedSink bufferedSink;
        Intrinsics.checkParameterIsNotNull(exchange, "exchange");
        Socket socket = this.socket;
        if (socket == null) {
            Intrinsics.throwNpe();
        }
        if ((bufferedSource = this.source) == null) {
            Intrinsics.throwNpe();
        }
        if ((bufferedSink = this.sink) == null) {
            Intrinsics.throwNpe();
        }
        socket.setSoTimeout(0);
        this.noNewExchanges$okhttp();
        return new RealWebSocket.Streams(exchange, bufferedSource, bufferedSink, true, bufferedSource, bufferedSink){
            final /* synthetic */ Exchange $exchange;
            final /* synthetic */ BufferedSink $sink;
            final /* synthetic */ BufferedSource $source;
            {
                this.$exchange = exchange;
                this.$source = bufferedSource;
                this.$sink = bufferedSink;
                super(bl, bufferedSource2, bufferedSink2);
            }

            public void close() {
                this.$exchange.bodyComplete(-1L, true, true, null);
            }
        };
    }

    public final void noCoalescedConnections$okhttp() {
        synchronized (this) {
            this.noCoalescedConnections = true;
            return;
        }
    }

    public final void noNewExchanges$okhttp() {
        synchronized (this) {
            this.noNewExchanges = true;
            return;
        }
    }

    @Override
    public void onSettings(Http2Connection http2Connection, Settings settings) {
        synchronized (this) {
            Intrinsics.checkParameterIsNotNull(http2Connection, "connection");
            Intrinsics.checkParameterIsNotNull(settings, "settings");
            this.allocationLimit = settings.getMaxConcurrentStreams();
            return;
        }
    }

    @Override
    public void onStream(Http2Stream http2Stream) throws IOException {
        Intrinsics.checkParameterIsNotNull(http2Stream, "stream");
        http2Stream.close(ErrorCode.REFUSED_STREAM, null);
    }

    @Override
    public Protocol protocol() {
        Protocol protocol = this.protocol;
        if (protocol != null) return protocol;
        Intrinsics.throwNpe();
        return protocol;
    }

    @Override
    public Route route() {
        return this.route;
    }

    public final void setIdleAtNs$okhttp(long l) {
        this.idleAtNs = l;
    }

    public final void setNoNewExchanges(boolean bl) {
        this.noNewExchanges = bl;
    }

    public final void setRouteFailureCount$okhttp(int n) {
        this.routeFailureCount = n;
    }

    @Override
    public Socket socket() {
        Socket socket = this.socket;
        if (socket != null) return socket;
        Intrinsics.throwNpe();
        return socket;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Connection{");
        stringBuilder.append(this.route.address().url().host());
        stringBuilder.append(':');
        stringBuilder.append(this.route.address().url().port());
        stringBuilder.append(',');
        stringBuilder.append(" proxy=");
        stringBuilder.append(this.route.proxy());
        stringBuilder.append(" hostAddress=");
        stringBuilder.append(this.route.socketAddress());
        stringBuilder.append(" cipherSuite=");
        Object object = this.handshake;
        if (object == null || (object = ((Handshake)object).cipherSuite()) == null) {
            object = "none";
        }
        stringBuilder.append(object);
        stringBuilder.append(" protocol=");
        stringBuilder.append((Object)this.protocol);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public final void trackFailure$okhttp(RealCall realCall, IOException iOException) {
        synchronized (this) {
            Intrinsics.checkParameterIsNotNull(realCall, "call");
            if (iOException instanceof StreamResetException) {
                if (((StreamResetException)iOException).errorCode == ErrorCode.REFUSED_STREAM) {
                    int n;
                    this.refusedStreamCount = n = this.refusedStreamCount + 1;
                    if (n <= 1) return;
                    this.noNewExchanges = true;
                    ++this.routeFailureCount;
                } else if (((StreamResetException)iOException).errorCode != ErrorCode.CANCEL || !realCall.isCanceled()) {
                    this.noNewExchanges = true;
                    ++this.routeFailureCount;
                }
            } else {
                if (this.isMultiplexed$okhttp()) {
                    if (!(iOException instanceof ConnectionShutdownException)) return;
                }
                this.noNewExchanges = true;
                if (this.successCount != 0) return;
                if (iOException != null) {
                    this.connectFailed$okhttp(realCall.getClient(), this.route, iOException);
                }
                ++this.routeFailureCount;
            }
            return;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J&\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lokhttp3/internal/connection/RealConnection$Companion;", "", "()V", "IDLE_CONNECTION_HEALTHY_NS", "", "MAX_TUNNEL_ATTEMPTS", "", "NPE_THROW_WITH_NULL", "", "newTestConnection", "Lokhttp3/internal/connection/RealConnection;", "connectionPool", "Lokhttp3/internal/connection/RealConnectionPool;", "route", "Lokhttp3/Route;", "socket", "Ljava/net/Socket;", "idleAtNs", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final RealConnection newTestConnection(RealConnectionPool object, Route route, Socket socket, long l) {
            Intrinsics.checkParameterIsNotNull(object, "connectionPool");
            Intrinsics.checkParameterIsNotNull(route, "route");
            Intrinsics.checkParameterIsNotNull(socket, "socket");
            object = new RealConnection((RealConnectionPool)object, route);
            RealConnection.access$setSocket$p((RealConnection)object, socket);
            ((RealConnection)object).setIdleAtNs$okhttp(l);
            return object;
        }
    }

}

