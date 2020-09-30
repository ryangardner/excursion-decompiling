/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.params;

import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;

public final class HttpProtocolParams
implements CoreProtocolPNames {
    private HttpProtocolParams() {
    }

    public static String getContentCharset(HttpParams object) {
        if (object == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        String string2 = (String)object.getParameter("http.protocol.content-charset");
        object = string2;
        if (string2 != null) return object;
        return "ISO-8859-1";
    }

    public static String getHttpElementCharset(HttpParams object) {
        if (object == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        String string2 = (String)object.getParameter("http.protocol.element-charset");
        object = string2;
        if (string2 != null) return object;
        return "US-ASCII";
    }

    public static String getUserAgent(HttpParams httpParams) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        return (String)httpParams.getParameter("http.useragent");
    }

    public static ProtocolVersion getVersion(HttpParams object) {
        if (object == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        if ((object = object.getParameter("http.protocol.version")) != null) return (ProtocolVersion)object;
        return HttpVersion.HTTP_1_1;
    }

    public static void setContentCharset(HttpParams httpParams, String string2) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        httpParams.setParameter("http.protocol.content-charset", string2);
    }

    public static void setHttpElementCharset(HttpParams httpParams, String string2) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        httpParams.setParameter("http.protocol.element-charset", string2);
    }

    public static void setUseExpectContinue(HttpParams httpParams, boolean bl) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        httpParams.setBooleanParameter("http.protocol.expect-continue", bl);
    }

    public static void setUserAgent(HttpParams httpParams, String string2) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        httpParams.setParameter("http.useragent", string2);
    }

    public static void setVersion(HttpParams httpParams, ProtocolVersion protocolVersion) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        httpParams.setParameter("http.protocol.version", protocolVersion);
    }

    public static boolean useExpectContinue(HttpParams httpParams) {
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        return httpParams.getBooleanParameter("http.protocol.expect-continue", false);
    }
}

