/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.testing.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class TestableByteArrayOutputStream
extends ByteArrayOutputStream {
    private boolean closed;

    @Override
    public void close() throws IOException {
        this.closed = true;
    }

    public final byte[] getBuffer() {
        return this.buf;
    }

    public final boolean isClosed() {
        return this.closed;
    }
}

