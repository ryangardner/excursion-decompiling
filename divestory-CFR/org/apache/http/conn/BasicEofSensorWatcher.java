/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.conn.EofSensorWatcher;
import org.apache.http.conn.ManagedClientConnection;

public class BasicEofSensorWatcher
implements EofSensorWatcher {
    protected final boolean attemptReuse;
    protected final ManagedClientConnection managedConn;

    public BasicEofSensorWatcher(ManagedClientConnection managedClientConnection, boolean bl) {
        if (managedClientConnection == null) throw new IllegalArgumentException("Connection may not be null.");
        this.managedConn = managedClientConnection;
        this.attemptReuse = bl;
    }

    @Override
    public boolean eofDetected(InputStream inputStream2) throws IOException {
        try {
            if (!this.attemptReuse) return false;
            inputStream2.close();
            this.managedConn.markReusable();
            return false;
        }
        finally {
            this.managedConn.releaseConnection();
        }
    }

    @Override
    public boolean streamAbort(InputStream inputStream2) throws IOException {
        this.managedConn.abortConnection();
        return false;
    }

    @Override
    public boolean streamClosed(InputStream inputStream2) throws IOException {
        try {
            if (!this.attemptReuse) return false;
            inputStream2.close();
            this.managedConn.markReusable();
            return false;
        }
        finally {
            this.managedConn.releaseConnection();
        }
    }
}

