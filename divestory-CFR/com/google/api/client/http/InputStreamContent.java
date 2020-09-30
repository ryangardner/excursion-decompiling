/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.util.Preconditions;
import java.io.InputStream;

public final class InputStreamContent
extends AbstractInputStreamContent {
    private final InputStream inputStream;
    private long length = -1L;
    private boolean retrySupported;

    public InputStreamContent(String string2, InputStream inputStream2) {
        super(string2);
        this.inputStream = Preconditions.checkNotNull(inputStream2);
    }

    @Override
    public InputStream getInputStream() {
        return this.inputStream;
    }

    @Override
    public long getLength() {
        return this.length;
    }

    @Override
    public boolean retrySupported() {
        return this.retrySupported;
    }

    @Override
    public InputStreamContent setCloseInputStream(boolean bl) {
        return (InputStreamContent)super.setCloseInputStream(bl);
    }

    public InputStreamContent setLength(long l) {
        this.length = l;
        return this;
    }

    public InputStreamContent setRetrySupported(boolean bl) {
        this.retrySupported = bl;
        return this;
    }

    @Override
    public InputStreamContent setType(String string2) {
        return (InputStreamContent)super.setType(string2);
    }
}

