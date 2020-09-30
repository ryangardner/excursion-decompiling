/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client;

import org.apache.http.client.RedirectException;

public class CircularRedirectException
extends RedirectException {
    private static final long serialVersionUID = 6830063487001091803L;

    public CircularRedirectException() {
    }

    public CircularRedirectException(String string2) {
        super(string2);
    }

    public CircularRedirectException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

