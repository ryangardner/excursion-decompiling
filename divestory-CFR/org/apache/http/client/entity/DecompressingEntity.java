/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;

abstract class DecompressingEntity
extends HttpEntityWrapper {
    private static final int BUFFER_SIZE = 2048;
    private InputStream content;

    public DecompressingEntity(HttpEntity httpEntity) {
        super(httpEntity);
    }

    @Override
    public InputStream getContent() throws IOException {
        if (!this.wrappedEntity.isStreaming()) return this.getDecompressingInputStream(this.wrappedEntity.getContent());
        if (this.content != null) return this.content;
        this.content = this.getDecompressingInputStream(this.wrappedEntity.getContent());
        return this.content;
    }

    abstract InputStream getDecompressingInputStream(InputStream var1) throws IOException;

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

