/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.auth;

import java.io.Serializable;
import java.security.Principal;
import org.apache.http.auth.BasicUserPrincipal;
import org.apache.http.auth.Credentials;
import org.apache.http.util.LangUtils;

public class UsernamePasswordCredentials
implements Credentials,
Serializable {
    private static final long serialVersionUID = 243343858802739403L;
    private final String password;
    private final BasicUserPrincipal principal;

    public UsernamePasswordCredentials(String string2) {
        if (string2 == null) throw new IllegalArgumentException("Username:password string may not be null");
        int n = string2.indexOf(58);
        if (n >= 0) {
            this.principal = new BasicUserPrincipal(string2.substring(0, n));
            this.password = string2.substring(n + 1);
            return;
        }
        this.principal = new BasicUserPrincipal(string2);
        this.password = null;
    }

    public UsernamePasswordCredentials(String string2, String string3) {
        if (string2 == null) throw new IllegalArgumentException("Username may not be null");
        this.principal = new BasicUserPrincipal(string2);
        this.password = string3;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof UsernamePasswordCredentials)) return false;
        object = (UsernamePasswordCredentials)object;
        if (!LangUtils.equals(this.principal, ((UsernamePasswordCredentials)object).principal)) return false;
        return true;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public String getUserName() {
        return this.principal.getName();
    }

    @Override
    public Principal getUserPrincipal() {
        return this.principal;
    }

    public int hashCode() {
        return this.principal.hashCode();
    }

    public String toString() {
        return this.principal.toString();
    }
}

