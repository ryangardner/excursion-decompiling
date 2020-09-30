/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.cookie;

import java.io.Serializable;
import java.util.Comparator;
import org.apache.http.cookie.Cookie;

public class CookiePathComparator
implements Serializable,
Comparator<Cookie> {
    private static final long serialVersionUID = 7523645369616405818L;

    private String normalizePath(Cookie object) {
        Object object2;
        object = object2 = object.getPath();
        if (object2 == null) {
            object = "/";
        }
        object2 = object;
        if (((String)object).endsWith("/")) return object2;
        object2 = new StringBuilder();
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append('/');
        return ((StringBuilder)object2).toString();
    }

    @Override
    public int compare(Cookie object, Cookie object2) {
        if (((String)(object = this.normalizePath((Cookie)object))).equals(object2 = this.normalizePath((Cookie)object2))) {
            return 0;
        }
        if (((String)object).startsWith((String)object2)) {
            return -1;
        }
        if (!((String)object2).startsWith((String)object)) return 0;
        return 1;
    }
}

