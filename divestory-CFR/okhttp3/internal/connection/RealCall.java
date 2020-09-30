/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okhttp3.internal.connection.RealCall$timeout
 */
package okhttp3.internal.connection;

import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Address;
import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CertificatePinner;
import okhttp3.Connection;
import okhttp3.ConnectionPool;
import okhttp3.ConnectionSpec;
import okhttp3.CookieJar;
import okhttp3.Dispatcher;
import okhttp3.Dns;
import okhttp3.EventListener;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.internal.connection.ConnectInterceptor;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.connection.ExchangeFinder;
import okhttp3.internal.connection.RealCall;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.RealConnectionPool;
import okhttp3.internal.http.BridgeInterceptor;
import okhttp3.internal.http.CallServerInterceptor;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http.RetryAndFollowUpInterceptor;
import okhttp3.internal.platform.Platform;
import okio.AsyncTimeout;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u00a7\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006*\u0001.\u0018\u00002\u00020\u0001:\u0002deB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u00101\u001a\u0002022\u0006\u0010\u0010\u001a\u00020\u000fJ!\u00103\u001a\u0002H4\"\n\b\u0000\u00104*\u0004\u0018\u0001052\u0006\u00106\u001a\u0002H4H\u0002\u00a2\u0006\u0002\u00107J\b\u00108\u001a\u000202H\u0002J\b\u00109\u001a\u000202H\u0016J\b\u0010:\u001a\u00020\u0000H\u0016J\u0010\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020>H\u0002J\u0010\u0010?\u001a\u0002022\u0006\u0010@\u001a\u00020AH\u0016J\u0016\u0010B\u001a\u0002022\u0006\u0010C\u001a\u00020\u00052\u0006\u0010D\u001a\u00020\u0007J\b\u0010E\u001a\u00020FH\u0016J\u0015\u0010G\u001a\u0002022\u0006\u0010H\u001a\u00020\u0007H\u0000\u00a2\u0006\u0002\bIJ\r\u0010J\u001a\u00020FH\u0000\u00a2\u0006\u0002\bKJ\u0015\u0010L\u001a\u00020\u001e2\u0006\u0010M\u001a\u00020NH\u0000\u00a2\u0006\u0002\bOJ\b\u0010P\u001a\u00020\u0007H\u0016J\b\u0010Q\u001a\u00020\u0007H\u0016J;\u0010R\u001a\u0002H4\"\n\b\u0000\u00104*\u0004\u0018\u0001052\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010S\u001a\u00020\u00072\u0006\u0010T\u001a\u00020\u00072\u0006\u00106\u001a\u0002H4H\u0000\u00a2\u0006\u0004\bU\u0010VJ\u0019\u0010W\u001a\u0004\u0018\u0001052\b\u00106\u001a\u0004\u0018\u000105H\u0000\u00a2\u0006\u0002\bXJ\r\u0010Y\u001a\u00020ZH\u0000\u00a2\u0006\u0002\b[J\u000f\u0010\\\u001a\u0004\u0018\u00010]H\u0000\u00a2\u0006\u0002\b^J\b\u0010C\u001a\u00020\u0005H\u0016J\u0006\u0010_\u001a\u00020\u0007J\b\u0010-\u001a\u00020`H\u0016J\u0006\u00100\u001a\u000202J!\u0010a\u001a\u0002H4\"\n\b\u0000\u00104*\u0004\u0018\u0001052\u0006\u0010b\u001a\u0002H4H\u0002\u00a2\u0006\u0002\u00107J\b\u0010c\u001a\u00020ZH\u0002R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\"\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0012\"\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u001aX\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\"\u0010&\u001a\u0004\u0018\u00010\u001e2\b\u0010\u000e\u001a\u0004\u0018\u00010\u001e@BX\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u000e\u0010+\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010-\u001a\u00020.X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010/R\u000e\u00100\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006f"}, d2={"Lokhttp3/internal/connection/RealCall;", "Lokhttp3/Call;", "client", "Lokhttp3/OkHttpClient;", "originalRequest", "Lokhttp3/Request;", "forWebSocket", "", "(Lokhttp3/OkHttpClient;Lokhttp3/Request;Z)V", "callStackTrace", "", "canceled", "getClient", "()Lokhttp3/OkHttpClient;", "<set-?>", "Lokhttp3/internal/connection/RealConnection;", "connection", "getConnection", "()Lokhttp3/internal/connection/RealConnection;", "connectionPool", "Lokhttp3/internal/connection/RealConnectionPool;", "connectionToCancel", "getConnectionToCancel", "setConnectionToCancel", "(Lokhttp3/internal/connection/RealConnection;)V", "eventListener", "Lokhttp3/EventListener;", "getEventListener$okhttp", "()Lokhttp3/EventListener;", "exchange", "Lokhttp3/internal/connection/Exchange;", "exchangeFinder", "Lokhttp3/internal/connection/ExchangeFinder;", "executed", "Ljava/util/concurrent/atomic/AtomicBoolean;", "expectMoreExchanges", "getForWebSocket", "()Z", "interceptorScopedExchange", "getInterceptorScopedExchange$okhttp", "()Lokhttp3/internal/connection/Exchange;", "getOriginalRequest", "()Lokhttp3/Request;", "requestBodyOpen", "responseBodyOpen", "timeout", "okhttp3/internal/connection/RealCall$timeout$1", "Lokhttp3/internal/connection/RealCall$timeout$1;", "timeoutEarlyExit", "acquireConnectionNoEvents", "", "callDone", "E", "Ljava/io/IOException;", "e", "(Ljava/io/IOException;)Ljava/io/IOException;", "callStart", "cancel", "clone", "createAddress", "Lokhttp3/Address;", "url", "Lokhttp3/HttpUrl;", "enqueue", "responseCallback", "Lokhttp3/Callback;", "enterNetworkInterceptorExchange", "request", "newExchangeFinder", "execute", "Lokhttp3/Response;", "exitNetworkInterceptorExchange", "closeExchange", "exitNetworkInterceptorExchange$okhttp", "getResponseWithInterceptorChain", "getResponseWithInterceptorChain$okhttp", "initExchange", "chain", "Lokhttp3/internal/http/RealInterceptorChain;", "initExchange$okhttp", "isCanceled", "isExecuted", "messageDone", "requestDone", "responseDone", "messageDone$okhttp", "(Lokhttp3/internal/connection/Exchange;ZZLjava/io/IOException;)Ljava/io/IOException;", "noMoreExchanges", "noMoreExchanges$okhttp", "redactedUrl", "", "redactedUrl$okhttp", "releaseConnectionNoEvents", "Ljava/net/Socket;", "releaseConnectionNoEvents$okhttp", "retryAfterFailure", "Lokio/AsyncTimeout;", "timeoutExit", "cause", "toLoggableString", "AsyncCall", "CallReference", "okhttp"}, k=1, mv={1, 1, 16})
public final class RealCall
implements Call {
    private Object callStackTrace;
    private volatile boolean canceled;
    private final OkHttpClient client;
    private RealConnection connection;
    private final RealConnectionPool connectionPool;
    private volatile RealConnection connectionToCancel;
    private final EventListener eventListener;
    private volatile Exchange exchange;
    private ExchangeFinder exchangeFinder;
    private final AtomicBoolean executed;
    private boolean expectMoreExchanges;
    private final boolean forWebSocket;
    private Exchange interceptorScopedExchange;
    private final Request originalRequest;
    private boolean requestBodyOpen;
    private boolean responseBodyOpen;
    private final timeout.1 timeout;
    private boolean timeoutEarlyExit;

    public RealCall(OkHttpClient object, Request request, boolean bl) {
        Intrinsics.checkParameterIsNotNull(object, "client");
        Intrinsics.checkParameterIsNotNull(request, "originalRequest");
        this.client = object;
        this.originalRequest = request;
        this.forWebSocket = bl;
        this.connectionPool = ((OkHttpClient)object).connectionPool().getDelegate$okhttp();
        this.eventListener = this.client.eventListenerFactory().create(this);
        object = new AsyncTimeout(this){
            final /* synthetic */ RealCall this$0;
            {
                this.this$0 = realCall;
            }

            protected void timedOut() {
                this.this$0.cancel();
            }
        };
        ((Timeout)object).timeout(this.client.callTimeoutMillis(), TimeUnit.MILLISECONDS);
        this.timeout = object;
        this.executed = new AtomicBoolean();
        this.expectMoreExchanges = true;
    }

    public static final /* synthetic */ timeout.1 access$getTimeout$p(RealCall realCall) {
        return realCall.timeout;
    }

    public static final /* synthetic */ String access$toLoggableString(RealCall realCall) {
        return realCall.toLoggableString();
    }

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    private final <E extends IOException> E callDone(E object) {
        Object thread3;
        if (Util.assertionsEnabled && Thread.holdsLock(this)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Thread ");
            Thread thread2 = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(thread2, "Thread.currentThread()");
            ((StringBuilder)object).append(thread2.getName());
            ((StringBuilder)object).append(" MUST NOT hold lock on ");
            ((StringBuilder)object).append(this);
            throw (Throwable)((Object)new AssertionError((Object)((StringBuilder)object).toString()));
        }
        Object thread2 = this.connection;
        if (thread2 != null) {
            if (Util.assertionsEnabled && Thread.holdsLock(thread2)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Thread ");
                Thread thread4 = Thread.currentThread();
                Intrinsics.checkExpressionValueIsNotNull(thread4, "Thread.currentThread()");
                ((StringBuilder)object).append(thread4.getName());
                ((StringBuilder)object).append(" MUST NOT hold lock on ");
                ((StringBuilder)object).append(thread2);
                throw (Throwable)((Object)new AssertionError((Object)((StringBuilder)object).toString()));
            }
            // MONITORENTER : thread2
            thread3 = this.releaseConnectionNoEvents$okhttp();
            // MONITOREXIT : thread2
            if (this.connection == null) {
                if (thread3 != null) {
                    Util.closeQuietly((Socket)thread3);
                }
                this.eventListener.connectionReleased(this, (Connection)thread2);
            } else {
                boolean bl = thread3 == null;
                if (!bl) throw (Throwable)new IllegalStateException("Check failed.".toString());
            }
        }
        thread2 = this.timeoutExit(object);
        if (object == null) {
            this.eventListener.callEnd(this);
            return (E)thread2;
        }
        object = this.eventListener;
        thread3 = this;
        if (thread2 == null) {
            Intrinsics.throwNpe();
        }
        ((EventListener)object).callFailed((Call)thread3, (IOException)thread2);
        return (E)thread2;
    }

    private final void callStart() {
        this.callStackTrace = Platform.Companion.get().getStackTraceForCloseable("response.body().close()");
        this.eventListener.callStart(this);
    }

    private final Address createAddress(HttpUrl httpUrl) {
        SSLSocketFactory sSLSocketFactory = null;
        HostnameVerifier hostnameVerifier = null;
        CertificatePinner certificatePinner = null;
        if (!httpUrl.isHttps()) return new Address(httpUrl.host(), httpUrl.port(), this.client.dns(), this.client.socketFactory(), sSLSocketFactory, hostnameVerifier, certificatePinner, this.client.proxyAuthenticator(), this.client.proxy(), this.client.protocols(), this.client.connectionSpecs(), this.client.proxySelector());
        sSLSocketFactory = this.client.sslSocketFactory();
        hostnameVerifier = this.client.hostnameVerifier();
        certificatePinner = this.client.certificatePinner();
        return new Address(httpUrl.host(), httpUrl.port(), this.client.dns(), this.client.socketFactory(), sSLSocketFactory, hostnameVerifier, certificatePinner, this.client.proxyAuthenticator(), this.client.proxy(), this.client.protocols(), this.client.connectionSpecs(), this.client.proxySelector());
    }

    private final <E extends IOException> E timeoutExit(E e) {
        if (this.timeoutEarlyExit) {
            return e;
        }
        if (!this.timeout.exit()) {
            return e;
        }
        InterruptedIOException interruptedIOException = new InterruptedIOException("timeout");
        if (e == null) return (E)interruptedIOException;
        interruptedIOException.initCause(e);
        return (E)interruptedIOException;
    }

    private final String toLoggableString() {
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = this.isCanceled() ? "canceled " : "";
        stringBuilder.append(string2);
        string2 = this.forWebSocket ? "web socket" : "call";
        stringBuilder.append(string2);
        stringBuilder.append(" to ");
        stringBuilder.append(this.redactedUrl$okhttp());
        return stringBuilder.toString();
    }

    public final void acquireConnectionNoEvents(RealConnection realConnection) {
        Intrinsics.checkParameterIsNotNull(realConnection, "connection");
        if (Util.assertionsEnabled && !Thread.holdsLock(realConnection)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Thread ");
            Thread thread2 = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(thread2, "Thread.currentThread()");
            stringBuilder.append(thread2.getName());
            stringBuilder.append(" MUST hold lock on ");
            stringBuilder.append(realConnection);
            throw (Throwable)((Object)new AssertionError((Object)stringBuilder.toString()));
        }
        boolean bl = this.connection == null;
        if (!bl) throw (Throwable)new IllegalStateException("Check failed.".toString());
        this.connection = realConnection;
        realConnection.getCalls().add(new CallReference(this, this.callStackTrace));
    }

    @Override
    public void cancel() {
        if (this.canceled) {
            return;
        }
        this.canceled = true;
        Object object = this.exchange;
        if (object != null) {
            ((Exchange)object).cancel();
        }
        if ((object = this.connectionToCancel) != null) {
            ((RealConnection)object).cancel();
        }
        this.eventListener.canceled(this);
    }

    @Override
    public RealCall clone() {
        return new RealCall(this.client, this.originalRequest, this.forWebSocket);
    }

    @Override
    public void enqueue(Callback callback) {
        Intrinsics.checkParameterIsNotNull(callback, "responseCallback");
        if (!this.executed.compareAndSet(false, true)) throw (Throwable)new IllegalStateException("Already Executed".toString());
        this.callStart();
        this.client.dispatcher().enqueue$okhttp(new AsyncCall(callback));
    }

    public final void enterNetworkInterceptorExchange(Request object, boolean bl) {
        Intrinsics.checkParameterIsNotNull(object, "request");
        boolean bl2 = this.interceptorScopedExchange == null;
        if (!bl2) throw (Throwable)new IllegalStateException("Check failed.".toString());
        synchronized (this) {
            if (this.responseBodyOpen ^ true) {
                if (this.requestBodyOpen ^ true) {
                    Unit unit = Unit.INSTANCE;
                    // MONITOREXIT [0, 2, 4, 5] lbl9 : MonitorExitStatement: MONITOREXIT : this
                    if (!bl) return;
                    this.exchangeFinder = new ExchangeFinder(this.connectionPool, this.createAddress(((Request)object).url()), this, this.eventListener);
                    return;
                }
                object = new IllegalStateException("Check failed.".toString());
                throw (Throwable)object;
            }
            object = new IllegalStateException("cannot make a new request because the previous response is still open: please call response.close()".toString());
            throw (Throwable)object;
        }
    }

    @Override
    public Response execute() {
        if (!this.executed.compareAndSet(false, true)) throw (Throwable)new IllegalStateException("Already Executed".toString());
        this.timeout.enter();
        this.callStart();
        try {
            this.client.dispatcher().executed$okhttp(this);
            Response response = this.getResponseWithInterceptorChain$okhttp();
            return response;
        }
        finally {
            this.client.dispatcher().finished$okhttp(this);
        }
    }

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public final void exitNetworkInterceptorExchange$okhttp(boolean bl) {
        // MONITORENTER : this
        if (!this.expectMoreExchanges) {
            IllegalStateException illegalStateException = new IllegalStateException("released".toString());
            throw (Throwable)illegalStateException;
        }
        Object object = Unit.INSTANCE;
        // MONITOREXIT : this
        if (bl && (object = this.exchange) != null) {
            ((Exchange)object).detachWithViolence();
        }
        this.interceptorScopedExchange = null;
    }

    public final OkHttpClient getClient() {
        return this.client;
    }

    public final RealConnection getConnection() {
        return this.connection;
    }

    public final RealConnection getConnectionToCancel() {
        return this.connectionToCancel;
    }

    public final EventListener getEventListener$okhttp() {
        return this.eventListener;
    }

    public final boolean getForWebSocket() {
        return this.forWebSocket;
    }

    public final Exchange getInterceptorScopedExchange$okhttp() {
        return this.interceptorScopedExchange;
    }

    public final Request getOriginalRequest() {
        return this.originalRequest;
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public final Response getResponseWithInterceptorChain$okhttp() throws IOException {
        boolean bl;
        Throwable throwable2222222;
        block8 : {
            Object object;
            boolean bl2;
            block7 : {
                object = new ArrayList();
                Collection collection = (Collection)object;
                CollectionsKt.addAll(collection, (Iterable)this.client.interceptors());
                collection.add(new RetryAndFollowUpInterceptor(this.client));
                collection.add(new BridgeInterceptor(this.client.cookieJar()));
                collection.add(new CacheInterceptor(this.client.cache()));
                collection.add(ConnectInterceptor.INSTANCE);
                if (!this.forWebSocket) {
                    CollectionsKt.addAll(collection, (Iterable)this.client.networkInterceptors());
                }
                collection.add(new CallServerInterceptor(this.forWebSocket));
                object = new RealInterceptorChain(this, (List<? extends Interceptor>)object, 0, null, this.originalRequest, this.client.connectTimeoutMillis(), this.client.readTimeoutMillis(), this.client.writeTimeoutMillis());
                bl = bl2 = false;
                object = ((RealInterceptorChain)object).proceed(this.originalRequest);
                bl = bl2;
                boolean bl3 = this.isCanceled();
                if (bl3) break block7;
                this.noMoreExchanges$okhttp(null);
                return object;
            }
            bl = bl2;
            try {
                Util.closeQuietly((Closeable)object);
                bl = bl2;
                bl = bl2;
                object = new IOException("Canceled");
                bl = bl2;
                throw (Throwable)object;
            }
            catch (Throwable throwable2222222) {
                break block8;
            }
            catch (IOException iOException) {
                bl = bl2 = true;
                Exception exception = this.noMoreExchanges$okhttp(iOException);
                if (exception == null) {
                    bl = bl2;
                    bl = bl2;
                    exception = new TypeCastException("null cannot be cast to non-null type kotlin.Throwable");
                    bl = bl2;
                    throw exception;
                }
                bl = bl2;
                throw (Throwable)exception;
            }
        }
        if (bl) throw throwable2222222;
        this.noMoreExchanges$okhttp(null);
        throw throwable2222222;
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final Exchange initExchange$okhttp(RealInterceptorChain object) {
        Object object2;
        Intrinsics.checkParameterIsNotNull(object, "chain");
        synchronized (this) {
            if (!this.expectMoreExchanges) {
                object = new IllegalStateException("released".toString());
                throw (Throwable)object;
            }
            if (!(this.responseBodyOpen ^ true)) {
                object = new IllegalStateException("Check failed.".toString());
                throw (Throwable)object;
            }
            if (!(this.requestBodyOpen ^ true)) {
                object = new IllegalStateException("Check failed.".toString());
                throw (Throwable)object;
            }
            object2 = Unit.INSTANCE;
        }
        object2 = this.exchangeFinder;
        if (object2 == null) {
            Intrinsics.throwNpe();
        }
        object = ((ExchangeFinder)object2).find(this.client, (RealInterceptorChain)object);
        this.interceptorScopedExchange = object2 = new Exchange(this, this.eventListener, (ExchangeFinder)object2, (ExchangeCodec)object);
        this.exchange = object2;
        synchronized (this) {
            this.requestBodyOpen = true;
            this.responseBodyOpen = true;
            object = Unit.INSTANCE;
        }
        if (this.canceled) throw (Throwable)new IOException("Canceled");
        return object2;
    }

    @Override
    public boolean isCanceled() {
        return this.canceled;
    }

    @Override
    public boolean isExecuted() {
        return this.executed.get();
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public final <E extends IOException> E messageDone$okhttp(Exchange var1_1, boolean var2_3, boolean var3_4, E var4_5) {
        block10 : {
            Intrinsics.checkParameterIsNotNull(var1_1, "exchange");
            var5_6 = Intrinsics.areEqual(var1_1, this.exchange);
            var6_7 = true;
            if (var5_6 ^ true) {
                return var4_5;
            }
            // MONITORENTER : this
            var7_8 = false;
            if (!var2_3) ** GOTO lbl11
            if (this.requestBodyOpen) ** GOTO lbl-1000
lbl11: // 2 sources:
            if (var3_4 && this.responseBodyOpen) lbl-1000: // 2 sources:
            {
                if (var2_3) {
                    this.requestBodyOpen = false;
                }
                if (var3_4) {
                    this.responseBodyOpen = false;
                }
                var7_8 = this.requestBodyOpen == false && this.responseBodyOpen == false;
                if (this.requestBodyOpen || this.responseBodyOpen || this.expectMoreExchanges) {
                    var6_7 = false;
                }
            } else {
                var6_7 = false;
            }
            var1_1 = Unit.INSTANCE;
            // MONITOREXIT : this
            if (!var7_8) break block10;
            this.exchange = null;
            var1_1 = this.connection;
            if (var1_1 != null) {
                var1_1.incrementSuccessCount$okhttp();
            }
        }
        if (var6_7 == false) return var4_5;
        return this.callDone(var4_5);
    }

    public final IOException noMoreExchanges$okhttp(IOException iOException) {
        synchronized (this) {
            boolean bl;
            boolean bl2 = this.expectMoreExchanges;
            boolean bl3 = bl = false;
            if (bl2) {
                this.expectMoreExchanges = false;
                bl3 = bl;
                if (!this.requestBodyOpen) {
                    bl3 = bl;
                    if (!this.responseBodyOpen) {
                        bl3 = true;
                    }
                }
            }
            Object object = Unit.INSTANCE;
            // MONITOREXIT [0, 1] lbl13 : MonitorExitStatement: MONITOREXIT : this
            object = iOException;
            if (!bl3) return object;
            return this.callDone(iOException);
        }
    }

    public final String redactedUrl$okhttp() {
        return this.originalRequest.url().redact();
    }

    public final Socket releaseConnectionNoEvents$okhttp() {
        int n;
        List<Reference<RealCall>> list;
        RealConnection realConnection;
        boolean bl;
        block5 : {
            realConnection = this.connection;
            if (realConnection == null) {
                Intrinsics.throwNpe();
            }
            if (Util.assertionsEnabled && !Thread.holdsLock(realConnection)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Thread ");
                Thread thread2 = Thread.currentThread();
                Intrinsics.checkExpressionValueIsNotNull(thread2, "Thread.currentThread()");
                stringBuilder.append(thread2.getName());
                stringBuilder.append(" MUST hold lock on ");
                stringBuilder.append(realConnection);
                throw (Throwable)((Object)new AssertionError((Object)stringBuilder.toString()));
            }
            list = realConnection.getCalls();
            Iterator<Reference<RealCall>> iterator2 = list.iterator();
            bl = false;
            n = 0;
            while (iterator2.hasNext()) {
                if (!Intrinsics.areEqual(iterator2.next().get(), this)) {
                    ++n;
                    continue;
                }
                break block5;
            }
            n = -1;
        }
        if (n != -1) {
            bl = true;
        }
        if (!bl) throw (Throwable)new IllegalStateException("Check failed.".toString());
        list.remove(n);
        this.connection = null;
        if (!list.isEmpty()) return null;
        realConnection.setIdleAtNs$okhttp(System.nanoTime());
        if (!this.connectionPool.connectionBecameIdle(realConnection)) return null;
        return realConnection.socket();
    }

    @Override
    public Request request() {
        return this.originalRequest;
    }

    public final boolean retryAfterFailure() {
        ExchangeFinder exchangeFinder = this.exchangeFinder;
        if (exchangeFinder != null) return exchangeFinder.retryAfterFailure();
        Intrinsics.throwNpe();
        return exchangeFinder.retryAfterFailure();
    }

    public final void setConnectionToCancel(RealConnection realConnection) {
        this.connectionToCancel = realConnection;
    }

    @Override
    public AsyncTimeout timeout() {
        return this.timeout;
    }

    public final void timeoutEarlyExit() {
        if (!(this.timeoutEarlyExit ^ true)) throw (Throwable)new IllegalStateException("Check failed.".toString());
        this.timeoutEarlyExit = true;
        this.timeout.exit();
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u0012\u0010\u001a\u001a\u00020\u00172\n\u0010\u001b\u001a\u00060\u0000R\u00020\u0006J\b\u0010\u001c\u001a\u00020\u0017H\u0016R\u0011\u0010\u0005\u001a\u00020\u00068F\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u001e\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000f8F\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u00138F\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2={"Lokhttp3/internal/connection/RealCall$AsyncCall;", "Ljava/lang/Runnable;", "responseCallback", "Lokhttp3/Callback;", "(Lokhttp3/internal/connection/RealCall;Lokhttp3/Callback;)V", "call", "Lokhttp3/internal/connection/RealCall;", "getCall", "()Lokhttp3/internal/connection/RealCall;", "<set-?>", "Ljava/util/concurrent/atomic/AtomicInteger;", "callsPerHost", "getCallsPerHost", "()Ljava/util/concurrent/atomic/AtomicInteger;", "host", "", "getHost", "()Ljava/lang/String;", "request", "Lokhttp3/Request;", "getRequest", "()Lokhttp3/Request;", "executeOn", "", "executorService", "Ljava/util/concurrent/ExecutorService;", "reuseCallsPerHostFrom", "other", "run", "okhttp"}, k=1, mv={1, 1, 16})
    public final class AsyncCall
    implements Runnable {
        private volatile AtomicInteger callsPerHost;
        private final Callback responseCallback;

        public AsyncCall(Callback callback) {
            Intrinsics.checkParameterIsNotNull(callback, "responseCallback");
            this.responseCallback = callback;
            this.callsPerHost = new AtomicInteger(0);
        }

        public final void executeOn(ExecutorService object) {
            Intrinsics.checkParameterIsNotNull(object, "executorService");
            Dispatcher dispatcher = RealCall.this.getClient().dispatcher();
            if (Util.assertionsEnabled && Thread.holdsLock(dispatcher)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Thread ");
                object = Thread.currentThread();
                Intrinsics.checkExpressionValueIsNotNull(object, "Thread.currentThread()");
                stringBuilder.append(((Thread)object).getName());
                stringBuilder.append(" MUST NOT hold lock on ");
                stringBuilder.append(dispatcher);
                throw (Throwable)((Object)new AssertionError((Object)stringBuilder.toString()));
            }
            try {
                try {
                    object.execute(this);
                    return;
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    object = new InterruptedIOException("executor rejected");
                    ((Throwable)object).initCause(rejectedExecutionException);
                    RealCall.this.noMoreExchanges$okhttp((IOException)object);
                    this.responseCallback.onFailure(RealCall.this, (IOException)object);
                    RealCall.this.getClient().dispatcher().finished$okhttp(this);
                }
                return;
            }
            catch (Throwable throwable) {}
            RealCall.this.getClient().dispatcher().finished$okhttp(this);
            throw throwable;
        }

        public final RealCall getCall() {
            return RealCall.this;
        }

        public final AtomicInteger getCallsPerHost() {
            return this.callsPerHost;
        }

        public final String getHost() {
            return RealCall.this.getOriginalRequest().url().host();
        }

        public final Request getRequest() {
            return RealCall.this.getOriginalRequest();
        }

        public final void reuseCallsPerHostFrom(AsyncCall asyncCall) {
            Intrinsics.checkParameterIsNotNull(asyncCall, "other");
            this.callsPerHost = asyncCall.callsPerHost;
        }

        /*
         * Unable to fully structure code
         * Enabled unnecessary exception pruning
         */
        @Override
        public void run() {
            block17 : {
                block18 : {
                    block16 : {
                        var1_1 = new StringBuilder();
                        var1_1.append("OkHttp ");
                        var1_1.append(RealCall.this.redactedUrl$okhttp());
                        var1_1 = var1_1.toString();
                        var2_9 = Thread.currentThread();
                        Intrinsics.checkExpressionValueIsNotNull(var2_9, "currentThread");
                        var3_10 = var2_9.getName();
                        var2_9.setName((String)var1_1);
                        RealCall.access$getTimeout$p(RealCall.this).enter();
                        var1_1 = RealCall.this.getResponseWithInterceptorChain$okhttp();
                        var4_11 = true;
                        var5_12 = true;
                        this.responseCallback.onResponse(RealCall.this, (Response)var1_1);
                        var1_1 = RealCall.this.getClient().dispatcher();
                        break block18;
                        catch (Throwable var1_2) {
                            break block16;
                        }
                        catch (IOException var1_3) {
                            var5_12 = var4_11;
                            ** GOTO lbl46
                        }
                        catch (Throwable var1_4) {
                            var5_12 = false;
                        }
                    }
                    try {
                        RealCall.this.cancel();
                        if (var5_12 != false) throw var1_5;
                        var7_15 = new StringBuilder();
                        var7_15.append("canceled due to ");
                        var7_15.append(var1_5);
                        var6_13 = new IOException(var7_15.toString());
                        var6_13.addSuppressed((Throwable)var1_5);
                        this.responseCallback.onFailure(RealCall.this, var6_13);
                        throw var1_5;
                        catch (IOException var1_7) {
                            var5_12 = false;
                        }
lbl46: // 2 sources:
                        if (var5_12) {
                            var6_14 = Platform.Companion.get();
                            var7_16 = new StringBuilder();
                            var7_16.append("Callback failure for ");
                            var7_16.append(RealCall.access$toLoggableString(RealCall.this));
                            var6_14.log(var7_16.toString(), 4, (Throwable)var1_1);
                        } else {
                            this.responseCallback.onFailure(RealCall.this, (IOException)var1_1);
                        }
                    }
                    catch (Throwable var1_6) {
                        break block17;
                    }
                    var1_1 = RealCall.this.getClient().dispatcher();
                }
                var1_1.finished$okhttp(this);
                return;
            }
            try {
                RealCall.this.getClient().dispatcher().finished$okhttp(this);
                throw var1_6;
            }
            finally {
                var2_9.setName(var3_10);
            }
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2={"Lokhttp3/internal/connection/RealCall$CallReference;", "Ljava/lang/ref/WeakReference;", "Lokhttp3/internal/connection/RealCall;", "referent", "callStackTrace", "", "(Lokhttp3/internal/connection/RealCall;Ljava/lang/Object;)V", "getCallStackTrace", "()Ljava/lang/Object;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class CallReference
    extends WeakReference<RealCall> {
        private final Object callStackTrace;

        public CallReference(RealCall realCall, Object object) {
            Intrinsics.checkParameterIsNotNull(realCall, "referent");
            super(realCall);
            this.callStackTrace = object;
        }

        public final Object getCallStackTrace() {
            return this.callStackTrace;
        }
    }

}

