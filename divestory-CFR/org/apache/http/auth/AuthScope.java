/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.auth;

import java.util.Locale;
import org.apache.http.util.LangUtils;

public class AuthScope {
    public static final AuthScope ANY = new AuthScope(ANY_HOST, -1, ANY_REALM, ANY_SCHEME);
    public static final String ANY_HOST;
    public static final int ANY_PORT = -1;
    public static final String ANY_REALM;
    public static final String ANY_SCHEME;
    private final String host;
    private final int port;
    private final String realm;
    private final String scheme;

    public AuthScope(String string2, int n) {
        this(string2, n, ANY_REALM, ANY_SCHEME);
    }

    public AuthScope(String string2, int n, String string3) {
        this(string2, n, string3, ANY_SCHEME);
    }

    public AuthScope(String string2, int n, String string3, String string4) {
        string2 = string2 == null ? ANY_HOST : string2.toLowerCase(Locale.ENGLISH);
        this.host = string2;
        int n2 = n;
        if (n < 0) {
            n2 = -1;
        }
        this.port = n2;
        string2 = string3;
        if (string3 == null) {
            string2 = ANY_REALM;
        }
        this.realm = string2;
        string2 = string4 == null ? ANY_SCHEME : string4.toUpperCase(Locale.ENGLISH);
        this.scheme = string2;
    }

    public AuthScope(AuthScope authScope) {
        if (authScope == null) throw new IllegalArgumentException("Scope may not be null");
        this.host = authScope.getHost();
        this.port = authScope.getPort();
        this.realm = authScope.getRealm();
        this.scheme = authScope.getScheme();
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (object == this) {
            return true;
        }
        if (!(object instanceof AuthScope)) {
            return super.equals(object);
        }
        object = (AuthScope)object;
        boolean bl2 = bl;
        if (!LangUtils.equals(this.host, ((AuthScope)object).host)) return bl2;
        bl2 = bl;
        if (this.port != ((AuthScope)object).port) return bl2;
        bl2 = bl;
        if (!LangUtils.equals(this.realm, ((AuthScope)object).realm)) return bl2;
        bl2 = bl;
        if (!LangUtils.equals(this.scheme, ((AuthScope)object).scheme)) return bl2;
        return true;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public String getRealm() {
        return this.realm;
    }

    public String getScheme() {
        return this.scheme;
    }

    public int hashCode() {
        return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(17, this.host), this.port), this.realm), this.scheme);
    }

    public int match(AuthScope authScope) {
        String string2;
        String string3;
        int n;
        int n2;
        if (LangUtils.equals(this.scheme, authScope.scheme)) {
            n = 1;
        } else {
            string2 = this.scheme;
            string3 = ANY_SCHEME;
            if (string2 != string3 && authScope.scheme != string3) {
                return -1;
            }
            n = 0;
        }
        if (LangUtils.equals(this.realm, authScope.realm)) {
            n2 = n + 2;
        } else {
            string2 = this.realm;
            string3 = ANY_REALM;
            n2 = n;
            if (string2 != string3) {
                n2 = n;
                if (authScope.realm != string3) {
                    return -1;
                }
            }
        }
        int n3 = this.port;
        int n4 = authScope.port;
        if (n3 == n4) {
            n = n2 + 4;
        } else {
            n = n2;
            if (n3 != -1) {
                n = n2;
                if (n4 != -1) {
                    return -1;
                }
            }
        }
        if (LangUtils.equals(this.host, authScope.host)) {
            return n + 8;
        }
        string3 = this.host;
        string2 = ANY_HOST;
        n2 = n;
        if (string3 == string2) return n2;
        n2 = n;
        if (authScope.host == string2) return n2;
        return -1;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = this.scheme;
        if (string2 != null) {
            stringBuilder.append(string2.toUpperCase(Locale.ENGLISH));
            stringBuilder.append(' ');
        }
        if (this.realm != null) {
            stringBuilder.append('\'');
            stringBuilder.append(this.realm);
            stringBuilder.append('\'');
        } else {
            stringBuilder.append("<any realm>");
        }
        if (this.host == null) return stringBuilder.toString();
        stringBuilder.append('@');
        stringBuilder.append(this.host);
        if (this.port < 0) return stringBuilder.toString();
        stringBuilder.append(':');
        stringBuilder.append(this.port);
        return stringBuilder.toString();
    }
}

