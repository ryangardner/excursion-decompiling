/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.util.StreamingContent;
import java.io.IOException;
import java.io.OutputStream;

public interface HttpContent
extends StreamingContent {
    public long getLength() throws IOException;

    public String getType();

    public boolean retrySupported();

    @Override
    public void writeTo(OutputStream var1) throws IOException;
}

