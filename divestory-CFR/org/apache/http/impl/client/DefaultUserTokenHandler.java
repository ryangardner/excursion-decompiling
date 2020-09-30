/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.client;

import java.security.Principal;
import javax.net.ssl.SSLSession;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.conn.HttpRoutedConnection;
import org.apache.http.protocol.HttpContext;

public class DefaultUserTokenHandler
implements UserTokenHandler {
    private static Principal getAuthPrincipal(AuthState object) {
        AuthScheme authScheme = ((AuthState)object).getAuthScheme();
        if (authScheme == null) return null;
        if (!authScheme.isComplete()) return null;
        if (!authScheme.isConnectionBased()) return null;
        if ((object = ((AuthState)object).getCredentials()) == null) return null;
        return object.getUserPrincipal();
    }

    @Override
    public Object getUserToken(HttpContext object) {
        Object object2;
        Object object3 = (AuthState)object.getAttribute("http.auth.target-scope");
        if (object3 != null) {
            object3 = object2 = DefaultUserTokenHandler.getAuthPrincipal((AuthState)object3);
            if (object2 == null) {
                object3 = DefaultUserTokenHandler.getAuthPrincipal((AuthState)object.getAttribute("http.auth.proxy-scope"));
            }
        } else {
            object3 = null;
        }
        object2 = object3;
        if (object3 != null) return object2;
        object = (HttpRoutedConnection)object.getAttribute("http.connection");
        object2 = object3;
        if (!object.isOpen()) return object2;
        object = object.getSSLSession();
        object2 = object3;
        if (object == null) return object2;
        return object.getLocalPrincipal();
    }
}

