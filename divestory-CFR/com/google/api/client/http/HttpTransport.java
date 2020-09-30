/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.LowLevelHttpRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

public abstract class HttpTransport {
    static final Logger LOGGER = Logger.getLogger(HttpTransport.class.getName());
    private static final String[] SUPPORTED_METHODS;

    static {
        Object[] arrobject = new String[]{"DELETE", "GET", "POST", "PUT"};
        SUPPORTED_METHODS = arrobject;
        Arrays.sort(arrobject);
    }

    HttpRequest buildRequest() {
        return new HttpRequest(this, null);
    }

    protected abstract LowLevelHttpRequest buildRequest(String var1, String var2) throws IOException;

    public final HttpRequestFactory createRequestFactory() {
        return this.createRequestFactory(null);
    }

    public final HttpRequestFactory createRequestFactory(HttpRequestInitializer httpRequestInitializer) {
        return new HttpRequestFactory(this, httpRequestInitializer);
    }

    public void shutdown() throws IOException {
    }

    public boolean supportsMethod(String string2) throws IOException {
        if (Arrays.binarySearch(SUPPORTED_METHODS, string2) < 0) return false;
        return true;
    }
}

