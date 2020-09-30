/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.auth;

import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeFactory;
import org.apache.http.impl.auth.NegotiateScheme;
import org.apache.http.impl.auth.SpnegoTokenGenerator;
import org.apache.http.params.HttpParams;

public class NegotiateSchemeFactory
implements AuthSchemeFactory {
    private final SpnegoTokenGenerator spengoGenerator;
    private final boolean stripPort;

    public NegotiateSchemeFactory() {
        this(null, false);
    }

    public NegotiateSchemeFactory(SpnegoTokenGenerator spnegoTokenGenerator) {
        this(spnegoTokenGenerator, false);
    }

    public NegotiateSchemeFactory(SpnegoTokenGenerator spnegoTokenGenerator, boolean bl) {
        this.spengoGenerator = spnegoTokenGenerator;
        this.stripPort = bl;
    }

    public SpnegoTokenGenerator getSpengoGenerator() {
        return this.spengoGenerator;
    }

    public boolean isStripPort() {
        return this.stripPort;
    }

    @Override
    public AuthScheme newInstance(HttpParams httpParams) {
        return new NegotiateScheme(this.spengoGenerator, this.stripPort);
    }
}

