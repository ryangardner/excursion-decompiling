/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn.ssl;

import javax.net.ssl.SSLException;
import org.apache.http.conn.ssl.AbstractVerifier;

public class BrowserCompatHostnameVerifier
extends AbstractVerifier {
    public final String toString() {
        return "BROWSER_COMPATIBLE";
    }

    @Override
    public final void verify(String string2, String[] arrstring, String[] arrstring2) throws SSLException {
        this.verify(string2, arrstring, arrstring2, false);
    }
}

