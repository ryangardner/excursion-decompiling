/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.client;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;

public class TunnelRefusedException
extends HttpException {
    private static final long serialVersionUID = -8646722842745617323L;
    private final HttpResponse response;

    public TunnelRefusedException(String string2, HttpResponse httpResponse) {
        super(string2);
        this.response = httpResponse;
    }

    public HttpResponse getResponse() {
        return this.response;
    }
}

