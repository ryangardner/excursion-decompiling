/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.auth;

import org.apache.http.ProtocolException;

public class MalformedChallengeException
extends ProtocolException {
    private static final long serialVersionUID = 814586927989932284L;

    public MalformedChallengeException() {
    }

    public MalformedChallengeException(String string2) {
        super(string2);
    }

    public MalformedChallengeException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

