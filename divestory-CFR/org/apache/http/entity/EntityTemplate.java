/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentProducer;

public class EntityTemplate
extends AbstractHttpEntity {
    private final ContentProducer contentproducer;

    public EntityTemplate(ContentProducer contentProducer) {
        if (contentProducer == null) throw new IllegalArgumentException("Content producer may not be null");
        this.contentproducer = contentProducer;
    }

    @Override
    public InputStream getContent() {
        throw new UnsupportedOperationException("Entity template does not implement getContent()");
    }

    @Override
    public long getContentLength() {
        return -1L;
    }

    @Override
    public boolean isRepeatable() {
        return true;
    }

    @Override
    public boolean isStreaming() {
        return false;
    }

    @Override
    public void writeTo(OutputStream outputStream2) throws IOException {
        if (outputStream2 == null) throw new IllegalArgumentException("Output stream may not be null");
        this.contentproducer.writeTo(outputStream2);
    }
}

