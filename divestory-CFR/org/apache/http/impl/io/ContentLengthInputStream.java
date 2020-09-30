/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.ConnectionClosedException;
import org.apache.http.io.BufferInfo;
import org.apache.http.io.SessionInputBuffer;

public class ContentLengthInputStream
extends InputStream {
    private static final int BUFFER_SIZE = 2048;
    private boolean closed = false;
    private long contentLength;
    private SessionInputBuffer in = null;
    private long pos = 0L;

    public ContentLengthInputStream(SessionInputBuffer sessionInputBuffer, long l) {
        if (sessionInputBuffer == null) throw new IllegalArgumentException("Input stream may not be null");
        if (l < 0L) throw new IllegalArgumentException("Content length may not be negative");
        this.in = sessionInputBuffer;
        this.contentLength = l;
    }

    @Override
    public int available() throws IOException {
        SessionInputBuffer sessionInputBuffer = this.in;
        if (!(sessionInputBuffer instanceof BufferInfo)) return 0;
        return Math.min(((BufferInfo)((Object)sessionInputBuffer)).length(), (int)(this.contentLength - this.pos));
    }

    @Override
    public void close() throws IOException {
        if (this.closed) return;
        try {
            if (this.pos < this.contentLength) {
                int n;
                byte[] arrby = new byte[2048];
                while ((n = this.read(arrby)) >= 0) {
                }
            }
            this.closed = true;
            return;
        }
        catch (Throwable throwable) {
            this.closed = true;
            throw throwable;
        }
    }

    @Override
    public int read() throws IOException {
        if (this.closed) throw new IOException("Attempted read from closed stream.");
        if (this.pos >= this.contentLength) {
            return -1;
        }
        int n = this.in.read();
        if (n != -1) {
            ++this.pos;
            return n;
        }
        if (this.pos >= this.contentLength) {
            return n;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Premature end of Content-Length delimited message body (expected: ");
        stringBuffer.append(this.contentLength);
        stringBuffer.append("; received: ");
        stringBuffer.append(this.pos);
        throw new ConnectionClosedException(stringBuffer.toString());
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    @Override
    public int read(byte[] object, int n, int n2) throws IOException {
        if (this.closed) throw new IOException("Attempted read from closed stream.");
        long l = this.pos;
        long l2 = this.contentLength;
        if (l >= l2) {
            return -1;
        }
        int n3 = n2;
        if ((long)n2 + l > l2) {
            n3 = (int)(l2 - l);
        }
        if ((n = this.in.read((byte[])object, n, n3)) == -1 && this.pos < this.contentLength) {
            object = new StringBuffer();
            ((StringBuffer)object).append("Premature end of Content-Length delimited message body (expected: ");
            ((StringBuffer)object).append(this.contentLength);
            ((StringBuffer)object).append("; received: ");
            ((StringBuffer)object).append(this.pos);
            throw new ConnectionClosedException(((StringBuffer)object).toString());
        }
        if (n <= 0) return n;
        this.pos += (long)n;
        return n;
    }

    @Override
    public long skip(long l) throws IOException {
        if (l <= 0L) {
            return 0L;
        }
        byte[] arrby = new byte[2048];
        l = Math.min(l, this.contentLength - this.pos);
        long l2 = 0L;
        while (l > 0L) {
            int n = this.read(arrby, 0, (int)Math.min(2048L, l));
            if (n == -1) {
                return l2;
            }
            long l3 = n;
            l2 += l3;
            l -= l3;
        }
        return l2;
    }
}

