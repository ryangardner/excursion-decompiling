/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StreamingContent;
import java.io.IOException;
import java.io.OutputStream;

public class ByteArrayStreamingContent
implements StreamingContent {
    private final byte[] byteArray;
    private final int length;
    private final int offset;

    public ByteArrayStreamingContent(byte[] arrby) {
        this(arrby, 0, arrby.length);
    }

    public ByteArrayStreamingContent(byte[] arrby, int n, int n2) {
        this.byteArray = Preconditions.checkNotNull(arrby);
        boolean bl = n >= 0 && n2 >= 0 && n + n2 <= arrby.length;
        Preconditions.checkArgument(bl);
        this.offset = n;
        this.length = n2;
    }

    @Override
    public void writeTo(OutputStream outputStream2) throws IOException {
        outputStream2.write(this.byteArray, this.offset, this.length);
        outputStream2.flush();
    }
}

