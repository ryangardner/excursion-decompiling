/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.client.AuthCache;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.protocol.HttpContext;

public class ResponseAuthCache
implements HttpResponseInterceptor {
    private final Log log = LogFactory.getLog(this.getClass());

    private void cache(AuthCache authCache, HttpHost httpHost, AuthState object) {
        AuthScheme authScheme = ((AuthState)object).getAuthScheme();
        if (((AuthState)object).getAuthScope() == null) return;
        if (((AuthState)object).getCredentials() == null) {
            authCache.remove(httpHost);
            return;
        }
        if (this.log.isDebugEnabled()) {
            Log log = this.log;
            object = new StringBuilder();
            ((StringBuilder)object).append("Caching '");
            ((StringBuilder)object).append(authScheme.getSchemeName());
            ((StringBuilder)object).append("' auth scheme for ");
            ((StringBuilder)object).append(httpHost);
            log.debug((Object)((StringBuilder)object).toString());
        }
        authCache.put(httpHost, authScheme);
    }

    private boolean isCachable(AuthState object) {
        boolean bl;
        object = ((AuthState)object).getAuthScheme();
        boolean bl2 = bl = false;
        if (object == null) return bl2;
        if (!object.isComplete()) {
            return bl;
        }
        if (((String)(object = object.getSchemeName())).equalsIgnoreCase("Basic")) return true;
        bl2 = bl;
        if (!((String)object).equalsIgnoreCase("Digest")) return bl2;
        return true;
    }

    @Override
    public void process(HttpResponse object, HttpContext httpContext) throws HttpException, IOException {
        if (object == null) throw new IllegalArgumentException("HTTP request may not be null");
        if (httpContext == null) throw new IllegalArgumentException("HTTP context may not be null");
        Object object2 = (AuthCache)httpContext.getAttribute("http.auth.auth-cache");
        HttpHost httpHost = (HttpHost)httpContext.getAttribute("http.target_host");
        AuthState authState = (AuthState)httpContext.getAttribute("http.auth.target-scope");
        object = object2;
        if (httpHost != null) {
            object = object2;
            if (authState != null) {
                object = object2;
                if (this.isCachable(authState)) {
                    object = object2;
                    if (object2 == null) {
                        object = new BasicAuthCache();
                        httpContext.setAttribute("http.auth.auth-cache", object);
                    }
                    this.cache((AuthCache)object, httpHost, authState);
                }
            }
        }
        httpHost = (HttpHost)httpContext.getAttribute("http.proxy_host");
        authState = (AuthState)httpContext.getAttribute("http.auth.proxy-scope");
        if (httpHost == null) return;
        if (authState == null) return;
        if (!this.isCachable(authState)) return;
        object2 = object;
        if (object == null) {
            object2 = new BasicAuthCache();
            httpContext.setAttribute("http.auth.auth-cache", object2);
        }
        this.cache((AuthCache)object2, httpHost, authState);
    }
}

