package okhttp3.internal.http;

import java.net.Proxy.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.HttpUrl;
import okhttp3.Request;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u000e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r¨\u0006\u000e"},
   d2 = {"Lokhttp3/internal/http/RequestLine;", "", "()V", "get", "", "request", "Lokhttp3/Request;", "proxyType", "Ljava/net/Proxy$Type;", "includeAuthorityInRequestLine", "", "requestPath", "url", "Lokhttp3/HttpUrl;", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class RequestLine {
   public static final RequestLine INSTANCE = new RequestLine();

   private RequestLine() {
   }

   private final boolean includeAuthorityInRequestLine(Request var1, Type var2) {
      boolean var3;
      if (!var1.isHttps() && var2 == Type.HTTP) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public final String get(Request var1, Type var2) {
      Intrinsics.checkParameterIsNotNull(var1, "request");
      Intrinsics.checkParameterIsNotNull(var2, "proxyType");
      StringBuilder var3 = new StringBuilder();
      var3.append(var1.method());
      var3.append(' ');
      if (INSTANCE.includeAuthorityInRequestLine(var1, var2)) {
         var3.append(var1.url());
      } else {
         var3.append(INSTANCE.requestPath(var1.url()));
      }

      var3.append(" HTTP/1.1");
      String var4 = var3.toString();
      Intrinsics.checkExpressionValueIsNotNull(var4, "StringBuilder().apply(builderAction).toString()");
      return var4;
   }

   public final String requestPath(HttpUrl var1) {
      Intrinsics.checkParameterIsNotNull(var1, "url");
      String var2 = var1.encodedPath();
      String var3 = var1.encodedQuery();
      String var4 = var2;
      if (var3 != null) {
         StringBuilder var5 = new StringBuilder();
         var5.append(var2);
         var5.append('?');
         var5.append(var3);
         var4 = var5.toString();
      }

      return var4;
   }
}
