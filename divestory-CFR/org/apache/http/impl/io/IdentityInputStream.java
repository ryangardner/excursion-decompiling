/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.io.BufferInfo;
import org.apache.http.io.SessionInputBuffer;

public class IdentityInputStream
extends InputStream {
    private boolean closed = false;
    private final SessionInputBuffer in;

    public IdentityInputStream(SessionInputBuffer sessionInputBuffer) {
        if (sessionInputBuffer == null) throw new IllegalArgumentException("Session input buffer may not be null");
        this.in = sessionInputBuffer;
    }

    @Override
    public int available() throws IOException {
        SessionInputBuffer sessionInputBuffer = this.in;
        if (!(sessionInputBuffer instanceof BufferInfo)) return 0;
        return ((BufferInfo)((Object)sessionInputBuffer)).length();
    }

    @Override
    public void close() throws IOException {
        this.closed = true;
    }

    @Override
    public int read() throws IOException {
        if (!this.closed) return this.in.read();
        return -1;
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if (!this.closed) return this.in.read(arrby, n, n2);
        return -1;
    }
}

