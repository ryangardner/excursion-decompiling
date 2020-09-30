/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client.params;

import org.apache.http.params.HttpParams;

public class HttpClientParams {
    private HttpClientParams() {
    }

    public static String getCookiePolicy(HttpParams object) {
        if (object == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        String string2 = (String)object.getParameter("http.protocol.cookie-policy");
        object = string2;
        if (string2 != null) return object;
        return "best-match";
    }

    public static boolean isAuthenticating(HttpParams httpParams) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        return httpParams.getBooleanParameter("http.protocol.handle-authentication", true);
    }

    public static boolean isRedirecting(HttpParams httpParams) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        return httpParams.getBooleanParameter("http.protocol.handle-redirects", true);
    }

    public static void setAuthenticating(HttpParams httpParams, boolean bl) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        httpParams.setBooleanParameter("http.protocol.handle-authentication", bl);
    }

    public static void setCookiePolicy(HttpParams httpParams, String string2) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        httpParams.setParameter("http.protocol.cookie-policy", string2);
    }

    public static void setRedirecting(HttpParams httpParams, boolean bl) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        httpParams.setBooleanParameter("http.protocol.handle-redirects", bl);
    }
}

