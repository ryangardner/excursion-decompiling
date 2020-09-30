/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 */
package org.apache.http.impl.conn;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.logging.Log;

public class Wire {
    private final Log log;

    public Wire(Log log) {
        this.log = log;
    }

    private void wire(String string2, InputStream inputStream2) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        do {
            int n;
            if ((n = inputStream2.read()) == -1) {
                if (stringBuilder.length() <= 0) return;
                stringBuilder.append('\"');
                stringBuilder.insert(0, '\"');
                stringBuilder.insert(0, string2);
                this.log.debug((Object)stringBuilder.toString());
                return;
            }
            if (n == 13) {
                stringBuilder.append("[\\r]");
                continue;
            }
            if (n == 10) {
                stringBuilder.append("[\\n]\"");
                stringBuilder.insert(0, "\"");
                stringBuilder.insert(0, string2);
                this.log.debug((Object)stringBuilder.toString());
                stringBuilder.setLength(0);
                continue;
            }
            if (n >= 32 && n <= 127) {
                stringBuilder.append((char)n);
                continue;
            }
            stringBuilder.append("[0x");
            stringBuilder.append(Integer.toHexString(n));
            stringBuilder.append("]");
        } while (true);
    }

    public boolean enabled() {
        return this.log.isDebugEnabled();
    }

    public void input(int n) throws IOException {
        this.input(new byte[]{(byte)n});
    }

    public void input(InputStream inputStream2) throws IOException {
        if (inputStream2 == null) throw new IllegalArgumentException("Input may not be null");
        this.wire("<< ", inputStream2);
    }

    @Deprecated
    public void input(String string2) throws IOException {
        if (string2 == null) throw new IllegalArgumentException("Input may not be null");
        this.input(string2.getBytes());
    }

    public void input(byte[] arrby) throws IOException {
        if (arrby == null) throw new IllegalArgumentException("Input may not be null");
        this.wire("<< ", new ByteArrayInputStream(arrby));
    }

    public void input(byte[] arrby, int n, int n2) throws IOException {
        if (arrby == null) throw new IllegalArgumentException("Input may not be null");
        this.wire("<< ", new ByteArrayInputStream(arrby, n, n2));
    }

    public void output(int n) throws IOException {
        this.output(new byte[]{(byte)n});
    }

    public void output(InputStream inputStream2) throws IOException {
        if (inputStream2 == null) throw new IllegalArgumentException("Output may not be null");
        this.wire(">> ", inputStream2);
    }

    @Deprecated
    public void output(String string2) throws IOException {
        if (string2 == null) throw new IllegalArgumentException("Output may not be null");
        this.output(string2.getBytes());
    }

    public void output(byte[] arrby) throws IOException {
        if (arrby == null) throw new IllegalArgumentException("Output may not be null");
        this.wire(">> ", new ByteArrayInputStream(arrby));
    }

    public void output(byte[] arrby, int n, int n2) throws IOException {
        if (arrby == null) throw new IllegalArgumentException("Output may not be null");
        this.wire(">> ", new ByteArrayInputStream(arrby, n, n2));
    }
}

