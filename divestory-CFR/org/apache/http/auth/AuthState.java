/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.auth;

import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;

public class AuthState {
    private AuthScheme authScheme;
    private AuthScope authScope;
    private Credentials credentials;

    public AuthScheme getAuthScheme() {
        return this.authScheme;
    }

    public AuthScope getAuthScope() {
        return this.authScope;
    }

    public Credentials getCredentials() {
        return this.credentials;
    }

    public void invalidate() {
        this.authScheme = null;
        this.authScope = null;
        this.credentials = null;
    }

    public boolean isValid() {
        if (this.authScheme == null) return false;
        return true;
    }

    public void setAuthScheme(AuthScheme authScheme) {
        if (authScheme == null) {
            this.invalidate();
            return;
        }
        this.authScheme = authScheme;
    }

    public void setAuthScope(AuthScope authScope) {
        this.authScope = authScope;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("auth scope [");
        stringBuilder.append(this.authScope);
        stringBuilder.append("]; credentials set [");
        String string2 = this.credentials != null ? "true" : "false";
        stringBuilder.append(string2);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

