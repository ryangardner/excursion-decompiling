/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import java.util.Collection;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.impl.cookie.RFC2965Spec;
import org.apache.http.params.HttpParams;

public class RFC2965SpecFactory
implements CookieSpecFactory {
    @Override
    public CookieSpec newInstance(HttpParams httpParams) {
        if (httpParams == null) return new RFC2965Spec();
        String[] arrstring = null;
        Collection collection = (Collection)httpParams.getParameter("http.protocol.cookie-datepatterns");
        if (collection == null) return new RFC2965Spec(arrstring, httpParams.getBooleanParameter("http.protocol.single-cookie-header", false));
        arrstring = collection.toArray(new String[collection.size()]);
        return new RFC2965Spec(arrstring, httpParams.getBooleanParameter("http.protocol.single-cookie-header", false));
    }
}

