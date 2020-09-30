/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn.ssl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.ssl.TrustStrategy;

class TrustManagerDecorator
implements X509TrustManager {
    private final X509TrustManager trustManager;
    private final TrustStrategy trustStrategy;

    TrustManagerDecorator(X509TrustManager x509TrustManager, TrustStrategy trustStrategy) {
        this.trustManager = x509TrustManager;
        this.trustStrategy = trustStrategy;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] arrx509Certificate, String string2) throws CertificateException {
        this.trustManager.checkClientTrusted(arrx509Certificate, string2);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] arrx509Certificate, String string2) throws CertificateException {
        if (this.trustStrategy.isTrusted(arrx509Certificate, string2)) return;
        this.trustManager.checkServerTrusted(arrx509Certificate, string2);
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return this.trustManager.getAcceptedIssuers();
    }
}

