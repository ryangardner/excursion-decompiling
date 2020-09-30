/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn;

import java.util.concurrent.TimeUnit;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ManagedClientConnection;

public interface ClientConnectionRequest {
    public void abortRequest();

    public ManagedClientConnection getConnection(long var1, TimeUnit var3) throws InterruptedException, ConnectionPoolTimeoutException;
}

