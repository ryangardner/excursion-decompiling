/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.conn.ConnectionReleaseTrigger;
import org.apache.http.conn.EofSensorWatcher;

public class EofSensorInputStream
extends InputStream
implements ConnectionReleaseTrigger {
    private final EofSensorWatcher eofWatcher;
    private boolean selfClosed;
    protected InputStream wrappedStream;

    public EofSensorInputStream(InputStream inputStream2, EofSensorWatcher eofSensorWatcher) {
        if (inputStream2 == null) throw new IllegalArgumentException("Wrapped stream may not be null.");
        this.wrappedStream = inputStream2;
        this.selfClosed = false;
        this.eofWatcher = eofSensorWatcher;
    }

    @Override
    public void abortConnection() throws IOException {
        this.selfClosed = true;
        this.checkAbort();
    }

    @Override
    public int available() throws IOException {
        if (!this.isReadAllowed()) {
            return 0;
        }
        try {
            return this.wrappedStream.available();
        }
        catch (IOException iOException) {
            this.checkAbort();
            throw iOException;
        }
    }

    protected void checkAbort() throws IOException {
        InputStream inputStream2 = this.wrappedStream;
        if (inputStream2 == null) return;
        boolean bl = true;
        try {
            if (this.eofWatcher != null) {
                bl = this.eofWatcher.streamAbort(inputStream2);
            }
            if (!bl) return;
            this.wrappedStream.close();
            return;
        }
        finally {
            this.wrappedStream = null;
        }
    }

    protected void checkClose() throws IOException {
        InputStream inputStream2 = this.wrappedStream;
        if (inputStream2 == null) return;
        boolean bl = true;
        try {
            if (this.eofWatcher != null) {
                bl = this.eofWatcher.streamClosed(inputStream2);
            }
            if (!bl) return;
            this.wrappedStream.close();
            return;
        }
        finally {
            this.wrappedStream = null;
        }
    }

    protected void checkEOF(int n) throws IOException {
        InputStream inputStream2 = this.wrappedStream;
        if (inputStream2 == null) return;
        if (n >= 0) return;
        boolean bl = true;
        try {
            if (this.eofWatcher != null) {
                bl = this.eofWatcher.eofDetected(inputStream2);
            }
            if (!bl) return;
            this.wrappedStream.close();
            return;
        }
        finally {
            this.wrappedStream = null;
        }
    }

    @Override
    public void close() throws IOException {
        this.selfClosed = true;
        this.checkClose();
    }

    protected boolean isReadAllowed() throws IOException {
        if (this.selfClosed) throw new IOException("Attempted read on closed stream.");
        if (this.wrappedStream == null) return false;
        return true;
    }

    @Override
    public int read() throws IOException {
        if (!this.isReadAllowed()) {
            return -1;
        }
        try {
            int n = this.wrappedStream.read();
            this.checkEOF(n);
            return n;
        }
        catch (IOException iOException) {
            this.checkAbort();
            throw iOException;
        }
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        if (!this.isReadAllowed()) {
            return -1;
        }
        try {
            int n = this.wrappedStream.read(arrby);
            this.checkEOF(n);
            return n;
        }
        catch (IOException iOException) {
            this.checkAbort();
            throw iOException;
        }
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if (!this.isReadAllowed()) {
            return -1;
        }
        try {
            n = this.wrappedStream.read(arrby, n, n2);
            this.checkEOF(n);
            return n;
        }
        catch (IOException iOException) {
            this.checkAbort();
            throw iOException;
        }
    }

    @Override
    public void releaseConnection() throws IOException {
        this.close();
    }
}

