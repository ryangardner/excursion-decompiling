/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.testing.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class TestableByteArrayInputStream
extends ByteArrayInputStream {
    private boolean closed;

    public TestableByteArrayInputStream(byte[] arrby) {
        super(arrby);
    }

    public TestableByteArrayInputStream(byte[] arrby, int n, int n2) {
        super(arrby);
    }

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

