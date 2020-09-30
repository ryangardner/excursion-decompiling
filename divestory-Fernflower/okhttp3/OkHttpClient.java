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

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000î\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0016\u0018\u0000 y2\u00020\u00012\u00020\u00022\u00020\u0003:\u0002xyB\u0007\b\u0016¢\u0006\u0002\u0010\u0004B\u000f\b\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\r\u0010\b\u001a\u00020\tH\u0007¢\u0006\u0002\bSJ\u000f\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007¢\u0006\u0002\bTJ\r\u0010\u000e\u001a\u00020\u000fH\u0007¢\u0006\u0002\bUJ\r\u0010\u0014\u001a\u00020\u0015H\u0007¢\u0006\u0002\bVJ\r\u0010\u0017\u001a\u00020\u000fH\u0007¢\u0006\u0002\bWJ\r\u0010\u0018\u001a\u00020\u0019H\u0007¢\u0006\u0002\bXJ\u0013\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH\u0007¢\u0006\u0002\bYJ\r\u0010\u001f\u001a\u00020 H\u0007¢\u0006\u0002\bZJ\r\u0010\"\u001a\u00020#H\u0007¢\u0006\u0002\b[J\r\u0010%\u001a\u00020&H\u0007¢\u0006\u0002\b\\J\r\u0010(\u001a\u00020)H\u0007¢\u0006\u0002\b]J\r\u0010+\u001a\u00020,H\u0007¢\u0006\u0002\b^J\r\u0010.\u001a\u00020,H\u0007¢\u0006\u0002\b_J\r\u0010/\u001a\u000200H\u0007¢\u0006\u0002\b`J\u0013\u00102\u001a\b\u0012\u0004\u0012\u0002030\u001cH\u0007¢\u0006\u0002\baJ\u0013\u00107\u001a\b\u0012\u0004\u0012\u0002030\u001cH\u0007¢\u0006\u0002\bbJ\b\u0010c\u001a\u00020\u0006H\u0016J\u0010\u0010d\u001a\u00020e2\u0006\u0010f\u001a\u00020gH\u0016J\u0018\u0010h\u001a\u00020i2\u0006\u0010f\u001a\u00020g2\u0006\u0010j\u001a\u00020kH\u0016J\r\u00108\u001a\u00020\u000fH\u0007¢\u0006\u0002\blJ\u0013\u00109\u001a\b\u0012\u0004\u0012\u00020:0\u001cH\u0007¢\u0006\u0002\bmJ\u000f\u0010;\u001a\u0004\u0018\u00010<H\u0007¢\u0006\u0002\bnJ\r\u0010>\u001a\u00020\tH\u0007¢\u0006\u0002\boJ\r\u0010?\u001a\u00020@H\u0007¢\u0006\u0002\bpJ\r\u0010B\u001a\u00020\u000fH\u0007¢\u0006\u0002\bqJ\r\u0010C\u001a\u00020,H\u0007¢\u0006\u0002\brJ\r\u0010H\u001a\u00020IH\u0007¢\u0006\u0002\bsJ\r\u0010K\u001a\u00020LH\u0007¢\u0006\u0002\btJ\b\u0010u\u001a\u00020vH\u0002J\r\u0010O\u001a\u00020\u000fH\u0007¢\u0006\u0002\bwR\u0013\u0010\b\u001a\u00020\t8G¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\nR\u0015\u0010\u000b\u001a\u0004\u0018\u00010\f8G¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\rR\u0013\u0010\u000e\u001a\u00020\u000f8G¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0010R\u0015\u0010\u0011\u001a\u0004\u0018\u00010\u00128G¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0013R\u0013\u0010\u0014\u001a\u00020\u00158G¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0016R\u0013\u0010\u0017\u001a\u00020\u000f8G¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010R\u0013\u0010\u0018\u001a\u00020\u00198G¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u001aR\u0019\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c8G¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001eR\u0013\u0010\u001f\u001a\u00020 8G¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010!R\u0013\u0010\"\u001a\u00020#8G¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010$R\u0013\u0010%\u001a\u00020&8G¢\u0006\b\n\u0000\u001a\u0004\b%\u0010'R\u0013\u0010(\u001a\u00020)8G¢\u0006\b\n\u0000\u001a\u0004\b(\u0010*R\u0013\u0010+\u001a\u00020,8G¢\u0006\b\n\u0000\u001a\u0004\b+\u0010-R\u0013\u0010.\u001a\u00020,8G¢\u0006\b\n\u0000\u001a\u0004\b.\u0010-R\u0013\u0010/\u001a\u0002008G¢\u0006\b\n\u0000\u001a\u0004\b/\u00101R\u0019\u00102\u001a\b\u0012\u0004\u0012\u0002030\u001c8G¢\u0006\b\n\u0000\u001a\u0004\b2\u0010\u001eR\u0013\u00104\u001a\u0002058G¢\u0006\b\n\u0000\u001a\u0004\b4\u00106R\u0019\u00107\u001a\b\u0012\u0004\u0012\u0002030\u001c8G¢\u0006\b\n\u0000\u001a\u0004\b7\u0010\u001eR\u0013\u00108\u001a\u00020\u000f8G¢\u0006\b\n\u0000\u001a\u0004\b8\u0010\u0010R\u0019\u00109\u001a\b\u0012\u0004\u0012\u00020:0\u001c8G¢\u0006\b\n\u0000\u001a\u0004\b9\u0010\u001eR\u0015\u0010;\u001a\u0004\u0018\u00010<8G¢\u0006\b\n\u0000\u001a\u0004\b;\u0010=R\u0013\u0010>\u001a\u00020\t8G¢\u0006\b\n\u0000\u001a\u0004\b>\u0010\nR\u0013\u0010?\u001a\u00020@8G¢\u0006\b\n\u0000\u001a\u0004\b?\u0010AR\u0013\u0010B\u001a\u00020\u000f8G¢\u0006\b\n\u0000\u001a\u0004\bB\u0010\u0010R\u0013\u0010C\u001a\u00020,8G¢\u0006\b\n\u0000\u001a\u0004\bC\u0010-R\u0011\u0010D\u001a\u00020E¢\u0006\b\n\u0000\u001a\u0004\bF\u0010GR\u0013\u0010H\u001a\u00020I8G¢\u0006\b\n\u0000\u001a\u0004\bH\u0010JR\u0011\u0010K\u001a\u00020L8G¢\u0006\u0006\u001a\u0004\bK\u0010MR\u0010\u0010N\u001a\u0004\u0018\u00010LX\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010O\u001a\u00020\u000f8G¢\u0006\b\n\u0000\u001a\u0004\bO\u0010\u0010R\u0015\u0010P\u001a\u0004\u0018\u00010Q8G¢\u0006\b\n\u0000\u001a\u0004\bP\u0010R¨\u0006z"},
   d2 = {"Lokhttp3/OkHttpClient;", "", "Lokhttp3/Call$Factory;", "Lokhttp3/WebSocket$Factory;", "()V", "builder", "Lokhttp3/OkHttpClient$Builder;", "(Lokhttp3/OkHttpClient$Builder;)V", "authenticator", "Lokhttp3/Authenticator;", "()Lokhttp3/Authenticator;", "cache", "Lokhttp3/Cache;", "()Lokhttp3/Cache;", "callTimeoutMillis", "", "()I", "certificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "()Lokhttp3/internal/tls/CertificateChainCleaner;", "certificatePinner", "Lokhttp3/CertificatePinner;", "()Lokhttp3/CertificatePinner;", "connectTimeoutMillis", "connectionPool", "Lokhttp3/ConnectionPool;", "()Lokhttp3/ConnectionPool;", "connectionSpecs", "", "Lokhttp3/ConnectionSpec;", "()Ljava/util/List;", "cookieJar", "Lokhttp3/CookieJar;", "()Lokhttp3/CookieJar;", "dispatcher", "Lokhttp3/Dispatcher;", "()Lokhttp3/Dispatcher;", "dns", "Lokhttp3/Dns;", "()Lokhttp3/Dns;", "eventListenerFactory", "Lokhttp3/EventListener$Factory;", "()Lokhttp3/EventListener$Factory;", "followRedirects", "", "()Z", "followSslRedirects", "hostnameVerifier", "Ljavax/net/ssl/HostnameVerifier;", "()Ljavax/net/ssl/HostnameVerifier;", "interceptors", "Lokhttp3/Interceptor;", "minWebSocketMessageToCompress", "", "()J", "networkInterceptors", "pingIntervalMillis", "protocols", "Lokhttp3/Protocol;", "proxy", "Ljava/net/Proxy;", "()Ljava/net/Proxy;", "proxyAuthenticator", "proxySelector", "Ljava/net/ProxySelector;", "()Ljava/net/ProxySelector;", "readTimeoutMillis", "retryOnConnectionFailure", "routeDatabase", "Lokhttp3/internal/connection/RouteDatabase;", "getRouteDatabase", "()Lokhttp3/internal/connection/RouteDatabase;", "socketFactory", "Ljavax/net/SocketFactory;", "()Ljavax/net/SocketFactory;", "sslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "()Ljavax/net/ssl/SSLSocketFactory;", "sslSocketFactoryOrNull", "writeTimeoutMillis", "x509TrustManager", "Ljavax/net/ssl/X509TrustManager;", "()Ljavax/net/ssl/X509TrustManager;", "-deprecated_authenticator", "-deprecated_cache", "-deprecated_callTimeoutMillis", "-deprecated_certificatePinner", "-deprecated_connectTimeoutMillis", "-deprecated_connectionPool", "-deprecated_connectionSpecs", "-deprecated_cookieJar", "-deprecated_dispatcher", "-deprecated_dns", "-deprecated_eventListenerFactory", "-deprecated_followRedirects", "-deprecated_followSslRedirects", "-deprecated_hostnameVerifier", "-deprecated_interceptors", "-deprecated_networkInterceptors", "newBuilder", "newCall", "Lokhttp3/Call;", "request", "Lokhttp3/Request;", "newWebSocket", "Lokhttp3/WebSocket;", "listener", "Lokhttp3/WebSocketListener;", "-deprecated_pingIntervalMillis", "-deprecated_protocols", "-deprecated_proxy", "-deprecated_proxyAuthenticator", "-deprecated_proxySelector", "-deprecated_readTimeoutMillis", "-deprecated_retryOnConnectionFailure", "-deprecated_socketFactory", "-deprecated_sslSocketFactory", "verifyClientState", "", "-deprecated_writeTimeoutMillis", "Builder", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public class OkHttpClient implements Cloneable, Call.Factory, WebSocket.Factory {
   public static final OkHttpClient.Companion Companion = new OkHttpClient.Companion((DefaultConstructorMarker)null);
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
      DEFAULT_PROTOCOLS = Util.immutableListOf(Protocol.HTTP_2, Protocol.HTTP_1_1);
      DEFAULT_CONNECTION_SPECS = Util.immutableListOf(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT);
   }

   public OkHttpClient() {
      this(new OkHttpClient.Builder());
   }

   public OkHttpClient(OkHttpClient.Builder var1) {
      Intrinsics.checkParameterIsNotNull(var1, "builder");
      super();
      this.dispatcher = var1.getDispatcher$okhttp();
      this.connectionPool = var1.getConnectionPool$okhttp();
      this.interceptors = Util.toImmutableList(var1.getInterceptors$okhttp());
      this.networkInterceptors = Util.toImmutableList(var1.getNetworkInterceptors$okhttp());
      this.eventListenerFactory = var1.getEventListenerFactory$okhttp();
      this.retryOnConnectionFailure = var1.getRetryOnConnectionFailure$okhttp();
      this.authenticator = var1.getAuthenticator$okhttp();
      this.followRedirects = var1.getFollowRedirects$okhttp();
      this.followSslRedirects = var1.getFollowSslRedirects$okhttp();
      this.cookieJar = var1.getCookieJar$okhttp();
      this.cache = var1.getCache$okhttp();
      this.dns = var1.getDns$okhttp();
      this.proxy = var1.getProxy$okhttp();
      ProxySelector var2;
      if (var1.getProxy$okhttp() != null) {
         var2 = (ProxySelector)NullProxySelector.INSTANCE;
      } else {
         var2 = var1.getProxySelector$okhttp();
         if (var2 == null) {
            var2 = ProxySelector.getDefault();
         }

         if (var2 == null) {
            var2 = (ProxySelector)NullProxySelector.INSTANCE;
         }
      }

      this.proxySelector = var2;
      this.proxyAuthenticator = var1.getProxyAuthenticator$okhttp();
      this.socketFactory = var1.getSocketFactory$okhttp();
      this.connectionSpecs = var1.getConnectionSpecs$okhttp();
      this.protocols = var1.getProtocols$okhttp();
      this.hostnameVerifier = var1.getHostnameVerifier$okhttp();
      this.callTimeoutMillis = var1.getCallTimeout$okhttp();
      this.connectTimeoutMillis = var1.getConnectTimeout$okhttp();
      this.readTimeoutMillis = var1.getReadTimeout$okhttp();
      this.writeTimeoutMillis = var1.getWriteTimeout$okhttp();
      this.pingIntervalMillis = var1.getPingInterval$okhttp();
      this.minWebSocketMessageToCompress = var1.getMinWebSocketMessageToCompress$okhttp();
      RouteDatabase var8 = var1.getRouteDatabase$okhttp();
      if (var8 == null) {
         var8 = new RouteDatabase();
      }

      this.routeDatabase = var8;
      Iterable var9 = (Iterable)this.connectionSpecs;
      boolean var3 = var9 instanceof Collection;
      boolean var4 = true;
      boolean var5;
      if (var3 && ((Collection)var9).isEmpty()) {
         var5 = var4;
      } else {
         Iterator var10 = var9.iterator();

         while(true) {
            var5 = var4;
            if (!var10.hasNext()) {
               break;
            }

            if (((ConnectionSpec)var10.next()).isTls()) {
               var5 = false;
               break;
            }
         }
      }

      if (var5) {
         this.sslSocketFactoryOrNull = (SSLSocketFactory)null;
         this.certificateChainCleaner = (CertificateChainCleaner)null;
         this.x509TrustManager = (X509TrustManager)null;
         this.certificatePinner = CertificatePinner.DEFAULT;
      } else {
         CertificateChainCleaner var7;
         X509TrustManager var12;
         CertificatePinner var14;
         if (var1.getSslSocketFactoryOrNull$okhttp() != null) {
            this.sslSocketFactoryOrNull = var1.getSslSocketFactoryOrNull$okhttp();
            CertificateChainCleaner var11 = var1.getCertificateChainCleaner$okhttp();
            if (var11 == null) {
               Intrinsics.throwNpe();
            }

            this.certificateChainCleaner = var11;
            var12 = var1.getX509TrustManagerOrNull$okhttp();
            if (var12 == null) {
               Intrinsics.throwNpe();
            }

            this.x509TrustManager = var12;
            var14 = var1.getCertificatePinner$okhttp();
            var7 = this.certificateChainCleaner;
            if (var7 == null) {
               Intrinsics.throwNpe();
            }

            this.certificatePinner = var14.withCertificateChainCleaner$okhttp(var7);
         } else {
            this.x509TrustManager = Platform.Companion.get().platformTrustManager();
            Platform var15 = Platform.Companion.get();
            X509TrustManager var6 = this.x509TrustManager;
            if (var6 == null) {
               Intrinsics.throwNpe();
            }

            this.sslSocketFactoryOrNull = var15.newSslSocketFactory(var6);
            CertificateChainCleaner.Companion var13 = CertificateChainCleaner.Companion;
            var12 = this.x509TrustManager;
            if (var12 == null) {
               Intrinsics.throwNpe();
            }

            this.certificateChainCleaner = var13.get(var12);
            var14 = var1.getCertificatePinner$okhttp();
            var7 = this.certificateChainCleaner;
            if (var7 == null) {
               Intrinsics.throwNpe();
            }

            this.certificatePinner = var14.withCertificateChainCleaner$okhttp(var7);
         }
      }

      this.verifyClientState();
   }

   private final void verifyClientState() {
      List var1 = this.interceptors;
      if (var1 != null) {
         boolean var2 = var1.contains((Object)null);
         boolean var3 = true;
         StringBuilder var7;
         if (!(var2 ^ true)) {
            var7 = new StringBuilder();
            var7.append("Null interceptor: ");
            var7.append(this.interceptors);
            throw (Throwable)(new IllegalStateException(var7.toString().toString()));
         } else {
            var1 = this.networkInterceptors;
            if (var1 != null) {
               if (!(var1.contains((Object)null) ^ true)) {
                  var7 = new StringBuilder();
                  var7.append("Null network interceptor: ");
                  var7.append(this.networkInterceptors);
                  throw (Throwable)(new IllegalStateException(var7.toString().toString()));
               } else {
                  boolean var4;
                  label94: {
                     Iterable var5 = (Iterable)this.connectionSpecs;
                     if (!(var5 instanceof Collection) || !((Collection)var5).isEmpty()) {
                        Iterator var6 = var5.iterator();

                        while(var6.hasNext()) {
                           if (((ConnectionSpec)var6.next()).isTls()) {
                              var4 = false;
                              break label94;
                           }
                        }
                     }

                     var4 = true;
                  }

                  if (var4) {
                     if (this.sslSocketFactoryOrNull == null) {
                        var4 = true;
                     } else {
                        var4 = false;
                     }

                     if (!var4) {
                        throw (Throwable)(new IllegalStateException("Check failed.".toString()));
                     }

                     if (this.certificateChainCleaner == null) {
                        var4 = true;
                     } else {
                        var4 = false;
                     }

                     if (!var4) {
                        throw (Throwable)(new IllegalStateException("Check failed.".toString()));
                     }

                     if (this.x509TrustManager == null) {
                        var4 = var3;
                     } else {
                        var4 = false;
                     }

                     if (!var4) {
                        throw (Throwable)(new IllegalStateException("Check failed.".toString()));
                     }

                     if (!Intrinsics.areEqual((Object)this.certificatePinner, (Object)CertificatePinner.DEFAULT)) {
                        throw (Throwable)(new IllegalStateException("Check failed.".toString()));
                     }
                  } else {
                     if (this.sslSocketFactoryOrNull == null) {
                        throw (Throwable)(new IllegalStateException("sslSocketFactory == null".toString()));
                     }

                     if (this.certificateChainCleaner == null) {
                        throw (Throwable)(new IllegalStateException("certificateChainCleaner == null".toString()));
                     }

                     if (this.x509TrustManager == null) {
                        throw (Throwable)(new IllegalStateException("x509TrustManager == null".toString()));
                     }
                  }

               }
            } else {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.List<okhttp3.Interceptor?>");
            }
         }
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.List<okhttp3.Interceptor?>");
      }
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "authenticator",
   imports = {}
)
   )
   public final Authenticator _deprecated_authenticator/* $FF was: -deprecated_authenticator*/() {
      return this.authenticator;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "cache",
   imports = {}
)
   )
   public final Cache _deprecated_cache/* $FF was: -deprecated_cache*/() {
      return this.cache;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "callTimeoutMillis",
   imports = {}
)
   )
   public final int _deprecated_callTimeoutMillis/* $FF was: -deprecated_callTimeoutMillis*/() {
      return this.callTimeoutMillis;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "certificatePinner",
   imports = {}
)
   )
   public final CertificatePinner _deprecated_certificatePinner/* $FF was: -deprecated_certificatePinner*/() {
      return this.certificatePinner;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "connectTimeoutMillis",
   imports = {}
)
   )
   public final int _deprecated_connectTimeoutMillis/* $FF was: -deprecated_connectTimeoutMillis*/() {
      return this.connectTimeoutMillis;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "connectionPool",
   imports = {}
)
   )
   public final ConnectionPool _deprecated_connectionPool/* $FF was: -deprecated_connectionPool*/() {
      return this.connectionPool;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "connectionSpecs",
   imports = {}
)
   )
   public final List<ConnectionSpec> _deprecated_connectionSpecs/* $FF was: -deprecated_connectionSpecs*/() {
      return this.connectionSpecs;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "cookieJar",
   imports = {}
)
   )
   public final CookieJar _deprecated_cookieJar/* $FF was: -deprecated_cookieJar*/() {
      return this.cookieJar;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "dispatcher",
   imports = {}
)
   )
   public final Dispatcher _deprecated_dispatcher/* $FF was: -deprecated_dispatcher*/() {
      return this.dispatcher;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "dns",
   imports = {}
)
   )
   public final Dns _deprecated_dns/* $FF was: -deprecated_dns*/() {
      return this.dns;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "eventListenerFactory",
   imports = {}
)
   )
   public final EventListener.Factory _deprecated_eventListenerFactory/* $FF was: -deprecated_eventListenerFactory*/() {
      return this.eventListenerFactory;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "followRedirects",
   imports = {}
)
   )
   public final boolean _deprecated_followRedirects/* $FF was: -deprecated_followRedirects*/() {
      return this.followRedirects;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "followSslRedirects",
   imports = {}
)
   )
   public final boolean _deprecated_followSslRedirects/* $FF was: -deprecated_followSslRedirects*/() {
      return this.followSslRedirects;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "hostnameVerifier",
   imports = {}
)
   )
   public final HostnameVerifier _deprecated_hostnameVerifier/* $FF was: -deprecated_hostnameVerifier*/() {
      return this.hostnameVerifier;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "interceptors",
   imports = {}
)
   )
   public final List<Interceptor> _deprecated_interceptors/* $FF was: -deprecated_interceptors*/() {
      return this.interceptors;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "networkInterceptors",
   imports = {}
)
   )
   public final List<Interceptor> _deprecated_networkInterceptors/* $FF was: -deprecated_networkInterceptors*/() {
      return this.networkInterceptors;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "pingIntervalMillis",
   imports = {}
)
   )
   public final int _deprecated_pingIntervalMillis/* $FF was: -deprecated_pingIntervalMillis*/() {
      return this.pingIntervalMillis;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "protocols",
   imports = {}
)
   )
   public final List<Protocol> _deprecated_protocols/* $FF was: -deprecated_protocols*/() {
      return this.protocols;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "proxy",
   imports = {}
)
   )
   public final Proxy _deprecated_proxy/* $FF was: -deprecated_proxy*/() {
      return this.proxy;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "proxyAuthenticator",
   imports = {}
)
   )
   public final Authenticator _deprecated_proxyAuthenticator/* $FF was: -deprecated_proxyAuthenticator*/() {
      return this.proxyAuthenticator;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "proxySelector",
   imports = {}
)
   )
   public final ProxySelector _deprecated_proxySelector/* $FF was: -deprecated_proxySelector*/() {
      return this.proxySelector;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "readTimeoutMillis",
   imports = {}
)
   )
   public final int _deprecated_readTimeoutMillis/* $FF was: -deprecated_readTimeoutMillis*/() {
      return this.readTimeoutMillis;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "retryOnConnectionFailure",
   imports = {}
)
   )
   public final boolean _deprecated_retryOnConnectionFailure/* $FF was: -deprecated_retryOnConnectionFailure*/() {
      return this.retryOnConnectionFailure;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "socketFactory",
   imports = {}
)
   )
   public final SocketFactory _deprecated_socketFactory/* $FF was: -deprecated_socketFactory*/() {
      return this.socketFactory;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "sslSocketFactory",
   imports = {}
)
   )
   public final SSLSocketFactory _deprecated_sslSocketFactory/* $FF was: -deprecated_sslSocketFactory*/() {
      return this.sslSocketFactory();
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "writeTimeoutMillis",
   imports = {}
)
   )
   public final int _deprecated_writeTimeoutMillis/* $FF was: -deprecated_writeTimeoutMillis*/() {
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

   public OkHttpClient.Builder newBuilder() {
      return new OkHttpClient.Builder(this);
   }

   public Call newCall(Request var1) {
      Intrinsics.checkParameterIsNotNull(var1, "request");
      return (Call)(new RealCall(this, var1, false));
   }

   public WebSocket newWebSocket(Request var1, WebSocketListener var2) {
      Intrinsics.checkParameterIsNotNull(var1, "request");
      Intrinsics.checkParameterIsNotNull(var2, "listener");
      RealWebSocket var3 = new RealWebSocket(TaskRunner.INSTANCE, var1, var2, new Random(), (long)this.pingIntervalMillis, (WebSocketExtensions)null, this.minWebSocketMessageToCompress);
      var3.connect(this);
      return (WebSocket)var3;
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
      SSLSocketFactory var1 = this.sslSocketFactoryOrNull;
      if (var1 != null) {
         return var1;
      } else {
         throw (Throwable)(new IllegalStateException("CLEARTEXT-only client"));
      }
   }

   public final int writeTimeoutMillis() {
      return this.writeTimeoutMillis;
   }

   public final X509TrustManager x509TrustManager() {
      return this.x509TrustManager;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000ø\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u000f\b\u0010\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0005¢\u0006\u0002\u0010\u0005J<\u0010\u009e\u0001\u001a\u00020\u00002*\b\u0004\u0010\u009f\u0001\u001a#\u0012\u0017\u0012\u00150¡\u0001¢\u0006\u000f\b¢\u0001\u0012\n\b£\u0001\u0012\u0005\b\b(¤\u0001\u0012\u0005\u0012\u00030¥\u00010 \u0001H\u0087\b¢\u0006\u0003\b¦\u0001J\u0010\u0010\u009e\u0001\u001a\u00020\u00002\u0007\u0010§\u0001\u001a\u00020]J<\u0010¨\u0001\u001a\u00020\u00002*\b\u0004\u0010\u009f\u0001\u001a#\u0012\u0017\u0012\u00150¡\u0001¢\u0006\u000f\b¢\u0001\u0012\n\b£\u0001\u0012\u0005\b\b(¤\u0001\u0012\u0005\u0012\u00030¥\u00010 \u0001H\u0087\b¢\u0006\u0003\b©\u0001J\u0010\u0010¨\u0001\u001a\u00020\u00002\u0007\u0010§\u0001\u001a\u00020]J\u000e\u0010\u0006\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0007J\u0007\u0010ª\u0001\u001a\u00020\u0003J\u0010\u0010\f\u001a\u00020\u00002\b\u0010\f\u001a\u0004\u0018\u00010\rJ\u0012\u0010\u0012\u001a\u00020\u00002\b\u0010«\u0001\u001a\u00030¬\u0001H\u0007J\u0019\u0010\u0012\u001a\u00020\u00002\u0007\u0010\u00ad\u0001\u001a\u00020`2\b\u0010®\u0001\u001a\u00030¯\u0001J\u000e\u0010\u001e\u001a\u00020\u00002\u0006\u0010\u001e\u001a\u00020\u001fJ\u0012\u0010$\u001a\u00020\u00002\b\u0010«\u0001\u001a\u00030¬\u0001H\u0007J\u0019\u0010$\u001a\u00020\u00002\u0007\u0010\u00ad\u0001\u001a\u00020`2\b\u0010®\u0001\u001a\u00030¯\u0001J\u000e\u0010'\u001a\u00020\u00002\u0006\u0010'\u001a\u00020(J\u0014\u0010-\u001a\u00020\u00002\f\u0010-\u001a\b\u0012\u0004\u0012\u00020/0.J\u000e\u00104\u001a\u00020\u00002\u0006\u00104\u001a\u000205J\u000e\u0010:\u001a\u00020\u00002\u0006\u0010:\u001a\u00020;J\u000e\u0010@\u001a\u00020\u00002\u0006\u0010@\u001a\u00020AJ\u0011\u0010°\u0001\u001a\u00020\u00002\b\u0010°\u0001\u001a\u00030±\u0001J\u000e\u0010F\u001a\u00020\u00002\u0006\u0010F\u001a\u00020GJ\u000e\u0010L\u001a\u00020\u00002\u0006\u0010L\u001a\u00020MJ\u000f\u0010R\u001a\u00020\u00002\u0007\u0010²\u0001\u001a\u00020MJ\u000e\u0010U\u001a\u00020\u00002\u0006\u0010U\u001a\u00020VJ\f\u0010[\u001a\b\u0012\u0004\u0012\u00020]0\\J\u000f\u0010_\u001a\u00020\u00002\u0007\u0010³\u0001\u001a\u00020`J\f\u0010e\u001a\b\u0012\u0004\u0012\u00020]0\\J\u0012\u0010g\u001a\u00020\u00002\b\u0010«\u0001\u001a\u00030¬\u0001H\u0007J\u0019\u0010g\u001a\u00020\u00002\u0007\u0010´\u0001\u001a\u00020`2\b\u0010®\u0001\u001a\u00030¯\u0001J\u0014\u0010j\u001a\u00020\u00002\f\u0010j\u001a\b\u0012\u0004\u0012\u00020k0.J\u0010\u0010n\u001a\u00020\u00002\b\u0010n\u001a\u0004\u0018\u00010oJ\u000e\u0010t\u001a\u00020\u00002\u0006\u0010t\u001a\u00020\u0007J\u000e\u0010w\u001a\u00020\u00002\u0006\u0010w\u001a\u00020xJ\u0012\u0010}\u001a\u00020\u00002\b\u0010«\u0001\u001a\u00030¬\u0001H\u0007J\u0019\u0010}\u001a\u00020\u00002\u0007\u0010\u00ad\u0001\u001a\u00020`2\b\u0010®\u0001\u001a\u00030¯\u0001J\u0010\u0010\u0080\u0001\u001a\u00020\u00002\u0007\u0010\u0080\u0001\u001a\u00020MJ\u0011\u0010\u0089\u0001\u001a\u00020\u00002\b\u0010\u0089\u0001\u001a\u00030\u008a\u0001J\u0013\u0010µ\u0001\u001a\u00020\u00002\b\u0010µ\u0001\u001a\u00030\u0090\u0001H\u0007J\u001b\u0010µ\u0001\u001a\u00020\u00002\b\u0010µ\u0001\u001a\u00030\u0090\u00012\b\u0010¶\u0001\u001a\u00030\u0099\u0001J\u0013\u0010\u0095\u0001\u001a\u00020\u00002\b\u0010«\u0001\u001a\u00030¬\u0001H\u0007J\u001a\u0010\u0095\u0001\u001a\u00020\u00002\u0007\u0010\u00ad\u0001\u001a\u00020`2\b\u0010®\u0001\u001a\u00030¯\u0001R\u001a\u0010\u0006\u001a\u00020\u0007X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\u001fX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001a\u0010$\u001a\u00020\u0013X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0015\"\u0004\b&\u0010\u0017R\u001a\u0010'\u001a\u00020(X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R \u0010-\u001a\b\u0012\u0004\u0012\u00020/0.X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u001a\u00104\u001a\u000205X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u001a\u0010:\u001a\u00020;X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R\u001a\u0010@\u001a\u00020AX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u001a\u0010F\u001a\u00020GX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010I\"\u0004\bJ\u0010KR\u001a\u0010L\u001a\u00020MX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bN\u0010O\"\u0004\bP\u0010QR\u001a\u0010R\u001a\u00020MX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bS\u0010O\"\u0004\bT\u0010QR\u001a\u0010U\u001a\u00020VX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bW\u0010X\"\u0004\bY\u0010ZR\u001a\u0010[\u001a\b\u0012\u0004\u0012\u00020]0\\X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b^\u00101R\u001a\u0010_\u001a\u00020`X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\ba\u0010b\"\u0004\bc\u0010dR\u001a\u0010e\u001a\b\u0012\u0004\u0012\u00020]0\\X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\bf\u00101R\u001a\u0010g\u001a\u00020\u0013X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bh\u0010\u0015\"\u0004\bi\u0010\u0017R \u0010j\u001a\b\u0012\u0004\u0012\u00020k0.X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bl\u00101\"\u0004\bm\u00103R\u001c\u0010n\u001a\u0004\u0018\u00010oX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bp\u0010q\"\u0004\br\u0010sR\u001a\u0010t\u001a\u00020\u0007X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bu\u0010\t\"\u0004\bv\u0010\u000bR\u001c\u0010w\u001a\u0004\u0018\u00010xX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\by\u0010z\"\u0004\b{\u0010|R\u001a\u0010}\u001a\u00020\u0013X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b~\u0010\u0015\"\u0004\b\u007f\u0010\u0017R\u001d\u0010\u0080\u0001\u001a\u00020MX\u0080\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0081\u0001\u0010O\"\u0005\b\u0082\u0001\u0010QR\"\u0010\u0083\u0001\u001a\u0005\u0018\u00010\u0084\u0001X\u0080\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u0085\u0001\u0010\u0086\u0001\"\u0006\b\u0087\u0001\u0010\u0088\u0001R \u0010\u0089\u0001\u001a\u00030\u008a\u0001X\u0080\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u008b\u0001\u0010\u008c\u0001\"\u0006\b\u008d\u0001\u0010\u008e\u0001R\"\u0010\u008f\u0001\u001a\u0005\u0018\u00010\u0090\u0001X\u0080\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u0091\u0001\u0010\u0092\u0001\"\u0006\b\u0093\u0001\u0010\u0094\u0001R\u001d\u0010\u0095\u0001\u001a\u00020\u0013X\u0080\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0096\u0001\u0010\u0015\"\u0005\b\u0097\u0001\u0010\u0017R\"\u0010\u0098\u0001\u001a\u0005\u0018\u00010\u0099\u0001X\u0080\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u009a\u0001\u0010\u009b\u0001\"\u0006\b\u009c\u0001\u0010\u009d\u0001¨\u0006·\u0001"},
      d2 = {"Lokhttp3/OkHttpClient$Builder;", "", "okHttpClient", "Lokhttp3/OkHttpClient;", "(Lokhttp3/OkHttpClient;)V", "()V", "authenticator", "Lokhttp3/Authenticator;", "getAuthenticator$okhttp", "()Lokhttp3/Authenticator;", "setAuthenticator$okhttp", "(Lokhttp3/Authenticator;)V", "cache", "Lokhttp3/Cache;", "getCache$okhttp", "()Lokhttp3/Cache;", "setCache$okhttp", "(Lokhttp3/Cache;)V", "callTimeout", "", "getCallTimeout$okhttp", "()I", "setCallTimeout$okhttp", "(I)V", "certificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "getCertificateChainCleaner$okhttp", "()Lokhttp3/internal/tls/CertificateChainCleaner;", "setCertificateChainCleaner$okhttp", "(Lokhttp3/internal/tls/CertificateChainCleaner;)V", "certificatePinner", "Lokhttp3/CertificatePinner;", "getCertificatePinner$okhttp", "()Lokhttp3/CertificatePinner;", "setCertificatePinner$okhttp", "(Lokhttp3/CertificatePinner;)V", "connectTimeout", "getConnectTimeout$okhttp", "setConnectTimeout$okhttp", "connectionPool", "Lokhttp3/ConnectionPool;", "getConnectionPool$okhttp", "()Lokhttp3/ConnectionPool;", "setConnectionPool$okhttp", "(Lokhttp3/ConnectionPool;)V", "connectionSpecs", "", "Lokhttp3/ConnectionSpec;", "getConnectionSpecs$okhttp", "()Ljava/util/List;", "setConnectionSpecs$okhttp", "(Ljava/util/List;)V", "cookieJar", "Lokhttp3/CookieJar;", "getCookieJar$okhttp", "()Lokhttp3/CookieJar;", "setCookieJar$okhttp", "(Lokhttp3/CookieJar;)V", "dispatcher", "Lokhttp3/Dispatcher;", "getDispatcher$okhttp", "()Lokhttp3/Dispatcher;", "setDispatcher$okhttp", "(Lokhttp3/Dispatcher;)V", "dns", "Lokhttp3/Dns;", "getDns$okhttp", "()Lokhttp3/Dns;", "setDns$okhttp", "(Lokhttp3/Dns;)V", "eventListenerFactory", "Lokhttp3/EventListener$Factory;", "getEventListenerFactory$okhttp", "()Lokhttp3/EventListener$Factory;", "setEventListenerFactory$okhttp", "(Lokhttp3/EventListener$Factory;)V", "followRedirects", "", "getFollowRedirects$okhttp", "()Z", "setFollowRedirects$okhttp", "(Z)V", "followSslRedirects", "getFollowSslRedirects$okhttp", "setFollowSslRedirects$okhttp", "hostnameVerifier", "Ljavax/net/ssl/HostnameVerifier;", "getHostnameVerifier$okhttp", "()Ljavax/net/ssl/HostnameVerifier;", "setHostnameVerifier$okhttp", "(Ljavax/net/ssl/HostnameVerifier;)V", "interceptors", "", "Lokhttp3/Interceptor;", "getInterceptors$okhttp", "minWebSocketMessageToCompress", "", "getMinWebSocketMessageToCompress$okhttp", "()J", "setMinWebSocketMessageToCompress$okhttp", "(J)V", "networkInterceptors", "getNetworkInterceptors$okhttp", "pingInterval", "getPingInterval$okhttp", "setPingInterval$okhttp", "protocols", "Lokhttp3/Protocol;", "getProtocols$okhttp", "setProtocols$okhttp", "proxy", "Ljava/net/Proxy;", "getProxy$okhttp", "()Ljava/net/Proxy;", "setProxy$okhttp", "(Ljava/net/Proxy;)V", "proxyAuthenticator", "getProxyAuthenticator$okhttp", "setProxyAuthenticator$okhttp", "proxySelector", "Ljava/net/ProxySelector;", "getProxySelector$okhttp", "()Ljava/net/ProxySelector;", "setProxySelector$okhttp", "(Ljava/net/ProxySelector;)V", "readTimeout", "getReadTimeout$okhttp", "setReadTimeout$okhttp", "retryOnConnectionFailure", "getRetryOnConnectionFailure$okhttp", "setRetryOnConnectionFailure$okhttp", "routeDatabase", "Lokhttp3/internal/connection/RouteDatabase;", "getRouteDatabase$okhttp", "()Lokhttp3/internal/connection/RouteDatabase;", "setRouteDatabase$okhttp", "(Lokhttp3/internal/connection/RouteDatabase;)V", "socketFactory", "Ljavax/net/SocketFactory;", "getSocketFactory$okhttp", "()Ljavax/net/SocketFactory;", "setSocketFactory$okhttp", "(Ljavax/net/SocketFactory;)V", "sslSocketFactoryOrNull", "Ljavax/net/ssl/SSLSocketFactory;", "getSslSocketFactoryOrNull$okhttp", "()Ljavax/net/ssl/SSLSocketFactory;", "setSslSocketFactoryOrNull$okhttp", "(Ljavax/net/ssl/SSLSocketFactory;)V", "writeTimeout", "getWriteTimeout$okhttp", "setWriteTimeout$okhttp", "x509TrustManagerOrNull", "Ljavax/net/ssl/X509TrustManager;", "getX509TrustManagerOrNull$okhttp", "()Ljavax/net/ssl/X509TrustManager;", "setX509TrustManagerOrNull$okhttp", "(Ljavax/net/ssl/X509TrustManager;)V", "addInterceptor", "block", "Lkotlin/Function1;", "Lokhttp3/Interceptor$Chain;", "Lkotlin/ParameterName;", "name", "chain", "Lokhttp3/Response;", "-addInterceptor", "interceptor", "addNetworkInterceptor", "-addNetworkInterceptor", "build", "duration", "Ljava/time/Duration;", "timeout", "unit", "Ljava/util/concurrent/TimeUnit;", "eventListener", "Lokhttp3/EventListener;", "followProtocolRedirects", "bytes", "interval", "sslSocketFactory", "trustManager", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
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
         this.interceptors = (List)(new ArrayList());
         this.networkInterceptors = (List)(new ArrayList());
         this.eventListenerFactory = Util.asFactory(EventListener.NONE);
         this.retryOnConnectionFailure = true;
         this.authenticator = Authenticator.NONE;
         this.followRedirects = true;
         this.followSslRedirects = true;
         this.cookieJar = CookieJar.NO_COOKIES;
         this.dns = Dns.SYSTEM;
         this.proxyAuthenticator = Authenticator.NONE;
         SocketFactory var1 = SocketFactory.getDefault();
         Intrinsics.checkExpressionValueIsNotNull(var1, "SocketFactory.getDefault()");
         this.socketFactory = var1;
         this.connectionSpecs = OkHttpClient.Companion.getDEFAULT_CONNECTION_SPECS$okhttp();
         this.protocols = OkHttpClient.Companion.getDEFAULT_PROTOCOLS$okhttp();
         this.hostnameVerifier = (HostnameVerifier)OkHostnameVerifier.INSTANCE;
         this.certificatePinner = CertificatePinner.DEFAULT;
         this.connectTimeout = 10000;
         this.readTimeout = 10000;
         this.writeTimeout = 10000;
         this.minWebSocketMessageToCompress = 1024L;
      }

      public Builder(OkHttpClient var1) {
         Intrinsics.checkParameterIsNotNull(var1, "okHttpClient");
         this();
         this.dispatcher = var1.dispatcher();
         this.connectionPool = var1.connectionPool();
         CollectionsKt.addAll((Collection)this.interceptors, (Iterable)var1.interceptors());
         CollectionsKt.addAll((Collection)this.networkInterceptors, (Iterable)var1.networkInterceptors());
         this.eventListenerFactory = var1.eventListenerFactory();
         this.retryOnConnectionFailure = var1.retryOnConnectionFailure();
         this.authenticator = var1.authenticator();
         this.followRedirects = var1.followRedirects();
         this.followSslRedirects = var1.followSslRedirects();
         this.cookieJar = var1.cookieJar();
         this.cache = var1.cache();
         this.dns = var1.dns();
         this.proxy = var1.proxy();
         this.proxySelector = var1.proxySelector();
         this.proxyAuthenticator = var1.proxyAuthenticator();
         this.socketFactory = var1.socketFactory();
         this.sslSocketFactoryOrNull = var1.sslSocketFactoryOrNull;
         this.x509TrustManagerOrNull = var1.x509TrustManager();
         this.connectionSpecs = var1.connectionSpecs();
         this.protocols = var1.protocols();
         this.hostnameVerifier = var1.hostnameVerifier();
         this.certificatePinner = var1.certificatePinner();
         this.certificateChainCleaner = var1.certificateChainCleaner();
         this.callTimeout = var1.callTimeoutMillis();
         this.connectTimeout = var1.connectTimeoutMillis();
         this.readTimeout = var1.readTimeoutMillis();
         this.writeTimeout = var1.writeTimeoutMillis();
         this.pingInterval = var1.pingIntervalMillis();
         this.minWebSocketMessageToCompress = var1.minWebSocketMessageToCompress();
         this.routeDatabase = var1.getRouteDatabase();
      }

      public final OkHttpClient.Builder _addInterceptor/* $FF was: -addInterceptor*/(final Function1<? super Interceptor.Chain, Response> var1) {
         Intrinsics.checkParameterIsNotNull(var1, "block");
         Interceptor.Companion var2 = Interceptor.Companion;
         return this.addInterceptor((Interceptor)(new Interceptor() {
            public Response intercept(Interceptor.Chain var1x) {
               Intrinsics.checkParameterIsNotNull(var1x, "chain");
               return (Response)var1.invoke(var1x);
            }
         }));
      }

      public final OkHttpClient.Builder _addNetworkInterceptor/* $FF was: -addNetworkInterceptor*/(final Function1<? super Interceptor.Chain, Response> var1) {
         Intrinsics.checkParameterIsNotNull(var1, "block");
         Interceptor.Companion var2 = Interceptor.Companion;
         return this.addNetworkInterceptor((Interceptor)(new Interceptor() {
            public Response intercept(Interceptor.Chain var1x) {
               Intrinsics.checkParameterIsNotNull(var1x, "chain");
               return (Response)var1.invoke(var1x);
            }
         }));
      }

      public final OkHttpClient.Builder addInterceptor(Interceptor var1) {
         Intrinsics.checkParameterIsNotNull(var1, "interceptor");
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         ((Collection)var2.interceptors).add(var1);
         return var2;
      }

      public final OkHttpClient.Builder addNetworkInterceptor(Interceptor var1) {
         Intrinsics.checkParameterIsNotNull(var1, "interceptor");
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         ((Collection)var2.networkInterceptors).add(var1);
         return var2;
      }

      public final OkHttpClient.Builder authenticator(Authenticator var1) {
         Intrinsics.checkParameterIsNotNull(var1, "authenticator");
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         var2.authenticator = var1;
         return var2;
      }

      public final OkHttpClient build() {
         return new OkHttpClient(this);
      }

      public final OkHttpClient.Builder cache(Cache var1) {
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         var2.cache = var1;
         return var2;
      }

      public final OkHttpClient.Builder callTimeout(long var1, TimeUnit var3) {
         Intrinsics.checkParameterIsNotNull(var3, "unit");
         OkHttpClient.Builder var4 = (OkHttpClient.Builder)this;
         var4.callTimeout = Util.checkDuration("timeout", var1, var3);
         return var4;
      }

      public final OkHttpClient.Builder callTimeout(Duration var1) {
         Intrinsics.checkParameterIsNotNull(var1, "duration");
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         var2.callTimeout(var1.toMillis(), TimeUnit.MILLISECONDS);
         return var2;
      }

      public final OkHttpClient.Builder certificatePinner(CertificatePinner var1) {
         Intrinsics.checkParameterIsNotNull(var1, "certificatePinner");
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         if (Intrinsics.areEqual((Object)var1, (Object)var2.certificatePinner) ^ true) {
            var2.routeDatabase = (RouteDatabase)null;
         }

         var2.certificatePinner = var1;
         return var2;
      }

      public final OkHttpClient.Builder connectTimeout(long var1, TimeUnit var3) {
         Intrinsics.checkParameterIsNotNull(var3, "unit");
         OkHttpClient.Builder var4 = (OkHttpClient.Builder)this;
         var4.connectTimeout = Util.checkDuration("timeout", var1, var3);
         return var4;
      }

      public final OkHttpClient.Builder connectTimeout(Duration var1) {
         Intrinsics.checkParameterIsNotNull(var1, "duration");
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         var2.connectTimeout(var1.toMillis(), TimeUnit.MILLISECONDS);
         return var2;
      }

      public final OkHttpClient.Builder connectionPool(ConnectionPool var1) {
         Intrinsics.checkParameterIsNotNull(var1, "connectionPool");
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         var2.connectionPool = var1;
         return var2;
      }

      public final OkHttpClient.Builder connectionSpecs(List<ConnectionSpec> var1) {
         Intrinsics.checkParameterIsNotNull(var1, "connectionSpecs");
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         if (Intrinsics.areEqual((Object)var1, (Object)var2.connectionSpecs) ^ true) {
            var2.routeDatabase = (RouteDatabase)null;
         }

         var2.connectionSpecs = Util.toImmutableList(var1);
         return var2;
      }

      public final OkHttpClient.Builder cookieJar(CookieJar var1) {
         Intrinsics.checkParameterIsNotNull(var1, "cookieJar");
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         var2.cookieJar = var1;
         return var2;
      }

      public final OkHttpClient.Builder dispatcher(Dispatcher var1) {
         Intrinsics.checkParameterIsNotNull(var1, "dispatcher");
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         var2.dispatcher = var1;
         return var2;
      }

      public final OkHttpClient.Builder dns(Dns var1) {
         Intrinsics.checkParameterIsNotNull(var1, "dns");
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         if (Intrinsics.areEqual((Object)var1, (Object)var2.dns) ^ true) {
            var2.routeDatabase = (RouteDatabase)null;
         }

         var2.dns = var1;
         return var2;
      }

      public final OkHttpClient.Builder eventListener(EventListener var1) {
         Intrinsics.checkParameterIsNotNull(var1, "eventListener");
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         var2.eventListenerFactory = Util.asFactory(var1);
         return var2;
      }

      public final OkHttpClient.Builder eventListenerFactory(EventListener.Factory var1) {
         Intrinsics.checkParameterIsNotNull(var1, "eventListenerFactory");
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         var2.eventListenerFactory = var1;
         return var2;
      }

      public final OkHttpClient.Builder followRedirects(boolean var1) {
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         var2.followRedirects = var1;
         return var2;
      }

      public final OkHttpClient.Builder followSslRedirects(boolean var1) {
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         var2.followSslRedirects = var1;
         return var2;
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

      public final OkHttpClient.Builder hostnameVerifier(HostnameVerifier var1) {
         Intrinsics.checkParameterIsNotNull(var1, "hostnameVerifier");
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         if (Intrinsics.areEqual((Object)var1, (Object)var2.hostnameVerifier) ^ true) {
            var2.routeDatabase = (RouteDatabase)null;
         }

         var2.hostnameVerifier = var1;
         return var2;
      }

      public final List<Interceptor> interceptors() {
         return this.interceptors;
      }

      public final OkHttpClient.Builder minWebSocketMessageToCompress(long var1) {
         OkHttpClient.Builder var3 = (OkHttpClient.Builder)this;
         boolean var4;
         if (var1 >= 0L) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            var3.minWebSocketMessageToCompress = var1;
            return var3;
         } else {
            StringBuilder var5 = new StringBuilder();
            var5.append("minWebSocketMessageToCompress must be positive: ");
            var5.append(var1);
            throw (Throwable)(new IllegalArgumentException(var5.toString().toString()));
         }
      }

      public final List<Interceptor> networkInterceptors() {
         return this.networkInterceptors;
      }

      public final OkHttpClient.Builder pingInterval(long var1, TimeUnit var3) {
         Intrinsics.checkParameterIsNotNull(var3, "unit");
         OkHttpClient.Builder var4 = (OkHttpClient.Builder)this;
         var4.pingInterval = Util.checkDuration("interval", var1, var3);
         return var4;
      }

      public final OkHttpClient.Builder pingInterval(Duration var1) {
         Intrinsics.checkParameterIsNotNull(var1, "duration");
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         var2.pingInterval(var1.toMillis(), TimeUnit.MILLISECONDS);
         return var2;
      }

      public final OkHttpClient.Builder protocols(List<? extends Protocol> var1) {
         Intrinsics.checkParameterIsNotNull(var1, "protocols");
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         var1 = CollectionsKt.toMutableList((Collection)var1);
         boolean var3 = var1.contains(Protocol.H2_PRIOR_KNOWLEDGE);
         boolean var4 = false;
         boolean var5;
         if (!var3 && !var1.contains(Protocol.HTTP_1_1)) {
            var5 = false;
         } else {
            var5 = true;
         }

         StringBuilder var6;
         if (!var5) {
            var6 = new StringBuilder();
            var6.append("protocols must contain h2_prior_knowledge or http/1.1: ");
            var6.append(var1);
            throw (Throwable)(new IllegalArgumentException(var6.toString().toString()));
         } else {
            label39: {
               if (var1.contains(Protocol.H2_PRIOR_KNOWLEDGE)) {
                  var5 = var4;
                  if (var1.size() > 1) {
                     break label39;
                  }
               }

               var5 = true;
            }

            if (var5) {
               if (var1.contains(Protocol.HTTP_1_0) ^ true) {
                  if (var1 != null) {
                     if (var1.contains((Object)null) ^ true) {
                        var1.remove(Protocol.SPDY_3);
                        if (Intrinsics.areEqual((Object)var1, (Object)var2.protocols) ^ true) {
                           var2.routeDatabase = (RouteDatabase)null;
                        }

                        var1 = Collections.unmodifiableList(var1);
                        Intrinsics.checkExpressionValueIsNotNull(var1, "Collections.unmodifiableList(protocolsCopy)");
                        var2.protocols = var1;
                        return var2;
                     } else {
                        throw (Throwable)(new IllegalArgumentException("protocols must not contain null".toString()));
                     }
                  } else {
                     throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.List<okhttp3.Protocol?>");
                  }
               } else {
                  var6 = new StringBuilder();
                  var6.append("protocols must not contain http/1.0: ");
                  var6.append(var1);
                  throw (Throwable)(new IllegalArgumentException(var6.toString().toString()));
               }
            } else {
               var6 = new StringBuilder();
               var6.append("protocols containing h2_prior_knowledge cannot use other protocols: ");
               var6.append(var1);
               throw (Throwable)(new IllegalArgumentException(var6.toString().toString()));
            }
         }
      }

      public final OkHttpClient.Builder proxy(Proxy var1) {
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         if (Intrinsics.areEqual((Object)var1, (Object)var2.proxy) ^ true) {
            var2.routeDatabase = (RouteDatabase)null;
         }

         var2.proxy = var1;
         return var2;
      }

      public final OkHttpClient.Builder proxyAuthenticator(Authenticator var1) {
         Intrinsics.checkParameterIsNotNull(var1, "proxyAuthenticator");
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         if (Intrinsics.areEqual((Object)var1, (Object)var2.proxyAuthenticator) ^ true) {
            var2.routeDatabase = (RouteDatabase)null;
         }

         var2.proxyAuthenticator = var1;
         return var2;
      }

      public final OkHttpClient.Builder proxySelector(ProxySelector var1) {
         Intrinsics.checkParameterIsNotNull(var1, "proxySelector");
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         if (Intrinsics.areEqual((Object)var1, (Object)var2.proxySelector) ^ true) {
            var2.routeDatabase = (RouteDatabase)null;
         }

         var2.proxySelector = var1;
         return var2;
      }

      public final OkHttpClient.Builder readTimeout(long var1, TimeUnit var3) {
         Intrinsics.checkParameterIsNotNull(var3, "unit");
         OkHttpClient.Builder var4 = (OkHttpClient.Builder)this;
         var4.readTimeout = Util.checkDuration("timeout", var1, var3);
         return var4;
      }

      public final OkHttpClient.Builder readTimeout(Duration var1) {
         Intrinsics.checkParameterIsNotNull(var1, "duration");
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         var2.readTimeout(var1.toMillis(), TimeUnit.MILLISECONDS);
         return var2;
      }

      public final OkHttpClient.Builder retryOnConnectionFailure(boolean var1) {
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         var2.retryOnConnectionFailure = var1;
         return var2;
      }

      public final void setAuthenticator$okhttp(Authenticator var1) {
         Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
         this.authenticator = var1;
      }

      public final void setCache$okhttp(Cache var1) {
         this.cache = var1;
      }

      public final void setCallTimeout$okhttp(int var1) {
         this.callTimeout = var1;
      }

      public final void setCertificateChainCleaner$okhttp(CertificateChainCleaner var1) {
         this.certificateChainCleaner = var1;
      }

      public final void setCertificatePinner$okhttp(CertificatePinner var1) {
         Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
         this.certificatePinner = var1;
      }

      public final void setConnectTimeout$okhttp(int var1) {
         this.connectTimeout = var1;
      }

      public final void setConnectionPool$okhttp(ConnectionPool var1) {
         Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
         this.connectionPool = var1;
      }

      public final void setConnectionSpecs$okhttp(List<ConnectionSpec> var1) {
         Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
         this.connectionSpecs = var1;
      }

      public final void setCookieJar$okhttp(CookieJar var1) {
         Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
         this.cookieJar = var1;
      }

      public final void setDispatcher$okhttp(Dispatcher var1) {
         Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
         this.dispatcher = var1;
      }

      public final void setDns$okhttp(Dns var1) {
         Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
         this.dns = var1;
      }

      public final void setEventListenerFactory$okhttp(EventListener.Factory var1) {
         Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
         this.eventListenerFactory = var1;
      }

      public final void setFollowRedirects$okhttp(boolean var1) {
         this.followRedirects = var1;
      }

      public final void setFollowSslRedirects$okhttp(boolean var1) {
         this.followSslRedirects = var1;
      }

      public final void setHostnameVerifier$okhttp(HostnameVerifier var1) {
         Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
         this.hostnameVerifier = var1;
      }

      public final void setMinWebSocketMessageToCompress$okhttp(long var1) {
         this.minWebSocketMessageToCompress = var1;
      }

      public final void setPingInterval$okhttp(int var1) {
         this.pingInterval = var1;
      }

      public final void setProtocols$okhttp(List<? extends Protocol> var1) {
         Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
         this.protocols = var1;
      }

      public final void setProxy$okhttp(Proxy var1) {
         this.proxy = var1;
      }

      public final void setProxyAuthenticator$okhttp(Authenticator var1) {
         Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
         this.proxyAuthenticator = var1;
      }

      public final void setProxySelector$okhttp(ProxySelector var1) {
         this.proxySelector = var1;
      }

      public final void setReadTimeout$okhttp(int var1) {
         this.readTimeout = var1;
      }

      public final void setRetryOnConnectionFailure$okhttp(boolean var1) {
         this.retryOnConnectionFailure = var1;
      }

      public final void setRouteDatabase$okhttp(RouteDatabase var1) {
         this.routeDatabase = var1;
      }

      public final void setSocketFactory$okhttp(SocketFactory var1) {
         Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
         this.socketFactory = var1;
      }

      public final void setSslSocketFactoryOrNull$okhttp(SSLSocketFactory var1) {
         this.sslSocketFactoryOrNull = var1;
      }

      public final void setWriteTimeout$okhttp(int var1) {
         this.writeTimeout = var1;
      }

      public final void setX509TrustManagerOrNull$okhttp(X509TrustManager var1) {
         this.x509TrustManagerOrNull = var1;
      }

      public final OkHttpClient.Builder socketFactory(SocketFactory var1) {
         Intrinsics.checkParameterIsNotNull(var1, "socketFactory");
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         if (var1 instanceof SSLSocketFactory ^ true) {
            if (Intrinsics.areEqual((Object)var1, (Object)var2.socketFactory) ^ true) {
               var2.routeDatabase = (RouteDatabase)null;
            }

            var2.socketFactory = var1;
            return var2;
         } else {
            throw (Throwable)(new IllegalArgumentException("socketFactory instanceof SSLSocketFactory".toString()));
         }
      }

      @Deprecated(
         level = DeprecationLevel.ERROR,
         message = "Use the sslSocketFactory overload that accepts a X509TrustManager."
      )
      public final OkHttpClient.Builder sslSocketFactory(SSLSocketFactory var1) {
         Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         if (Intrinsics.areEqual((Object)var1, (Object)var2.sslSocketFactoryOrNull) ^ true) {
            var2.routeDatabase = (RouteDatabase)null;
         }

         var2.sslSocketFactoryOrNull = var1;
         X509TrustManager var3 = Platform.Companion.get().trustManager(var1);
         if (var3 != null) {
            var2.x509TrustManagerOrNull = var3;
            Platform var4 = Platform.Companion.get();
            var3 = var2.x509TrustManagerOrNull;
            if (var3 == null) {
               Intrinsics.throwNpe();
            }

            var2.certificateChainCleaner = var4.buildCertificateChainCleaner(var3);
            return var2;
         } else {
            StringBuilder var5 = new StringBuilder();
            var5.append("Unable to extract the trust manager on ");
            var5.append(Platform.Companion.get());
            var5.append(", ");
            var5.append("sslSocketFactory is ");
            var5.append(var1.getClass());
            throw (Throwable)(new IllegalStateException(var5.toString()));
         }
      }

      public final OkHttpClient.Builder sslSocketFactory(SSLSocketFactory var1, X509TrustManager var2) {
         Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
         Intrinsics.checkParameterIsNotNull(var2, "trustManager");
         OkHttpClient.Builder var3 = (OkHttpClient.Builder)this;
         if (Intrinsics.areEqual((Object)var1, (Object)var3.sslSocketFactoryOrNull) ^ true || Intrinsics.areEqual((Object)var2, (Object)var3.x509TrustManagerOrNull) ^ true) {
            var3.routeDatabase = (RouteDatabase)null;
         }

         var3.sslSocketFactoryOrNull = var1;
         var3.certificateChainCleaner = CertificateChainCleaner.Companion.get(var2);
         var3.x509TrustManagerOrNull = var2;
         return var3;
      }

      public final OkHttpClient.Builder writeTimeout(long var1, TimeUnit var3) {
         Intrinsics.checkParameterIsNotNull(var3, "unit");
         OkHttpClient.Builder var4 = (OkHttpClient.Builder)this;
         var4.writeTimeout = Util.checkDuration("timeout", var1, var3);
         return var4;
      }

      public final OkHttpClient.Builder writeTimeout(Duration var1) {
         Intrinsics.checkParameterIsNotNull(var1, "duration");
         OkHttpClient.Builder var2 = (OkHttpClient.Builder)this;
         var2.writeTimeout(var1.toMillis(), TimeUnit.MILLISECONDS);
         return var2;
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0007¨\u0006\u000b"},
      d2 = {"Lokhttp3/OkHttpClient$Companion;", "", "()V", "DEFAULT_CONNECTION_SPECS", "", "Lokhttp3/ConnectionSpec;", "getDEFAULT_CONNECTION_SPECS$okhttp", "()Ljava/util/List;", "DEFAULT_PROTOCOLS", "Lokhttp3/Protocol;", "getDEFAULT_PROTOCOLS$okhttp", "okhttp"},
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

      public final List<ConnectionSpec> getDEFAULT_CONNECTION_SPECS$okhttp() {
         return OkHttpClient.DEFAULT_CONNECTION_SPECS;
      }

      public final List<Protocol> getDEFAULT_PROTOCOLS$okhttp() {
         return OkHttpClient.DEFAULT_PROTOCOLS;
      }
   }
}
