/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client;

import org.apache.http.client.ClientProtocolException;

public class HttpResponseException
extends ClientProtocolException {
    private static final long serialVersionUID = -7186627969477257933L;
    private final int statusCode;

    public HttpResponseException(int n, String string2) {
        super(string2);
        this.statusCode = n;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}

