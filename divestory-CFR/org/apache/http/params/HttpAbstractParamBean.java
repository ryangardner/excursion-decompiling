/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.params;

import org.apache.http.params.HttpParams;

public abstract class HttpAbstractParamBean {
    protected final HttpParams params;

    public HttpAbstractParamBean(HttpParams httpParams) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        this.params = httpParams;
    }
}

