package okhttp3.internal;

import javax.net.ssl.SSLSocket;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Cache;
import okhttp3.ConnectionSpec;
import okhttp3.Cookie;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000T\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004\u001a\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e\u001a\u0018\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014\u001a\u0016\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000e\u001a \u0010\u0019\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0004¨\u0006\u001f"},
   d2 = {"addHeaderLenient", "Lokhttp3/Headers$Builder;", "builder", "line", "", "name", "value", "applyConnectionSpec", "", "connectionSpec", "Lokhttp3/ConnectionSpec;", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "isFallback", "", "cacheGet", "Lokhttp3/Response;", "cache", "Lokhttp3/Cache;", "request", "Lokhttp3/Request;", "cookieToString", "cookie", "Lokhttp3/Cookie;", "forObsoleteRfc2965", "parseCookie", "currentTimeMillis", "", "url", "Lokhttp3/HttpUrl;", "setCookie", "okhttp"},
   k = 2,
   mv = {1, 1, 16}
)
public final class Internal {
   public static final Headers.Builder addHeaderLenient(Headers.Builder var0, String var1) {
      Intrinsics.checkParameterIsNotNull(var0, "builder");
      Intrinsics.checkParameterIsNotNull(var1, "line");
      return var0.addLenient$okhttp(var1);
   }

   public static final Headers.Builder addHeaderLenient(Headers.Builder var0, String var1, String var2) {
      Intrinsics.checkParameterIsNotNull(var0, "builder");
      Intrinsics.checkParameterIsNotNull(var1, "name");
      Intrinsics.checkParameterIsNotNull(var2, "value");
      return var0.addLenient$okhttp(var1, var2);
   }

   public static final void applyConnectionSpec(ConnectionSpec var0, SSLSocket var1, boolean var2) {
      Intrinsics.checkParameterIsNotNull(var0, "connectionSpec");
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      var0.apply$okhttp(var1, var2);
   }

   public static final Response cacheGet(Cache var0, Request var1) {
      Intrinsics.checkParameterIsNotNull(var0, "cache");
      Intrinsics.checkParameterIsNotNull(var1, "request");
      return var0.get$okhttp(var1);
   }

   public static final String cookieToString(Cookie var0, boolean var1) {
      Intrinsics.checkParameterIsNotNull(var0, "cookie");
      return var0.toString$okhttp(var1);
   }

   public static final Cookie parseCookie(long var0, HttpUrl var2, String var3) {
      Intrinsics.checkParameterIsNotNull(var2, "url");
      Intrinsics.checkParameterIsNotNull(var3, "setCookie");
      return Cookie.Companion.parse$okhttp(var0, var2, var3);
   }
}
