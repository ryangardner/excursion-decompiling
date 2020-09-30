/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.auth.params;

import org.apache.http.params.HttpParams;

public final class AuthParams {
    private AuthParams() {
    }

    public static String getCredentialCharset(HttpParams object) {
        if (object == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        String string2 = (String)object.getParameter("http.auth.credential-charset");
        object = string2;
        if (string2 != null) return object;
        return "US-ASCII";
    }

    public static void setCredentialCharset(HttpParams httpParams, String string2) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        httpParams.setParameter("http.auth.credential-charset", string2);
    }
}

