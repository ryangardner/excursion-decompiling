/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.conn.tsccm;

import java.util.concurrent.TimeUnit;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.impl.conn.tsccm.BasicPoolEntry;

public interface PoolEntryRequest {
    public void abortRequest();

    public BasicPoolEntry getPoolEntry(long var1, TimeUnit var3) throws InterruptedException, ConnectionPoolTimeoutException;
}

