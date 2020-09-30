/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.impl.io.HttpTransportMetricsImpl;
import org.apache.http.io.BufferInfo;
import org.apache.http.io.HttpTransportMetrics;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.CharArrayBuffer;

public abstract class AbstractSessionInputBuffer
implements SessionInputBuffer,
BufferInfo {
    private boolean ascii = true;
    private byte[] buffer;
    private int bufferlen;
    private int bufferpos;
    private String charset = "US-ASCII";
    private InputStream instream;
    private ByteArrayBuffer linebuffer = null;
    private int maxLineLen = -1;
    private HttpTransportMetricsImpl metrics;
    private int minChunkLimit = 512;

    private int lineFromLineBuffer(CharArrayBuffer charArrayBuffer) throws IOException {
        int n;
        int n2 = this.linebuffer.length();
        if (n2 > 0) {
            n = n2;
            if (this.linebuffer.byteAt(n2 - 1) == 10) {
                n = n2 - 1;
                this.linebuffer.setLength(n);
            }
            if (n > 0 && this.linebuffer.byteAt(n - 1) == 13) {
                this.linebuffer.setLength(n - 1);
            }
        }
        n = this.linebuffer.length();
        if (this.ascii) {
            charArrayBuffer.append(this.linebuffer, 0, n);
        } else {
            String string2 = new String(this.linebuffer.buffer(), 0, n, this.charset);
            n = string2.length();
            charArrayBuffer.append(string2);
        }
        this.linebuffer.clear();
        return n;
    }

    private int lineFromReadBuffer(CharArrayBuffer charArrayBuffer, int n) throws IOException {
        int n2 = this.bufferpos;
        this.bufferpos = n + 1;
        int n3 = n;
        if (n > 0) {
            n3 = n;
            if (this.buffer[n - 1] == 13) {
                n3 = n - 1;
            }
        }
        n = n3 - n2;
        if (this.ascii) {
            charArrayBuffer.append(this.buffer, n2, n);
            return n;
        }
        String string2 = new String(this.buffer, n2, n, this.charset);
        charArrayBuffer.append(string2);
        return string2.length();
    }

    private int locateLF() {
        int n = this.bufferpos;
        while (n < this.bufferlen) {
            if (this.buffer[n] == 10) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    @Override
    public int available() {
        return this.capacity() - this.length();
    }

    @Override
    public int capacity() {
        return this.buffer.length;
    }

    protected HttpTransportMetricsImpl createTransportMetrics() {
        return new HttpTransportMetricsImpl();
    }

    protected int fillBuffer() throws IOException {
        int n;
        byte[] arrby;
        int n2 = this.bufferpos;
        if (n2 > 0) {
            n = this.bufferlen - n2;
            if (n > 0) {
                arrby = this.buffer;
                System.arraycopy(arrby, n2, arrby, 0, n);
            }
            this.bufferpos = 0;
            this.bufferlen = n;
        }
        n2 = this.bufferlen;
        arrby = this.buffer;
        n = arrby.length;
        if ((n = this.instream.read(arrby, n2, n - n2)) == -1) {
            return -1;
        }
        this.bufferlen = n2 + n;
        this.metrics.incrementBytesTransferred(n);
        return n;
    }

    @Override
    public HttpTransportMetrics getMetrics() {
        return this.metrics;
    }

    protected boolean hasBufferedData() {
        if (this.bufferpos >= this.bufferlen) return false;
        return true;
    }

    protected void init(InputStream object, int n, HttpParams httpParams) {
        if (object == null) throw new IllegalArgumentException("Input stream may not be null");
        if (n <= 0) throw new IllegalArgumentException("Buffer size may not be negative or zero");
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        this.instream = object;
        this.buffer = new byte[n];
        boolean bl = false;
        this.bufferpos = 0;
        this.bufferlen = 0;
        this.linebuffer = new ByteArrayBuffer(n);
        this.charset = object = HttpProtocolParams.getHttpElementCharset(httpParams);
        if (((String)object).equalsIgnoreCase("US-ASCII") || this.charset.equalsIgnoreCase("ASCII")) {
            bl = true;
        }
        this.ascii = bl;
        this.maxLineLen = httpParams.getIntParameter("http.connection.max-line-length", -1);
        this.minChunkLimit = httpParams.getIntParameter("http.connection.min-chunk-limit", 512);
        this.metrics = this.createTransportMetrics();
    }

    @Override
    public int length() {
        return this.bufferlen - this.bufferpos;
    }

    @Override
    public int read() throws IOException {
        do {
            if (!this.hasBufferedData()) continue;
            byte[] arrby = this.buffer;
            int n = this.bufferpos;
            this.bufferpos = n + 1;
            return arrby[n] & 255;
        } while (this.fillBuffer() != -1);
        return -1;
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        if (arrby != null) return this.read(arrby, 0, arrby.length);
        return 0;
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if (arrby == null) {
            return 0;
        }
        if (this.hasBufferedData()) {
            n2 = Math.min(n2, this.bufferlen - this.bufferpos);
            System.arraycopy(this.buffer, this.bufferpos, arrby, n, n2);
            this.bufferpos += n2;
            return n2;
        }
        if (n2 > this.minChunkLimit) {
            if ((n = this.instream.read(arrby, n, n2)) <= 0) return n;
            this.metrics.incrementBytesTransferred(n);
            return n;
        }
        do {
            if (!this.hasBufferedData()) continue;
            n2 = Math.min(n2, this.bufferlen - this.bufferpos);
            System.arraycopy(this.buffer, this.bufferpos, arrby, n, n2);
            this.bufferpos += n2;
            return n2;
        } while (this.fillBuffer() != -1);
        return -1;
    }

    @Override
    public int readLine(CharArrayBuffer charArrayBuffer) throws IOException {
        if (charArrayBuffer == null) throw new IllegalArgumentException("Char array buffer may not be null");
        int n = 1;
        int n2 = 0;
        do {
            int n3;
            int n4;
            block9 : {
                block8 : {
                    block7 : {
                        if (n == 0) {
                            if (n2 != -1) return this.lineFromLineBuffer(charArrayBuffer);
                            if (!this.linebuffer.isEmpty()) return this.lineFromLineBuffer(charArrayBuffer);
                            return -1;
                        }
                        n3 = this.locateLF();
                        if (n3 == -1) break block7;
                        if (this.linebuffer.isEmpty()) {
                            return this.lineFromReadBuffer(charArrayBuffer, n3);
                        }
                        n = this.bufferpos;
                        this.linebuffer.append(this.buffer, n, ++n3 - n);
                        this.bufferpos = n3;
                        break block8;
                    }
                    if (this.hasBufferedData()) {
                        n2 = this.bufferlen;
                        n3 = this.bufferpos;
                        this.linebuffer.append(this.buffer, n3, n2 - n3);
                        this.bufferpos = this.bufferlen;
                    }
                    n2 = this.fillBuffer();
                    n4 = n;
                    n3 = n2;
                    if (n2 != -1) break block9;
                }
                n4 = 0;
                n3 = n2;
            }
            n = n4;
            n2 = n3;
            if (this.maxLineLen <= 0) continue;
            if (this.linebuffer.length() >= this.maxLineLen) throw new IOException("Maximum line length limit exceeded");
            n = n4;
            n2 = n3;
        } while (true);
    }

    @Override
    public String readLine() throws IOException {
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(64);
        if (this.readLine(charArrayBuffer) == -1) return null;
        return charArrayBuffer.toString();
    }
}

