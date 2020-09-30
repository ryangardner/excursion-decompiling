/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import com.google.api.client.util.LoggingByteArrayOutputStream;
import com.google.api.client.util.LoggingOutputStream;
import com.google.api.client.util.StreamingContent;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class LoggingStreamingContent
implements StreamingContent {
    private final StreamingContent content;
    private final int contentLoggingLimit;
    private final Logger logger;
    private final Level loggingLevel;

    public LoggingStreamingContent(StreamingContent streamingContent, Logger logger, Level level, int n) {
        this.content = streamingContent;
        this.logger = logger;
        this.loggingLevel = level;
        this.contentLoggingLimit = n;
    }

    @Override
    public void writeTo(OutputStream outputStream2) throws IOException {
        LoggingOutputStream loggingOutputStream = new LoggingOutputStream(outputStream2, this.logger, this.loggingLevel, this.contentLoggingLimit);
        try {
            this.content.writeTo(loggingOutputStream);
            outputStream2.flush();
            return;
        }
        finally {
            loggingOutputStream.getLogStream().close();
        }
    }
}

