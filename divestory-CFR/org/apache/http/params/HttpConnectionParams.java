/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.params;

import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;

public final class HttpConnectionParams
implements CoreConnectionPNames {
    private HttpConnectionParams() {
    }

    public static int getConnectionTimeout(HttpParams httpParams) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        return httpParams.getIntParameter("http.connection.timeout", 0);
    }

    public static int getLinger(HttpParams httpParams) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        return httpParams.getIntParameter("http.socket.linger", -1);
    }

    public static boolean getSoReuseaddr(HttpParams httpParams) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        return httpParams.getBooleanParameter("http.socket.reuseaddr", false);
    }

    public static int getSoTimeout(HttpParams httpParams) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        return httpParams.getIntParameter("http.socket.timeout", 0);
    }

    public static int getSocketBufferSize(HttpParams httpParams) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        return httpParams.getIntParameter("http.socket.buffer-size", -1);
    }

    public static boolean getTcpNoDelay(HttpParams httpParams) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        return httpParams.getBooleanParameter("http.tcp.nodelay", true);
    }

    public static boolean isStaleCheckingEnabled(HttpParams httpParams) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        return httpParams.getBooleanParameter("http.connection.stalecheck", true);
    }

    public static void setConnectionTimeout(HttpParams httpParams, int n) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        httpParams.setIntParameter("http.connection.timeout", n);
    }

    public static void setLinger(HttpParams httpParams, int n) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        httpParams.setIntParameter("http.socket.linger", n);
    }

    public static void setSoReuseaddr(HttpParams httpParams, boolean bl) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        httpParams.setBooleanParameter("http.socket.reuseaddr", bl);
    }

    public static void setSoTimeout(HttpParams httpParams, int n) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        httpParams.setIntParameter("http.socket.timeout", n);
    }

    public static void setSocketBufferSize(HttpParams httpParams, int n) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        httpParams.setIntParameter("http.socket.buffer-size", n);
    }

    public static void setStaleCheckingEnabled(HttpParams httpParams, boolean bl) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        httpParams.setBooleanParameter("http.connection.stalecheck", bl);
    }

    public static void setTcpNoDelay(HttpParams httpParams, boolean bl) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        httpParams.setBooleanParameter("http.tcp.nodelay", bl);
    }
}

