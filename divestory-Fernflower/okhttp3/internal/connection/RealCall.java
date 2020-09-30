package okhttp3.internal.connection;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CertificatePinner;
import okhttp3.Connection;
import okhttp3.Dispatcher;
import okhttp3.EventListener;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.platform.Platform;
import okio.AsyncTimeout;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000§\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006*\u0001.\u0018\u00002\u00020\u0001:\u0002deB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u00101\u001a\u0002022\u0006\u0010\u0010\u001a\u00020\u000fJ!\u00103\u001a\u0002H4\"\n\b\u0000\u00104*\u0004\u0018\u0001052\u0006\u00106\u001a\u0002H4H\u0002¢\u0006\u0002\u00107J\b\u00108\u001a\u000202H\u0002J\b\u00109\u001a\u000202H\u0016J\b\u0010:\u001a\u00020\u0000H\u0016J\u0010\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020>H\u0002J\u0010\u0010?\u001a\u0002022\u0006\u0010@\u001a\u00020AH\u0016J\u0016\u0010B\u001a\u0002022\u0006\u0010C\u001a\u00020\u00052\u0006\u0010D\u001a\u00020\u0007J\b\u0010E\u001a\u00020FH\u0016J\u0015\u0010G\u001a\u0002022\u0006\u0010H\u001a\u00020\u0007H\u0000¢\u0006\u0002\bIJ\r\u0010J\u001a\u00020FH\u0000¢\u0006\u0002\bKJ\u0015\u0010L\u001a\u00020\u001e2\u0006\u0010M\u001a\u00020NH\u0000¢\u0006\u0002\bOJ\b\u0010P\u001a\u00020\u0007H\u0016J\b\u0010Q\u001a\u00020\u0007H\u0016J;\u0010R\u001a\u0002H4\"\n\b\u0000\u00104*\u0004\u0018\u0001052\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010S\u001a\u00020\u00072\u0006\u0010T\u001a\u00020\u00072\u0006\u00106\u001a\u0002H4H\u0000¢\u0006\u0004\bU\u0010VJ\u0019\u0010W\u001a\u0004\u0018\u0001052\b\u00106\u001a\u0004\u0018\u000105H\u0000¢\u0006\u0002\bXJ\r\u0010Y\u001a\u00020ZH\u0000¢\u0006\u0002\b[J\u000f\u0010\\\u001a\u0004\u0018\u00010]H\u0000¢\u0006\u0002\b^J\b\u0010C\u001a\u00020\u0005H\u0016J\u0006\u0010_\u001a\u00020\u0007J\b\u0010-\u001a\u00020`H\u0016J\u0006\u00100\u001a\u000202J!\u0010a\u001a\u0002H4\"\n\b\u0000\u00104*\u0004\u0018\u0001052\u0006\u0010b\u001a\u0002H4H\u0002¢\u0006\u0002\u00107J\b\u0010c\u001a\u00020ZH\u0002R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\"\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0012\"\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u001aX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\"\u0010&\u001a\u0004\u0018\u00010\u001e2\b\u0010\u000e\u001a\u0004\u0018\u00010\u001e@BX\u0080\u000e¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u000e\u0010+\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010-\u001a\u00020.X\u0082\u0004¢\u0006\u0004\n\u0002\u0010/R\u000e\u00100\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006f"},
   d2 = {"Lokhttp3/internal/connection/RealCall;", "Lokhttp3/Call;", "client", "Lokhttp3/OkHttpClient;", "originalRequest", "Lokhttp3/Request;", "forWebSocket", "", "(Lokhttp3/OkHttpClient;Lokhttp3/Request;Z)V", "callStackTrace", "", "canceled", "getClient", "()Lokhttp3/OkHttpClient;", "<set-?>", "Lokhttp3/internal/connection/RealConnection;", "connection", "getConnection", "()Lokhttp3/internal/connection/RealConnection;", "connectionPool", "Lokhttp3/internal/connection/RealConnectionPool;", "connectionToCancel", "getConnectionToCancel", "setConnectionToCancel", "(Lokhttp3/internal/connection/RealConnection;)V", "eventListener", "Lokhttp3/EventListener;", "getEventListener$okhttp", "()Lokhttp3/EventListener;", "exchange", "Lokhttp3/internal/connection/Exchange;", "exchangeFinder", "Lokhttp3/internal/connection/ExchangeFinder;", "executed", "Ljava/util/concurrent/atomic/AtomicBoolean;", "expectMoreExchanges", "getForWebSocket", "()Z", "interceptorScopedExchange", "getInterceptorScopedExchange$okhttp", "()Lokhttp3/internal/connection/Exchange;", "getOriginalRequest", "()Lokhttp3/Request;", "requestBodyOpen", "responseBodyOpen", "timeout", "okhttp3/internal/connection/RealCall$timeout$1", "Lokhttp3/internal/connection/RealCall$timeout$1;", "timeoutEarlyExit", "acquireConnectionNoEvents", "", "callDone", "E", "Ljava/io/IOException;", "e", "(Ljava/io/IOException;)Ljava/io/IOException;", "callStart", "cancel", "clone", "createAddress", "Lokhttp3/Address;", "url", "Lokhttp3/HttpUrl;", "enqueue", "responseCallback", "Lokhttp3/Callback;", "enterNetworkInterceptorExchange", "request", "newExchangeFinder", "execute", "Lokhttp3/Response;", "exitNetworkInterceptorExchange", "closeExchange", "exitNetworkInterceptorExchange$okhttp", "getResponseWithInterceptorChain", "getResponseWithInterceptorChain$okhttp", "initExchange", "chain", "Lokhttp3/internal/http/RealInterceptorChain;", "initExchange$okhttp", "isCanceled", "isExecuted", "messageDone", "requestDone", "responseDone", "messageDone$okhttp", "(Lokhttp3/internal/connection/Exchange;ZZLjava/io/IOException;)Ljava/io/IOException;", "noMoreExchanges", "noMoreExchanges$okhttp", "redactedUrl", "", "redactedUrl$okhttp", "releaseConnectionNoEvents", "Ljava/net/Socket;", "releaseConnectionNoEvents$okhttp", "retryAfterFailure", "Lokio/AsyncTimeout;", "timeoutExit", "cause", "toLoggableString", "AsyncCall", "CallReference", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class RealCall implements Call {
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
   private final <undefinedtype> timeout;
   private boolean timeoutEarlyExit;

   public RealCall(OkHttpClient var1, Request var2, boolean var3) {
      Intrinsics.checkParameterIsNotNull(var1, "client");
      Intrinsics.checkParameterIsNotNull(var2, "originalRequest");
      super();
      this.client = var1;
      this.originalRequest = var2;
      this.forWebSocket = var3;
      this.connectionPool = var1.connectionPool().getDelegate$okhttp();
      this.eventListener = this.client.eventListenerFactory().create((Call)this);
      AsyncTimeout var4 = new AsyncTimeout() {
         protected void timedOut() {
            RealCall.this.cancel();
         }
      };
      var4.timeout((long)this.client.callTimeoutMillis(), TimeUnit.MILLISECONDS);
      this.timeout = var4;
      this.executed = new AtomicBoolean();
      this.expectMoreExchanges = true;
   }

   // $FF: synthetic method
   public static final <undefinedtype> access$getTimeout$p(RealCall var0) {
      return var0.timeout;
   }

   // $FF: synthetic method
   public static final String access$toLoggableString(RealCall var0) {
      return var0.toLoggableString();
   }

   private final <E extends IOException> E callDone(E var1) {
      StringBuilder var8;
      if (Util.assertionsEnabled && Thread.holdsLock(this)) {
         var8 = new StringBuilder();
         var8.append("Thread ");
         Thread var10 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var10, "Thread.currentThread()");
         var8.append(var10.getName());
         var8.append(" MUST NOT hold lock on ");
         var8.append(this);
         throw (Throwable)(new AssertionError(var8.toString()));
      } else {
         RealConnection var2 = this.connection;
         if (var2 != null) {
            if (Util.assertionsEnabled && Thread.holdsLock(var2)) {
               var8 = new StringBuilder();
               var8.append("Thread ");
               Thread var12 = Thread.currentThread();
               Intrinsics.checkExpressionValueIsNotNull(var12, "Thread.currentThread()");
               var8.append(var12.getName());
               var8.append(" MUST NOT hold lock on ");
               var8.append(var2);
               throw (Throwable)(new AssertionError(var8.toString()));
            }

            synchronized(var2){}

            Socket var3;
            try {
               var3 = this.releaseConnectionNoEvents$okhttp();
            } finally {
               ;
            }

            if (this.connection == null) {
               if (var3 != null) {
                  Util.closeQuietly(var3);
               }

               this.eventListener.connectionReleased((Call)this, (Connection)var2);
            } else {
               boolean var4;
               if (var3 == null) {
                  var4 = true;
               } else {
                  var4 = false;
               }

               if (!var4) {
                  throw (Throwable)(new IllegalStateException("Check failed.".toString()));
               }
            }
         }

         IOException var9 = this.timeoutExit(var1);
         if (var1 != null) {
            EventListener var7 = this.eventListener;
            Call var11 = (Call)this;
            if (var9 == null) {
               Intrinsics.throwNpe();
            }

            var7.callFailed(var11, var9);
         } else {
            this.eventListener.callEnd((Call)this);
         }

         return var9;
      }
   }

   private final void callStart() {
      this.callStackTrace = Platform.Companion.get().getStackTraceForCloseable("response.body().close()");
      this.eventListener.callStart((Call)this);
   }

   private final Address createAddress(HttpUrl var1) {
      SSLSocketFactory var2 = (SSLSocketFactory)null;
      HostnameVerifier var3 = (HostnameVerifier)null;
      CertificatePinner var4 = (CertificatePinner)null;
      if (var1.isHttps()) {
         var2 = this.client.sslSocketFactory();
         var3 = this.client.hostnameVerifier();
         var4 = this.client.certificatePinner();
      }

      return new Address(var1.host(), var1.port(), this.client.dns(), this.client.socketFactory(), var2, var3, var4, this.client.proxyAuthenticator(), this.client.proxy(), this.client.protocols(), this.client.connectionSpecs(), this.client.proxySelector());
   }

   private final <E extends IOException> E timeoutExit(E var1) {
      if (this.timeoutEarlyExit) {
         return var1;
      } else if (!this.timeout.exit()) {
         return var1;
      } else {
         InterruptedIOException var2 = new InterruptedIOException("timeout");
         if (var1 != null) {
            var2.initCause((Throwable)var1);
         }

         return (IOException)var2;
      }
   }

   private final String toLoggableString() {
      StringBuilder var1 = new StringBuilder();
      String var2;
      if (this.isCanceled()) {
         var2 = "canceled ";
      } else {
         var2 = "";
      }

      var1.append(var2);
      if (this.forWebSocket) {
         var2 = "web socket";
      } else {
         var2 = "call";
      }

      var1.append(var2);
      var1.append(" to ");
      var1.append(this.redactedUrl$okhttp());
      return var1.toString();
   }

   public final void acquireConnectionNoEvents(RealConnection var1) {
      Intrinsics.checkParameterIsNotNull(var1, "connection");
      if (Util.assertionsEnabled && !Thread.holdsLock(var1)) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Thread ");
         Thread var3 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var3, "Thread.currentThread()");
         var2.append(var3.getName());
         var2.append(" MUST hold lock on ");
         var2.append(var1);
         throw (Throwable)(new AssertionError(var2.toString()));
      } else {
         boolean var4;
         if (this.connection == null) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            this.connection = var1;
            var1.getCalls().add(new RealCall.CallReference(this, this.callStackTrace));
         } else {
            throw (Throwable)(new IllegalStateException("Check failed.".toString()));
         }
      }
   }

   public void cancel() {
      if (!this.canceled) {
         this.canceled = true;
         Exchange var1 = this.exchange;
         if (var1 != null) {
            var1.cancel();
         }

         RealConnection var2 = this.connectionToCancel;
         if (var2 != null) {
            var2.cancel();
         }

         this.eventListener.canceled((Call)this);
      }
   }

   public RealCall clone() {
      return new RealCall(this.client, this.originalRequest, this.forWebSocket);
   }

   public void enqueue(Callback var1) {
      Intrinsics.checkParameterIsNotNull(var1, "responseCallback");
      if (this.executed.compareAndSet(false, true)) {
         this.callStart();
         this.client.dispatcher().enqueue$okhttp(new RealCall.AsyncCall(var1));
      } else {
         throw (Throwable)(new IllegalStateException("Already Executed".toString()));
      }
   }

   public final void enterNetworkInterceptorExchange(Request var1, boolean var2) {
      Intrinsics.checkParameterIsNotNull(var1, "request");
      boolean var3;
      if (this.interceptorScopedExchange == null) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         synchronized(this){}

         try {
            IllegalStateException var7;
            if (!(this.responseBodyOpen ^ true)) {
               var7 = new IllegalStateException("cannot make a new request because the previous response is still open: please call response.close()".toString());
               throw (Throwable)var7;
            }

            if (!(this.requestBodyOpen ^ true)) {
               var7 = new IllegalStateException("Check failed.".toString());
               throw (Throwable)var7;
            }

            Unit var4 = Unit.INSTANCE;
         } finally {
            ;
         }

         if (var2) {
            this.exchangeFinder = new ExchangeFinder(this.connectionPool, this.createAddress(var1.url()), this, this.eventListener);
         }

      } else {
         throw (Throwable)(new IllegalStateException("Check failed.".toString()));
      }
   }

   public Response execute() {
      if (this.executed.compareAndSet(false, true)) {
         this.timeout.enter();
         this.callStart();

         Response var1;
         try {
            this.client.dispatcher().executed$okhttp(this);
            var1 = this.getResponseWithInterceptorChain$okhttp();
         } finally {
            this.client.dispatcher().finished$okhttp(this);
         }

         return var1;
      } else {
         throw (Throwable)(new IllegalStateException("Already Executed".toString()));
      }
   }

   public final void exitNetworkInterceptorExchange$okhttp(boolean var1) {
      synchronized(this){}

      try {
         if (!this.expectMoreExchanges) {
            IllegalStateException var5 = new IllegalStateException("released".toString());
            throw (Throwable)var5;
         }

         Unit var2 = Unit.INSTANCE;
      } finally {
         ;
      }

      if (var1) {
         Exchange var6 = this.exchange;
         if (var6 != null) {
            var6.detachWithViolence();
         }
      }

      this.interceptorScopedExchange = (Exchange)null;
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

   public final Response getResponseWithInterceptorChain$okhttp() throws IOException {
      // $FF: Couldn't be decompiled
   }

   public final Exchange initExchange$okhttp(RealInterceptorChain var1) {
      Intrinsics.checkParameterIsNotNull(var1, "chain");
      synchronized(this){}

      try {
         label123: {
            IllegalStateException var9;
            if (this.expectMoreExchanges) {
               if (!(this.responseBodyOpen ^ true)) {
                  var9 = new IllegalStateException("Check failed.".toString());
                  throw (Throwable)var9;
               }

               if (this.requestBodyOpen ^ true) {
                  Unit var2 = Unit.INSTANCE;
                  break label123;
               }

               var9 = new IllegalStateException("Check failed.".toString());
               throw (Throwable)var9;
            }

            var9 = new IllegalStateException("released".toString());
            throw (Throwable)var9;
         }
      } finally {
         ;
      }

      ExchangeFinder var10 = this.exchangeFinder;
      if (var10 == null) {
         Intrinsics.throwNpe();
      }

      ExchangeCodec var11 = var10.find(this.client, var1);
      Exchange var12 = new Exchange(this, this.eventListener, var10, var11);
      this.interceptorScopedExchange = var12;
      this.exchange = var12;
      synchronized(this){}

      try {
         this.requestBodyOpen = true;
         this.responseBodyOpen = true;
         Unit var13 = Unit.INSTANCE;
      } finally {
         ;
      }

      if (!this.canceled) {
         return var12;
      } else {
         throw (Throwable)(new IOException("Canceled"));
      }
   }

   public boolean isCanceled() {
      return this.canceled;
   }

   public boolean isExecuted() {
      return this.executed.get();
   }

   public final <E extends IOException> E messageDone$okhttp(Exchange var1, boolean var2, boolean var3, E var4) {
      Intrinsics.checkParameterIsNotNull(var1, "exchange");
      boolean var5 = Intrinsics.areEqual((Object)var1, (Object)this.exchange);
      boolean var6 = true;
      if (var5 ^ true) {
         return var4;
      } else {
         boolean var7;
         label750: {
            Throwable var10000;
            label749: {
               boolean var10001;
               label756: {
                  label755: {
                     synchronized(this){}
                     var7 = false;
                     if (var2) {
                        try {
                           if (this.requestBodyOpen) {
                              break label755;
                           }
                        } catch (Throwable var63) {
                           var10000 = var63;
                           var10001 = false;
                           break label749;
                        }
                     }

                     if (var3) {
                        try {
                           if (this.responseBodyOpen) {
                              break label755;
                           }
                        } catch (Throwable var62) {
                           var10000 = var62;
                           var10001 = false;
                           break label749;
                        }
                     }

                     var6 = false;
                     break label756;
                  }

                  if (var2) {
                     try {
                        this.requestBodyOpen = false;
                     } catch (Throwable var59) {
                        var10000 = var59;
                        var10001 = false;
                        break label749;
                     }
                  }

                  if (var3) {
                     try {
                        this.responseBodyOpen = false;
                     } catch (Throwable var58) {
                        var10000 = var58;
                        var10001 = false;
                        break label749;
                     }
                  }

                  label723: {
                     label722: {
                        try {
                           if (this.requestBodyOpen || this.responseBodyOpen) {
                              break label722;
                           }
                        } catch (Throwable var60) {
                           var10000 = var60;
                           var10001 = false;
                           break label749;
                        }

                        var7 = true;
                        break label723;
                     }

                     var7 = false;
                  }

                  try {
                     if (!this.requestBodyOpen && !this.responseBodyOpen && !this.expectMoreExchanges) {
                        break label756;
                     }
                  } catch (Throwable var61) {
                     var10000 = var61;
                     var10001 = false;
                     break label749;
                  }

                  var6 = false;
               }

               label710:
               try {
                  Unit var65 = Unit.INSTANCE;
                  break label750;
               } catch (Throwable var57) {
                  var10000 = var57;
                  var10001 = false;
                  break label710;
               }
            }

            Throwable var64 = var10000;
            throw var64;
         }

         if (var7) {
            this.exchange = (Exchange)null;
            RealConnection var66 = this.connection;
            if (var66 != null) {
               var66.incrementSuccessCount$okhttp();
            }
         }

         return var6 ? this.callDone(var4) : var4;
      }
   }

   public final IOException noMoreExchanges$okhttp(IOException var1) {
      synchronized(this){}

      boolean var4;
      label307: {
         Throwable var10000;
         label311: {
            boolean var2;
            boolean var10001;
            try {
               var2 = this.expectMoreExchanges;
            } catch (Throwable var35) {
               var10000 = var35;
               var10001 = false;
               break label311;
            }

            boolean var3 = false;
            var4 = var3;
            if (var2) {
               label312: {
                  try {
                     this.expectMoreExchanges = false;
                  } catch (Throwable var32) {
                     var10000 = var32;
                     var10001 = false;
                     break label311;
                  }

                  var4 = var3;

                  try {
                     if (this.requestBodyOpen) {
                        break label312;
                     }
                  } catch (Throwable var34) {
                     var10000 = var34;
                     var10001 = false;
                     break label311;
                  }

                  var4 = var3;

                  try {
                     if (this.responseBodyOpen) {
                        break label312;
                     }
                  } catch (Throwable var33) {
                     var10000 = var33;
                     var10001 = false;
                     break label311;
                  }

                  var4 = true;
               }
            }

            label288:
            try {
               Unit var5 = Unit.INSTANCE;
               break label307;
            } catch (Throwable var31) {
               var10000 = var31;
               var10001 = false;
               break label288;
            }
         }

         Throwable var36 = var10000;
         throw var36;
      }

      IOException var37 = var1;
      if (var4) {
         var37 = this.callDone(var1);
      }

      return var37;
   }

   public final String redactedUrl$okhttp() {
      return this.originalRequest.url().redact();
   }

   public final Socket releaseConnectionNoEvents$okhttp() {
      RealConnection var1 = this.connection;
      if (var1 == null) {
         Intrinsics.throwNpe();
      }

      if (Util.assertionsEnabled && !Thread.holdsLock(var1)) {
         StringBuilder var6 = new StringBuilder();
         var6.append("Thread ");
         Thread var7 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var7, "Thread.currentThread()");
         var6.append(var7.getName());
         var6.append(" MUST hold lock on ");
         var6.append(var1);
         throw (Throwable)(new AssertionError(var6.toString()));
      } else {
         List var2 = var1.getCalls();
         Iterator var3 = var2.iterator();
         boolean var4 = false;
         int var5 = 0;

         while(true) {
            if (!var3.hasNext()) {
               var5 = -1;
               break;
            }

            if (Intrinsics.areEqual((Object)((RealCall)((Reference)var3.next()).get()), (Object)((RealCall)this))) {
               break;
            }

            ++var5;
         }

         if (var5 != -1) {
            var4 = true;
         }

         if (var4) {
            var2.remove(var5);
            this.connection = (RealConnection)null;
            if (var2.isEmpty()) {
               var1.setIdleAtNs$okhttp(System.nanoTime());
               if (this.connectionPool.connectionBecameIdle(var1)) {
                  return var1.socket();
               }
            }

            return null;
         } else {
            throw (Throwable)(new IllegalStateException("Check failed.".toString()));
         }
      }
   }

   public Request request() {
      return this.originalRequest;
   }

   public final boolean retryAfterFailure() {
      ExchangeFinder var1 = this.exchangeFinder;
      if (var1 == null) {
         Intrinsics.throwNpe();
      }

      return var1.retryAfterFailure();
   }

   public final void setConnectionToCancel(RealConnection var1) {
      this.connectionToCancel = var1;
   }

   public AsyncTimeout timeout() {
      return (AsyncTimeout)this.timeout;
   }

   public final void timeoutEarlyExit() {
      if (this.timeoutEarlyExit ^ true) {
         this.timeoutEarlyExit = true;
         this.timeout.exit();
      } else {
         throw (Throwable)(new IllegalStateException("Check failed.".toString()));
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0080\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u0012\u0010\u001a\u001a\u00020\u00172\n\u0010\u001b\u001a\u00060\u0000R\u00020\u0006J\b\u0010\u001c\u001a\u00020\u0017H\u0016R\u0011\u0010\u0005\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u001e\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u00138F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"},
      d2 = {"Lokhttp3/internal/connection/RealCall$AsyncCall;", "Ljava/lang/Runnable;", "responseCallback", "Lokhttp3/Callback;", "(Lokhttp3/internal/connection/RealCall;Lokhttp3/Callback;)V", "call", "Lokhttp3/internal/connection/RealCall;", "getCall", "()Lokhttp3/internal/connection/RealCall;", "<set-?>", "Ljava/util/concurrent/atomic/AtomicInteger;", "callsPerHost", "getCallsPerHost", "()Ljava/util/concurrent/atomic/AtomicInteger;", "host", "", "getHost", "()Ljava/lang/String;", "request", "Lokhttp3/Request;", "getRequest", "()Lokhttp3/Request;", "executeOn", "", "executorService", "Ljava/util/concurrent/ExecutorService;", "reuseCallsPerHostFrom", "other", "run", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public final class AsyncCall implements Runnable {
      private volatile AtomicInteger callsPerHost;
      private final Callback responseCallback;

      public AsyncCall(Callback var2) {
         Intrinsics.checkParameterIsNotNull(var2, "responseCallback");
         super();
         this.responseCallback = var2;
         this.callsPerHost = new AtomicInteger(0);
      }

      public final void executeOn(ExecutorService var1) {
         Intrinsics.checkParameterIsNotNull(var1, "executorService");
         Dispatcher var2 = RealCall.this.getClient().dispatcher();
         if (Util.assertionsEnabled && Thread.holdsLock(var2)) {
            StringBuilder var3 = new StringBuilder();
            var3.append("Thread ");
            Thread var10 = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(var10, "Thread.currentThread()");
            var3.append(var10.getName());
            var3.append(" MUST NOT hold lock on ");
            var3.append(var2);
            throw (Throwable)(new AssertionError(var3.toString()));
         } else {
            boolean var6 = false;

            try {
               var6 = true;
               var1.execute((Runnable)this);
               var6 = false;
               return;
            } catch (RejectedExecutionException var7) {
               InterruptedIOException var9 = new InterruptedIOException("executor rejected");
               var9.initCause((Throwable)var7);
               RealCall.this.noMoreExchanges$okhttp((IOException)var9);
               this.responseCallback.onFailure((Call)RealCall.this, (IOException)var9);
               var6 = false;
            } finally {
               if (var6) {
                  RealCall.this.getClient().dispatcher().finished$okhttp(this);
               }
            }

            RealCall.this.getClient().dispatcher().finished$okhttp(this);
         }
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

      public final void reuseCallsPerHostFrom(RealCall.AsyncCall var1) {
         Intrinsics.checkParameterIsNotNull(var1, "other");
         this.callsPerHost = var1.callsPerHost;
      }

      public void run() {
         // $FF: Couldn't be decompiled
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"},
      d2 = {"Lokhttp3/internal/connection/RealCall$CallReference;", "Ljava/lang/ref/WeakReference;", "Lokhttp3/internal/connection/RealCall;", "referent", "callStackTrace", "", "(Lokhttp3/internal/connection/RealCall;Ljava/lang/Object;)V", "getCallStackTrace", "()Ljava/lang/Object;", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class CallReference extends WeakReference<RealCall> {
      private final Object callStackTrace;

      public CallReference(RealCall var1, Object var2) {
         Intrinsics.checkParameterIsNotNull(var1, "referent");
         super(var1);
         this.callStackTrace = var2;
      }

      public final Object getCallStackTrace() {
         return this.callStackTrace;
      }
   }
}
