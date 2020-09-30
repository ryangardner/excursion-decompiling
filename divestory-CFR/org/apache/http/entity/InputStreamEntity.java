/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.entity.AbstractHttpEntity;

public class InputStreamEntity
extends AbstractHttpEntity {
    private static final int BUFFER_SIZE = 2048;
    private final InputStream content;
    private final long length;

    public InputStreamEntity(InputStream inputStream2, long l) {
        if (inputStream2 == null) throw new IllegalArgumentException("Source input stream may not be null");
        this.content = inputStream2;
        this.length = l;
    }

    @Override
    public void consumeContent() throws IOException {
        this.content.close();
    }

    @Override
    public InputStream getContent() throws IOException {
        return this.content;
    }

    @Override
    public long getContentLength() {
        return this.length;
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }

    @Override
    public boolean isStreaming() {
        return true;
    }

    @Override
    public void writeTo(OutputStream outputStream2) throws IOException {
        if (outputStream2 == null) throw new IllegalArgumentException("Output stream may not be null");
        InputStream inputStream2 = this.content;
        try {
            byte[] arrby = new byte[2048];
            if (this.length < 0L) {
                int n;
                while ((n = inputStream2.read(arrby)) != -1) {
                    outputStream2.write(arrby, 0, n);
                }
                return;
            }
            long l = this.length;
            while (l > 0L) {
                int n = inputStream2.read(arrby, 0, (int)Math.min(2048L, l));
                if (n == -1) {
                    return;
                }
                outputStream2.write(arrby, 0, n);
                l -= (long)n;
            }
            return;
        }
        finally {
            inputStream2.close();
        }
    }
}

