/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.util;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public class SSLContextUtils {
    private SSLContextUtils() {
    }

    public static SSLContext createSSLContext(String string2, KeyManager arrkeyManager, TrustManager arrtrustManager) throws IOException {
        TrustManager[] arrtrustManager2 = null;
        if (arrkeyManager == null) {
            arrkeyManager = null;
        } else {
            KeyManager[] arrkeyManager2 = new KeyManager[]{arrkeyManager};
            arrkeyManager = arrkeyManager2;
        }
        if (arrtrustManager == null) {
            arrtrustManager = arrtrustManager2;
            return SSLContextUtils.createSSLContext(string2, arrkeyManager, arrtrustManager);
        }
        arrtrustManager2 = new TrustManager[]{arrtrustManager};
        arrtrustManager = arrtrustManager2;
        return SSLContextUtils.createSSLContext(string2, arrkeyManager, arrtrustManager);
    }

    public static SSLContext createSSLContext(String object, KeyManager[] object2, TrustManager[] arrtrustManager) throws IOException {
        try {
            object = SSLContext.getInstance((String)object);
            ((SSLContext)object).init((KeyManager[])object2, arrtrustManager, null);
            return object;
        }
        catch (GeneralSecurityException generalSecurityException) {
            object2 = new IOException("Could not initialize SSL context");
            ((Throwable)object2).initCause(generalSecurityException);
            throw object2;
        }
    }
}

