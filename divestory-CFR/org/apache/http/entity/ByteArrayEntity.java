/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.entity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.entity.AbstractHttpEntity;

public class ByteArrayEntity
extends AbstractHttpEntity
implements Cloneable {
    protected final byte[] content;

    public ByteArrayEntity(byte[] arrby) {
        if (arrby == null) throw new IllegalArgumentException("Source byte array may not be null");
        this.content = arrby;
    }

    public Object clone() throws CloneNotSupportedException {
        return Object.super.clone();
    }

    @Override
    public InputStream getContent() {
        return new ByteArrayInputStream(this.content);
    }

    @Override
    public long getContentLength() {
        return this.content.length;
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
        outputStream2.write(this.content);
        outputStream2.flush();
    }
}

