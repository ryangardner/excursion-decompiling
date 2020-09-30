/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn;

import org.apache.http.conn.ConnectTimeoutException;

public class ConnectionPoolTimeoutException
extends ConnectTimeoutException {
    private static final long serialVersionUID = -7898874842020245128L;

    public ConnectionPoolTimeoutException() {
    }

    public ConnectionPoolTimeoutException(String string2) {
        super(string2);
    }
}

