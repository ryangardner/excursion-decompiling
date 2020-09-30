/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import com.google.api.client.util.Preconditions;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingByteArrayOutputStream
extends ByteArrayOutputStream {
    private int bytesWritten;
    private boolean closed;
    private final Logger logger;
    private final Level loggingLevel;
    private final int maximumBytesToLog;

    public LoggingByteArrayOutputStream(Logger logger, Level level, int n) {
        this.logger = Preconditions.checkNotNull(logger);
        this.loggingLevel = Preconditions.checkNotNull(level);
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl);
        this.maximumBytesToLog = n;
    }

    private static void appendBytes(StringBuilder stringBuilder, int n) {
        if (n == 1) {
            stringBuilder.append("1 byte");
            return;
        }
        stringBuilder.append(NumberFormat.getInstance().format(n));
        stringBuilder.append(" bytes");
    }

    @Override
    public void close() throws IOException {
        synchronized (this) {
            if (this.closed) return;
            if (this.bytesWritten != 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Total: ");
                LoggingByteArrayOutputStream.appendBytes(stringBuilder, this.bytesWritten);
                if (this.count != 0 && this.count < this.bytesWritten) {
                    stringBuilder.append(" (logging first ");
                    LoggingByteArrayOutputStream.appendBytes(stringBuilder, this.count);
                    stringBuilder.append(")");
                }
                this.logger.config(stringBuilder.toString());
                if (this.count != 0) {
                    this.logger.log(this.loggingLevel, this.toString("UTF-8").replaceAll("[\\x00-\\x09\\x0B\\x0C\\x0E-\\x1F\\x7F]", " "));
                }
            }
            this.closed = true;
            return;
        }
    }

    public final int getBytesWritten() {
        synchronized (this) {
            return this.bytesWritten;
        }
    }

    public final int getMaximumBytesToLog() {
        return this.maximumBytesToLog;
    }

    @Override
    public void write(int n) {
        synchronized (this) {
            boolean bl = !this.closed;
            Preconditions.checkArgument(bl);
            ++this.bytesWritten;
            if (this.count >= this.maximumBytesToLog) return;
            super.write(n);
            return;
        }
    }

    @Override
    public void write(byte[] arrby, int n, int n2) {
        synchronized (this) {
            boolean bl = !this.closed;
            Preconditions.checkArgument(bl);
            this.bytesWritten += n2;
            if (this.count >= this.maximumBytesToLog) return;
            int n3 = this.count + n2;
            int n4 = n2;
            if (n3 > this.maximumBytesToLog) {
                n4 = n2 + (this.maximumBytesToLog - n3);
            }
            super.write(arrby, n, n4);
            return;
        }
    }
}

