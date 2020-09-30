/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.io;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.io.SessionOutputBuffer;

public class IdentityOutputStream
extends OutputStream {
    private boolean closed = false;
    private final SessionOutputBuffer out;

    public IdentityOutputStream(SessionOutputBuffer sessionOutputBuffer) {
        if (sessionOutputBuffer == null) throw new IllegalArgumentException("Session output buffer may not be null");
        this.out = sessionOutputBuffer;
    }

    @Override
    public void close() throws IOException {
        if (this.closed) return;
        this.closed = true;
        this.out.flush();
    }

    @Override
    public void flush() throws IOException {
        this.out.flush();
    }

    @Override
    public void write(int n) throws IOException {
        if (this.closed) throw new IOException("Attempted write to closed stream.");
        this.out.write(n);
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        this.write(arrby, 0, arrby.length);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        if (this.closed) throw new IOException("Attempted write to closed stream.");
        this.out.write(arrby, n, n2);
    }
}

