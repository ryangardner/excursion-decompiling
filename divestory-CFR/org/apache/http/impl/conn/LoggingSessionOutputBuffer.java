/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import org.apache.http.impl.conn.Wire;
import org.apache.http.io.HttpTransportMetrics;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.util.CharArrayBuffer;

public class LoggingSessionOutputBuffer
implements SessionOutputBuffer {
    private final String charset;
    private final SessionOutputBuffer out;
    private final Wire wire;

    public LoggingSessionOutputBuffer(SessionOutputBuffer sessionOutputBuffer, Wire wire) {
        this(sessionOutputBuffer, wire, null);
    }

    public LoggingSessionOutputBuffer(SessionOutputBuffer sessionOutputBuffer, Wire wire, String string2) {
        this.out = sessionOutputBuffer;
        this.wire = wire;
        if (string2 == null) {
            string2 = "ASCII";
        }
        this.charset = string2;
    }

    @Override
    public void flush() throws IOException {
        this.out.flush();
    }

    @Override
    public HttpTransportMetrics getMetrics() {
        return this.out.getMetrics();
    }

    @Override
    public void write(int n) throws IOException {
        this.out.write(n);
        if (!this.wire.enabled()) return;
        this.wire.output(n);
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        this.out.write(arrby);
        if (!this.wire.enabled()) return;
        this.wire.output(arrby);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        this.out.write(arrby, n, n2);
        if (!this.wire.enabled()) return;
        this.wire.output(arrby, n, n2);
    }

    @Override
    public void writeLine(String string2) throws IOException {
        this.out.writeLine(string2);
        if (!this.wire.enabled()) return;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("\r\n");
        string2 = stringBuilder.toString();
        this.wire.output(string2.getBytes(this.charset));
    }

    @Override
    public void writeLine(CharArrayBuffer object) throws IOException {
        this.out.writeLine((CharArrayBuffer)object);
        if (!this.wire.enabled()) return;
        object = new String(((CharArrayBuffer)object).buffer(), 0, ((CharArrayBuffer)object).length());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)object);
        stringBuilder.append("\r\n");
        object = stringBuilder.toString();
        this.wire.output(((String)object).getBytes(this.charset));
    }
}

