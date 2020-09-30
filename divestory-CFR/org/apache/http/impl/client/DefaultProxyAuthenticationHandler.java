/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.client;

import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.impl.client.AbstractAuthenticationHandler;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public class DefaultProxyAuthenticationHandler
extends AbstractAuthenticationHandler {
    @Override
    protected List<String> getAuthPreferences(HttpResponse httpResponse, HttpContext httpContext) {
        List list = (List)httpResponse.getParams().getParameter("http.auth.proxy-scheme-pref");
        if (list == null) return super.getAuthPreferences(httpResponse, httpContext);
        return list;
    }

    @Override
    public Map<String, Header> getChallenges(HttpResponse httpResponse, HttpContext httpContext) throws MalformedChallengeException {
        if (httpResponse == null) throw new IllegalArgumentException("HTTP response may not be null");
        return this.parseChallenges(httpResponse.getHeaders("Proxy-Authenticate"));
    }

    @Override
    public boolean isAuthenticationRequested(HttpResponse httpResponse, HttpContext httpContext) {
        if (httpResponse == null) throw new IllegalArgumentException("HTTP response may not be null");
        if (httpResponse.getStatusLine().getStatusCode() != 407) return false;
        return true;
    }
}

