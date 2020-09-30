/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.http;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u000e\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u000b"}, d2={"Lokhttp3/internal/http/HttpMethod;", "", "()V", "invalidatesCache", "", "method", "", "permitsRequestBody", "redirectsToGet", "redirectsWithBody", "requiresRequestBody", "okhttp"}, k=1, mv={1, 1, 16})
public final class HttpMethod {
    public static final HttpMethod INSTANCE = new HttpMethod();

    private HttpMethod() {
    }

    @JvmStatic
    public static final boolean permitsRequestBody(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "method");
        if (Intrinsics.areEqual(string2, "GET")) return false;
        if (Intrinsics.areEqual(string2, "HEAD")) return false;
        return true;
    }

    @JvmStatic
    public static final boolean requiresRequestBody(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "method");
        if (Intrinsics.areEqual(string2, "POST")) return true;
        if (Intrinsics.areEqual(string2, "PUT")) return true;
        if (Intrinsics.areEqual(string2, "PATCH")) return true;
        if (Intrinsics.areEqual(string2, "PROPPATCH")) return true;
        if (Intrinsics.areEqual(string2, "REPORT")) return true;
        return false;
    }

    public final boolean invalidatesCache(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "method");
        if (Intrinsics.areEqual(string2, "POST")) return true;
        if (Intrinsics.areEqual(string2, "PATCH")) return true;
        if (Intrinsics.areEqual(string2, "PUT")) return true;
        if (Intrinsics.areEqual(string2, "DELETE")) return true;
        if (Intrinsics.areEqual(string2, "MOVE")) return true;
        return false;
    }

    public final boolean redirectsToGet(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "method");
        return Intrinsics.areEqual(string2, "PROPFIND") ^ true;
    }

    public final boolean redirectsWithBody(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "method");
        return Intrinsics.areEqual(string2, "PROPFIND");
    }
}

