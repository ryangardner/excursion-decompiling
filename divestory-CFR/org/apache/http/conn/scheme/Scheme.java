/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn.scheme;

import java.util.Locale;
import org.apache.http.conn.scheme.LayeredSchemeSocketFactory;
import org.apache.http.conn.scheme.LayeredSchemeSocketFactoryAdaptor;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.scheme.LayeredSocketFactoryAdaptor;
import org.apache.http.conn.scheme.SchemeSocketFactory;
import org.apache.http.conn.scheme.SchemeSocketFactoryAdaptor;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.scheme.SocketFactoryAdaptor;
import org.apache.http.util.LangUtils;

public final class Scheme {
    private final int defaultPort;
    private final boolean layered;
    private final String name;
    private final SchemeSocketFactory socketFactory;
    private String stringRep;

    public Scheme(String charSequence, int n, SchemeSocketFactory schemeSocketFactory) {
        if (charSequence == null) throw new IllegalArgumentException("Scheme name may not be null");
        if (n > 0 && n <= 65535) {
            if (schemeSocketFactory == null) throw new IllegalArgumentException("Socket factory may not be null");
            this.name = ((String)charSequence).toLowerCase(Locale.ENGLISH);
            this.socketFactory = schemeSocketFactory;
            this.defaultPort = n;
            this.layered = schemeSocketFactory instanceof LayeredSchemeSocketFactory;
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Port is invalid: ");
        ((StringBuilder)charSequence).append(n);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    @Deprecated
    public Scheme(String charSequence, SocketFactory socketFactory, int n) {
        if (charSequence == null) throw new IllegalArgumentException("Scheme name may not be null");
        if (socketFactory == null) throw new IllegalArgumentException("Socket factory may not be null");
        if (n > 0 && n <= 65535) {
            this.name = ((String)charSequence).toLowerCase(Locale.ENGLISH);
            if (socketFactory instanceof LayeredSocketFactory) {
                this.socketFactory = new LayeredSchemeSocketFactoryAdaptor((LayeredSocketFactory)socketFactory);
                this.layered = true;
            } else {
                this.socketFactory = new SchemeSocketFactoryAdaptor(socketFactory);
                this.layered = false;
            }
            this.defaultPort = n;
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Port is invalid: ");
        ((StringBuilder)charSequence).append(n);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    public final boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof Scheme)) return false;
        object = (Scheme)object;
        if (!this.name.equals(((Scheme)object).name)) return false;
        if (this.defaultPort != ((Scheme)object).defaultPort) return false;
        if (this.layered != ((Scheme)object).layered) return false;
        return bl;
    }

    public final int getDefaultPort() {
        return this.defaultPort;
    }

    public final String getName() {
        return this.name;
    }

    public final SchemeSocketFactory getSchemeSocketFactory() {
        return this.socketFactory;
    }

    @Deprecated
    public final SocketFactory getSocketFactory() {
        SchemeSocketFactory schemeSocketFactory = this.socketFactory;
        if (schemeSocketFactory instanceof SchemeSocketFactoryAdaptor) {
            return ((SchemeSocketFactoryAdaptor)schemeSocketFactory).getFactory();
        }
        if (!this.layered) return new SocketFactoryAdaptor(this.socketFactory);
        return new LayeredSocketFactoryAdaptor((LayeredSchemeSocketFactory)this.socketFactory);
    }

    public int hashCode() {
        return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(17, this.defaultPort), this.name), this.layered);
    }

    public final boolean isLayered() {
        return this.layered;
    }

    public final int resolvePort(int n) {
        int n2 = n;
        if (n > 0) return n2;
        return this.defaultPort;
    }

    public final String toString() {
        if (this.stringRep != null) return this.stringRep;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name);
        stringBuilder.append(':');
        stringBuilder.append(Integer.toString(this.defaultPort));
        this.stringRep = stringBuilder.toString();
        return this.stringRep;
    }
}

