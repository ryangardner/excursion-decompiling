/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.auth;

import org.apache.http.auth.AuthenticationException;

public class NTLMEngineException
extends AuthenticationException {
    private static final long serialVersionUID = 6027981323731768824L;

    public NTLMEngineException() {
    }

    public NTLMEngineException(String string2) {
        super(string2);
    }

    public NTLMEngineException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

