/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.auth;

import org.apache.http.auth.AuthenticationException;

public class InvalidCredentialsException
extends AuthenticationException {
    private static final long serialVersionUID = -4834003835215460648L;

    public InvalidCredentialsException() {
    }

    public InvalidCredentialsException(String string2) {
        super(string2);
    }

    public InvalidCredentialsException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

