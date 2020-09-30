/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.common.io.ByteStreams;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

final class ConsumingInputStream
extends FilterInputStream {
    private boolean closed = false;

    ConsumingInputStream(InputStream inputStream2) {
        super(inputStream2);
    }

    @Override
    public void close() throws IOException {
        if (this.closed) return;
        if (this.in == null) return;
        try {
            ByteStreams.exhaust(this);
            this.in.close();
            return;
        }
        finally {
            this.closed = true;
        }
    }
}

