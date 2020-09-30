/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.contrib.http.util;

import io.opencensus.contrib.http.util.CloudTraceFormat;
import io.opencensus.trace.propagation.TextFormat;

public class HttpPropagationUtil {
    private HttpPropagationUtil() {
    }

    public static TextFormat getCloudTraceFormat() {
        return new CloudTraceFormat();
    }
}

