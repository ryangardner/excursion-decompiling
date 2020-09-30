package okhttp3.internal.http;

import java.io.IOException;
import java.net.ProtocolException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.internal.connection.Exchange;
import okio.BufferedSink;
import okio.Okio;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"},
   d2 = {"Lokhttp3/internal/http/CallServerInterceptor;", "Lokhttp3/Interceptor;", "forWebSocket", "", "(Z)V", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class CallServerInterceptor implements Interceptor {
   private final boolean forWebSocket;

   public CallServerInterceptor(boolean var1) {
      this.forWebSocket = var1;
   }

   public Response intercept(Interceptor.Chain var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "chain");
      RealInterceptorChain var12 = (RealInterceptorChain)var1;
      Exchange var2 = var12.getExchange$okhttp();
      if (var2 == null) {
         Intrinsics.throwNpe();
      }

      Request var3 = var12.getRequest$okhttp();
      RequestBody var4 = var3.body();
      long var5 = System.currentTimeMillis();
      var2.writeRequestHeaders(var3);
      Object var7 = null;
      Response.Builder var13 = (Response.Builder)null;
      Response.Builder var8;
      boolean var9;
      boolean var10;
      if (HttpMethod.permitsRequestBody(var3.method()) && var4 != null) {
         if (StringsKt.equals("100-continue", var3.header("Expect"), true)) {
            var2.flushRequest();
            var8 = var2.readResponseHeaders(true);
            var2.responseHeadersStart();
            var9 = false;
         } else {
            var9 = true;
            var8 = var13;
         }

         if (var8 == null) {
            if (var4.isDuplex()) {
               var2.flushRequest();
               var4.writeTo(Okio.buffer(var2.createRequestBody(var3, true)));
               var13 = var8;
               var10 = var9;
            } else {
               BufferedSink var14 = Okio.buffer(var2.createRequestBody(var3, false));
               var4.writeTo(var14);
               var14.close();
               var13 = var8;
               var10 = var9;
            }
         } else {
            var2.noRequestBody();
            var13 = var8;
            var10 = var9;
            if (!var2.getConnection$okhttp().isMultiplexed$okhttp()) {
               var2.noNewExchangesOnConnection();
               var13 = var8;
               var10 = var9;
            }
         }
      } else {
         var2.noRequestBody();
         var10 = true;
      }

      if (var4 == null || !var4.isDuplex()) {
         var2.finishRequest();
      }

      var8 = var13;
      var9 = var10;
      if (var13 == null) {
         var13 = var2.readResponseHeaders(false);
         if (var13 == null) {
            Intrinsics.throwNpe();
         }

         var8 = var13;
         var9 = var10;
         if (var10) {
            var2.responseHeadersStart();
            var9 = false;
            var8 = var13;
         }
      }

      Response var16 = var8.request(var3).handshake(var2.getConnection$okhttp().handshake()).sentRequestAtMillis(var5).receivedResponseAtMillis(System.currentTimeMillis()).build();
      int var11 = var16.code();
      int var20 = var11;
      if (var11 == 100) {
         var13 = var2.readResponseHeaders(false);
         if (var13 == null) {
            Intrinsics.throwNpe();
         }

         if (var9) {
            var2.responseHeadersStart();
         }

         var16 = var13.request(var3).handshake(var2.getConnection$okhttp().handshake()).sentRequestAtMillis(var5).receivedResponseAtMillis(System.currentTimeMillis()).build();
         var20 = var16.code();
      }

      var2.responseHeadersEnd(var16);
      if (this.forWebSocket && var20 == 101) {
         var16 = var16.newBuilder().body(Util.EMPTY_RESPONSE).build();
      } else {
         var16 = var16.newBuilder().body(var2.openResponseBody(var16)).build();
      }

      if (StringsKt.equals("close", var16.request().header("Connection"), true) || StringsKt.equals("close", Response.header$default(var16, "Connection", (String)null, 2, (Object)null), true)) {
         var2.noNewExchangesOnConnection();
      }

      if (var20 == 204 || var20 == 205) {
         ResponseBody var18 = var16.body();
         if (var18 != null) {
            var5 = var18.contentLength();
         } else {
            var5 = -1L;
         }

         if (var5 > 0L) {
            StringBuilder var19 = new StringBuilder();
            var19.append("HTTP ");
            var19.append(var20);
            var19.append(" had non-zero Content-Length: ");
            ResponseBody var15 = var16.body();
            Long var17 = (Long)var7;
            if (var15 != null) {
               var17 = var15.contentLength();
            }

            var19.append(var17);
            throw (Throwable)(new ProtocolException(var19.toString()));
         }
      }

      return var16;
   }
}
