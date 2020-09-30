/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.protocol;

import java.util.HashMap;
import java.util.Map;
import org.apache.http.protocol.HttpContext;

public class BasicHttpContext
implements HttpContext {
    private Map map = null;
    private final HttpContext parentContext;

    public BasicHttpContext() {
        this(null);
    }

    public BasicHttpContext(HttpContext httpContext) {
        this.parentContext = httpContext;
    }

    @Override
    public Object getAttribute(String string2) {
        if (string2 == null) throw new IllegalArgumentException("Id may not be null");
        Object var2_2 = null;
        Object object = this.map;
        if (object != null) {
            var2_2 = object.get(string2);
        }
        object = var2_2;
        if (var2_2 != null) return object;
        HttpContext httpContext = this.parentContext;
        object = var2_2;
        if (httpContext == null) return object;
        return httpContext.getAttribute(string2);
    }

    @Override
    public Object removeAttribute(String string2) {
        if (string2 == null) throw new IllegalArgumentException("Id may not be null");
        Map map = this.map;
        if (map == null) return null;
        return map.remove(string2);
    }

    @Override
    public void setAttribute(String string2, Object object) {
        if (string2 == null) throw new IllegalArgumentException("Id may not be null");
        if (this.map == null) {
            this.map = new HashMap();
        }
        this.map.put(string2, object);
    }
}

