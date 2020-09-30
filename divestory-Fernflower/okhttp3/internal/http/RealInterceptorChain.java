package okhttp3.internal.http;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.connection.RealCall;
import okhttp3.internal.connection.RealConnection;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001BM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\b\u0012\u0006\u0010\u000e\u001a\u00020\b\u0012\u0006\u0010\u000f\u001a\u00020\b¢\u0006\u0002\u0010\u0010J\b\u0010\u0002\u001a\u00020\u001cH\u0016J\b\u0010\r\u001a\u00020\bH\u0016J\n\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016JK\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\u000e\u001a\u00020\b2\b\b\u0002\u0010\u000f\u001a\u00020\bH\u0000¢\u0006\u0002\b J\u0010\u0010!\u001a\u00020\"2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\u000e\u001a\u00020\bH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010#\u001a\u00020\u00012\u0006\u0010$\u001a\u00020\b2\u0006\u0010%\u001a\u00020&H\u0016J\u0018\u0010'\u001a\u00020\u00012\u0006\u0010$\u001a\u00020\b2\u0006\u0010%\u001a\u00020&H\u0016J\u0018\u0010(\u001a\u00020\u00012\u0006\u0010$\u001a\u00020\b2\u0006\u0010%\u001a\u00020&H\u0016J\b\u0010\u000f\u001a\u00020\bH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\t\u001a\u0004\u0018\u00010\nX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\u00020\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u0014\u0010\u000b\u001a\u00020\fX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u000f\u001a\u00020\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0015¨\u0006)"},
   d2 = {"Lokhttp3/internal/http/RealInterceptorChain;", "Lokhttp3/Interceptor$Chain;", "call", "Lokhttp3/internal/connection/RealCall;", "interceptors", "", "Lokhttp3/Interceptor;", "index", "", "exchange", "Lokhttp3/internal/connection/Exchange;", "request", "Lokhttp3/Request;", "connectTimeoutMillis", "readTimeoutMillis", "writeTimeoutMillis", "(Lokhttp3/internal/connection/RealCall;Ljava/util/List;ILokhttp3/internal/connection/Exchange;Lokhttp3/Request;III)V", "getCall$okhttp", "()Lokhttp3/internal/connection/RealCall;", "calls", "getConnectTimeoutMillis$okhttp", "()I", "getExchange$okhttp", "()Lokhttp3/internal/connection/Exchange;", "getReadTimeoutMillis$okhttp", "getRequest$okhttp", "()Lokhttp3/Request;", "getWriteTimeoutMillis$okhttp", "Lokhttp3/Call;", "connection", "Lokhttp3/Connection;", "copy", "copy$okhttp", "proceed", "Lokhttp3/Response;", "withConnectTimeout", "timeout", "unit", "Ljava/util/concurrent/TimeUnit;", "withReadTimeout", "withWriteTimeout", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class RealInterceptorChain implements Interceptor.Chain {
   private final RealCall call;
   private int calls;
   private final int connectTimeoutMillis;
   private final Exchange exchange;
   private final int index;
   private final List<Interceptor> interceptors;
   private final int readTimeoutMillis;
   private final Request request;
   private final int writeTimeoutMillis;

   public RealInterceptorChain(RealCall var1, List<? extends Interceptor> var2, int var3, Exchange var4, Request var5, int var6, int var7, int var8) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      Intrinsics.checkParameterIsNotNull(var2, "interceptors");
      Intrinsics.checkParameterIsNotNull(var5, "request");
      super();
      this.call = var1;
      this.interceptors = var2;
      this.index = var3;
      this.exchange = var4;
      this.request = var5;
      this.connectTimeoutMillis = var6;
      this.readTimeoutMillis = var7;
      this.writeTimeoutMillis = var8;
   }

   // $FF: synthetic method
   public static RealInterceptorChain copy$okhttp$default(RealInterceptorChain var0, int var1, Exchange var2, Request var3, int var4, int var5, int var6, int var7, Object var8) {
      if ((var7 & 1) != 0) {
         var1 = var0.index;
      }

      if ((var7 & 2) != 0) {
         var2 = var0.exchange;
      }

      if ((var7 & 4) != 0) {
         var3 = var0.request;
      }

      if ((var7 & 8) != 0) {
         var4 = var0.connectTimeoutMillis;
      }

      if ((var7 & 16) != 0) {
         var5 = var0.readTimeoutMillis;
      }

      if ((var7 & 32) != 0) {
         var6 = var0.writeTimeoutMillis;
      }

      return var0.copy$okhttp(var1, var2, var3, var4, var5, var6);
   }

   public Call call() {
      return (Call)this.call;
   }

   public int connectTimeoutMillis() {
      return this.connectTimeoutMillis;
   }

   public Connection connection() {
      Exchange var1 = this.exchange;
      RealConnection var2;
      if (var1 != null) {
         var2 = var1.getConnection$okhttp();
      } else {
         var2 = null;
      }

      return (Connection)var2;
   }

   public final RealInterceptorChain copy$okhttp(int var1, Exchange var2, Request var3, int var4, int var5, int var6) {
      Intrinsics.checkParameterIsNotNull(var3, "request");
      return new RealInterceptorChain(this.call, this.interceptors, var1, var2, var3, var4, var5, var6);
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

   public Response proceed(Request var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "request");
      int var2 = this.index;
      int var3 = this.interceptors.size();
      boolean var4 = false;
      boolean var9;
      if (var2 < var3) {
         var9 = true;
      } else {
         var9 = false;
      }

      if (!var9) {
         throw (Throwable)(new IllegalStateException("Check failed.".toString()));
      } else {
         ++this.calls;
         Exchange var5 = this.exchange;
         if (var5 != null) {
            StringBuilder var8;
            if (!var5.getFinder$okhttp().sameHostAndPort(var1.url())) {
               var8 = new StringBuilder();
               var8.append("network interceptor ");
               var8.append((Interceptor)this.interceptors.get(this.index - 1));
               var8.append(" must retain the same host and port");
               throw (Throwable)(new IllegalStateException(var8.toString().toString()));
            }

            if (this.calls == 1) {
               var9 = true;
            } else {
               var9 = false;
            }

            if (!var9) {
               var8 = new StringBuilder();
               var8.append("network interceptor ");
               var8.append((Interceptor)this.interceptors.get(this.index - 1));
               var8.append(" must call proceed() exactly once");
               throw (Throwable)(new IllegalStateException(var8.toString().toString()));
            }
         }

         RealInterceptorChain var6 = copy$okhttp$default(this, this.index + 1, (Exchange)null, var1, 0, 0, 0, 58, (Object)null);
         Interceptor var7 = (Interceptor)this.interceptors.get(this.index);
         Response var10 = var7.intercept((Interceptor.Chain)var6);
         StringBuilder var11;
         if (var10 == null) {
            var11 = new StringBuilder();
            var11.append("interceptor ");
            var11.append(var7);
            var11.append(" returned null");
            throw (Throwable)(new NullPointerException(var11.toString()));
         } else {
            if (this.exchange != null) {
               if (this.index + 1 < this.interceptors.size() && var6.calls != 1) {
                  var9 = false;
               } else {
                  var9 = true;
               }

               if (!var9) {
                  var11 = new StringBuilder();
                  var11.append("network interceptor ");
                  var11.append(var7);
                  var11.append(" must call proceed() exactly once");
                  throw (Throwable)(new IllegalStateException(var11.toString().toString()));
               }
            }

            var9 = var4;
            if (var10.body() != null) {
               var9 = true;
            }

            if (var9) {
               return var10;
            } else {
               var11 = new StringBuilder();
               var11.append("interceptor ");
               var11.append(var7);
               var11.append(" returned a response with no body");
               throw (Throwable)(new IllegalStateException(var11.toString().toString()));
            }
         }
      }
   }

   public int readTimeoutMillis() {
      return this.readTimeoutMillis;
   }

   public Request request() {
      return this.request;
   }

   public Interceptor.Chain withConnectTimeout(int var1, TimeUnit var2) {
      Intrinsics.checkParameterIsNotNull(var2, "unit");
      boolean var3;
      if (this.exchange == null) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         return (Interceptor.Chain)copy$okhttp$default(this, 0, (Exchange)null, (Request)null, Util.checkDuration("connectTimeout", (long)var1, var2), 0, 0, 55, (Object)null);
      } else {
         throw (Throwable)(new IllegalStateException("Timeouts can't be adjusted in a network interceptor".toString()));
      }
   }

   public Interceptor.Chain withReadTimeout(int var1, TimeUnit var2) {
      Intrinsics.checkParameterIsNotNull(var2, "unit");
      boolean var3;
      if (this.exchange == null) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         return (Interceptor.Chain)copy$okhttp$default(this, 0, (Exchange)null, (Request)null, 0, Util.checkDuration("readTimeout", (long)var1, var2), 0, 47, (Object)null);
      } else {
         throw (Throwable)(new IllegalStateException("Timeouts can't be adjusted in a network interceptor".toString()));
      }
   }

   public Interceptor.Chain withWriteTimeout(int var1, TimeUnit var2) {
      Intrinsics.checkParameterIsNotNull(var2, "unit");
      boolean var3;
      if (this.exchange == null) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         return (Interceptor.Chain)copy$okhttp$default(this, 0, (Exchange)null, (Request)null, 0, 0, Util.checkDuration("writeTimeout", (long)var1, var2), 31, (Object)null);
      } else {
         throw (Throwable)(new IllegalStateException("Timeouts can't be adjusted in a network interceptor".toString()));
      }
   }

   public int writeTimeoutMillis() {
      return this.writeTimeoutMillis;
   }
}
