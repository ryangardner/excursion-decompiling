/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.http.HttpResponse;
import java.io.IOException;

public interface HttpResponseInterceptor {
    public void interceptResponse(HttpResponse var1) throws IOException;
}

