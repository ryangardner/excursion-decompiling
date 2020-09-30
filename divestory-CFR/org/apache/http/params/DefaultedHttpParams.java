/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.params;

import org.apache.http.params.AbstractHttpParams;
import org.apache.http.params.HttpParams;

public final class DefaultedHttpParams
extends AbstractHttpParams {
    private final HttpParams defaults;
    private final HttpParams local;

    public DefaultedHttpParams(HttpParams httpParams, HttpParams httpParams2) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        this.local = httpParams;
        this.defaults = httpParams2;
    }

    @Override
    public HttpParams copy() {
        return new DefaultedHttpParams(this.local.copy(), this.defaults);
    }

    public HttpParams getDefaults() {
        return this.defaults;
    }

    @Override
    public Object getParameter(String string2) {
        Object object;
        Object object2 = object = this.local.getParameter(string2);
        if (object != null) return object2;
        HttpParams httpParams = this.defaults;
        object2 = object;
        if (httpParams == null) return object2;
        return httpParams.getParameter(string2);
    }

    @Override
    public boolean removeParameter(String string2) {
        return this.local.removeParameter(string2);
    }

    @Override
    public HttpParams setParameter(String string2, Object object) {
        return this.local.setParameter(string2, object);
    }
}

