/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client;

import org.apache.http.ProtocolException;

public class RedirectException
extends ProtocolException {
    private static final long serialVersionUID = 4418824536372559326L;

    public RedirectException() {
    }

    public RedirectException(String string2) {
        super(string2);
    }

    public RedirectException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

