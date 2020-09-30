/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn;

import java.net.ConnectException;
import org.apache.http.HttpHost;

public class HttpHostConnectException
extends ConnectException {
    private static final long serialVersionUID = -3194482710275220224L;
    private final HttpHost host;

    public HttpHostConnectException(HttpHost httpHost, ConnectException connectException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Connection to ");
        stringBuilder.append(httpHost);
        stringBuilder.append(" refused");
        super(stringBuilder.toString());
        this.host = httpHost;
        this.initCause(connectException);
    }

    public HttpHost getHost() {
        return this.host;
    }
}

