/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import java.util.Collection;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.impl.cookie.NetscapeDraftSpec;
import org.apache.http.params.HttpParams;

public class NetscapeDraftSpecFactory
implements CookieSpecFactory {
    @Override
    public CookieSpec newInstance(HttpParams arrstring) {
        if (arrstring == null) return new NetscapeDraftSpec();
        Object var2_2 = null;
        Collection collection = (Collection)arrstring.getParameter("http.protocol.cookie-datepatterns");
        arrstring = var2_2;
        if (collection == null) return new NetscapeDraftSpec(arrstring);
        arrstring = collection.toArray(new String[collection.size()]);
        return new NetscapeDraftSpec(arrstring);
    }
}
