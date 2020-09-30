/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import com.google.api.client.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.X509TrustManager;

public final class SecurityUtils {
    private SecurityUtils() {
    }

    public static KeyStore getDefaultKeyStore() throws KeyStoreException {
        return KeyStore.getInstance(KeyStore.getDefaultType());
    }

    public static KeyStore getJavaKeyStore() throws KeyStoreException {
        return KeyStore.getInstance("JKS");
    }

    public static KeyStore getPkcs12KeyStore() throws KeyStoreException {
        return KeyStore.getInstance("PKCS12");
    }

    public static PrivateKey getPrivateKey(KeyStore keyStore, String string2, String string3) throws GeneralSecurityException {
        return (PrivateKey)keyStore.getKey(string2, string3.toCharArray());
    }

    public static KeyFactory getRsaKeyFactory() throws NoSuchAlgorithmException {
        return KeyFactory.getInstance("RSA");
    }

    public static Signature getSha1WithRsaSignatureAlgorithm() throws NoSuchAlgorithmException {
        return Signature.getInstance("SHA1withRSA");
    }

    public static Signature getSha256WithRsaSignatureAlgorithm() throws NoSuchAlgorithmException {
        return Signature.getInstance("SHA256withRSA");
    }

    public static CertificateFactory getX509CertificateFactory() throws CertificateException {
        return CertificateFactory.getInstance("X.509");
    }

    public static void loadKeyStore(KeyStore keyStore, InputStream inputStream2, String string2) throws IOException, GeneralSecurityException {
        try {
            keyStore.load(inputStream2, string2.toCharArray());
            return;
        }
        finally {
            inputStream2.close();
        }
    }

    public static void loadKeyStoreFromCertificates(KeyStore keyStore, CertificateFactory object, InputStream inputStream2) throws GeneralSecurityException {
        object = ((CertificateFactory)object).generateCertificates(inputStream2).iterator();
        int n = 0;
        while (object.hasNext()) {
            keyStore.setCertificateEntry(String.valueOf(n), (Certificate)object.next());
            ++n;
        }
    }

    public static PrivateKey loadPrivateKeyFromKeyStore(KeyStore keyStore, InputStream inputStream2, String string2, String string3, String string4) throws IOException, GeneralSecurityException {
        SecurityUtils.loadKeyStore(keyStore, inputStream2, string2);
        return SecurityUtils.getPrivateKey(keyStore, string3, string4);
    }

    public static byte[] sign(Signature signature, PrivateKey privateKey, byte[] arrby) throws InvalidKeyException, SignatureException {
        signature.initSign(privateKey);
        signature.update(arrby);
        return signature.sign();
    }

    public static X509Certificate verify(Signature signature, X509TrustManager x509TrustManager, List<String> object, byte[] arrby, byte[] arrby2) throws InvalidKeyException, SignatureException {
        CertificateFactory certificateFactory = SecurityUtils.getX509CertificateFactory();
        X509Certificate[] arrx509Certificate = new X509Certificate[object.size()];
        object = object.iterator();
        int n = 0;
        while (object.hasNext()) {
            Object object2 = new ByteArrayInputStream(Base64.decodeBase64((String)object.next()));
            try {
                object2 = certificateFactory.generateCertificate((InputStream)object2);
                if (!(object2 instanceof X509Certificate)) {
                    return null;
                }
                arrx509Certificate[n] = (X509Certificate)object2;
                ++n;
            }
            catch (CertificateException certificateException) {
                return null;
            }
        }
        try {
            x509TrustManager.checkServerTrusted(arrx509Certificate, "RSA");
        }
        catch (CertificateException certificateException) {
            return null;
        }
        if (!SecurityUtils.verify(signature, arrx509Certificate[0].getPublicKey(), arrby, arrby2)) return null;
        return arrx509Certificate[0];
    }

    public static boolean verify(Signature signature, PublicKey publicKey, byte[] arrby, byte[] arrby2) throws InvalidKeyException, SignatureException {
        signature.initVerify(publicKey);
        signature.update(arrby2);
        try {
            return signature.verify(arrby);
        }
        catch (SignatureException signatureException) {
            return false;
        }
    }
}

