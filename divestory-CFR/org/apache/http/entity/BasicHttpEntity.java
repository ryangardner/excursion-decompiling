/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.entity.AbstractHttpEntity;

public class BasicHttpEntity
extends AbstractHttpEntity {
    private InputStream content;
    private long length = -1L;

    @Override
    public void consumeContent() throws IOException {
        InputStream inputStream2 = this.content;
        if (inputStream2 == null) return;
        inputStream2.close();
    }

    @Override
    public InputStream getContent() throws IllegalStateException {
        InputStream inputStream2 = this.content;
        if (inputStream2 == null) throw new IllegalStateException("Content has not been provided");
        return inputStream2;
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
        if (this.content == null) return false;
        return true;
    }

    public void setContent(InputStream inputStream2) {
        this.content = inputStream2;
    }

    public void setContentLength(long l) {
        this.length = l;
    }

    @Override
    public void writeTo(OutputStream outputStream2) throws IOException {
        if (outputStream2 == null) throw new IllegalArgumentException("Output stream may not be null");
        InputStream inputStream2 = this.getContent();
        try {
            int n;
            byte[] arrby = new byte[2048];
            while ((n = inputStream2.read(arrby)) != -1) {
                outputStream2.write(arrby, 0, n);
            }
            return;
        }
        finally {
            inputStream2.close();
        }
    }
}

