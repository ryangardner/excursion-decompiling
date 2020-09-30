/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.conn;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import org.apache.http.HttpHost;

class HttpInetSocketAddress
extends InetSocketAddress {
    private static final long serialVersionUID = -6650701828361907957L;
    private final HttpHost host;

    public HttpInetSocketAddress(HttpHost httpHost, InetAddress inetAddress, int n) {
        super(inetAddress, n);
        if (httpHost == null) throw new IllegalArgumentException("HTTP host may not be null");
        this.host = httpHost;
    }

    public HttpHost getHost() {
        return this.host;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.host.getHostName());
        stringBuilder.append(":");
        stringBuilder.append(this.getPort());
        return stringBuilder.toString();
    }
}

