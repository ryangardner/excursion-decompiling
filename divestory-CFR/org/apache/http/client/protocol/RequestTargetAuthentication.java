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
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.RequestLine;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.ContextAwareAuthScheme;
import org.apache.http.auth.Credentials;
import org.apache.http.protocol.HttpContext;

public class RequestTargetAuthentication
implements HttpRequestInterceptor {
    private final Log log = LogFactory.getLog(this.getClass());

    @Override
    public void process(HttpRequest httpRequest, HttpContext object) throws HttpException, IOException {
        if (httpRequest == null) throw new IllegalArgumentException("HTTP request may not be null");
        if (object == null) throw new IllegalArgumentException("HTTP context may not be null");
        if (httpRequest.getRequestLine().getMethod().equalsIgnoreCase("CONNECT")) {
            return;
        }
        if (httpRequest.containsHeader("Authorization")) {
            return;
        }
        AuthState authState = (AuthState)object.getAttribute("http.auth.target-scope");
        if (authState == null) {
            this.log.debug((Object)"Target auth state not set in the context");
            return;
        }
        Object object2 = authState.getAuthScheme();
        if (object2 == null) {
            return;
        }
        Credentials credentials = authState.getCredentials();
        if (credentials == null) {
            this.log.debug((Object)"User credentials not available");
            return;
        }
        if (authState.getAuthScope() == null) {
            if (object2.isConnectionBased()) return;
        }
        try {
            object = object2 instanceof ContextAwareAuthScheme ? ((ContextAwareAuthScheme)object2).authenticate(credentials, httpRequest, (HttpContext)object) : object2.authenticate(credentials, httpRequest);
            httpRequest.addHeader((Header)object);
            return;
        }
        catch (AuthenticationException authenticationException) {
            if (!this.log.isErrorEnabled()) return;
            object = this.log;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Authentication error: ");
            ((StringBuilder)object2).append(authenticationException.getMessage());
            object.error((Object)((StringBuilder)object2).toString());
        }
    }
}

