/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

@Deprecated
public class FTPSTrustManager
implements X509TrustManager {
    private static final X509Certificate[] EMPTY_X509CERTIFICATE_ARRAY = new X509Certificate[0];

    @Override
    public void checkClientTrusted(X509Certificate[] arrx509Certificate, String string2) {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] arrx509Certificate, String string2) throws CertificateException {
        int n = arrx509Certificate.length;
        int n2 = 0;
        while (n2 < n) {
            arrx509Certificate[n2].checkValidity();
            ++n2;
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return EMPTY_X509CERTIFICATE_ARRAY;
    }
}

