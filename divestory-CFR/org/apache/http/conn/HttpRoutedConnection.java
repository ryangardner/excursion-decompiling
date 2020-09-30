/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn;

import javax.net.ssl.SSLSession;
import org.apache.http.HttpInetConnection;
import org.apache.http.conn.routing.HttpRoute;

public interface HttpRoutedConnection
extends HttpInetConnection {
    public HttpRoute getRoute();

    public SSLSession getSSLSession();

    public boolean isSecure();
}

