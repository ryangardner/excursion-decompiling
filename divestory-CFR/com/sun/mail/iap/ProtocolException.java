/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.iap;

import com.sun.mail.iap.Response;

public class ProtocolException
extends Exception {
    private static final long serialVersionUID = -4360500807971797439L;
    protected transient Response response = null;

    public ProtocolException() {
    }

    public ProtocolException(Response response) {
        super(response.toString());
        this.response = response;
    }

    public ProtocolException(String string2) {
        super(string2);
    }

    public Response getResponse() {
        return this.response;
    }
}

