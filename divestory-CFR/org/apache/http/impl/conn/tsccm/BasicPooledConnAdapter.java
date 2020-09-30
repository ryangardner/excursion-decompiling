/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.conn.tsccm;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.conn.AbstractPoolEntry;
import org.apache.http.impl.conn.AbstractPooledConnAdapter;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;

public class BasicPooledConnAdapter
extends AbstractPooledConnAdapter {
    protected BasicPooledConnAdapter(ThreadSafeClientConnManager threadSafeClientConnManager, AbstractPoolEntry abstractPoolEntry) {
        super(threadSafeClientConnManager, abstractPoolEntry);
        this.markReusable();
    }

    @Override
    protected void detach() {
        super.detach();
    }

    @Override
    protected ClientConnectionManager getManager() {
        return super.getManager();
    }

    @Override
    protected AbstractPoolEntry getPoolEntry() {
        return super.getPoolEntry();
    }
}

