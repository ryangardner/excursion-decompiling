/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.protocol;

import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class SyncBasicHttpContext
extends BasicHttpContext {
    public SyncBasicHttpContext(HttpContext httpContext) {
        super(httpContext);
    }

    @Override
    public Object getAttribute(String object) {
        synchronized (this) {
            return super.getAttribute((String)object);
        }
    }

    @Override
    public Object removeAttribute(String object) {
        synchronized (this) {
            return super.removeAttribute((String)object);
        }
    }

    @Override
    public void setAttribute(String string2, Object object) {
        synchronized (this) {
            super.setAttribute(string2, object);
            return;
        }
    }
}

