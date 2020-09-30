/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharSink;
import com.google.common.io.Closer;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

public abstract class ByteSink {
    protected ByteSink() {
    }

    public CharSink asCharSink(Charset charset) {
        return new AsCharSink(charset);
    }

    public OutputStream openBufferedStream() throws IOException {
        OutputStream outputStream2 = this.openStream();
        if (!(outputStream2 instanceof BufferedOutputStream)) return new BufferedOutputStream(outputStream2);
        return (BufferedOutputStream)outputStream2;
    }

    public abstract OutputStream openStream() throws IOException;

    public void write(byte[] arrby) throws IOException {
        Preconditions.checkNotNull(arrby);
        Closer closer = Closer.create();
        try {
            OutputStream outputStream2 = closer.register(this.openStream());
            outputStream2.write(arrby);
            outputStream2.flush();
        }
        catch (Throwable throwable) {
            try {
                throw closer.rethrow(throwable);
            }
            catch (Throwable throwable2) {
                closer.close();
                throw throwable2;
            }
        }
        closer.close();
    }

    public long writeFrom(InputStream inputStream2) throws IOException {
        long l;
        Preconditions.checkNotNull(inputStream2);
        Closer closer = Closer.create();
        try {
            OutputStream outputStream2 = closer.register(this.openStream());
            l = ByteStreams.copy(inputStream2, outputStream2);
            outputStream2.flush();
        }
        catch (Throwable throwable) {
            try {
                throw closer.rethrow(throwable);
            }
            catch (Throwable throwable2) {
                closer.close();
                throw throwable2;
            }
        }
        closer.close();
        return l;
    }

    private final class AsCharSink
    extends CharSink {
        private final Charset charset;

        private AsCharSink(Charset charset) {
            this.charset = Preconditions.checkNotNull(charset);
        }

        @Override
        public Writer openStream() throws IOException {
            return new OutputStreamWriter(ByteSink.this.openStream(), this.charset);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(ByteSink.this.toString());
            stringBuilder.append(".asCharSink(");
            stringBuilder.append(this.charset);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

}

