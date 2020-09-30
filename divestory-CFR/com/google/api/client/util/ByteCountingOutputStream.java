/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import java.io.IOException;
import java.io.OutputStream;

final class ByteCountingOutputStream
extends OutputStream {
    long count;

    ByteCountingOutputStream() {
    }

    @Override
    public void write(int n) throws IOException {
        ++this.count;
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        this.count += (long)n2;
    }
}

