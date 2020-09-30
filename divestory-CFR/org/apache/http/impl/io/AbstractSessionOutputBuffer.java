/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.io;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.impl.io.HttpTransportMetricsImpl;
import org.apache.http.io.BufferInfo;
import org.apache.http.io.HttpTransportMetrics;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.CharArrayBuffer;

public abstract class AbstractSessionOutputBuffer
implements SessionOutputBuffer,
BufferInfo {
    private static final byte[] CRLF = new byte[]{13, 10};
    private boolean ascii = true;
    private ByteArrayBuffer buffer;
    private String charset = "US-ASCII";
    private HttpTransportMetricsImpl metrics;
    private int minChunkLimit = 512;
    private OutputStream outstream;

    @Override
    public int available() {
        return this.capacity() - this.length();
    }

    @Override
    public int capacity() {
        return this.buffer.capacity();
    }

    protected HttpTransportMetricsImpl createTransportMetrics() {
        return new HttpTransportMetricsImpl();
    }

    @Override
    public void flush() throws IOException {
        this.flushBuffer();
        this.outstream.flush();
    }

    protected void flushBuffer() throws IOException {
        int n = this.buffer.length();
        if (n <= 0) return;
        this.outstream.write(this.buffer.buffer(), 0, n);
        this.buffer.clear();
        this.metrics.incrementBytesTransferred(n);
    }

    @Override
    public HttpTransportMetrics getMetrics() {
        return this.metrics;
    }

    protected void init(OutputStream object, int n, HttpParams httpParams) {
        if (object == null) throw new IllegalArgumentException("Input stream may not be null");
        if (n <= 0) throw new IllegalArgumentException("Buffer size may not be negative or zero");
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        this.outstream = object;
        this.buffer = new ByteArrayBuffer(n);
        this.charset = object = HttpProtocolParams.getHttpElementCharset(httpParams);
        boolean bl = ((String)object).equalsIgnoreCase("US-ASCII") || this.charset.equalsIgnoreCase("ASCII");
        this.ascii = bl;
        this.minChunkLimit = httpParams.getIntParameter("http.connection.min-chunk-limit", 512);
        this.metrics = this.createTransportMetrics();
    }

    @Override
    public int length() {
        return this.buffer.length();
    }

    @Override
    public void write(int n) throws IOException {
        if (this.buffer.isFull()) {
            this.flushBuffer();
        }
        this.buffer.append(n);
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        if (arrby == null) {
            return;
        }
        this.write(arrby, 0, arrby.length);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        if (arrby == null) {
            return;
        }
        if (n2 <= this.minChunkLimit && n2 <= this.buffer.capacity()) {
            if (n2 > this.buffer.capacity() - this.buffer.length()) {
                this.flushBuffer();
            }
            this.buffer.append(arrby, n, n2);
            return;
        }
        this.flushBuffer();
        this.outstream.write(arrby, n, n2);
        this.metrics.incrementBytesTransferred(n2);
    }

    @Override
    public void writeLine(String string2) throws IOException {
        if (string2 == null) {
            return;
        }
        if (string2.length() > 0) {
            this.write(string2.getBytes(this.charset));
        }
        this.write(CRLF);
    }

    @Override
    public void writeLine(CharArrayBuffer charArrayBuffer) throws IOException {
        if (charArrayBuffer == null) {
            return;
        }
        if (this.ascii) {
            int n;
            int n2 = 0;
            for (int i = charArrayBuffer.length(); i > 0; n2 += n, i -= n) {
                n = Math.min(this.buffer.capacity() - this.buffer.length(), i);
                if (n > 0) {
                    this.buffer.append(charArrayBuffer, n2, n);
                }
                if (!this.buffer.isFull()) continue;
                this.flushBuffer();
            }
        } else {
            this.write(charArrayBuffer.toString().getBytes(this.charset));
        }
        this.write(CRLF);
    }
}

