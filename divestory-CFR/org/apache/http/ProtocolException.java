/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http;

import org.apache.http.HttpException;

public class ProtocolException
extends HttpException {
    private static final long serialVersionUID = -2143571074341228994L;

    public ProtocolException() {
    }

    public ProtocolException(String string2) {
        super(string2);
    }

    public ProtocolException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

