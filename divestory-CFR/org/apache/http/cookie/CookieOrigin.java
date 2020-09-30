/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.cookie;

import java.util.Locale;

public final class CookieOrigin {
    private final String host;
    private final String path;
    private final int port;
    private final boolean secure;

    public CookieOrigin(String charSequence, int n, String string2, boolean bl) {
        if (charSequence == null) throw new IllegalArgumentException("Host of origin may not be null");
        if (((String)charSequence).trim().length() == 0) throw new IllegalArgumentException("Host of origin may not be blank");
        if (n < 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Invalid port: ");
            ((StringBuilder)charSequence).append(n);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        if (string2 == null) throw new IllegalArgumentException("Path of origin may not be null.");
        this.host = ((String)charSequence).toLowerCase(Locale.ENGLISH);
        this.port = n;
        this.path = string2.trim().length() != 0 ? string2 : "/";
        this.secure = bl;
    }

    public String getHost() {
        return this.host;
    }

    public String getPath() {
        return this.path;
    }

    public int getPort() {
        return this.port;
    }

    public boolean isSecure() {
        return this.secure;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        if (this.secure) {
            stringBuilder.append("(secure)");
        }
        stringBuilder.append(this.host);
        stringBuilder.append(':');
        stringBuilder.append(Integer.toString(this.port));
        stringBuilder.append(this.path);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}

