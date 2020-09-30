/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.Header;

public interface HttpEntity {
    public void consumeContent() throws IOException;

    public InputStream getContent() throws IOException, IllegalStateException;

    public Header getContentEncoding();

    public long getContentLength();

    public Header getContentType();

    public boolean isChunked();

    public boolean isRepeatable();

    public boolean isStreaming();

    public void writeTo(OutputStream var1) throws IOException;
}

