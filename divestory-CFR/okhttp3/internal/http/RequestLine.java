/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.http;

import java.net.Proxy;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.HttpUrl;
import okhttp3.Request;

@Metadata(bv={1, 0, 3}, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u000e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r\u00a8\u0006\u000e"}, d2={"Lokhttp3/internal/http/RequestLine;", "", "()V", "get", "", "request", "Lokhttp3/Request;", "proxyType", "Ljava/net/Proxy$Type;", "includeAuthorityInRequestLine", "", "requestPath", "url", "Lokhttp3/HttpUrl;", "okhttp"}, k=1, mv={1, 1, 16})
public final class RequestLine {
    public static final RequestLine INSTANCE = new RequestLine();

    private RequestLine() {
    }

    private final boolean includeAuthorityInRequestLine(Request request, Proxy.Type type) {
        if (request.isHttps()) return false;
        if (type != Proxy.Type.HTTP) return false;
        return true;
    }

    public final String get(Request object, Proxy.Type type) {
        Intrinsics.checkParameterIsNotNull(object, "request");
        Intrinsics.checkParameterIsNotNull((Object)type, "proxyType");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((Request)object).method());
        stringBuilder.append(' ');
        if (INSTANCE.includeAuthorityInRequestLine((Request)object, type)) {
            stringBuilder.append(((Request)object).url());
        } else {
            stringBuilder.append(INSTANCE.requestPath(((Request)object).url()));
        }
        stringBuilder.append(" HTTP/1.1");
        object = stringBuilder.toString();
        Intrinsics.checkExpressionValueIsNotNull(object, "StringBuilder().apply(builderAction).toString()");
        return object;
    }

    public final String requestPath(HttpUrl object) {
        Intrinsics.checkParameterIsNotNull(object, "url");
        String string2 = ((HttpUrl)object).encodedPath();
        String string3 = ((HttpUrl)object).encodedQuery();
        object = string2;
        if (string3 == null) return object;
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append('?');
        ((StringBuilder)object).append(string3);
        return ((StringBuilder)object).toString();
    }
}

