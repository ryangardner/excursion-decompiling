/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.auth;

import org.apache.http.ProtocolException;

public class AuthenticationException
extends ProtocolException {
    private static final long serialVersionUID = -6794031905674764776L;

    public AuthenticationException() {
    }

    public AuthenticationException(String string2) {
        super(string2);
    }

    public AuthenticationException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

