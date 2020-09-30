/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http;

import org.apache.http.util.ExceptionUtils;

public class HttpException
extends Exception {
    private static final long serialVersionUID = -5437299376222011036L;

    public HttpException() {
    }

    public HttpException(String string2) {
        super(string2);
    }

    public HttpException(String string2, Throwable throwable) {
        super(string2);
        ExceptionUtils.initCause(this, throwable);
    }
}

