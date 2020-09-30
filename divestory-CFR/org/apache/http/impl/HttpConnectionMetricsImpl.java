/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl;

import java.io.Serializable;
import java.util.HashMap;
import org.apache.http.HttpConnectionMetrics;
import org.apache.http.io.HttpTransportMetrics;

public class HttpConnectionMetricsImpl
implements HttpConnectionMetrics {
    public static final String RECEIVED_BYTES_COUNT = "http.received-bytes-count";
    public static final String REQUEST_COUNT = "http.request-count";
    public static final String RESPONSE_COUNT = "http.response-count";
    public static final String SENT_BYTES_COUNT = "http.sent-bytes-count";
    private final HttpTransportMetrics inTransportMetric;
    private HashMap metricsCache;
    private final HttpTransportMetrics outTransportMetric;
    private long requestCount = 0L;
    private long responseCount = 0L;

    public HttpConnectionMetricsImpl(HttpTransportMetrics httpTransportMetrics, HttpTransportMetrics httpTransportMetrics2) {
        this.inTransportMetric = httpTransportMetrics;
        this.outTransportMetric = httpTransportMetrics2;
    }

    @Override
    public Object getMetric(String string2) {
        Serializable serializable = this.metricsCache;
        Object var3_3 = serializable != null ? serializable.get(string2) : null;
        serializable = var3_3;
        if (var3_3 != null) return serializable;
        if (REQUEST_COUNT.equals(string2)) {
            return new Long(this.requestCount);
        }
        if (RESPONSE_COUNT.equals(string2)) {
            return new Long(this.responseCount);
        }
        if (RECEIVED_BYTES_COUNT.equals(string2)) {
            if (this.inTransportMetric == null) return null;
            return new Long(this.inTransportMetric.getBytesTransferred());
        }
        serializable = var3_3;
        if (!SENT_BYTES_COUNT.equals(string2)) return serializable;
        if (this.outTransportMetric == null) return null;
        return new Long(this.outTransportMetric.getBytesTransferred());
    }

    @Override
    public long getReceivedBytesCount() {
        HttpTransportMetrics httpTransportMetrics = this.inTransportMetric;
        if (httpTransportMetrics == null) return -1L;
        return httpTransportMetrics.getBytesTransferred();
    }

    @Override
    public long getRequestCount() {
        return this.requestCount;
    }

    @Override
    public long getResponseCount() {
        return this.responseCount;
    }

    @Override
    public long getSentBytesCount() {
        HttpTransportMetrics httpTransportMetrics = this.outTransportMetric;
        if (httpTransportMetrics == null) return -1L;
        return httpTransportMetrics.getBytesTransferred();
    }

    public void incrementRequestCount() {
        ++this.requestCount;
    }

    public void incrementResponseCount() {
        ++this.responseCount;
    }

    @Override
    public void reset() {
        HttpTransportMetrics httpTransportMetrics = this.outTransportMetric;
        if (httpTransportMetrics != null) {
            httpTransportMetrics.reset();
        }
        if ((httpTransportMetrics = this.inTransportMetric) != null) {
            httpTransportMetrics.reset();
        }
        this.requestCount = 0L;
        this.responseCount = 0L;
        this.metricsCache = null;
    }

    public void setMetric(String string2, Object object) {
        if (this.metricsCache == null) {
            this.metricsCache = new HashMap();
        }
        this.metricsCache.put(string2, object);
    }
}

