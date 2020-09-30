/*
 * Decompiled with CFR <Could not determine version>.
 */
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
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpMethod;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0001*BA\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u0016\u0010\n\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f\u0012\u0004\u0012\u00020\u00010\u000b\u00a2\u0006\u0002\u0010\rJ\u000f\u0010\b\u001a\u0004\u0018\u00010\tH\u0007\u00a2\u0006\u0002\b\u001bJ\r\u0010\u000f\u001a\u00020\u0010H\u0007\u00a2\u0006\u0002\b\u001cJ\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001e\u001a\u00020\u0005J\r\u0010\u0006\u001a\u00020\u0007H\u0007\u00a2\u0006\u0002\b\u001fJ\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050 2\u0006\u0010\u001e\u001a\u00020\u0005J\r\u0010\u0004\u001a\u00020\u0005H\u0007\u00a2\u0006\u0002\b!J\u0006\u0010\"\u001a\u00020#J\b\u0010$\u001a\u0004\u0018\u00010\u0001J#\u0010$\u001a\u0004\u0018\u0001H%\"\u0004\b\u0000\u0010%2\u000e\u0010&\u001a\n\u0012\u0006\b\u0001\u0012\u0002H%0\f\u00a2\u0006\u0002\u0010'J\b\u0010(\u001a\u00020\u0005H\u0016J\r\u0010\u0002\u001a\u00020\u0003H\u0007\u00a2\u0006\u0002\b)R\u0015\u0010\b\u001a\u0004\u0018\u00010\t8\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u00108G\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0011R\u0013\u0010\u0006\u001a\u00020\u00078\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u00148F\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0015R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0013\u0010\u0004\u001a\u00020\u00058\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0017R$\u0010\n\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f\u0012\u0004\u0012\u00020\u00010\u000bX\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0013\u0010\u0002\u001a\u00020\u00038\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u001a\u00a8\u0006+"}, d2={"Lokhttp3/Request;", "", "url", "Lokhttp3/HttpUrl;", "method", "", "headers", "Lokhttp3/Headers;", "body", "Lokhttp3/RequestBody;", "tags", "", "Ljava/lang/Class;", "(Lokhttp3/HttpUrl;Ljava/lang/String;Lokhttp3/Headers;Lokhttp3/RequestBody;Ljava/util/Map;)V", "()Lokhttp3/RequestBody;", "cacheControl", "Lokhttp3/CacheControl;", "()Lokhttp3/CacheControl;", "()Lokhttp3/Headers;", "isHttps", "", "()Z", "lazyCacheControl", "()Ljava/lang/String;", "getTags$okhttp", "()Ljava/util/Map;", "()Lokhttp3/HttpUrl;", "-deprecated_body", "-deprecated_cacheControl", "header", "name", "-deprecated_headers", "", "-deprecated_method", "newBuilder", "Lokhttp3/Request$Builder;", "tag", "T", "type", "(Ljava/lang/Class;)Ljava/lang/Object;", "toString", "-deprecated_url", "Builder", "okhttp"}, k=1, mv={1, 1, 16})
public final class Request {
    private final RequestBody body;
    private final Headers headers;
    private CacheControl lazyCacheControl;
    private final String method;
    private final Map<Class<?>, Object> tags;
    private final HttpUrl url;

    public Request(HttpUrl httpUrl, String string2, Headers headers, RequestBody requestBody, Map<Class<?>, ? extends Object> map) {
        Intrinsics.checkParameterIsNotNull(httpUrl, "url");
        Intrinsics.checkParameterIsNotNull(string2, "method");
        Intrinsics.checkParameterIsNotNull(headers, "headers");
        Intrinsics.checkParameterIsNotNull(map, "tags");
        this.url = httpUrl;
        this.method = string2;
        this.headers = headers;
        this.body = requestBody;
        this.tags = map;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="body", imports={}))
    public final RequestBody -deprecated_body() {
        return this.body;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="cacheControl", imports={}))
    public final CacheControl -deprecated_cacheControl() {
        return this.cacheControl();
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="headers", imports={}))
    public final Headers -deprecated_headers() {
        return this.headers;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="method", imports={}))
    public final String -deprecated_method() {
        return this.method;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="url", imports={}))
    public final HttpUrl -deprecated_url() {
        return this.url;
    }

    public final RequestBody body() {
        return this.body;
    }

    public final CacheControl cacheControl() {
        CacheControl cacheControl;
        CacheControl cacheControl2 = cacheControl = this.lazyCacheControl;
        if (cacheControl != null) return cacheControl2;
        this.lazyCacheControl = cacheControl2 = CacheControl.Companion.parse(this.headers);
        return cacheControl2;
    }

    public final Map<Class<?>, Object> getTags$okhttp() {
        return this.tags;
    }

    public final String header(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "name");
        return this.headers.get(string2);
    }

    public final List<String> headers(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "name");
        return this.headers.values(string2);
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

    public final Builder newBuilder() {
        return new Builder(this);
    }

    public final Object tag() {
        return this.tag(Object.class);
    }

    public final <T> T tag(Class<? extends T> class_) {
        Intrinsics.checkParameterIsNotNull(class_, "type");
        return class_.cast(this.tags.get(class_));
    }

    public String toString() {
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Request{method=");
        ((StringBuilder)charSequence).append(this.method);
        ((StringBuilder)charSequence).append(", url=");
        ((StringBuilder)charSequence).append(this.url);
        if (this.headers.size() != 0) {
            ((StringBuilder)charSequence).append(", headers=[");
            Object object = this.headers;
            int n = 0;
            object = object.iterator();
            while (object.hasNext()) {
                Object object2 = object.next();
                if (n < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                Object object3 = (Pair)object2;
                object2 = (String)((Pair)object3).component1();
                object3 = (String)((Pair)object3).component2();
                if (n > 0) {
                    ((StringBuilder)charSequence).append(", ");
                }
                ((StringBuilder)charSequence).append((String)object2);
                ((StringBuilder)charSequence).append(':');
                ((StringBuilder)charSequence).append((String)object3);
                ++n;
            }
            ((StringBuilder)charSequence).append(']');
        }
        if (this.tags.isEmpty() ^ true) {
            ((StringBuilder)charSequence).append(", tags=");
            ((StringBuilder)charSequence).append(this.tags);
        }
        ((StringBuilder)charSequence).append('}');
        charSequence = ((StringBuilder)charSequence).toString();
        Intrinsics.checkExpressionValueIsNotNull(charSequence, "StringBuilder().apply(builderAction).toString()");
        return charSequence;
    }

    public final HttpUrl url() {
        return this.url;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0007\b\u0016\u00a2\u0006\u0002\u0010\u0002B\u000f\b\u0010\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u0018\u0010%\u001a\u00020\u00002\u0006\u0010&\u001a\u00020\u00132\u0006\u0010'\u001a\u00020\u0013H\u0016J\b\u0010(\u001a\u00020\u0004H\u0016J\u0010\u0010)\u001a\u00020\u00002\u0006\u0010)\u001a\u00020*H\u0016J\u0014\u0010+\u001a\u00020\u00002\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0017J\b\u0010,\u001a\u00020\u0000H\u0016J\b\u0010-\u001a\u00020\u0000H\u0016J\u0018\u0010.\u001a\u00020\u00002\u0006\u0010&\u001a\u00020\u00132\u0006\u0010'\u001a\u00020\u0013H\u0016J\u0010\u0010\f\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020/H\u0016J\u001a\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016J\u0010\u00100\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u00101\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u00102\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u00103\u001a\u00020\u00002\u0006\u0010&\u001a\u00020\u0013H\u0016J-\u00104\u001a\u00020\u0000\"\u0004\b\u0000\u001052\u000e\u00106\u001a\n\u0012\u0006\b\u0000\u0012\u0002H50\u001a2\b\u00104\u001a\u0004\u0018\u0001H5H\u0016\u00a2\u0006\u0002\u00107J\u0012\u00104\u001a\u00020\u00002\b\u00104\u001a\u0004\u0018\u00010\u0001H\u0016J\u0010\u0010\u001f\u001a\u00020\u00002\u0006\u0010\u001f\u001a\u000208H\u0016J\u0010\u0010\u001f\u001a\u00020\u00002\u0006\u0010\u001f\u001a\u00020\u0013H\u0016J\u0010\u0010\u001f\u001a\u00020\u00002\u0006\u0010\u001f\u001a\u00020 H\u0016R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R*\u0010\u0018\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001a\u0012\u0004\u0012\u00020\u00010\u0019X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001c\u0010\u001f\u001a\u0004\u0018\u00010 X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$\u00a8\u00069"}, d2={"Lokhttp3/Request$Builder;", "", "()V", "request", "Lokhttp3/Request;", "(Lokhttp3/Request;)V", "body", "Lokhttp3/RequestBody;", "getBody$okhttp", "()Lokhttp3/RequestBody;", "setBody$okhttp", "(Lokhttp3/RequestBody;)V", "headers", "Lokhttp3/Headers$Builder;", "getHeaders$okhttp", "()Lokhttp3/Headers$Builder;", "setHeaders$okhttp", "(Lokhttp3/Headers$Builder;)V", "method", "", "getMethod$okhttp", "()Ljava/lang/String;", "setMethod$okhttp", "(Ljava/lang/String;)V", "tags", "", "Ljava/lang/Class;", "getTags$okhttp", "()Ljava/util/Map;", "setTags$okhttp", "(Ljava/util/Map;)V", "url", "Lokhttp3/HttpUrl;", "getUrl$okhttp", "()Lokhttp3/HttpUrl;", "setUrl$okhttp", "(Lokhttp3/HttpUrl;)V", "addHeader", "name", "value", "build", "cacheControl", "Lokhttp3/CacheControl;", "delete", "get", "head", "header", "Lokhttp3/Headers;", "patch", "post", "put", "removeHeader", "tag", "T", "type", "(Ljava/lang/Class;Ljava/lang/Object;)Lokhttp3/Request$Builder;", "Ljava/net/URL;", "okhttp"}, k=1, mv={1, 1, 16})
    public static class Builder {
        private RequestBody body;
        private Headers.Builder headers;
        private String method;
        private Map<Class<?>, Object> tags;
        private HttpUrl url;

        public Builder() {
            this.tags = new LinkedHashMap();
            this.method = "GET";
            this.headers = new Headers.Builder();
        }

        public Builder(Request request) {
            Intrinsics.checkParameterIsNotNull(request, "request");
            this.tags = new LinkedHashMap();
            this.url = request.url();
            this.method = request.method();
            this.body = request.body();
            Map map = request.getTags$okhttp().isEmpty() ? (Map)new LinkedHashMap() : MapsKt.toMutableMap(request.getTags$okhttp());
            this.tags = map;
            this.headers = request.headers().newBuilder();
        }

        public static /* synthetic */ Builder delete$default(Builder builder, RequestBody requestBody, int n, Object object) {
            if (object != null) throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: delete");
            if ((n & 1) == 0) return builder.delete(requestBody);
            requestBody = Util.EMPTY_REQUEST;
            return builder.delete(requestBody);
        }

        public Builder addHeader(String string2, String string3) {
            Intrinsics.checkParameterIsNotNull(string2, "name");
            Intrinsics.checkParameterIsNotNull(string3, "value");
            Builder builder = this;
            builder.headers.add(string2, string3);
            return builder;
        }

        public Request build() {
            HttpUrl httpUrl = this.url;
            if (httpUrl == null) throw (Throwable)new IllegalStateException("url == null".toString());
            return new Request(httpUrl, this.method, this.headers.build(), this.body, Util.toImmutableMap(this.tags));
        }

        public Builder cacheControl(CacheControl object) {
            Intrinsics.checkParameterIsNotNull(object, "cacheControl");
            object = ((CacheControl)object).toString();
            boolean bl = ((CharSequence)object).length() == 0;
            if (!bl) return this.header("Cache-Control", (String)object);
            return this.removeHeader("Cache-Control");
        }

        public Builder delete() {
            return Builder.delete$default(this, null, 1, null);
        }

        public Builder delete(RequestBody requestBody) {
            return this.method("DELETE", requestBody);
        }

        public Builder get() {
            return this.method("GET", null);
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

        public Builder head() {
            return this.method("HEAD", null);
        }

        public Builder header(String string2, String string3) {
            Intrinsics.checkParameterIsNotNull(string2, "name");
            Intrinsics.checkParameterIsNotNull(string3, "value");
            Builder builder = this;
            builder.headers.set(string2, string3);
            return builder;
        }

        public Builder headers(Headers headers) {
            Intrinsics.checkParameterIsNotNull(headers, "headers");
            Builder builder = this;
            builder.headers = headers.newBuilder();
            return builder;
        }

        public Builder method(String string2, RequestBody object) {
            Intrinsics.checkParameterIsNotNull(string2, "method");
            Builder builder = this;
            boolean bl = ((CharSequence)string2).length() > 0;
            if (!bl) throw (Throwable)new IllegalArgumentException("method.isEmpty() == true".toString());
            if (object == null) {
                if (!(true ^ HttpMethod.requiresRequestBody(string2))) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("method ");
                    ((StringBuilder)object).append(string2);
                    ((StringBuilder)object).append(" must have a request body.");
                    throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
                }
            } else if (!HttpMethod.permitsRequestBody(string2)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("method ");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" must not have a request body.");
                throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
            }
            builder.method = string2;
            builder.body = object;
            return builder;
        }

        public Builder patch(RequestBody requestBody) {
            Intrinsics.checkParameterIsNotNull(requestBody, "body");
            return this.method("PATCH", requestBody);
        }

        public Builder post(RequestBody requestBody) {
            Intrinsics.checkParameterIsNotNull(requestBody, "body");
            return this.method("POST", requestBody);
        }

        public Builder put(RequestBody requestBody) {
            Intrinsics.checkParameterIsNotNull(requestBody, "body");
            return this.method("PUT", requestBody);
        }

        public Builder removeHeader(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "name");
            Builder builder = this;
            builder.headers.removeAll(string2);
            return builder;
        }

        public final void setBody$okhttp(RequestBody requestBody) {
            this.body = requestBody;
        }

        public final void setHeaders$okhttp(Headers.Builder builder) {
            Intrinsics.checkParameterIsNotNull(builder, "<set-?>");
            this.headers = builder;
        }

        public final void setMethod$okhttp(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "<set-?>");
            this.method = string2;
        }

        public final void setTags$okhttp(Map<Class<?>, Object> map) {
            Intrinsics.checkParameterIsNotNull(map, "<set-?>");
            this.tags = map;
        }

        public final void setUrl$okhttp(HttpUrl httpUrl) {
            this.url = httpUrl;
        }

        public <T> Builder tag(Class<? super T> class_, T t) {
            Intrinsics.checkParameterIsNotNull(class_, "type");
            Builder builder = this;
            if (t == null) {
                builder.tags.remove(class_);
                return builder;
            }
            if (builder.tags.isEmpty()) {
                builder.tags = new LinkedHashMap();
            }
            Map<Class<?>, Object> map = builder.tags;
            if ((t = class_.cast(t)) == null) {
                Intrinsics.throwNpe();
            }
            map.put(class_, t);
            return builder;
        }

        public Builder tag(Object object) {
            return this.tag(Object.class, object);
        }

        public Builder url(String string2) {
            CharSequence charSequence;
            Intrinsics.checkParameterIsNotNull(string2, "url");
            if (StringsKt.startsWith(string2, "ws:", true)) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("http:");
                string2 = string2.substring(3);
                Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).substring(startIndex)");
                ((StringBuilder)charSequence).append(string2);
                charSequence = ((StringBuilder)charSequence).toString();
                return this.url(HttpUrl.Companion.get((String)charSequence));
            }
            charSequence = string2;
            if (!StringsKt.startsWith(string2, "wss:", true)) return this.url(HttpUrl.Companion.get((String)charSequence));
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("https:");
            string2 = string2.substring(4);
            Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).substring(startIndex)");
            ((StringBuilder)charSequence).append(string2);
            charSequence = ((StringBuilder)charSequence).toString();
            return this.url(HttpUrl.Companion.get((String)charSequence));
        }

        public Builder url(URL object) {
            Intrinsics.checkParameterIsNotNull(object, "url");
            HttpUrl.Companion companion = HttpUrl.Companion;
            object = ((URL)object).toString();
            Intrinsics.checkExpressionValueIsNotNull(object, "url.toString()");
            return this.url(companion.get((String)object));
        }

        public Builder url(HttpUrl httpUrl) {
            Intrinsics.checkParameterIsNotNull(httpUrl, "url");
            Builder builder = this;
            builder.url = httpUrl;
            return builder;
        }
    }

}

