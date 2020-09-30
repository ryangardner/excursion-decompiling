package okhttp3.internal.http;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okio.GzipSource;
import okio.Okio;
import okio.Source;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"},
   d2 = {"Lokhttp3/internal/http/BridgeInterceptor;", "Lokhttp3/Interceptor;", "cookieJar", "Lokhttp3/CookieJar;", "(Lokhttp3/CookieJar;)V", "cookieHeader", "", "cookies", "", "Lokhttp3/Cookie;", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class BridgeInterceptor implements Interceptor {
   private final CookieJar cookieJar;

   public BridgeInterceptor(CookieJar var1) {
      Intrinsics.checkParameterIsNotNull(var1, "cookieJar");
      super();
      this.cookieJar = var1;
   }

   private final String cookieHeader(List<Cookie> var1) {
      StringBuilder var2 = new StringBuilder();
      Iterator var5 = ((Iterable)var1).iterator();

      for(int var3 = 0; var5.hasNext(); ++var3) {
         Object var4 = var5.next();
         if (var3 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         Cookie var7 = (Cookie)var4;
         if (var3 > 0) {
            var2.append("; ");
         }

         var2.append(var7.name());
         var2.append('=');
         var2.append(var7.value());
      }

      String var6 = var2.toString();
      Intrinsics.checkExpressionValueIsNotNull(var6, "StringBuilder().apply(builderAction).toString()");
      return var6;
   }

   public Response intercept(Interceptor.Chain var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "chain");
      Request var2 = var1.request();
      Request.Builder var3 = var2.newBuilder();
      RequestBody var4 = var2.body();
      if (var4 != null) {
         MediaType var5 = var4.contentType();
         if (var5 != null) {
            var3.header("Content-Type", var5.toString());
         }

         long var6 = var4.contentLength();
         if (var6 != -1L) {
            var3.header("Content-Length", String.valueOf(var6));
            var3.removeHeader("Transfer-Encoding");
         } else {
            var3.header("Transfer-Encoding", "chunked");
            var3.removeHeader("Content-Length");
         }
      }

      String var14 = var2.header("Host");
      boolean var8 = false;
      if (var14 == null) {
         var3.header("Host", Util.toHostHeader$default(var2.url(), false, 1, (Object)null));
      }

      if (var2.header("Connection") == null) {
         var3.header("Connection", "Keep-Alive");
      }

      boolean var9 = var8;
      if (var2.header("Accept-Encoding") == null) {
         var9 = var8;
         if (var2.header("Range") == null) {
            var3.header("Accept-Encoding", "gzip");
            var9 = true;
         }
      }

      List var15 = this.cookieJar.loadForRequest(var2.url());
      if (((Collection)var15).isEmpty() ^ true) {
         var3.header("Cookie", this.cookieHeader(var15));
      }

      if (var2.header("User-Agent") == null) {
         var3.header("User-Agent", "okhttp/4.8.1");
      }

      Response var10 = var1.proceed(var3.build());
      HttpHeaders.receiveHeaders(this.cookieJar, var2.url(), var10.headers());
      Response.Builder var11 = var10.newBuilder().request(var2);
      if (var9 && StringsKt.equals("gzip", Response.header$default(var10, "Content-Encoding", (String)null, 2, (Object)null), true) && HttpHeaders.promisesBody(var10)) {
         ResponseBody var12 = var10.body();
         if (var12 != null) {
            GzipSource var13 = new GzipSource((Source)var12.source());
            var11.headers(var10.headers().newBuilder().removeAll("Content-Encoding").removeAll("Content-Length").build());
            var11.body((ResponseBody)(new RealResponseBody(Response.header$default(var10, "Content-Type", (String)null, 2, (Object)null), -1L, Okio.buffer((Source)var13))));
         }
      }

      return var11.build();
   }
}
