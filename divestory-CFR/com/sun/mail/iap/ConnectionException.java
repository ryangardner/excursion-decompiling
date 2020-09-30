/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.iap;

import com.sun.mail.iap.Protocol;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;

public class ConnectionException
extends ProtocolException {
    private static final long serialVersionUID = 5749739604257464727L;
    private transient Protocol p;

    public ConnectionException() {
    }

    public ConnectionException(Protocol protocol, Response response) {
        super(response);
        this.p = protocol;
    }

    public ConnectionException(String string2) {
        super(string2);
    }

    public Protocol getProtocol() {
        return this.p;
    }
}

