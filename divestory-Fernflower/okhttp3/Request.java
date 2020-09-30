package okhttp3;

import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ReplaceWith;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpMethod;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0001*BA\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u0016\u0010\n\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f\u0012\u0004\u0012\u00020\u00010\u000b¢\u0006\u0002\u0010\rJ\u000f\u0010\b\u001a\u0004\u0018\u00010\tH\u0007¢\u0006\u0002\b\u001bJ\r\u0010\u000f\u001a\u00020\u0010H\u0007¢\u0006\u0002\b\u001cJ\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001e\u001a\u00020\u0005J\r\u0010\u0006\u001a\u00020\u0007H\u0007¢\u0006\u0002\b\u001fJ\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050 2\u0006\u0010\u001e\u001a\u00020\u0005J\r\u0010\u0004\u001a\u00020\u0005H\u0007¢\u0006\u0002\b!J\u0006\u0010\"\u001a\u00020#J\b\u0010$\u001a\u0004\u0018\u00010\u0001J#\u0010$\u001a\u0004\u0018\u0001H%\"\u0004\b\u0000\u0010%2\u000e\u0010&\u001a\n\u0012\u0006\b\u0001\u0012\u0002H%0\f¢\u0006\u0002\u0010'J\b\u0010(\u001a\u00020\u0005H\u0016J\r\u0010\u0002\u001a\u00020\u0003H\u0007¢\u0006\u0002\b)R\u0015\u0010\b\u001a\u0004\u0018\u00010\t8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u00108G¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0011R\u0013\u0010\u0006\u001a\u00020\u00078\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u00148F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0015R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0013\u0010\u0004\u001a\u00020\u00058\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0017R$\u0010\n\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f\u0012\u0004\u0012\u00020\u00010\u000bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0013\u0010\u0002\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u001a¨\u0006+"},
   d2 = {"Lokhttp3/Request;", "", "url", "Lokhttp3/HttpUrl;", "method", "", "headers", "Lokhttp3/Headers;", "body", "Lokhttp3/RequestBody;", "tags", "", "Ljava/lang/Class;", "(Lokhttp3/HttpUrl;Ljava/lang/String;Lokhttp3/Headers;Lokhttp3/RequestBody;Ljava/util/Map;)V", "()Lokhttp3/RequestBody;", "cacheControl", "Lokhttp3/CacheControl;", "()Lokhttp3/CacheControl;", "()Lokhttp3/Headers;", "isHttps", "", "()Z", "lazyCacheControl", "()Ljava/lang/String;", "getTags$okhttp", "()Ljava/util/Map;", "()Lokhttp3/HttpUrl;", "-deprecated_body", "-deprecated_cacheControl", "header", "name", "-deprecated_headers", "", "-deprecated_method", "newBuilder", "Lokhttp3/Request$Builder;", "tag", "T", "type", "(Ljava/lang/Class;)Ljava/lang/Object;", "toString", "-deprecated_url", "Builder", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Request {
   private final RequestBody body;
   private final Headers headers;
   private CacheControl lazyCacheControl;
   private final String method;
   private final Map<Class<?>, Object> tags;
   private final HttpUrl url;

   public Request(HttpUrl var1, String var2, Headers var3, RequestBody var4, Map<Class<?>, ? extends Object> var5) {
      Intrinsics.checkParameterIsNotNull(var1, "url");
      Intrinsics.checkParameterIsNotNull(var2, "method");
      Intrinsics.checkParameterIsNotNull(var3, "headers");
      Intrinsics.checkParameterIsNotNull(var5, "tags");
      super();
      this.url = var1;
      this.method = var2;
      this.headers = var3;
      this.body = var4;
      this.tags = var5;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "body",
   imports = {}
)
   )
   public final RequestBody _deprecated_body/* $FF was: -deprecated_body*/() {
      return this.body;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "cacheControl",
   imports = {}
)
   )
   public final CacheControl _deprecated_cacheControl/* $FF was: -deprecated_cacheControl*/() {
      return this.cacheControl();
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "headers",
   imports = {}
)
   )
   public final Headers _deprecated_headers/* $FF was: -deprecated_headers*/() {
      return this.headers;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "method",
   imports = {}
)
   )
   public final String _deprecated_method/* $FF was: -deprecated_method*/() {
      return this.method;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "url",
   imports = {}
)
   )
   public final HttpUrl _deprecated_url/* $FF was: -deprecated_url*/() {
      return this.url;
   }

   public final RequestBody body() {
      return this.body;
   }

   public final CacheControl cacheControl() {
      CacheControl var1 = this.lazyCacheControl;
      CacheControl var2 = var1;
      if (var1 == null) {
         var2 = CacheControl.Companion.parse(this.headers);
         this.lazyCacheControl = var2;
      }

      return var2;
   }

   public final Map<Class<?>, Object> getTags$okhttp() {
      return this.tags;
   }

   public final String header(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      return this.headers.get(var1);
   }

   public final List<String> headers(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      return this.headers.values(var1);
   }

   public final Headers headers() {
      return this.headers;
   }

   public final boolean isHttps() {
      return this.url.isHttps();
   }

   public final String method() {
      return this.method;
   }

   public final Request.Builder newBuilder() {
      return new Request.Builder(this);
   }

   public final Object tag() {
      return this.tag(Object.class);
   }

   public final <T> T tag(Class<? extends T> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "type");
      return var1.cast(this.tags.get(var1));
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Request{method=");
      var1.append(this.method);
      var1.append(", url=");
      var1.append(this.url);
      if (this.headers.size() != 0) {
         var1.append(", headers=[");
         Iterable var2 = (Iterable)this.headers;
         int var3 = 0;

         for(Iterator var7 = var2.iterator(); var7.hasNext(); ++var3) {
            Object var4 = var7.next();
            if (var3 < 0) {
               CollectionsKt.throwIndexOverflow();
            }

            Pair var5 = (Pair)var4;
            String var8 = (String)var5.component1();
            String var9 = (String)var5.component2();
            if (var3 > 0) {
               var1.append(", ");
            }

            var1.append(var8);
            var1.append(':');
            var1.append(var9);
         }

         var1.append(']');
      }

      if (this.tags.isEmpty() ^ true) {
         var1.append(", tags=");
         var1.append(this.tags);
      }

      var1.append('}');
      String var6 = var1.toString();
      Intrinsics.checkExpressionValueIsNotNull(var6, "StringBuilder().apply(builderAction).toString()");
      return var6;
   }

   public final HttpUrl url() {
      return this.url;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0007\b\u0016¢\u0006\u0002\u0010\u0002B\u000f\b\u0010\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0018\u0010%\u001a\u00020\u00002\u0006\u0010&\u001a\u00020\u00132\u0006\u0010'\u001a\u00020\u0013H\u0016J\b\u0010(\u001a\u00020\u0004H\u0016J\u0010\u0010)\u001a\u00020\u00002\u0006\u0010)\u001a\u00020*H\u0016J\u0014\u0010+\u001a\u00020\u00002\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0017J\b\u0010,\u001a\u00020\u0000H\u0016J\b\u0010-\u001a\u00020\u0000H\u0016J\u0018\u0010.\u001a\u00020\u00002\u0006\u0010&\u001a\u00020\u00132\u0006\u0010'\u001a\u00020\u0013H\u0016J\u0010\u0010\f\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020/H\u0016J\u001a\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016J\u0010\u00100\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u00101\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u00102\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u00103\u001a\u00020\u00002\u0006\u0010&\u001a\u00020\u0013H\u0016J-\u00104\u001a\u00020\u0000\"\u0004\b\u0000\u001052\u000e\u00106\u001a\n\u0012\u0006\b\u0000\u0012\u0002H50\u001a2\b\u00104\u001a\u0004\u0018\u0001H5H\u0016¢\u0006\u0002\u00107J\u0012\u00104\u001a\u00020\u00002\b\u00104\u001a\u0004\u0018\u00010\u0001H\u0016J\u0010\u0010\u001f\u001a\u00020\u00002\u0006\u0010\u001f\u001a\u000208H\u0016J\u0010\u0010\u001f\u001a\u00020\u00002\u0006\u0010\u001f\u001a\u00020\u0013H\u0016J\u0010\u0010\u001f\u001a\u00020\u00002\u0006\u0010\u001f\u001a\u00020 H\u0016R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R*\u0010\u0018\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001a\u0012\u0004\u0012\u00020\u00010\u0019X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001c\u0010\u001f\u001a\u0004\u0018\u00010 X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$¨\u00069"},
      d2 = {"Lokhttp3/Request$Builder;", "", "()V", "request", "Lokhttp3/Request;", "(Lokhttp3/Request;)V", "body", "Lokhttp3/RequestBody;", "getBody$okhttp", "()Lokhttp3/RequestBody;", "setBody$okhttp", "(Lokhttp3/RequestBody;)V", "headers", "Lokhttp3/Headers$Builder;", "getHeaders$okhttp", "()Lokhttp3/Headers$Builder;", "setHeaders$okhttp", "(Lokhttp3/Headers$Builder;)V", "method", "", "getMethod$okhttp", "()Ljava/lang/String;", "setMethod$okhttp", "(Ljava/lang/String;)V", "tags", "", "Ljava/lang/Class;", "getTags$okhttp", "()Ljava/util/Map;", "setTags$okhttp", "(Ljava/util/Map;)V", "url", "Lokhttp3/HttpUrl;", "getUrl$okhttp", "()Lokhttp3/HttpUrl;", "setUrl$okhttp", "(Lokhttp3/HttpUrl;)V", "addHeader", "name", "value", "build", "cacheControl", "Lokhttp3/CacheControl;", "delete", "get", "head", "header", "Lokhttp3/Headers;", "patch", "post", "put", "removeHeader", "tag", "T", "type", "(Ljava/lang/Class;Ljava/lang/Object;)Lokhttp3/Request$Builder;", "Ljava/net/URL;", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static class Builder {
      private RequestBody body;
      private Headers.Builder headers;
      private String method;
      private Map<Class<?>, Object> tags;
      private HttpUrl url;

      public Builder() {
         this.tags = (Map)(new LinkedHashMap());
         this.method = "GET";
         this.headers = new Headers.Builder();
      }

      public Builder(Request var1) {
         Intrinsics.checkParameterIsNotNull(var1, "request");
         super();
         this.tags = (Map)(new LinkedHashMap());
         this.url = var1.url();
         this.method = var1.method();
         this.body = var1.body();
         Map var2;
         if (var1.getTags$okhttp().isEmpty()) {
            var2 = (Map)(new LinkedHashMap());
         } else {
            var2 = MapsKt.toMutableMap(var1.getTags$okhttp());
         }

         this.tags = var2;
         this.headers = var1.headers().newBuilder();
      }

      // $FF: synthetic method
      public static Request.Builder delete$default(Request.Builder var0, RequestBody var1, int var2, Object var3) {
         if (var3 == null) {
            if ((var2 & 1) != 0) {
               var1 = Util.EMPTY_REQUEST;
            }

            return var0.delete(var1);
         } else {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: delete");
         }
      }

      public Request.Builder addHeader(String var1, String var2) {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         Request.Builder var3 = (Request.Builder)this;
         var3.headers.add(var1, var2);
         return var3;
      }

      public Request build() {
         HttpUrl var1 = this.url;
         if (var1 != null) {
            return new Request(var1, this.method, this.headers.build(), this.body, Util.toImmutableMap(this.tags));
         } else {
            throw (Throwable)(new IllegalStateException("url == null".toString()));
         }
      }

      public Request.Builder cacheControl(CacheControl var1) {
         Intrinsics.checkParameterIsNotNull(var1, "cacheControl");
         String var3 = var1.toString();
         boolean var2;
         if (((CharSequence)var3).length() == 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         Request.Builder var4;
         if (var2) {
            var4 = this.removeHeader("Cache-Control");
         } else {
            var4 = this.header("Cache-Control", var3);
         }

         return var4;
      }

      public Request.Builder delete() {
         return delete$default(this, (RequestBody)null, 1, (Object)null);
      }

      public Request.Builder delete(RequestBody var1) {
         return this.method("DELETE", var1);
      }

      public Request.Builder get() {
         return this.method("GET", (RequestBody)null);
      }

      public final RequestBody getBody$okhttp() {
         return this.body;
      }

      public final Headers.Builder getHeaders$okhttp() {
         return this.headers;
      }

      public final String getMethod$okhttp() {
         return this.method;
      }

      public final Map<Class<?>, Object> getTags$okhttp() {
         return this.tags;
      }

      public final HttpUrl getUrl$okhttp() {
         return this.url;
      }

      public Request.Builder head() {
         return this.method("HEAD", (RequestBody)null);
      }

      public Request.Builder header(String var1, String var2) {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         Request.Builder var3 = (Request.Builder)this;
         var3.headers.set(var1, var2);
         return var3;
      }

      public Request.Builder headers(Headers var1) {
         Intrinsics.checkParameterIsNotNull(var1, "headers");
         Request.Builder var2 = (Request.Builder)this;
         var2.headers = var1.newBuilder();
         return var2;
      }

      public Request.Builder method(String var1, RequestBody var2) {
         Intrinsics.checkParameterIsNotNull(var1, "method");
         Request.Builder var3 = (Request.Builder)this;
         boolean var4;
         if (((CharSequence)var1).length() > 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            StringBuilder var5;
            if (var2 == null) {
               if (!(true ^ HttpMethod.requiresRequestBody(var1))) {
                  var5 = new StringBuilder();
                  var5.append("method ");
                  var5.append(var1);
                  var5.append(" must have a request body.");
                  throw (Throwable)(new IllegalArgumentException(var5.toString().toString()));
               }
            } else if (!HttpMethod.permitsRequestBody(var1)) {
               var5 = new StringBuilder();
               var5.append("method ");
               var5.append(var1);
               var5.append(" must not have a request body.");
               throw (Throwable)(new IllegalArgumentException(var5.toString().toString()));
            }

            var3.method = var1;
            var3.body = var2;
            return var3;
         } else {
            throw (Throwable)(new IllegalArgumentException("method.isEmpty() == true".toString()));
         }
      }

      public Request.Builder patch(RequestBody var1) {
         Intrinsics.checkParameterIsNotNull(var1, "body");
         return this.method("PATCH", var1);
      }

      public Request.Builder post(RequestBody var1) {
         Intrinsics.checkParameterIsNotNull(var1, "body");
         return this.method("POST", var1);
      }

      public Request.Builder put(RequestBody var1) {
         Intrinsics.checkParameterIsNotNull(var1, "body");
         return this.method("PUT", var1);
      }

      public Request.Builder removeHeader(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Request.Builder var2 = (Request.Builder)this;
         var2.headers.removeAll(var1);
         return var2;
      }

      public final void setBody$okhttp(RequestBody var1) {
         this.body = var1;
      }

      public final void setHeaders$okhttp(Headers.Builder var1) {
         Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
         this.headers = var1;
      }

      public final void setMethod$okhttp(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
         this.method = var1;
      }

      public final void setTags$okhttp(Map<Class<?>, Object> var1) {
         Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
         this.tags = var1;
      }

      public final void setUrl$okhttp(HttpUrl var1) {
         this.url = var1;
      }

      public <T> Request.Builder tag(Class<? super T> var1, T var2) {
         Intrinsics.checkParameterIsNotNull(var1, "type");
         Request.Builder var3 = (Request.Builder)this;
         if (var2 == null) {
            var3.tags.remove(var1);
         } else {
            if (var3.tags.isEmpty()) {
               var3.tags = (Map)(new LinkedHashMap());
            }

            Map var4 = var3.tags;
            var2 = var1.cast(var2);
            if (var2 == null) {
               Intrinsics.throwNpe();
            }

            var4.put(var1, var2);
         }

         return var3;
      }

      public Request.Builder tag(Object var1) {
         return this.tag(Object.class, var1);
      }

      public Request.Builder url(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "url");
         StringBuilder var2;
         String var3;
         if (StringsKt.startsWith(var1, "ws:", true)) {
            var2 = new StringBuilder();
            var2.append("http:");
            var1 = var1.substring(3);
            Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).substring(startIndex)");
            var2.append(var1);
            var3 = var2.toString();
         } else {
            var3 = var1;
            if (StringsKt.startsWith(var1, "wss:", true)) {
               var2 = new StringBuilder();
               var2.append("https:");
               var1 = var1.substring(4);
               Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).substring(startIndex)");
               var2.append(var1);
               var3 = var2.toString();
            }
         }

         return this.url(HttpUrl.Companion.get(var3));
      }

      public Request.Builder url(URL var1) {
         Intrinsics.checkParameterIsNotNull(var1, "url");
         HttpUrl.Companion var2 = HttpUrl.Companion;
         String var3 = var1.toString();
         Intrinsics.checkExpressionValueIsNotNull(var3, "url.toString()");
         return this.url(var2.get(var3));
      }

      public Request.Builder url(HttpUrl var1) {
         Intrinsics.checkParameterIsNotNull(var1, "url");
         Request.Builder var2 = (Request.Builder)this;
         var2.url = var1;
         return var2;
      }
   }
}
