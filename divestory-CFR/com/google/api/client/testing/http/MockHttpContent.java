/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.testing.http;

import com.google.api.client.http.HttpContent;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.io.OutputStream;

public class MockHttpContent
implements HttpContent {
    private byte[] content = new byte[0];
    private long length = -1L;
    private String type;

    public final byte[] getContent() {
        return this.content;
    }

    @Override
    public long getLength() throws IOException {
        return this.length;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public boolean retrySupported() {
        return true;
    }

    public MockHttpContent setContent(byte[] arrby) {
        this.content = Preconditions.checkNotNull(arrby);
        return this;
    }

    public MockHttpContent setLength(long l) {
        boolean bl = l >= -1L;
        Preconditions.checkArgument(bl);
        this.length = l;
        return this;
    }

    public MockHttpContent setType(String string2) {
        this.type = string2;
        return this;
    }

    @Override
    public void writeTo(OutputStream outputStream2) throws IOException {
        outputStream2.write(this.content);
        outputStream2.flush();
    }
}

