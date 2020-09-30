/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.client;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.UnknownHostException;
import javax.net.ssl.SSLException;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;

public class DefaultHttpRequestRetryHandler
implements HttpRequestRetryHandler {
    private final boolean requestSentRetryEnabled;
    private final int retryCount;

    public DefaultHttpRequestRetryHandler() {
        this(3, false);
    }

    public DefaultHttpRequestRetryHandler(int n, boolean bl) {
        this.retryCount = n;
        this.requestSentRetryEnabled = bl;
    }

    private boolean handleAsIdempotent(HttpRequest httpRequest) {
        return httpRequest instanceof HttpEntityEnclosingRequest ^ true;
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    public boolean isRequestSentRetryEnabled() {
        return this.requestSentRetryEnabled;
    }

    @Override
    public boolean retryRequest(IOException serializable, int n, HttpContext httpContext) {
        if (serializable == null) throw new IllegalArgumentException("Exception parameter may not be null");
        if (httpContext == null) throw new IllegalArgumentException("HTTP context may not be null");
        if (n > this.retryCount) {
            return false;
        }
        if (serializable instanceof InterruptedIOException) {
            return false;
        }
        if (serializable instanceof UnknownHostException) {
            return false;
        }
        if (serializable instanceof ConnectException) {
            return false;
        }
        if (serializable instanceof SSLException) {
            return false;
        }
        if (this.handleAsIdempotent((HttpRequest)httpContext.getAttribute("http.request"))) {
            return true;
        }
        serializable = (Boolean)httpContext.getAttribute("http.request_sent");
        n = serializable != null && ((Boolean)serializable).booleanValue() ? 1 : 0;
        if (n == 0) return true;
        if (!this.requestSentRetryEnabled) return false;
        return true;
    }
}

