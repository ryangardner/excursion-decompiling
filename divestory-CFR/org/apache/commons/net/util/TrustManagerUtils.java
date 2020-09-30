/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.util;

import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public final class TrustManagerUtils {
    private static final X509TrustManager ACCEPT_ALL;
    private static final X509TrustManager CHECK_SERVER_VALIDITY;
    private static final X509Certificate[] EMPTY_X509CERTIFICATE_ARRAY;

    static {
        EMPTY_X509CERTIFICATE_ARRAY = new X509Certificate[0];
        ACCEPT_ALL = new TrustManager(false);
        CHECK_SERVER_VALIDITY = new TrustManager(true);
    }

    public static X509TrustManager getAcceptAllTrustManager() {
        return ACCEPT_ALL;
    }

    public static X509TrustManager getDefaultTrustManager(KeyStore keyStore) throws GeneralSecurityException {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        return (X509TrustManager)trustManagerFactory.getTrustManagers()[0];
    }

    public static X509TrustManager getValidateServerCertificateTrustManager() {
        return CHECK_SERVER_VALIDITY;
    }

    private static class TrustManager
    implements X509TrustManager {
        private final boolean checkServerValidity;

        TrustManager(boolean bl) {
            this.checkServerValidity = bl;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] arrx509Certificate, String string2) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] arrx509Certificate, String string2) throws CertificateException {
            if (!this.checkServerValidity) return;
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

}

