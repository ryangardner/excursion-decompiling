/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.conn.ConnectionReleaseTrigger;
import org.apache.http.conn.EofSensorInputStream;
import org.apache.http.conn.EofSensorWatcher;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.util.EntityUtils;

public class BasicManagedEntity
extends HttpEntityWrapper
implements ConnectionReleaseTrigger,
EofSensorWatcher {
    protected final boolean attemptReuse;
    protected ManagedClientConnection managedConn;

    public BasicManagedEntity(HttpEntity httpEntity, ManagedClientConnection managedClientConnection, boolean bl) {
        super(httpEntity);
        if (managedClientConnection == null) throw new IllegalArgumentException("Connection may not be null.");
        this.managedConn = managedClientConnection;
        this.attemptReuse = bl;
    }

    private void ensureConsumed() throws IOException {
        if (this.managedConn == null) {
            return;
        }
        try {
            if (!this.attemptReuse) return;
            EntityUtils.consume(this.wrappedEntity);
            this.managedConn.markReusable();
            return;
        }
        finally {
            this.releaseManagedConnection();
        }
    }

    @Override
    public void abortConnection() throws IOException {
        ManagedClientConnection managedClientConnection = this.managedConn;
        if (managedClientConnection == null) return;
        try {
            managedClientConnection.abortConnection();
            return;
        }
        finally {
            this.managedConn = null;
        }
    }

    @Deprecated
    @Override
    public void consumeContent() throws IOException {
        this.ensureConsumed();
    }

    @Override
    public boolean eofDetected(InputStream inputStream2) throws IOException {
        try {
            if (!this.attemptReuse) return false;
            if (this.managedConn == null) return false;
            inputStream2.close();
            this.managedConn.markReusable();
            return false;
        }
        finally {
            this.releaseManagedConnection();
        }
    }

    @Override
    public InputStream getContent() throws IOException {
        return new EofSensorInputStream(this.wrappedEntity.getContent(), this);
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }

    @Override
    public void releaseConnection() throws IOException {
        this.ensureConsumed();
    }

    protected void releaseManagedConnection() throws IOException {
        ManagedClientConnection managedClientConnection = this.managedConn;
        if (managedClientConnection == null) return;
        try {
            managedClientConnection.releaseConnection();
            return;
        }
        finally {
            this.managedConn = null;
        }
    }

    @Override
    public boolean streamAbort(InputStream object) throws IOException {
        object = this.managedConn;
        if (object == null) return false;
        object.abortConnection();
        return false;
    }

    @Override
    public boolean streamClosed(InputStream inputStream2) throws IOException {
        try {
            if (!this.attemptReuse) return false;
            if (this.managedConn == null) return false;
            inputStream2.close();
            this.managedConn.markReusable();
            return false;
        }
        finally {
            this.releaseManagedConnection();
        }
    }

    @Override
    public void writeTo(OutputStream outputStream2) throws IOException {
        super.writeTo(outputStream2);
        this.ensureConsumed();
    }
}

