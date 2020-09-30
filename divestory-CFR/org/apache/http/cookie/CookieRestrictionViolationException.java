/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.cookie;

import org.apache.http.cookie.MalformedCookieException;

public class CookieRestrictionViolationException
extends MalformedCookieException {
    private static final long serialVersionUID = 7371235577078589013L;

    public CookieRestrictionViolationException() {
    }

    public CookieRestrictionViolationException(String string2) {
        super(string2);
    }
}

