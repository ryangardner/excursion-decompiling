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
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.ContextAwareAuthScheme;
import org.apache.http.auth.Credentials;
import org.apache.http.conn.HttpRoutedConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.protocol.HttpContext;

public class RequestProxyAuthentication
implements HttpRequestInterceptor {
    private final Log log = LogFactory.getLog(this.getClass());

    @Override
    public void process(HttpRequest httpRequest, HttpContext object) throws HttpException, IOException {
        if (httpRequest == null) throw new IllegalArgumentException("HTTP request may not be null");
        if (object == null) throw new IllegalArgumentException("HTTP context may not be null");
        if (httpRequest.containsHeader("Proxy-Authorization")) {
            return;
        }
        Object object2 = (HttpRoutedConnection)object.getAttribute("http.connection");
        if (object2 == null) {
            this.log.debug((Object)"HTTP connection not set in the context");
            return;
        }
        if (object2.getRoute().isTunnelled()) {
            return;
        }
        object2 = (AuthState)object.getAttribute("http.auth.proxy-scope");
        if (object2 == null) {
            this.log.debug((Object)"Proxy auth state not set in the context");
            return;
        }
        AuthScheme authScheme = ((AuthState)object2).getAuthScheme();
        if (authScheme == null) {
            return;
        }
        Credentials credentials = ((AuthState)object2).getCredentials();
        if (credentials == null) {
            this.log.debug((Object)"User credentials not available");
            return;
        }
        if (((AuthState)object2).getAuthScope() == null) {
            if (authScheme.isConnectionBased()) return;
        }
        try {
            object = authScheme instanceof ContextAwareAuthScheme ? ((ContextAwareAuthScheme)authScheme).authenticate(credentials, httpRequest, (HttpContext)object) : authScheme.authenticate(credentials, httpRequest);
            httpRequest.addHeader((Header)object);
            return;
        }
        catch (AuthenticationException authenticationException) {
            if (!this.log.isErrorEnabled()) return;
            object = this.log;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Proxy authentication error: ");
            ((StringBuilder)object2).append(authenticationException.getMessage());
            object.error((Object)((StringBuilder)object2).toString());
        }
    }
}

