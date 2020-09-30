/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http;

public interface HttpConnectionMetrics {
    public Object getMetric(String var1);

    public long getReceivedBytesCount();

    public long getRequestCount();

    public long getResponseCount();

    public long getSentBytesCount();

    public void reset();
}

