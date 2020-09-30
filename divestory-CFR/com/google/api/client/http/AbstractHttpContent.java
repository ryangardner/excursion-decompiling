/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpMediaType;
import com.google.api.client.util.Charsets;
import com.google.api.client.util.IOUtils;
import java.io.IOException;
import java.nio.charset.Charset;

public abstract class AbstractHttpContent
implements HttpContent {
    private long computedLength;
    private HttpMediaType mediaType;

    protected AbstractHttpContent(HttpMediaType httpMediaType) {
        this.computedLength = -1L;
        this.mediaType = httpMediaType;
    }

    protected AbstractHttpContent(String object) {
        object = object == null ? null : new HttpMediaType((String)object);
        this((HttpMediaType)object);
    }

    public static long computeLength(HttpContent httpContent) throws IOException {
        if (httpContent.retrySupported()) return IOUtils.computeLength(httpContent);
        return -1L;
    }

    protected long computeLength() throws IOException {
        return AbstractHttpContent.computeLength(this);
    }

    protected final Charset getCharset() {
        HttpMediaType httpMediaType = this.mediaType;
        if (httpMediaType == null) return Charsets.ISO_8859_1;
        if (httpMediaType.getCharsetParameter() == null) return Charsets.ISO_8859_1;
        return this.mediaType.getCharsetParameter();
    }

    @Override
    public long getLength() throws IOException {
        if (this.computedLength != -1L) return this.computedLength;
        this.computedLength = this.computeLength();
        return this.computedLength;
    }

    public final HttpMediaType getMediaType() {
        return this.mediaType;
    }

    @Override
    public String getType() {
        HttpMediaType httpMediaType = this.mediaType;
        if (httpMediaType != null) return httpMediaType.build();
        return null;
    }

    @Override
    public boolean retrySupported() {
        return true;
    }

    public AbstractHttpContent setMediaType(HttpMediaType httpMediaType) {
        this.mediaType = httpMediaType;
        return this;
    }
}

