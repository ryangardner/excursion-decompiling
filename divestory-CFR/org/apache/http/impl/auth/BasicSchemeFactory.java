/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.auth;

import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeFactory;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.params.HttpParams;

public class BasicSchemeFactory
implements AuthSchemeFactory {
    @Override
    public AuthScheme newInstance(HttpParams httpParams) {
        return new BasicScheme();
    }
}

