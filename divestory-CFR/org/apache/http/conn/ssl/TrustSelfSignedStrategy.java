/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn.ssl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import org.apache.http.conn.ssl.TrustStrategy;

public class TrustSelfSignedStrategy
implements TrustStrategy {
    @Override
    public boolean isTrusted(X509Certificate[] arrx509Certificate, String string2) throws CertificateException {
        int n = arrx509Certificate.length;
        boolean bl = true;
        if (n != 1) return false;
        return bl;
    }
}

