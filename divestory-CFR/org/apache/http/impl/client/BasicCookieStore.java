/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieIdentityComparator;

public class BasicCookieStore
implements CookieStore,
Serializable {
    private static final long serialVersionUID = -7581093305228232025L;
    private final TreeSet<Cookie> cookies = new TreeSet<Cookie>(new CookieIdentityComparator());

    @Override
    public void addCookie(Cookie cookie) {
        synchronized (this) {
            if (cookie == null) return;
            this.cookies.remove(cookie);
            Date date = new Date();
            if (cookie.isExpired(date)) return;
            this.cookies.add(cookie);
            return;
        }
    }

    public void addCookies(Cookie[] arrcookie) {
        synchronized (this) {
            if (arrcookie == null) return;
            int n = arrcookie.length;
            int n2 = 0;
            while (n2 < n) {
                this.addCookie(arrcookie[n2]);
                ++n2;
            }
            return;
        }
    }

    @Override
    public void clear() {
        synchronized (this) {
            this.cookies.clear();
            return;
        }
    }

    @Override
    public boolean clearExpired(Date date) {
        synchronized (this) {
            boolean bl = false;
            if (date == null) {
                return false;
            }
            Iterator<Cookie> iterator2 = this.cookies.iterator();
            while (iterator2.hasNext()) {
                if (!iterator2.next().isExpired(date)) continue;
                iterator2.remove();
                bl = true;
            }
            return bl;
        }
    }

    @Override
    public List<Cookie> getCookies() {
        synchronized (this) {
            return new ArrayList<Cookie>(this.cookies);
        }
    }

    public String toString() {
        synchronized (this) {
            return this.cookies.toString();
        }
    }
}

