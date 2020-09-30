/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieSpec;

public abstract class AbstractCookieSpec
implements CookieSpec {
    private final Map<String, CookieAttributeHandler> attribHandlerMap = new HashMap<String, CookieAttributeHandler>(10);

    protected CookieAttributeHandler findAttribHandler(String string2) {
        return this.attribHandlerMap.get(string2);
    }

    protected CookieAttributeHandler getAttribHandler(String string2) {
        Object object = this.findAttribHandler(string2);
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Handler not registered for ");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" attribute.");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    protected Collection<CookieAttributeHandler> getAttribHandlers() {
        return this.attribHandlerMap.values();
    }

    public void registerAttribHandler(String string2, CookieAttributeHandler cookieAttributeHandler) {
        if (string2 == null) throw new IllegalArgumentException("Attribute name may not be null");
        if (cookieAttributeHandler == null) throw new IllegalArgumentException("Attribute handler may not be null");
        this.attribHandlerMap.put(string2, cookieAttributeHandler);
    }
}

