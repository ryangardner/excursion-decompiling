/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import org.apache.http.impl.conn.Wire;
import org.apache.http.io.EofSensor;
import org.apache.http.io.HttpTransportMetrics;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.util.CharArrayBuffer;

public class LoggingSessionInputBuffer
implements SessionInputBuffer,
EofSensor {
    private final String charset;
    private final EofSensor eofSensor;
    private final SessionInputBuffer in;
    private final Wire wire;

    public LoggingSessionInputBuffer(SessionInputBuffer sessionInputBuffer, Wire wire) {
        this(sessionInputBuffer, wire, null);
    }

    public LoggingSessionInputBuffer(SessionInputBuffer object, Wire wire, String string2) {
        this.in = object;
        object = object instanceof EofSensor ? (EofSensor)object : null;
        this.eofSensor = object;
        this.wire = wire;
        if (string2 == null) {
            string2 = "ASCII";
        }
        this.charset = string2;
    }

    @Override
    public HttpTransportMetrics getMetrics() {
        return this.in.getMetrics();
    }

    @Override
    public boolean isDataAvailable(int n) throws IOException {
        return this.in.isDataAvailable(n);
    }

    @Override
    public boolean isEof() {
        EofSensor eofSensor = this.eofSensor;
        if (eofSensor == null) return false;
        return eofSensor.isEof();
    }

    @Override
    public int read() throws IOException {
        int n = this.in.read();
        if (!this.wire.enabled()) return n;
        if (n == -1) return n;
        this.wire.input(n);
        return n;
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        int n = this.in.read(arrby);
        if (!this.wire.enabled()) return n;
        if (n <= 0) return n;
        this.wire.input(arrby, 0, n);
        return n;
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        n2 = this.in.read(arrby, n, n2);
        if (!this.wire.enabled()) return n2;
        if (n2 <= 0) return n2;
        this.wire.input(arrby, n, n2);
        return n2;
    }

    @Override
    public int readLine(CharArrayBuffer object) throws IOException {
        int n = this.in.readLine((CharArrayBuffer)object);
        if (!this.wire.enabled()) return n;
        if (n < 0) return n;
        int n2 = ((CharArrayBuffer)object).length();
        String string2 = new String(((CharArrayBuffer)object).buffer(), n2 - n, n);
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("\r\n");
        object = ((StringBuilder)object).toString();
        this.wire.input(((String)object).getBytes(this.charset));
        return n;
    }

    @Override
    public String readLine() throws IOException {
        String string2 = this.in.readLine();
        if (!this.wire.enabled()) return string2;
        if (string2 == null) return string2;
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append("\r\n");
        charSequence = ((StringBuilder)charSequence).toString();
        this.wire.input(((String)charSequence).getBytes(this.charset));
        return string2;
    }
}

