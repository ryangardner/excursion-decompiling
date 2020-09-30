/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSession;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpHost;
import org.apache.http.conn.ConnectionReleaseTrigger;
import org.apache.http.conn.HttpRoutedConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public interface ManagedClientConnection
extends HttpClientConnection,
HttpRoutedConnection,
ConnectionReleaseTrigger {
    @Override
    public HttpRoute getRoute();

    @Override
    public SSLSession getSSLSession();

    public Object getState();

    public boolean isMarkedReusable();

    @Override
    public boolean isSecure();

    public void layerProtocol(HttpContext var1, HttpParams var2) throws IOException;

    public void markReusable();

    public void open(HttpRoute var1, HttpContext var2, HttpParams var3) throws IOException;

    public void setIdleDuration(long var1, TimeUnit var3);

    public void setState(Object var1);

    public void tunnelProxy(HttpHost var1, boolean var2, HttpParams var3) throws IOException;

    public void tunnelTarget(boolean var1, HttpParams var2) throws IOException;

    public void unmarkReusable();
}

