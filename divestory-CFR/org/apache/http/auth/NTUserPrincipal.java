/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.auth;

import java.io.Serializable;
import java.security.Principal;
import java.util.Locale;
import org.apache.http.util.LangUtils;

public class NTUserPrincipal
implements Principal,
Serializable {
    private static final long serialVersionUID = -6870169797924406894L;
    private final String domain;
    private final String ntname;
    private final String username;

    public NTUserPrincipal(String charSequence, String string2) {
        if (string2 == null) throw new IllegalArgumentException("User name may not be null");
        this.username = string2;
        this.domain = charSequence != null ? ((String)charSequence).toUpperCase(Locale.ENGLISH) : null;
        charSequence = this.domain;
        if (charSequence != null && ((String)charSequence).length() > 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.domain);
            ((StringBuilder)charSequence).append('/');
            ((StringBuilder)charSequence).append(this.username);
            this.ntname = ((StringBuilder)charSequence).toString();
            return;
        }
        this.ntname = this.username;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof NTUserPrincipal)) return false;
        object = (NTUserPrincipal)object;
        if (!LangUtils.equals(this.username, ((NTUserPrincipal)object).username)) return false;
        if (!LangUtils.equals(this.domain, ((NTUserPrincipal)object).domain)) return false;
        return true;
    }

    public String getDomain() {
        return this.domain;
    }

    @Override
    public String getName() {
        return this.ntname;
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    public int hashCode() {
        return LangUtils.hashCode(LangUtils.hashCode(17, this.username), this.domain);
    }

    @Override
    public String toString() {
        return this.ntname;
    }
}

