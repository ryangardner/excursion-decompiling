/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.http;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.connection.ExchangeFinder;
import okhttp3.internal.connection.RealCall;
import okhttp3.internal.connection.RealConnection;

@Metadata(bv={1, 0, 3}, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001BM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\b\u0012\u0006\u0010\u000e\u001a\u00020\b\u0012\u0006\u0010\u000f\u001a\u00020\b\u00a2\u0006\u0002\u0010\u0010J\b\u0010\u0002\u001a\u00020\u001cH\u0016J\b\u0010\r\u001a\u00020\bH\u0016J\n\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016JK\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\u000e\u001a\u00020\b2\b\b\u0002\u0010\u000f\u001a\u00020\bH\u0000\u00a2\u0006\u0002\b J\u0010\u0010!\u001a\u00020\"2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\u000e\u001a\u00020\bH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010#\u001a\u00020\u00012\u0006\u0010$\u001a\u00020\b2\u0006\u0010%\u001a\u00020&H\u0016J\u0018\u0010'\u001a\u00020\u00012\u0006\u0010$\u001a\u00020\b2\u0006\u0010%\u001a\u00020&H\u0016J\u0018\u0010(\u001a\u00020\u00012\u0006\u0010$\u001a\u00020\b2\u0006\u0010%\u001a\u00020&H\u0016J\b\u0010\u000f\u001a\u00020\bH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\bX\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\t\u001a\u0004\u0018\u00010\nX\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\u00020\bX\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u0014\u0010\u000b\u001a\u00020\fX\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u000f\u001a\u00020\bX\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0015\u00a8\u0006)"}, d2={"Lokhttp3/internal/http/RealInterceptorChain;", "Lokhttp3/Interceptor$Chain;", "call", "Lokhttp3/internal/connection/RealCall;", "interceptors", "", "Lokhttp3/Interceptor;", "index", "", "exchange", "Lokhttp3/internal/connection/Exchange;", "request", "Lokhttp3/Request;", "connectTimeoutMillis", "readTimeoutMillis", "writeTimeoutMillis", "(Lokhttp3/internal/connection/RealCall;Ljava/util/List;ILokhttp3/internal/connection/Exchange;Lokhttp3/Request;III)V", "getCall$okhttp", "()Lokhttp3/internal/connection/RealCall;", "calls", "getConnectTimeoutMillis$okhttp", "()I", "getExchange$okhttp", "()Lokhttp3/internal/connection/Exchange;", "getReadTimeoutMillis$okhttp", "getRequest$okhttp", "()Lokhttp3/Request;", "getWriteTimeoutMillis$okhttp", "Lokhttp3/Call;", "connection", "Lokhttp3/Connection;", "copy", "copy$okhttp", "proceed", "Lokhttp3/Response;", "withConnectTimeout", "timeout", "unit", "Ljava/util/concurrent/TimeUnit;", "withReadTimeout", "withWriteTimeout", "okhttp"}, k=1, mv={1, 1, 16})
public final class RealInterceptorChain
implements Interceptor.Chain {
    private final RealCall call;
    private int calls;
    private final int connectTimeoutMillis;
    private final Exchange exchange;
    private final int index;
    private final List<Interceptor> interceptors;
    private final int readTimeoutMillis;
    private final Request request;
    private final int writeTimeoutMillis;

    public RealInterceptorChain(RealCall realCall, List<? extends Interceptor> list, int n, Exchange exchange, Request request, int n2, int n3, int n4) {
        Intrinsics.checkParameterIsNotNull(realCall, "call");
        Intrinsics.checkParameterIsNotNull(list, "interceptors");
        Intrinsics.checkParameterIsNotNull(request, "request");
        this.call = realCall;
        this.interceptors = list;
        this.index = n;
        this.exchange = exchange;
        this.request = request;
        this.connectTimeoutMillis = n2;
        this.readTimeoutMillis = n3;
        this.writeTimeoutMillis = n4;
    }

    public static /* synthetic */ RealInterceptorChain copy$okhttp$default(RealInterceptorChain realInterceptorChain, int n, Exchange exchange, Request request, int n2, int n3, int n4, int n5, Object object) {
        if ((n5 & 1) != 0) {
            n = realInterceptorChain.index;
        }
        if ((n5 & 2) != 0) {
            exchange = realInterceptorChain.exchange;
        }
        if ((n5 & 4) != 0) {
            request = realInterceptorChain.request;
        }
        if ((n5 & 8) != 0) {
            n2 = realInterceptorChain.connectTimeoutMillis;
        }
        if ((n5 & 16) != 0) {
            n3 = realInterceptorChain.readTimeoutMillis;
        }
        if ((n5 & 32) == 0) return realInterceptorChain.copy$okhttp(n, exchange, request, n2, n3, n4);
        n4 = realInterceptorChain.writeTimeoutMillis;
        return realInterceptorChain.copy$okhttp(n, exchange, request, n2, n3, n4);
    }

    @Override
    public Call call() {
        return this.call;
    }

    @Override
    public int connectTimeoutMillis() {
        return this.connectTimeoutMillis;
    }

    @Override
    public Connection connection() {
        Object object = this.exchange;
        if (object != null) {
            object = ((Exchange)object).getConnection$okhttp();
            return (Connection)object;
        }
        object = null;
        return (Connection)object;
    }

    public final RealInterceptorChain copy$okhttp(int n, Exchange exchange, Request request, int n2, int n3, int n4) {
        Intrinsics.checkParameterIsNotNull(request, "request");
        return new RealInterceptorChain(this.call, this.interceptors, n, exchange, request, n2, n3, n4);
    }

    public final RealCall getCall$okhttp() {
        return this.call;
    }

    public final int getConnectTimeoutMillis$okhttp() {
        return this.connectTimeoutMillis;
    }

    public final Exchange getExchange$okhttp() {
        return this.exchange;
    }

    public final int getReadTimeoutMillis$okhttp() {
        return this.readTimeoutMillis;
    }

    public final Request getRequest$okhttp() {
        return this.request;
    }

    public final int getWriteTimeoutMillis$okhttp() {
        return this.writeTimeoutMillis;
    }

    @Override
    public Response proceed(Request object) throws IOException {
        Intrinsics.checkParameterIsNotNull(object, "request");
        int n = this.index;
        int n2 = this.interceptors.size();
        int n3 = 0;
        n = n < n2 ? 1 : 0;
        if (n == 0) throw (Throwable)new IllegalStateException("Check failed.".toString());
        ++this.calls;
        Object object2 = this.exchange;
        if (object2 != null) {
            if (!((Exchange)object2).getFinder$okhttp().sameHostAndPort(((Request)object).url())) {
                object = new StringBuilder();
                ((StringBuilder)object).append("network interceptor ");
                ((StringBuilder)object).append(this.interceptors.get(this.index - 1));
                ((StringBuilder)object).append(" must retain the same host and port");
                throw (Throwable)new IllegalStateException(((StringBuilder)object).toString().toString());
            }
            n = this.calls == 1 ? 1 : 0;
            if (n == 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append("network interceptor ");
                ((StringBuilder)object).append(this.interceptors.get(this.index - 1));
                ((StringBuilder)object).append(" must call proceed() exactly once");
                throw (Throwable)new IllegalStateException(((StringBuilder)object).toString().toString());
            }
        }
        RealInterceptorChain realInterceptorChain = RealInterceptorChain.copy$okhttp$default(this, this.index + 1, null, (Request)object, 0, 0, 0, 58, null);
        object = this.interceptors.get(this.index);
        object2 = object.intercept(realInterceptorChain);
        if (object2 == null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("interceptor ");
            ((StringBuilder)object2).append(object);
            ((StringBuilder)object2).append(" returned null");
            throw (Throwable)new NullPointerException(((StringBuilder)object2).toString());
        }
        if (this.exchange != null) {
            n = this.index + 1 < this.interceptors.size() && realInterceptorChain.calls != 1 ? 0 : 1;
            if (n == 0) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("network interceptor ");
                ((StringBuilder)object2).append(object);
                ((StringBuilder)object2).append(" must call proceed() exactly once");
                throw (Throwable)new IllegalStateException(((StringBuilder)object2).toString().toString());
            }
        }
        n = n3;
        if (((Response)object2).body() != null) {
            n = 1;
        }
        if (n != 0) {
            return object2;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("interceptor ");
        ((StringBuilder)object2).append(object);
        ((StringBuilder)object2).append(" returned a response with no body");
        throw (Throwable)new IllegalStateException(((StringBuilder)object2).toString().toString());
    }

    @Override
    public int readTimeoutMillis() {
        return this.readTimeoutMillis;
    }

    @Override
    public Request request() {
        return this.request;
    }

    @Override
    public Interceptor.Chain withConnectTimeout(int n, TimeUnit timeUnit) {
        Intrinsics.checkParameterIsNotNull((Object)timeUnit, "unit");
        boolean bl = this.exchange == null;
        if (!bl) throw (Throwable)new IllegalStateException("Timeouts can't be adjusted in a network interceptor".toString());
        return RealInterceptorChain.copy$okhttp$default(this, 0, null, null, Util.checkDuration("connectTimeout", n, timeUnit), 0, 0, 55, null);
    }

    @Override
    public Interceptor.Chain withReadTimeout(int n, TimeUnit timeUnit) {
        Intrinsics.checkParameterIsNotNull((Object)timeUnit, "unit");
        boolean bl = this.exchange == null;
        if (!bl) throw (Throwable)new IllegalStateException("Timeouts can't be adjusted in a network interceptor".toString());
        return RealInterceptorChain.copy$okhttp$default(this, 0, null, null, 0, Util.checkDuration("readTimeout", n, timeUnit), 0, 47, null);
    }

    @Override
    public Interceptor.Chain withWriteTimeout(int n, TimeUnit timeUnit) {
        Intrinsics.checkParameterIsNotNull((Object)timeUnit, "unit");
        boolean bl = this.exchange == null;
        if (!bl) throw (Throwable)new IllegalStateException("Timeouts can't be adjusted in a network interceptor".toString());
        return RealInterceptorChain.copy$okhttp$default(this, 0, null, null, 0, 0, Util.checkDuration("writeTimeout", n, timeUnit), 31, null);
    }

    @Override
    public int writeTimeoutMillis() {
        return this.writeTimeoutMillis;
    }
}

