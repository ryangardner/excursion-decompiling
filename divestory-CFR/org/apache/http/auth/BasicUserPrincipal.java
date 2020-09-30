/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.auth;

import java.io.Serializable;
import java.security.Principal;
import org.apache.http.util.LangUtils;

public final class BasicUserPrincipal
implements Principal,
Serializable {
    private static final long serialVersionUID = -2266305184969850467L;
    private final String username;

    public BasicUserPrincipal(String string2) {
        if (string2 == null) throw new IllegalArgumentException("User name may not be null");
        this.username = string2;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof BasicUserPrincipal)) return false;
        object = (BasicUserPrincipal)object;
        if (!LangUtils.equals(this.username, ((BasicUserPrincipal)object).username)) return false;
        return true;
    }

    @Override
    public String getName() {
        return this.username;
    }

    @Override
    public int hashCode() {
        return LangUtils.hashCode(17, this.username);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[principal: ");
        stringBuilder.append(this.username);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

