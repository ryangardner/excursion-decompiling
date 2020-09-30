/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.io;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.io.SessionOutputBuffer;

public class ContentLengthOutputStream
extends OutputStream {
    private boolean closed = false;
    private final long contentLength;
    private final SessionOutputBuffer out;
    private long total = 0L;

    public ContentLengthOutputStream(SessionOutputBuffer sessionOutputBuffer, long l) {
        if (sessionOutputBuffer == null) throw new IllegalArgumentException("Session output buffer may not be null");
        if (l < 0L) throw new IllegalArgumentException("Content length may not be negative");
        this.out = sessionOutputBuffer;
        this.contentLength = l;
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
        if (this.total >= this.contentLength) return;
        this.out.write(n);
        ++this.total;
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        this.write(arrby, 0, arrby.length);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        if (this.closed) throw new IOException("Attempted write to closed stream.");
        long l = this.total;
        long l2 = this.contentLength;
        if (l >= l2) return;
        int n3 = n2;
        if ((long)n2 > (l2 -= l)) {
            n3 = (int)l2;
        }
        this.out.write(arrby, n, n3);
        this.total += (long)n3;
    }
}

