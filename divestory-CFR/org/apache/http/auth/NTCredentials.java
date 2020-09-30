/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.auth;

import java.io.Serializable;
import java.security.Principal;
import java.util.Locale;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.NTUserPrincipal;
import org.apache.http.util.LangUtils;

public class NTCredentials
implements Credentials,
Serializable {
    private static final long serialVersionUID = -7385699315228907265L;
    private final String password;
    private final NTUserPrincipal principal;
    private final String workstation;

    public NTCredentials(String string2) {
        if (string2 == null) throw new IllegalArgumentException("Username:password string may not be null");
        int n = string2.indexOf(58);
        if (n >= 0) {
            String string3 = string2.substring(0, n);
            this.password = string2.substring(n + 1);
            string2 = string3;
        } else {
            this.password = null;
        }
        n = string2.indexOf(47);
        this.principal = n >= 0 ? new NTUserPrincipal(string2.substring(0, n).toUpperCase(Locale.ENGLISH), string2.substring(n + 1)) : new NTUserPrincipal(null, string2.substring(n + 1));
        this.workstation = null;
    }

    public NTCredentials(String string2, String string3, String string4, String string5) {
        if (string2 == null) throw new IllegalArgumentException("User name may not be null");
        this.principal = new NTUserPrincipal(string5, string2);
        this.password = string3;
        if (string4 != null) {
            this.workstation = string4.toUpperCase(Locale.ENGLISH);
            return;
        }
        this.workstation = null;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof NTCredentials)) return false;
        object = (NTCredentials)object;
        if (!LangUtils.equals(this.principal, ((NTCredentials)object).principal)) return false;
        if (!LangUtils.equals(this.workstation, ((NTCredentials)object).workstation)) return false;
        return true;
    }

    public String getDomain() {
        return this.principal.getDomain();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public String getUserName() {
        return this.principal.getUsername();
    }

    @Override
    public Principal getUserPrincipal() {
        return this.principal;
    }

    public String getWorkstation() {
        return this.workstation;
    }

    public int hashCode() {
        return LangUtils.hashCode(LangUtils.hashCode(17, this.principal), this.workstation);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[principal: ");
        stringBuilder.append(this.principal);
        stringBuilder.append("][workstation: ");
        stringBuilder.append(this.workstation);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

