/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.auth;

import org.apache.http.auth.AuthScheme;
import org.apache.http.params.HttpParams;

public interface AuthSchemeFactory {
    public AuthScheme newInstance(HttpParams var1);
}

