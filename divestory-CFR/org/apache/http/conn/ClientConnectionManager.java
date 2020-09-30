/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn;

import java.util.concurrent.TimeUnit;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;

public interface ClientConnectionManager {
    public void closeExpiredConnections();

    public void closeIdleConnections(long var1, TimeUnit var3);

    public SchemeRegistry getSchemeRegistry();

    public void releaseConnection(ManagedClientConnection var1, long var2, TimeUnit var4);

    public ClientConnectionRequest requestConnection(HttpRoute var1, Object var2);

    public void shutdown();
}

