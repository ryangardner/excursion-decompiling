/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import com.google.api.client.util.LoggingByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingInputStream
extends FilterInputStream {
    private final LoggingByteArrayOutputStream logStream;

    public LoggingInputStream(InputStream inputStream2, Logger logger, Level level, int n) {
        super(inputStream2);
        this.logStream = new LoggingByteArrayOutputStream(logger, level, n);
    }

    @Override
    public void close() throws IOException {
        this.logStream.close();
        super.close();
    }

    public final LoggingByteArrayOutputStream getLogStream() {
        return this.logStream;
    }

    @Override
    public int read() throws IOException {
        int n = super.read();
        this.logStream.write(n);
        return n;
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if ((n2 = super.read(arrby, n, n2)) <= 0) return n2;
        this.logStream.write(arrby, n, n2);
        return n2;
    }
}

