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
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.protocol.HttpContext;

public class RequestAuthCache
implements HttpRequestInterceptor {
    private final Log log = LogFactory.getLog(this.getClass());

    private void doPreemptiveAuth(HttpHost object, AuthScheme authScheme, AuthState authState, CredentialsProvider credentialsProvider) {
        String string2 = authScheme.getSchemeName();
        if (this.log.isDebugEnabled()) {
            Log log = this.log;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Re-using cached '");
            stringBuilder.append(string2);
            stringBuilder.append("' auth scheme for ");
            stringBuilder.append(object);
            log.debug((Object)stringBuilder.toString());
        }
        if ((object = credentialsProvider.getCredentials(new AuthScope(((HttpHost)object).getHostName(), ((HttpHost)object).getPort(), AuthScope.ANY_REALM, string2))) != null) {
            authState.setAuthScheme(authScheme);
            authState.setCredentials((Credentials)object);
            return;
        }
        this.log.debug((Object)"No credentials for preemptive authentication");
    }

    @Override
    public void process(HttpRequest object, HttpContext object2) throws HttpException, IOException {
        AuthScheme authScheme;
        if (object == null) throw new IllegalArgumentException("HTTP request may not be null");
        if (object2 == null) throw new IllegalArgumentException("HTTP context may not be null");
        Object object3 = (AuthCache)object2.getAttribute("http.auth.auth-cache");
        if (object3 == null) {
            this.log.debug((Object)"Auth cache not set in the context");
            return;
        }
        object = (CredentialsProvider)object2.getAttribute("http.auth.credentials-provider");
        if (object == null) {
            this.log.debug((Object)"Credentials provider not set in the context");
            return;
        }
        HttpHost httpHost = (HttpHost)object2.getAttribute("http.target_host");
        Object object4 = (AuthState)object2.getAttribute("http.auth.target-scope");
        if (httpHost != null && object4 != null && ((AuthState)object4).getAuthScheme() == null && (authScheme = object3.get(httpHost)) != null) {
            this.doPreemptiveAuth(httpHost, authScheme, (AuthState)object4, (CredentialsProvider)object);
        }
        object4 = (HttpHost)object2.getAttribute("http.proxy_host");
        object2 = (AuthState)object2.getAttribute("http.auth.proxy-scope");
        if (object4 == null) return;
        if (object2 == null) return;
        if (((AuthState)object2).getAuthScheme() != null) return;
        if ((object3 = object3.get((HttpHost)object4)) == null) return;
        this.doPreemptiveAuth((HttpHost)object4, (AuthScheme)object3, (AuthState)object2, (CredentialsProvider)object);
    }
}

