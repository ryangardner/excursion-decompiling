/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.entity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.util.EntityUtils;

public class BufferedHttpEntity
extends HttpEntityWrapper {
    private final byte[] buffer;

    public BufferedHttpEntity(HttpEntity httpEntity) throws IOException {
        super(httpEntity);
        if (httpEntity.isRepeatable() && httpEntity.getContentLength() >= 0L) {
            this.buffer = null;
            return;
        }
        this.buffer = EntityUtils.toByteArray(httpEntity);
    }

    @Override
    public InputStream getContent() throws IOException {
        if (this.buffer == null) return this.wrappedEntity.getContent();
        return new ByteArrayInputStream(this.buffer);
    }

    @Override
    public long getContentLength() {
        byte[] arrby = this.buffer;
        if (arrby == null) return this.wrappedEntity.getContentLength();
        return arrby.length;
    }

    @Override
    public boolean isChunked() {
        if (this.buffer != null) return false;
        if (!this.wrappedEntity.isChunked()) return false;
        return true;
    }

    @Override
    public boolean isRepeatable() {
        return true;
    }

    @Override
    public boolean isStreaming() {
        if (this.buffer != null) return false;
        if (!this.wrappedEntity.isStreaming()) return false;
        return true;
    }

    @Override
    public void writeTo(OutputStream outputStream2) throws IOException {
        if (outputStream2 == null) throw new IllegalArgumentException("Output stream may not be null");
        byte[] arrby = this.buffer;
        if (arrby != null) {
            outputStream2.write(arrby);
            return;
        }
        this.wrappedEntity.writeTo(outputStream2);
    }
}

