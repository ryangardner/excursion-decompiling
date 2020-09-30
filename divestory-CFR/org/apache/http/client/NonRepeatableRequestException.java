/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client;

import org.apache.http.ProtocolException;

public class NonRepeatableRequestException
extends ProtocolException {
    private static final long serialVersionUID = 82685265288806048L;

    public NonRepeatableRequestException() {
    }

    public NonRepeatableRequestException(String string2) {
        super(string2);
    }

    public NonRepeatableRequestException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

