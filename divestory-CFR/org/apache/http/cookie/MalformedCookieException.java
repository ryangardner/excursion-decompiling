/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.cookie;

import org.apache.http.ProtocolException;

public class MalformedCookieException
extends ProtocolException {
    private static final long serialVersionUID = -6695462944287282185L;

    public MalformedCookieException() {
    }

    public MalformedCookieException(String string2) {
        super(string2);
    }

    public MalformedCookieException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

