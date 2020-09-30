/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.util.StreamingContent;
import java.io.IOException;

public abstract class LowLevelHttpRequest {
    private String contentEncoding;
    private long contentLength = -1L;
    private String contentType;
    private StreamingContent streamingContent;

    public abstract void addHeader(String var1, String var2) throws IOException;

    public abstract LowLevelHttpResponse execute() throws IOException;

    public final String getContentEncoding() {
        return this.contentEncoding;
    }

    public final long getContentLength() {
        return this.contentLength;
    }

    public final String getContentType() {
        return this.contentType;
    }

    public final StreamingContent getStreamingContent() {
        return this.streamingContent;
    }

    public final void setContentEncoding(String string2) throws IOException {
        this.contentEncoding = string2;
    }

    public final void setContentLength(long l) throws IOException {
        this.contentLength = l;
    }

    public final void setContentType(String string2) throws IOException {
        this.contentType = string2;
    }

    public final void setStreamingContent(StreamingContent streamingContent) throws IOException {
        this.streamingContent = streamingContent;
    }

    public void setTimeout(int n, int n2) throws IOException {
    }

    public void setWriteTimeout(int n) throws IOException {
    }
}

