/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.io;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.io.SessionOutputBuffer;

public class ChunkedOutputStream
extends OutputStream {
    private byte[] cache;
    private int cachePosition = 0;
    private boolean closed = false;
    private final SessionOutputBuffer out;
    private boolean wroteLastChunk = false;

    public ChunkedOutputStream(SessionOutputBuffer sessionOutputBuffer) throws IOException {
        this(sessionOutputBuffer, 2048);
    }

    public ChunkedOutputStream(SessionOutputBuffer sessionOutputBuffer, int n) throws IOException {
        this.cache = new byte[n];
        this.out = sessionOutputBuffer;
    }

    @Override
    public void close() throws IOException {
        if (this.closed) return;
        this.closed = true;
        this.finish();
        this.out.flush();
    }

    public void finish() throws IOException {
        if (this.wroteLastChunk) return;
        this.flushCache();
        this.writeClosingChunk();
        this.wroteLastChunk = true;
    }

    @Override
    public void flush() throws IOException {
        this.flushCache();
        this.out.flush();
    }

    protected void flushCache() throws IOException {
        int n = this.cachePosition;
        if (n <= 0) return;
        this.out.writeLine(Integer.toHexString(n));
        this.out.write(this.cache, 0, this.cachePosition);
        this.out.writeLine("");
        this.cachePosition = 0;
    }

    protected void flushCacheWithAppend(byte[] arrby, int n, int n2) throws IOException {
        this.out.writeLine(Integer.toHexString(this.cachePosition + n2));
        this.out.write(this.cache, 0, this.cachePosition);
        this.out.write(arrby, n, n2);
        this.out.writeLine("");
        this.cachePosition = 0;
    }

    @Override
    public void write(int n) throws IOException {
        if (this.closed) throw new IOException("Attempted write to closed stream.");
        byte[] arrby = this.cache;
        int n2 = this.cachePosition;
        arrby[n2] = (byte)n;
        this.cachePosition = n = n2 + 1;
        if (n != arrby.length) return;
        this.flushCache();
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        this.write(arrby, 0, arrby.length);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        if (this.closed) throw new IOException("Attempted write to closed stream.");
        byte[] arrby2 = this.cache;
        int n3 = arrby2.length;
        int n4 = this.cachePosition;
        if (n2 >= n3 - n4) {
            this.flushCacheWithAppend(arrby, n, n2);
            return;
        }
        System.arraycopy(arrby, n, arrby2, n4, n2);
        this.cachePosition += n2;
    }

    protected void writeClosingChunk() throws IOException {
        this.out.writeLine("0");
        this.out.writeLine("");
    }
}

