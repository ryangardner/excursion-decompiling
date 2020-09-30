/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis;

import com.google.api.client.util.SecurityUtils;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GoogleUtils {
    public static final Integer BUGFIX_VERSION;
    public static final Integer MAJOR_VERSION;
    public static final Integer MINOR_VERSION;
    public static final String VERSION;
    static final Pattern VERSION_PATTERN;
    static KeyStore certTrustStore;

    static {
        VERSION = GoogleUtils.getVersion();
        Object object = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)(-SNAPSHOT)?");
        VERSION_PATTERN = object;
        object = ((Pattern)object).matcher(VERSION);
        ((Matcher)object).find();
        MAJOR_VERSION = Integer.parseInt(((Matcher)object).group(1));
        MINOR_VERSION = Integer.parseInt(((Matcher)object).group(2));
        BUGFIX_VERSION = Integer.parseInt(((Matcher)object).group(3));
    }

    private GoogleUtils() {
    }

    public static KeyStore getCertificateTrustStore() throws IOException, GeneralSecurityException {
        synchronized (GoogleUtils.class) {
            Object object;
            if (certTrustStore == null) {
                certTrustStore = SecurityUtils.getJavaKeyStore();
                object = GoogleUtils.class.getResourceAsStream("google.jks");
                SecurityUtils.loadKeyStore(certTrustStore, (InputStream)object, "notasecret");
            }
            object = certTrustStore;
            return object;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private static String getVersion() {
        var0 = null;
        var1_1 = null;
        var2_4 = var0;
        var3_5 = GoogleUtils.class.getResourceAsStream("google-api-client.properties");
        if (var3_5 == null) ** GOTO lbl26
        try {
            var1_1 = new Properties();
            var1_1.load(var3_5);
            var1_1 = var1_1.getProperty("google-api-client.version");
            ** GOTO lbl26
        }
        catch (Throwable var4_7) {
            try {
                throw var4_7;
            }
            catch (Throwable var1_2) {
                if (var3_5 == null) ** GOTO lbl24
                try {
                    var3_5.close();
                    ** GOTO lbl24
                }
                catch (Throwable var3_6) {
                    var2_4 = var0;
                    try {
                        var4_7.addSuppressed(var3_6);
lbl24: // 3 sources:
                        var2_4 = var0;
                        throw var1_2;
lbl26: // 2 sources:
                        var2_4 = var1_1;
                        if (var3_5 != null) {
                            var2_4 = var1_1;
                            var3_5.close();
                            var2_4 = var1_1;
                        }
                    }
                    catch (IOException var1_3) {
                        // empty catch block
                    }
                }
            }
        }
        var1_1 = var2_4;
        if (var2_4 != null) return var1_1;
        return "unknown-version";
    }
}

