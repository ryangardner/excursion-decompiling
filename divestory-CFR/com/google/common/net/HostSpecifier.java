/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.net;

import com.google.common.base.Preconditions;
import com.google.common.net.HostAndPort;
import com.google.common.net.InetAddresses;
import com.google.common.net.InternetDomainName;
import java.net.InetAddress;
import java.text.ParseException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class HostSpecifier {
    private final String canonicalForm;

    private HostSpecifier(String string2) {
        this.canonicalForm = string2;
    }

    public static HostSpecifier from(String object) throws ParseException {
        try {
            return HostSpecifier.fromValid((String)object);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid host specifier: ");
            stringBuilder.append((String)object);
            object = new ParseException(stringBuilder.toString(), 0);
            ((Throwable)object).initCause(illegalArgumentException);
            throw object;
        }
    }

    public static HostSpecifier fromValid(String object) {
        object = HostAndPort.fromString((String)object);
        Preconditions.checkArgument(((HostAndPort)object).hasPort() ^ true);
        String string2 = ((HostAndPort)object).getHost();
        object = null;
        try {
            InetAddress inetAddress = InetAddresses.forString(string2);
            object = inetAddress;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        if (object != null) {
            return new HostSpecifier(InetAddresses.toUriString((InetAddress)object));
        }
        object = InternetDomainName.from(string2);
        if (((InternetDomainName)object).hasPublicSuffix()) {
            return new HostSpecifier(((InternetDomainName)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Domain name does not have a recognized public suffix: ");
        ((StringBuilder)object).append(string2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static boolean isValid(String string2) {
        try {
            HostSpecifier.fromValid(string2);
            return true;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }
    }

    public boolean equals(@NullableDecl Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof HostSpecifier)) return false;
        object = (HostSpecifier)object;
        return this.canonicalForm.equals(((HostSpecifier)object).canonicalForm);
    }

    public int hashCode() {
        return this.canonicalForm.hashCode();
    }

    public String toString() {
        return this.canonicalForm;
    }
}

