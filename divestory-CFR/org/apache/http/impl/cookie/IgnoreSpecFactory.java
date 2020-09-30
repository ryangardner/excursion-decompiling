/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.impl.cookie.IgnoreSpec;
import org.apache.http.params.HttpParams;

public class IgnoreSpecFactory
implements CookieSpecFactory {
    @Override
    public CookieSpec newInstance(HttpParams httpParams) {
        return new IgnoreSpec();
    }
}

