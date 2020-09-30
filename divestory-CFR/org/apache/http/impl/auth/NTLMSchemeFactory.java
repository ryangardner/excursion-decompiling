/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.auth;

import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeFactory;
import org.apache.http.impl.auth.NTLMEngine;
import org.apache.http.impl.auth.NTLMEngineImpl;
import org.apache.http.impl.auth.NTLMScheme;
import org.apache.http.params.HttpParams;

public class NTLMSchemeFactory
implements AuthSchemeFactory {
    @Override
    public AuthScheme newInstance(HttpParams httpParams) {
        return new NTLMScheme(new NTLMEngineImpl());
    }
}

