/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import com.google.api.client.util.LoggingByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingOutputStream
extends FilterOutputStream {
    private final LoggingByteArrayOutputStream logStream;

    public LoggingOutputStream(OutputStream outputStream2, Logger logger, Level level, int n) {
        super(outputStream2);
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
    public void write(int n) throws IOException {
        this.out.write(n);
        this.logStream.write(n);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        this.out.write(arrby, n, n2);
        this.logStream.write(arrby, n, n2);
    }
}

