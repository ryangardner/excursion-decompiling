/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http;

import java.io.IOException;
import org.apache.http.HttpConnectionMetrics;

public interface HttpConnection {
    public void close() throws IOException;

    public HttpConnectionMetrics getMetrics();

    public int getSocketTimeout();

    public boolean isOpen();

    public boolean isStale();

    public void setSocketTimeout(int var1);

    public void shutdown() throws IOException;
}

