/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http;

import org.apache.http.HttpException;

public class MethodNotSupportedException
extends HttpException {
    private static final long serialVersionUID = 3365359036840171201L;

    public MethodNotSupportedException(String string2) {
        super(string2);
    }

    public MethodNotSupportedException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

