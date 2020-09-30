/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.net;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
public final class HostAndPort
implements Serializable {
    private static final int NO_PORT = -1;
    private static final long serialVersionUID = 0L;
    private final boolean hasBracketlessColons;
    private final String host;
    private final int port;

    private HostAndPort(String string2, int n, boolean bl) {
        this.host = string2;
        this.port = n;
        this.hasBracketlessColons = bl;
    }

    public static HostAndPort fromHost(String string2) {
        HostAndPort hostAndPort = HostAndPort.fromString(string2);
        Preconditions.checkArgument(hostAndPort.hasPort() ^ true, "Host has a port: %s", (Object)string2);
        return hostAndPort;
    }

    public static HostAndPort fromParts(String string2, int n) {
        Preconditions.checkArgument(HostAndPort.isValidPort(n), "Port out of range: %s", n);
        HostAndPort hostAndPort = HostAndPort.fromString(string2);
        Preconditions.checkArgument(hostAndPort.hasPort() ^ true, "Host has a port: %s", (Object)string2);
        return new HostAndPort(hostAndPort.host, n, hostAndPort.hasBracketlessColons);
    }

    public static HostAndPort fromString(String string2) {
        String string3;
        Object object;
        Preconditions.checkNotNull(string2);
        boolean bl = string2.startsWith("[");
        int n = -1;
        boolean bl2 = false;
        boolean bl3 = false;
        if (bl) {
            object = HostAndPort.getHostAndPortFromBracketedHost(string2);
            string3 = object[0];
            object = object[1];
            bl3 = bl2;
        } else {
            int n2;
            int n3 = string2.indexOf(58);
            if (n3 >= 0 && string2.indexOf(58, n2 = n3 + 1) == -1) {
                string3 = string2.substring(0, n3);
                object = string2.substring(n2);
                bl3 = bl2;
            } else {
                if (n3 >= 0) {
                    bl3 = true;
                }
                object = null;
                string3 = string2;
            }
        }
        if (Strings.isNullOrEmpty((String)object)) return new HostAndPort(string3, n, bl3);
        Preconditions.checkArgument(((String)object).startsWith("+") ^ true, "Unparseable port number: %s", (Object)string2);
        try {
            n = Integer.parseInt((String)object);
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unparseable port number: ");
            stringBuilder.append(string2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        Preconditions.checkArgument(HostAndPort.isValidPort(n), "Port number out of range: %s", (Object)string2);
        return new HostAndPort(string3, n, bl3);
    }

    private static String[] getHostAndPortFromBracketedHost(String string2) {
        boolean bl = string2.charAt(0) == '[';
        Preconditions.checkArgument(bl, "Bracketed host-port string must start with a bracket: %s", (Object)string2);
        int n = string2.indexOf(58);
        int n2 = string2.lastIndexOf(93);
        bl = n > -1 && n2 > n;
        Preconditions.checkArgument(bl, "Invalid bracketed host/port: %s", (Object)string2);
        String string3 = string2.substring(1, n2);
        n = n2 + 1;
        if (n == string2.length()) {
            return new String[]{string3, ""};
        }
        bl = string2.charAt(n) == ':';
        Preconditions.checkArgument(bl, "Only a colon may follow a close bracket: %s", (Object)string2);
        n2 = n = n2 + 2;
        while (n2 < string2.length()) {
            Preconditions.checkArgument(Character.isDigit(string2.charAt(n2)), "Port must be numeric: %s", (Object)string2);
            ++n2;
        }
        return new String[]{string3, string2.substring(n)};
    }

    private static boolean isValidPort(int n) {
        if (n < 0) return false;
        if (n > 65535) return false;
        return true;
    }

    public boolean equals(@NullableDecl Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof HostAndPort)) return false;
        object = (HostAndPort)object;
        if (!Objects.equal(this.host, ((HostAndPort)object).host)) return false;
        if (this.port != ((HostAndPort)object).port) return false;
        return bl;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        Preconditions.checkState(this.hasPort());
        return this.port;
    }

    public int getPortOrDefault(int n) {
        if (!this.hasPort()) return n;
        return this.port;
    }

    public boolean hasPort() {
        if (this.port < 0) return false;
        return true;
    }

    public int hashCode() {
        return Objects.hashCode(this.host, this.port);
    }

    public HostAndPort requireBracketsForIPv6() {
        Preconditions.checkArgument(this.hasBracketlessColons ^ true, "Possible bracketless IPv6 literal: %s", (Object)this.host);
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.host.length() + 8);
        if (this.host.indexOf(58) >= 0) {
            stringBuilder.append('[');
            stringBuilder.append(this.host);
            stringBuilder.append(']');
        } else {
            stringBuilder.append(this.host);
        }
        if (!this.hasPort()) return stringBuilder.toString();
        stringBuilder.append(':');
        stringBuilder.append(this.port);
        return stringBuilder.toString();
    }

    public HostAndPort withDefaultPort(int n) {
        Preconditions.checkArgument(HostAndPort.isValidPort(n));
        if (!this.hasPort()) return new HostAndPort(this.host, n, this.hasBracketlessColons);
        return this;
    }
}

