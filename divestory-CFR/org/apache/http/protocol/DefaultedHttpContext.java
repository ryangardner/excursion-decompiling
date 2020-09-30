/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.protocol;

import org.apache.http.protocol.HttpContext;

public final class DefaultedHttpContext
implements HttpContext {
    private final HttpContext defaults;
    private final HttpContext local;

    public DefaultedHttpContext(HttpContext httpContext, HttpContext httpContext2) {
        if (httpContext == null) throw new IllegalArgumentException("HTTP context may not be null");
        this.local = httpContext;
        this.defaults = httpContext2;
    }

    @Override
    public Object getAttribute(String string2) {
        Object object = this.local.getAttribute(string2);
        if (object != null) return object;
        return this.defaults.getAttribute(string2);
    }

    public HttpContext getDefaults() {
        return this.defaults;
    }

    @Override
    public Object removeAttribute(String string2) {
        return this.local.removeAttribute(string2);
    }

    @Override
    public void setAttribute(String string2, Object object) {
        this.local.setAttribute(string2, object);
    }
}

