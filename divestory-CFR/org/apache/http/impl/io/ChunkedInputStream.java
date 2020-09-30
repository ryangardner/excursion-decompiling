/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.MalformedChunkCodingException;
import org.apache.http.TruncatedChunkException;
import org.apache.http.impl.io.AbstractMessageParser;
import org.apache.http.io.BufferInfo;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.ExceptionUtils;

public class ChunkedInputStream
extends InputStream {
    private static final int BUFFER_SIZE = 2048;
    private static final int CHUNK_CRLF = 3;
    private static final int CHUNK_DATA = 2;
    private static final int CHUNK_LEN = 1;
    private final CharArrayBuffer buffer;
    private int chunkSize;
    private boolean closed = false;
    private boolean eof = false;
    private Header[] footers = new Header[0];
    private final SessionInputBuffer in;
    private int pos;
    private int state;

    public ChunkedInputStream(SessionInputBuffer sessionInputBuffer) {
        if (sessionInputBuffer == null) throw new IllegalArgumentException("Session input buffer may not be null");
        this.in = sessionInputBuffer;
        this.pos = 0;
        this.buffer = new CharArrayBuffer(16);
        this.state = 1;
    }

    private int getChunkSize() throws IOException {
        int n;
        int n2 = this.state;
        if (n2 != 1) {
            if (n2 != 3) throw new IllegalStateException("Inconsistent codec state");
            this.buffer.clear();
            if (this.in.readLine(this.buffer) == -1) {
                return 0;
            }
            if (!this.buffer.isEmpty()) throw new MalformedChunkCodingException("Unexpected content at the end of chunk");
            this.state = 1;
        }
        this.buffer.clear();
        if (this.in.readLine(this.buffer) == -1) {
            return 0;
        }
        n2 = n = this.buffer.indexOf(59);
        if (n < 0) {
            n2 = this.buffer.length();
        }
        try {
            return Integer.parseInt(this.buffer.substringTrimmed(0, n2), 16);
        }
        catch (NumberFormatException numberFormatException) {
            throw new MalformedChunkCodingException("Bad chunk header");
        }
    }

    private void nextChunk() throws IOException {
        int n;
        this.chunkSize = n = this.getChunkSize();
        if (n < 0) throw new MalformedChunkCodingException("Negative chunk size");
        this.state = 2;
        this.pos = 0;
        if (n != 0) return;
        this.eof = true;
        this.parseTrailerHeaders();
    }

    private void parseTrailerHeaders() throws IOException {
        try {
            this.footers = AbstractMessageParser.parseHeaders(this.in, -1, -1, null);
            return;
        }
        catch (HttpException httpException) {
            Serializable serializable = new StringBuffer();
            serializable.append("Invalid footer: ");
            serializable.append(httpException.getMessage());
            serializable = new MalformedChunkCodingException(serializable.toString());
            ExceptionUtils.initCause((Throwable)serializable, httpException);
            throw serializable;
        }
    }

    @Override
    public int available() throws IOException {
        SessionInputBuffer sessionInputBuffer = this.in;
        if (!(sessionInputBuffer instanceof BufferInfo)) return 0;
        return Math.min(((BufferInfo)((Object)sessionInputBuffer)).length(), this.chunkSize - this.pos);
    }

    @Override
    public void close() throws IOException {
        if (this.closed) return;
        try {
            if (!this.eof) {
                int n;
                byte[] arrby = new byte[2048];
                while ((n = this.read(arrby)) >= 0) {
                }
            }
            this.eof = true;
            this.closed = true;
            return;
        }
        catch (Throwable throwable) {
            this.eof = true;
            this.closed = true;
            throw throwable;
        }
    }

    public Header[] getFooters() {
        return (Header[])this.footers.clone();
    }

    @Override
    public int read() throws IOException {
        int n;
        int n2;
        if (this.closed) throw new IOException("Attempted read from closed stream.");
        if (this.eof) {
            return -1;
        }
        if (this.state != 2) {
            this.nextChunk();
            if (this.eof) {
                return -1;
            }
        }
        if ((n = this.in.read()) == -1) return n;
        this.pos = n2 = this.pos + 1;
        if (n2 < this.chunkSize) return n;
        this.state = 3;
        return n;
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    @Override
    public int read(byte[] object, int n, int n2) throws IOException {
        if (this.closed) throw new IOException("Attempted read from closed stream.");
        if (this.eof) {
            return -1;
        }
        if (this.state != 2) {
            this.nextChunk();
            if (this.eof) {
                return -1;
            }
        }
        if ((n = this.in.read((byte[])object, n, n2 = Math.min(n2, this.chunkSize - this.pos))) != -1) {
            this.pos = n2 = this.pos + n;
            if (n2 < this.chunkSize) return n;
            this.state = 3;
            return n;
        }
        this.eof = true;
        object = new StringBuffer();
        ((StringBuffer)object).append("Truncated chunk ( expected size: ");
        ((StringBuffer)object).append(this.chunkSize);
        ((StringBuffer)object).append("; actual size: ");
        ((StringBuffer)object).append(this.pos);
        ((StringBuffer)object).append(")");
        throw new TruncatedChunkException(((StringBuffer)object).toString());
    }
}

