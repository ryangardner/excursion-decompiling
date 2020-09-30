/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn.ssl;

import org.apache.http.conn.ssl.AbstractVerifier;

public class AllowAllHostnameVerifier
extends AbstractVerifier {
    public final String toString() {
        return "ALLOW_ALL";
    }

    @Override
    public final void verify(String string2, String[] arrstring, String[] arrstring2) {
    }
}

