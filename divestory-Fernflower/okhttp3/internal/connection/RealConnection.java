package okhttp3.internal.connection;

import java.io.IOException;
import java.lang.ref.Reference;
import java.net.ConnectException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownServiceException;
import java.net.Proxy.Type;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.CertificatePinner;
import okhttp3.CipherSuite;
import okhttp3.Connection;
import okhttp3.ConnectionSpec;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.TlsVersion;
import okhttp3.internal.Util;
import okhttp3.internal.concurrent.TaskRunner;
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
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.OkHostnameVerifier;
import okhttp3.internal.ws.RealWebSocket;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000ì\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000 {2\u00020\u00012\u00020\u0002:\u0001{B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0006\u00105\u001a\u000206J\u0018\u00107\u001a\u00020\u001d2\u0006\u00108\u001a\u0002092\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J>\u0010:\u001a\u0002062\u0006\u0010;\u001a\u00020\t2\u0006\u0010<\u001a\u00020\t2\u0006\u0010=\u001a\u00020\t2\u0006\u0010>\u001a\u00020\t2\u0006\u0010?\u001a\u00020\u001d2\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020CJ%\u0010D\u001a\u0002062\u0006\u0010E\u001a\u00020F2\u0006\u0010G\u001a\u00020\u00062\u0006\u0010H\u001a\u00020IH\u0000¢\u0006\u0002\bJJ(\u0010K\u001a\u0002062\u0006\u0010;\u001a\u00020\t2\u0006\u0010<\u001a\u00020\t2\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020CH\u0002J\u0010\u0010L\u001a\u0002062\u0006\u0010M\u001a\u00020NH\u0002J0\u0010O\u001a\u0002062\u0006\u0010;\u001a\u00020\t2\u0006\u0010<\u001a\u00020\t2\u0006\u0010=\u001a\u00020\t2\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020CH\u0002J*\u0010P\u001a\u0004\u0018\u00010Q2\u0006\u0010<\u001a\u00020\t2\u0006\u0010=\u001a\u00020\t2\u0006\u0010R\u001a\u00020Q2\u0006\u00108\u001a\u000209H\u0002J\b\u0010S\u001a\u00020QH\u0002J(\u0010T\u001a\u0002062\u0006\u0010M\u001a\u00020N2\u0006\u0010>\u001a\u00020\t2\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020CH\u0002J\n\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J\r\u0010U\u001a\u000206H\u0000¢\u0006\u0002\bVJ%\u0010W\u001a\u00020\u001d2\u0006\u0010X\u001a\u00020Y2\u000e\u0010Z\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010[H\u0000¢\u0006\u0002\b\\J\u000e\u0010]\u001a\u00020\u001d2\u0006\u0010^\u001a\u00020\u001dJ\u001d\u0010_\u001a\u00020`2\u0006\u0010E\u001a\u00020F2\u0006\u0010a\u001a\u00020bH\u0000¢\u0006\u0002\bcJ\u0015\u0010d\u001a\u00020e2\u0006\u0010f\u001a\u00020gH\u0000¢\u0006\u0002\bhJ\r\u0010 \u001a\u000206H\u0000¢\u0006\u0002\biJ\r\u0010!\u001a\u000206H\u0000¢\u0006\u0002\bjJ\u0018\u0010k\u001a\u0002062\u0006\u0010l\u001a\u00020\u00152\u0006\u0010m\u001a\u00020nH\u0016J\u0010\u0010o\u001a\u0002062\u0006\u0010p\u001a\u00020qH\u0016J\b\u0010%\u001a\u00020&H\u0016J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0016\u0010r\u001a\u00020\u001d2\f\u0010s\u001a\b\u0012\u0004\u0012\u00020\u00060[H\u0002J\b\u00101\u001a\u00020(H\u0016J\u0010\u0010t\u001a\u0002062\u0006\u0010>\u001a\u00020\tH\u0002J\u0010\u0010u\u001a\u00020\u001d2\u0006\u00108\u001a\u000209H\u0002J\b\u0010v\u001a\u00020wH\u0016J\u001f\u0010x\u001a\u0002062\u0006\u0010@\u001a\u00020\r2\b\u0010y\u001a\u0004\u0018\u00010IH\u0000¢\u0006\u0002\bzR\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\u00020\u0017X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001c\u001a\u00020\u001d8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u000e\u0010 \u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010!\u001a\u00020\u001dX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u001f\"\u0004\b#\u0010$R\u0010\u0010%\u001a\u0004\u0018\u00010&X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010'\u001a\u0004\u0018\u00010(X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010*\u001a\u00020\tX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u0010\u0010/\u001a\u0004\u0018\u000100X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00101\u001a\u0004\u0018\u00010(X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00102\u001a\u0004\u0018\u000103X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006|"},
   d2 = {"Lokhttp3/internal/connection/RealConnection;", "Lokhttp3/internal/http2/Http2Connection$Listener;", "Lokhttp3/Connection;", "connectionPool", "Lokhttp3/internal/connection/RealConnectionPool;", "route", "Lokhttp3/Route;", "(Lokhttp3/internal/connection/RealConnectionPool;Lokhttp3/Route;)V", "allocationLimit", "", "calls", "", "Ljava/lang/ref/Reference;", "Lokhttp3/internal/connection/RealCall;", "getCalls", "()Ljava/util/List;", "getConnectionPool", "()Lokhttp3/internal/connection/RealConnectionPool;", "handshake", "Lokhttp3/Handshake;", "http2Connection", "Lokhttp3/internal/http2/Http2Connection;", "idleAtNs", "", "getIdleAtNs$okhttp", "()J", "setIdleAtNs$okhttp", "(J)V", "isMultiplexed", "", "isMultiplexed$okhttp", "()Z", "noCoalescedConnections", "noNewExchanges", "getNoNewExchanges", "setNoNewExchanges", "(Z)V", "protocol", "Lokhttp3/Protocol;", "rawSocket", "Ljava/net/Socket;", "refusedStreamCount", "routeFailureCount", "getRouteFailureCount$okhttp", "()I", "setRouteFailureCount$okhttp", "(I)V", "sink", "Lokio/BufferedSink;", "socket", "source", "Lokio/BufferedSource;", "successCount", "cancel", "", "certificateSupportHost", "url", "Lokhttp3/HttpUrl;", "connect", "connectTimeout", "readTimeout", "writeTimeout", "pingIntervalMillis", "connectionRetryEnabled", "call", "Lokhttp3/Call;", "eventListener", "Lokhttp3/EventListener;", "connectFailed", "client", "Lokhttp3/OkHttpClient;", "failedRoute", "failure", "Ljava/io/IOException;", "connectFailed$okhttp", "connectSocket", "connectTls", "connectionSpecSelector", "Lokhttp3/internal/connection/ConnectionSpecSelector;", "connectTunnel", "createTunnel", "Lokhttp3/Request;", "tunnelRequest", "createTunnelRequest", "establishProtocol", "incrementSuccessCount", "incrementSuccessCount$okhttp", "isEligible", "address", "Lokhttp3/Address;", "routes", "", "isEligible$okhttp", "isHealthy", "doExtensiveChecks", "newCodec", "Lokhttp3/internal/http/ExchangeCodec;", "chain", "Lokhttp3/internal/http/RealInterceptorChain;", "newCodec$okhttp", "newWebSocketStreams", "Lokhttp3/internal/ws/RealWebSocket$Streams;", "exchange", "Lokhttp3/internal/connection/Exchange;", "newWebSocketStreams$okhttp", "noCoalescedConnections$okhttp", "noNewExchanges$okhttp", "onSettings", "connection", "settings", "Lokhttp3/internal/http2/Settings;", "onStream", "stream", "Lokhttp3/internal/http2/Http2Stream;", "routeMatchesAny", "candidates", "startHttp2", "supportsUrl", "toString", "", "trackFailure", "e", "trackFailure$okhttp", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class RealConnection extends Http2Connection.Listener implements Connection {
   public static final RealConnection.Companion Companion = new RealConnection.Companion((DefaultConstructorMarker)null);
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

   public RealConnection(RealConnectionPool var1, Route var2) {
      Intrinsics.checkParameterIsNotNull(var1, "connectionPool");
      Intrinsics.checkParameterIsNotNull(var2, "route");
      super();
      this.connectionPool = var1;
      this.route = var2;
      this.allocationLimit = 1;
      this.calls = (List)(new ArrayList());
      this.idleAtNs = Long.MAX_VALUE;
   }

   // $FF: synthetic method
   public static final Socket access$getSocket$p(RealConnection var0) {
      return var0.socket;
   }

   // $FF: synthetic method
   public static final void access$setHandshake$p(RealConnection var0, Handshake var1) {
      var0.handshake = var1;
   }

   private final boolean certificateSupportHost(HttpUrl var1, Handshake var2) {
      List var3 = var2.peerCertificates();
      boolean var4 = ((Collection)var3).isEmpty();
      boolean var5 = true;
      if (var4 ^ true) {
         OkHostnameVerifier var7 = OkHostnameVerifier.INSTANCE;
         String var6 = var1.host();
         Object var8 = var3.get(0);
         if (var8 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
         }

         if (var7.verify(var6, (X509Certificate)var8)) {
            return var5;
         }
      }

      var5 = false;
      return var5;
   }

   private final void connectSocket(int var1, int var2, Call var3, EventListener var4) throws IOException {
      Proxy var5;
      Socket var14;
      label43: {
         var5 = this.route.proxy();
         Address var6 = this.route.address();
         Type var7 = var5.type();
         if (var7 != null) {
            int var8 = RealConnection$WhenMappings.$EnumSwitchMapping$0[var7.ordinal()];
            if (var8 == 1 || var8 == 2) {
               Socket var13 = var6.socketFactory().createSocket();
               var14 = var13;
               if (var13 == null) {
                  Intrinsics.throwNpe();
                  var14 = var13;
               }
               break label43;
            }
         }

         var14 = new Socket(var5);
      }

      this.rawSocket = var14;
      var4.connectStart(var3, this.route.socketAddress(), var5);
      var14.setSoTimeout(var2);

      try {
         Platform.Companion.get().connectSocket(var14, this.route.socketAddress(), var1);
      } catch (ConnectException var9) {
         StringBuilder var11 = new StringBuilder();
         var11.append("Failed to connect to ");
         var11.append(this.route.socketAddress());
         ConnectException var12 = new ConnectException(var11.toString());
         var12.initCause((Throwable)var9);
         throw (Throwable)var12;
      }

      try {
         this.source = Okio.buffer(Okio.source(var14));
         this.sink = Okio.buffer(Okio.sink(var14));
      } catch (NullPointerException var10) {
         if (Intrinsics.areEqual((Object)var10.getMessage(), (Object)"throw with null exception")) {
            throw (Throwable)(new IOException((Throwable)var10));
         }
      }

   }

   private final void connectTls(ConnectionSpecSelector var1) throws IOException {
      SSLSocket var402;
      Throwable var405;
      label2949: {
         SSLSocket var5;
         Throwable var10000;
         label2952: {
            final Address var2 = this.route.address();
            SSLSocketFactory var3 = var2.sslSocketFactory();
            StringBuilder var4 = null;
            var5 = (SSLSocket)null;
            boolean var10001;
            if (var3 == null) {
               try {
                  Intrinsics.throwNpe();
               } catch (Throwable var391) {
                  var10000 = var391;
                  var10001 = false;
                  break label2952;
               }
            }

            Socket var400;
            try {
               var400 = var3.createSocket(this.rawSocket, var2.url().host(), var2.url().port(), true);
            } catch (Throwable var390) {
               var10000 = var390;
               var10001 = false;
               break label2952;
            }

            TypeCastException var398;
            if (var400 != null) {
               label2953: {
                  try {
                     var402 = (SSLSocket)var400;
                  } catch (Throwable var389) {
                     var10000 = var389;
                     var10001 = false;
                     break label2953;
                  }

                  label2954: {
                     ConnectionSpec var406;
                     try {
                        var406 = var1.configureSecureSocket(var402);
                        if (var406.supportsTlsExtensions()) {
                           Platform.Companion.get().configureTlsExtensions(var402, var2.url().host(), var2.protocols());
                        }
                     } catch (Throwable var386) {
                        var10000 = var386;
                        var10001 = false;
                        break label2954;
                     }

                     HostnameVerifier var7;
                     SSLSession var392;
                     final Handshake var408;
                     try {
                        var402.startHandshake();
                        var392 = var402.getSession();
                        Handshake.Companion var6 = Handshake.Companion;
                        Intrinsics.checkExpressionValueIsNotNull(var392, "sslSocketSession");
                        var408 = var6.get(var392);
                        var7 = var2.hostnameVerifier();
                     } catch (Throwable var385) {
                        var10000 = var385;
                        var10001 = false;
                        break label2954;
                     }

                     if (var7 == null) {
                        try {
                           Intrinsics.throwNpe();
                        } catch (Throwable var384) {
                           var10000 = var384;
                           var10001 = false;
                           break label2954;
                        }
                     }

                     label2931: {
                        Object var395;
                        label2930: {
                           try {
                              if (var7.verify(var2.url().host(), var392)) {
                                 break label2931;
                              }

                              List var393 = var408.peerCertificates();
                              if (((Collection)var393).isEmpty() ^ true) {
                                 var395 = var393.get(0);
                                 break label2930;
                              }
                           } catch (Throwable var387) {
                              var10000 = var387;
                              var10001 = false;
                              break label2954;
                           }

                           try {
                              var4 = new StringBuilder();
                              var4.append("Hostname ");
                              var4.append(var2.url().host());
                              var4.append(" not verified (no certificates)");
                              SSLPeerUnverifiedException var394 = new SSLPeerUnverifiedException(var4.toString());
                              throw (Throwable)var394;
                           } catch (Throwable var375) {
                              var10000 = var375;
                              var10001 = false;
                              break label2954;
                           }
                        }

                        if (var395 == null) {
                           try {
                              var398 = new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
                              throw var398;
                           } catch (Throwable var373) {
                              var10000 = var373;
                              var10001 = false;
                              break label2954;
                           }
                        } else {
                           try {
                              X509Certificate var399 = (X509Certificate)var395;
                              var4 = new StringBuilder();
                              var4.append("\n              |Hostname ");
                              var4.append(var2.url().host());
                              var4.append(" not verified:\n              |    certificate: ");
                              var4.append(CertificatePinner.Companion.pin((Certificate)var399));
                              var4.append("\n              |    DN: ");
                              Principal var396 = var399.getSubjectDN();
                              Intrinsics.checkExpressionValueIsNotNull(var396, "cert.subjectDN");
                              var4.append(var396.getName());
                              var4.append("\n              |    subjectAltNames: ");
                              var4.append(OkHostnameVerifier.INSTANCE.allSubjectAltNames(var399));
                              var4.append("\n              ");
                              SSLPeerUnverifiedException var407 = new SSLPeerUnverifiedException(StringsKt.trimMargin$default(var4.toString(), (String)null, 1, (Object)null));
                              throw (Throwable)var407;
                           } catch (Throwable var374) {
                              var10000 = var374;
                              var10001 = false;
                              break label2954;
                           }
                        }
                     }

                     final CertificatePinner var401;
                     try {
                        var401 = var2.certificatePinner();
                     } catch (Throwable var383) {
                        var10000 = var383;
                        var10001 = false;
                        break label2954;
                     }

                     if (var401 == null) {
                        try {
                           Intrinsics.throwNpe();
                        } catch (Throwable var382) {
                           var10000 = var382;
                           var10001 = false;
                           break label2954;
                        }
                     }

                     try {
                        TlsVersion var9 = var408.tlsVersion();
                        CipherSuite var410 = var408.cipherSuite();
                        List var10 = var408.localCertificates();
                        Function0 var11 = new Function0<List<? extends Certificate>>() {
                           public final List<Certificate> invoke() {
                              CertificateChainCleaner var1 = var401.getCertificateChainCleaner$okhttp();
                              if (var1 == null) {
                                 Intrinsics.throwNpe();
                              }

                              return var1.clean(var408.peerCertificates(), var2.url().host());
                           }
                        };
                        Handshake var8 = new Handshake(var9, var410, var10, (Function0)var11);
                        this.handshake = var8;
                        String var409 = var2.url().host();
                        Function0 var397 = new Function0<List<? extends X509Certificate>>() {
                           public final List<X509Certificate> invoke() {
                              Handshake var1 = RealConnection.this.handshake;
                              if (var1 == null) {
                                 Intrinsics.throwNpe();
                              }

                              Iterable var2 = (Iterable)var1.peerCertificates();
                              Collection var4 = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(var2, 10)));
                              Iterator var5 = var2.iterator();

                              while(var5.hasNext()) {
                                 Certificate var3 = (Certificate)var5.next();
                                 if (var3 == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
                                 }

                                 var4.add((X509Certificate)var3);
                              }

                              return (List)var4;
                           }
                        };
                        var401.check$okhttp(var409, (Function0)var397);
                     } catch (Throwable var381) {
                        var10000 = var381;
                        var10001 = false;
                        break label2954;
                     }

                     String var403 = var4;

                     try {
                        if (var406.supportsTlsExtensions()) {
                           var403 = Platform.Companion.get().getSelectedProtocol(var402);
                        }
                     } catch (Throwable var380) {
                        var10000 = var380;
                        var10001 = false;
                        break label2954;
                     }

                     try {
                        this.socket = (Socket)var402;
                        this.source = Okio.buffer(Okio.source((Socket)var402));
                        this.sink = Okio.buffer(Okio.sink((Socket)var402));
                     } catch (Throwable var379) {
                        var10000 = var379;
                        var10001 = false;
                        break label2954;
                     }

                     Protocol var404;
                     if (var403 != null) {
                        try {
                           var404 = Protocol.Companion.get(var403);
                        } catch (Throwable var378) {
                           var10000 = var378;
                           var10001 = false;
                           break label2954;
                        }
                     } else {
                        try {
                           var404 = Protocol.HTTP_1_1;
                        } catch (Throwable var377) {
                           var10000 = var377;
                           var10001 = false;
                           break label2954;
                        }
                     }

                     try {
                        this.protocol = var404;
                     } catch (Throwable var376) {
                        var10000 = var376;
                        var10001 = false;
                        break label2954;
                     }

                     if (var402 != null) {
                        Platform.Companion.get().afterHandshake(var402);
                     }

                     return;
                  }

                  var405 = var10000;
                  break label2949;
               }
            } else {
               label2936:
               try {
                  var398 = new TypeCastException("null cannot be cast to non-null type javax.net.ssl.SSLSocket");
                  throw var398;
               } catch (Throwable var388) {
                  var10000 = var388;
                  var10001 = false;
                  break label2936;
               }
            }
         }

         var405 = var10000;
         var402 = var5;
      }

      if (var402 != null) {
         Platform.Companion.get().afterHandshake(var402);
      }

      if (var402 != null) {
         Util.closeQuietly((Socket)var402);
      }

      throw var405;
   }

   private final void connectTunnel(int var1, int var2, int var3, Call var4, EventListener var5) throws IOException {
      Request var6 = this.createTunnelRequest();
      HttpUrl var7 = var6.url();

      for(int var8 = 0; var8 < 21; ++var8) {
         this.connectSocket(var1, var2, var4, var5);
         var6 = this.createTunnel(var2, var3, var6, var7);
         if (var6 == null) {
            break;
         }

         Socket var9 = this.rawSocket;
         if (var9 != null) {
            Util.closeQuietly(var9);
         }

         this.rawSocket = (Socket)null;
         this.sink = (BufferedSink)null;
         this.source = (BufferedSource)null;
         var5.connectEnd(var4, this.route.socketAddress(), this.route.proxy(), (Protocol)null);
      }

   }

   private final Request createTunnel(int var1, int var2, Request var3, HttpUrl var4) throws IOException {
      StringBuilder var5 = new StringBuilder();
      var5.append("CONNECT ");
      var5.append(Util.toHostHeader(var4, true));
      var5.append(" HTTP/1.1");
      String var11 = var5.toString();

      Response var13;
      do {
         BufferedSource var6 = this.source;
         if (var6 == null) {
            Intrinsics.throwNpe();
         }

         BufferedSink var7 = this.sink;
         if (var7 == null) {
            Intrinsics.throwNpe();
         }

         Http1ExchangeCodec var12 = new Http1ExchangeCodec((OkHttpClient)null, this, var6, var7);
         var6.timeout().timeout((long)var1, TimeUnit.MILLISECONDS);
         var7.timeout().timeout((long)var2, TimeUnit.MILLISECONDS);
         var12.writeRequest(var3.headers(), var11);
         var12.finishRequest();
         Response.Builder var8 = var12.readResponseHeaders(false);
         if (var8 == null) {
            Intrinsics.throwNpe();
         }

         var13 = var8.request(var3).build();
         var12.skipConnectBody(var13);
         int var9 = var13.code();
         if (var9 == 200) {
            if (var6.getBuffer().exhausted() && var7.getBuffer().exhausted()) {
               return null;
            }

            throw (Throwable)(new IOException("TLS tunnel buffered too many bytes!"));
         }

         if (var9 != 407) {
            StringBuilder var10 = new StringBuilder();
            var10.append("Unexpected response code for CONNECT: ");
            var10.append(var13.code());
            throw (Throwable)(new IOException(var10.toString()));
         }

         var3 = this.route.address().proxyAuthenticator().authenticate(this.route, var13);
         if (var3 == null) {
            throw (Throwable)(new IOException("Failed to authenticate with proxy"));
         }
      } while(!StringsKt.equals("close", Response.header$default(var13, "Connection", (String)null, 2, (Object)null), true));

      return var3;
   }

   private final Request createTunnelRequest() throws IOException {
      Request var1 = (new Request.Builder()).url(this.route.address().url()).method("CONNECT", (RequestBody)null).header("Host", Util.toHostHeader(this.route.address().url(), true)).header("Proxy-Connection", "Keep-Alive").header("User-Agent", "okhttp/4.8.1").build();
      Response var2 = (new Response.Builder()).request(var1).protocol(Protocol.HTTP_1_1).code(407).message("Preemptive Authenticate").body(Util.EMPTY_RESPONSE).sentRequestAtMillis(-1L).receivedResponseAtMillis(-1L).header("Proxy-Authenticate", "OkHttp-Preemptive").build();
      Request var3 = this.route.address().proxyAuthenticator().authenticate(this.route, var2);
      if (var3 != null) {
         var1 = var3;
      }

      return var1;
   }

   private final void establishProtocol(ConnectionSpecSelector var1, int var2, Call var3, EventListener var4) throws IOException {
      if (this.route.address().sslSocketFactory() == null) {
         if (this.route.address().protocols().contains(Protocol.H2_PRIOR_KNOWLEDGE)) {
            this.socket = this.rawSocket;
            this.protocol = Protocol.H2_PRIOR_KNOWLEDGE;
            this.startHttp2(var2);
         } else {
            this.socket = this.rawSocket;
            this.protocol = Protocol.HTTP_1_1;
         }
      } else {
         var4.secureConnectStart(var3);
         this.connectTls(var1);
         var4.secureConnectEnd(var3, this.handshake);
         if (this.protocol == Protocol.HTTP_2) {
            this.startHttp2(var2);
         }

      }
   }

   private final boolean routeMatchesAny(List<Route> var1) {
      Iterable var6 = (Iterable)var1;
      boolean var2 = var6 instanceof Collection;
      boolean var3 = true;
      if (!var2 || !((Collection)var6).isEmpty()) {
         Iterator var4 = var6.iterator();

         while(var4.hasNext()) {
            Route var7 = (Route)var4.next();
            boolean var5;
            if (var7.proxy().type() == Type.DIRECT && this.route.proxy().type() == Type.DIRECT && Intrinsics.areEqual((Object)this.route.socketAddress(), (Object)var7.socketAddress())) {
               var5 = true;
            } else {
               var5 = false;
            }

            if (var5) {
               return var3;
            }
         }
      }

      var3 = false;
      return var3;
   }

   private final void startHttp2(int var1) throws IOException {
      Socket var2 = this.socket;
      if (var2 == null) {
         Intrinsics.throwNpe();
      }

      BufferedSource var3 = this.source;
      if (var3 == null) {
         Intrinsics.throwNpe();
      }

      BufferedSink var4 = this.sink;
      if (var4 == null) {
         Intrinsics.throwNpe();
      }

      var2.setSoTimeout(0);
      Http2Connection var5 = (new Http2Connection.Builder(true, TaskRunner.INSTANCE)).socket(var2, this.route.address().url().host(), var3, var4).listener((Http2Connection.Listener)this).pingIntervalMillis(var1).build();
      this.http2Connection = var5;
      this.allocationLimit = Http2Connection.Companion.getDEFAULT_SETTINGS().getMaxConcurrentStreams();
      Http2Connection.start$default(var5, false, (TaskRunner)null, 3, (Object)null);
   }

   private final boolean supportsUrl(HttpUrl var1) {
      if (Util.assertionsEnabled && !Thread.holdsLock(this)) {
         StringBuilder var9 = new StringBuilder();
         var9.append("Thread ");
         Thread var7 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var7, "Thread.currentThread()");
         var9.append(var7.getName());
         var9.append(" MUST hold lock on ");
         var9.append(this);
         throw (Throwable)(new AssertionError(var9.toString()));
      } else {
         HttpUrl var2 = this.route.address().url();
         int var3 = var1.port();
         int var4 = var2.port();
         boolean var5 = false;
         if (var3 != var4) {
            return false;
         } else if (Intrinsics.areEqual((Object)var1.host(), (Object)var2.host())) {
            return true;
         } else {
            boolean var6 = var5;
            if (!this.noCoalescedConnections) {
               Handshake var8 = this.handshake;
               var6 = var5;
               if (var8 != null) {
                  if (var8 == null) {
                     Intrinsics.throwNpe();
                  }

                  var6 = var5;
                  if (this.certificateSupportHost(var1, var8)) {
                     var6 = true;
                  }
               }
            }

            return var6;
         }
      }
   }

   public final void cancel() {
      Socket var1 = this.rawSocket;
      if (var1 != null) {
         Util.closeQuietly(var1);
      }

   }

   public final void connect(int var1, int var2, int var3, int var4, boolean var5, Call var6, EventListener var7) {
      Intrinsics.checkParameterIsNotNull(var6, "call");
      Intrinsics.checkParameterIsNotNull(var7, "eventListener");
      boolean var8;
      if (this.protocol == null) {
         var8 = true;
      } else {
         var8 = false;
      }

      if (!var8) {
         throw (Throwable)(new IllegalStateException("already connected".toString()));
      } else {
         RouteException var9 = (RouteException)null;
         List var10 = this.route.address().connectionSpecs();
         ConnectionSpecSelector var11 = new ConnectionSpecSelector(var10);
         if (this.route.address().sslSocketFactory() == null) {
            if (!var10.contains(ConnectionSpec.CLEARTEXT)) {
               throw (Throwable)(new RouteException((IOException)(new UnknownServiceException("CLEARTEXT communication not enabled for client"))));
            }

            String var17 = this.route.address().url().host();
            if (!Platform.Companion.get().isCleartextTrafficPermitted(var17)) {
               StringBuilder var16 = new StringBuilder();
               var16.append("CLEARTEXT communication to ");
               var16.append(var17);
               var16.append(" not permitted by network security policy");
               throw (Throwable)(new RouteException((IOException)(new UnknownServiceException(var16.toString()))));
            }
         } else if (this.route.address().protocols().contains(Protocol.H2_PRIOR_KNOWLEDGE)) {
            throw (Throwable)(new RouteException((IOException)(new UnknownServiceException("H2_PRIOR_KNOWLEDGE cannot be used with HTTPS"))));
         }

         while(true) {
            IOException var18;
            label82: {
               label81: {
                  Socket var19;
                  label80: {
                     try {
                        if (this.route.requiresTunnel()) {
                           this.connectTunnel(var1, var2, var3, var6, var7);
                           var19 = this.rawSocket;
                           break label80;
                        }
                     } catch (IOException var15) {
                        var18 = var15;
                        break label82;
                     }

                     try {
                        this.connectSocket(var1, var2, var6, var7);
                        break label81;
                     } catch (IOException var14) {
                        var18 = var14;
                        break label82;
                     }
                  }

                  if (var19 == null) {
                     break;
                  }
               }

               try {
                  this.establishProtocol(var11, var4, var6, var7);
                  var7.connectEnd(var6, this.route.socketAddress(), this.route.proxy(), this.protocol);
                  break;
               } catch (IOException var13) {
                  var18 = var13;
               }
            }

            Socket var12 = this.socket;
            if (var12 != null) {
               Util.closeQuietly(var12);
            }

            var12 = this.rawSocket;
            if (var12 != null) {
               Util.closeQuietly(var12);
            }

            var12 = (Socket)null;
            this.socket = var12;
            this.rawSocket = var12;
            this.source = (BufferedSource)null;
            this.sink = (BufferedSink)null;
            this.handshake = (Handshake)null;
            this.protocol = (Protocol)null;
            this.http2Connection = (Http2Connection)null;
            this.allocationLimit = 1;
            var7.connectFailed(var6, this.route.socketAddress(), this.route.proxy(), (Protocol)null, var18);
            if (var9 == null) {
               var9 = new RouteException(var18);
            } else {
               var9.addConnectException(var18);
            }

            if (!var5 || !var11.connectionFailed(var18)) {
               throw (Throwable)var9;
            }
         }

         if (this.route.requiresTunnel() && this.rawSocket == null) {
            throw (Throwable)(new RouteException((IOException)(new ProtocolException("Too many tunnel connections attempted: 21"))));
         } else {
            this.idleAtNs = System.nanoTime();
         }
      }
   }

   public final void connectFailed$okhttp(OkHttpClient var1, Route var2, IOException var3) {
      Intrinsics.checkParameterIsNotNull(var1, "client");
      Intrinsics.checkParameterIsNotNull(var2, "failedRoute");
      Intrinsics.checkParameterIsNotNull(var3, "failure");
      if (var2.proxy().type() != Type.DIRECT) {
         Address var4 = var2.address();
         var4.proxySelector().connectFailed(var4.url().uri(), var2.proxy().address(), var3);
      }

      var1.getRouteDatabase().failed(var2);
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

   public Handshake handshake() {
      return this.handshake;
   }

   public final void incrementSuccessCount$okhttp() {
      synchronized(this){}

      try {
         ++this.successCount;
      } finally {
         ;
      }

   }

   public final boolean isEligible$okhttp(Address var1, List<Route> var2) {
      Intrinsics.checkParameterIsNotNull(var1, "address");
      if (Util.assertionsEnabled && !Thread.holdsLock(this)) {
         StringBuilder var12 = new StringBuilder();
         var12.append("Thread ");
         Thread var10 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var10, "Thread.currentThread()");
         var12.append(var10.getName());
         var12.append(" MUST hold lock on ");
         var12.append(this);
         throw (Throwable)(new AssertionError(var12.toString()));
      } else {
         if (this.calls.size() < this.allocationLimit && !this.noNewExchanges) {
            if (!this.route.address().equalsNonHost$okhttp(var1)) {
               return false;
            }

            if (Intrinsics.areEqual((Object)var1.url().host(), (Object)this.route().address().url().host())) {
               return true;
            }

            if (this.http2Connection == null) {
               return false;
            }

            if (var2 != null && this.routeMatchesAny(var2)) {
               if (var1.hostnameVerifier() != OkHostnameVerifier.INSTANCE) {
                  return false;
               }

               if (!this.supportsUrl(var1.url())) {
                  return false;
               }

               boolean var10001;
               CertificatePinner var11;
               try {
                  var11 = var1.certificatePinner();
               } catch (SSLPeerUnverifiedException var8) {
                  var10001 = false;
                  return false;
               }

               if (var11 == null) {
                  try {
                     Intrinsics.throwNpe();
                  } catch (SSLPeerUnverifiedException var7) {
                     var10001 = false;
                     return false;
                  }
               }

               Handshake var3;
               String var9;
               try {
                  var9 = var1.url().host();
                  var3 = this.handshake();
               } catch (SSLPeerUnverifiedException var6) {
                  var10001 = false;
                  return false;
               }

               if (var3 == null) {
                  try {
                     Intrinsics.throwNpe();
                  } catch (SSLPeerUnverifiedException var5) {
                     var10001 = false;
                     return false;
                  }
               }

               try {
                  var11.check(var9, var3.peerCertificates());
                  return true;
               } catch (SSLPeerUnverifiedException var4) {
                  var10001 = false;
               }
            }
         }

         return false;
      }
   }

   public final boolean isHealthy(boolean var1) {
      if (Util.assertionsEnabled && Thread.holdsLock(this)) {
         StringBuilder var11 = new StringBuilder();
         var11.append("Thread ");
         Thread var12 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var12, "Thread.currentThread()");
         var11.append(var12.getName());
         var11.append(" MUST NOT hold lock on ");
         var11.append(this);
         throw (Throwable)(new AssertionError(var11.toString()));
      } else {
         long var4 = System.nanoTime();
         Socket var6 = this.rawSocket;
         if (var6 == null) {
            Intrinsics.throwNpe();
         }

         Socket var2 = this.socket;
         if (var2 == null) {
            Intrinsics.throwNpe();
         }

         BufferedSource var3 = this.source;
         if (var3 == null) {
            Intrinsics.throwNpe();
         }

         if (!var6.isClosed() && !var2.isClosed() && !var2.isInputShutdown() && !var2.isOutputShutdown()) {
            Http2Connection var13 = this.http2Connection;
            if (var13 != null) {
               return var13.isHealthy(var4);
            } else {
               synchronized(this){}

               long var7;
               try {
                  var7 = this.idleAtNs;
               } finally {
                  ;
               }

               return var4 - var7 >= 10000000000L && var1 ? Util.isHealthy(var2, var3) : true;
            }
         } else {
            return false;
         }
      }
   }

   public final boolean isMultiplexed$okhttp() {
      boolean var1;
      if (this.http2Connection != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final ExchangeCodec newCodec$okhttp(OkHttpClient var1, RealInterceptorChain var2) throws SocketException {
      Intrinsics.checkParameterIsNotNull(var1, "client");
      Intrinsics.checkParameterIsNotNull(var2, "chain");
      Socket var3 = this.socket;
      if (var3 == null) {
         Intrinsics.throwNpe();
      }

      BufferedSource var4 = this.source;
      if (var4 == null) {
         Intrinsics.throwNpe();
      }

      BufferedSink var5 = this.sink;
      if (var5 == null) {
         Intrinsics.throwNpe();
      }

      Http2Connection var6 = this.http2Connection;
      ExchangeCodec var7;
      if (var6 != null) {
         var7 = (ExchangeCodec)(new Http2ExchangeCodec(var1, this, var2, var6));
      } else {
         var3.setSoTimeout(var2.readTimeoutMillis());
         var4.timeout().timeout((long)var2.getReadTimeoutMillis$okhttp(), TimeUnit.MILLISECONDS);
         var5.timeout().timeout((long)var2.getWriteTimeoutMillis$okhttp(), TimeUnit.MILLISECONDS);
         var7 = (ExchangeCodec)(new Http1ExchangeCodec(var1, this, var4, var5));
      }

      return var7;
   }

   public final RealWebSocket.Streams newWebSocketStreams$okhttp(final Exchange var1) throws SocketException {
      Intrinsics.checkParameterIsNotNull(var1, "exchange");
      Socket var2 = this.socket;
      if (var2 == null) {
         Intrinsics.throwNpe();
      }

      final BufferedSource var3 = this.source;
      if (var3 == null) {
         Intrinsics.throwNpe();
      }

      final BufferedSink var4 = this.sink;
      if (var4 == null) {
         Intrinsics.throwNpe();
      }

      var2.setSoTimeout(0);
      this.noNewExchanges$okhttp();
      return (RealWebSocket.Streams)(new RealWebSocket.Streams(true, var3, var4) {
         public void close() {
            var1.bodyComplete(-1L, true, true, (IOException)null);
         }
      });
   }

   public final void noCoalescedConnections$okhttp() {
      synchronized(this){}

      try {
         this.noCoalescedConnections = true;
      } finally {
         ;
      }

   }

   public final void noNewExchanges$okhttp() {
      synchronized(this){}

      try {
         this.noNewExchanges = true;
      } finally {
         ;
      }

   }

   public void onSettings(Http2Connection var1, Settings var2) {
      synchronized(this){}

      try {
         Intrinsics.checkParameterIsNotNull(var1, "connection");
         Intrinsics.checkParameterIsNotNull(var2, "settings");
         this.allocationLimit = var2.getMaxConcurrentStreams();
      } finally {
         ;
      }

   }

   public void onStream(Http2Stream var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "stream");
      var1.close(ErrorCode.REFUSED_STREAM, (IOException)null);
   }

   public Protocol protocol() {
      Protocol var1 = this.protocol;
      if (var1 == null) {
         Intrinsics.throwNpe();
      }

      return var1;
   }

   public Route route() {
      return this.route;
   }

   public final void setIdleAtNs$okhttp(long var1) {
      this.idleAtNs = var1;
   }

   public final void setNoNewExchanges(boolean var1) {
      this.noNewExchanges = var1;
   }

   public final void setRouteFailureCount$okhttp(int var1) {
      this.routeFailureCount = var1;
   }

   public Socket socket() {
      Socket var1 = this.socket;
      if (var1 == null) {
         Intrinsics.throwNpe();
      }

      return var1;
   }

   public String toString() {
      StringBuilder var1;
      Object var3;
      label12: {
         var1 = new StringBuilder();
         var1.append("Connection{");
         var1.append(this.route.address().url().host());
         var1.append(':');
         var1.append(this.route.address().url().port());
         var1.append(',');
         var1.append(" proxy=");
         var1.append(this.route.proxy());
         var1.append(" hostAddress=");
         var1.append(this.route.socketAddress());
         var1.append(" cipherSuite=");
         Handshake var2 = this.handshake;
         if (var2 != null) {
            var3 = var2.cipherSuite();
            if (var3 != null) {
               break label12;
            }
         }

         var3 = "none";
      }

      var1.append(var3);
      var1.append(" protocol=");
      var1.append(this.protocol);
      var1.append('}');
      return var1.toString();
   }

   public final void trackFailure$okhttp(RealCall var1, IOException var2) {
      synchronized(this){}

      Throwable var10000;
      label719: {
         boolean var10001;
         label720: {
            int var3;
            label721: {
               try {
                  Intrinsics.checkParameterIsNotNull(var1, "call");
                  if (var2 instanceof StreamResetException) {
                     if (((StreamResetException)var2).errorCode != ErrorCode.REFUSED_STREAM) {
                        break label720;
                     }

                     var3 = this.refusedStreamCount + 1;
                     this.refusedStreamCount = var3;
                     break label721;
                  }
               } catch (Throwable var75) {
                  var10000 = var75;
                  var10001 = false;
                  break label719;
               }

               try {
                  if (this.isMultiplexed$okhttp() && !(var2 instanceof ConnectionShutdownException)) {
                     return;
                  }
               } catch (Throwable var74) {
                  var10000 = var74;
                  var10001 = false;
                  break label719;
               }

               try {
                  this.noNewExchanges = true;
                  if (this.successCount != 0) {
                     return;
                  }
               } catch (Throwable var73) {
                  var10000 = var73;
                  var10001 = false;
                  break label719;
               }

               if (var2 != null) {
                  try {
                     this.connectFailed$okhttp(var1.getClient(), this.route, var2);
                  } catch (Throwable var72) {
                     var10000 = var72;
                     var10001 = false;
                     break label719;
                  }
               }

               try {
                  ++this.routeFailureCount;
                  return;
               } catch (Throwable var71) {
                  var10000 = var71;
                  var10001 = false;
                  break label719;
               }
            }

            if (var3 <= 1) {
               return;
            }

            try {
               this.noNewExchanges = true;
               ++this.routeFailureCount;
               return;
            } catch (Throwable var68) {
               var10000 = var68;
               var10001 = false;
               break label719;
            }
         }

         try {
            if (((StreamResetException)var2).errorCode == ErrorCode.CANCEL && var1.isCanceled()) {
               return;
            }
         } catch (Throwable var70) {
            var10000 = var70;
            var10001 = false;
            break label719;
         }

         label687:
         try {
            this.noNewExchanges = true;
            ++this.routeFailureCount;
            return;
         } catch (Throwable var69) {
            var10000 = var69;
            var10001 = false;
            break label687;
         }
      }

      Throwable var76 = var10000;
      throw var76;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0012"},
      d2 = {"Lokhttp3/internal/connection/RealConnection$Companion;", "", "()V", "IDLE_CONNECTION_HEALTHY_NS", "", "MAX_TUNNEL_ATTEMPTS", "", "NPE_THROW_WITH_NULL", "", "newTestConnection", "Lokhttp3/internal/connection/RealConnection;", "connectionPool", "Lokhttp3/internal/connection/RealConnectionPool;", "route", "Lokhttp3/Route;", "socket", "Ljava/net/Socket;", "idleAtNs", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final RealConnection newTestConnection(RealConnectionPool var1, Route var2, Socket var3, long var4) {
         Intrinsics.checkParameterIsNotNull(var1, "connectionPool");
         Intrinsics.checkParameterIsNotNull(var2, "route");
         Intrinsics.checkParameterIsNotNull(var3, "socket");
         RealConnection var6 = new RealConnection(var1, var2);
         var6.socket = var3;
         var6.setIdleAtNs$okhttp(var4);
         return var6;
      }
   }
}
