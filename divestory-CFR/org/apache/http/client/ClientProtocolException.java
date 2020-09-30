/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client;

import java.io.IOException;

public class ClientProtocolException
extends IOException {
    private static final long serialVersionUID = -5596590843227115865L;

    public ClientProtocolException() {
    }

    public ClientProtocolException(String string2) {
        super(string2);
    }

    public ClientProtocolException(String string2, Throwable throwable) {
        super(string2);
        this.initCause(throwable);
    }

    public ClientProtocolException(Throwable throwable) {
        this.initCause(throwable);
    }
}

