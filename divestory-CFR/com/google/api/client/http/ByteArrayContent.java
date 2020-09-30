/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public final class ByteArrayContent
extends AbstractInputStreamContent {
    private final byte[] byteArray;
    private final int length;
    private final int offset;

    public ByteArrayContent(String string2, byte[] arrby) {
        this(string2, arrby, 0, arrby.length);
    }

    public ByteArrayContent(String string2, byte[] arrby, int n, int n2) {
        super(string2);
        this.byteArray = Preconditions.checkNotNull(arrby);
        boolean bl = n >= 0 && n2 >= 0 && n + n2 <= arrby.length;
        Preconditions.checkArgument(bl, "offset %s, length %s, array length %s", n, n2, arrby.length);
        this.offset = n;
        this.length = n2;
    }

    public static ByteArrayContent fromString(String string2, String string3) {
        return new ByteArrayContent(string2, StringUtils.getBytesUtf8(string3));
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(this.byteArray, this.offset, this.length);
    }

    @Override
    public long getLength() {
        return this.length;
    }

    @Override
    public boolean retrySupported() {
        return true;
    }

    @Override
    public ByteArrayContent setCloseInputStream(boolean bl) {
        return (ByteArrayContent)super.setCloseInputStream(bl);
    }

    @Override
    public ByteArrayContent setType(String string2) {
        return (ByteArrayContent)super.setType(string2);
    }
}

