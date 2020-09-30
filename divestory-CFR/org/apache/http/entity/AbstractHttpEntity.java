/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.entity;

import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;

public abstract class AbstractHttpEntity
implements HttpEntity {
    protected boolean chunked;
    protected Header contentEncoding;
    protected Header contentType;

    protected AbstractHttpEntity() {
    }

    @Override
    public void consumeContent() throws IOException {
    }

    @Override
    public Header getContentEncoding() {
        return this.contentEncoding;
    }

    @Override
    public Header getContentType() {
        return this.contentType;
    }

    @Override
    public boolean isChunked() {
        return this.chunked;
    }

    public void setChunked(boolean bl) {
        this.chunked = bl;
    }

    public void setContentEncoding(String object) {
        object = object != null ? new BasicHeader("Content-Encoding", (String)object) : null;
        this.setContentEncoding((Header)object);
    }

    public void setContentEncoding(Header header) {
        this.contentEncoding = header;
    }

    public void setContentType(String object) {
        object = object != null ? new BasicHeader("Content-Type", (String)object) : null;
        this.setContentType((Header)object);
    }

    public void setContentType(Header header) {
        this.contentType = header;
    }
}

