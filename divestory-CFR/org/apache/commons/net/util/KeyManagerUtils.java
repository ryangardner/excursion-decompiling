/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import javax.net.ssl.KeyManager;
import javax.net.ssl.X509ExtendedKeyManager;
import org.apache.commons.net.io.Util;

public final class KeyManagerUtils {
    private static final String DEFAULT_STORE_TYPE = KeyStore.getDefaultType();

    private KeyManagerUtils() {
    }

    public static KeyManager createClientKeyManager(File file, String string2) throws IOException, GeneralSecurityException {
        return KeyManagerUtils.createClientKeyManager(DEFAULT_STORE_TYPE, file, string2, null, string2);
    }

    public static KeyManager createClientKeyManager(File file, String string2, String string3) throws IOException, GeneralSecurityException {
        return KeyManagerUtils.createClientKeyManager(DEFAULT_STORE_TYPE, file, string2, string3, string2);
    }

    public static KeyManager createClientKeyManager(String string2, File file, String string3, String string4, String string5) throws IOException, GeneralSecurityException {
        return KeyManagerUtils.createClientKeyManager(KeyManagerUtils.loadStore(string2, file, string3), string4, string5);
    }

    public static KeyManager createClientKeyManager(KeyStore keyStore, String string2, String string3) throws GeneralSecurityException {
        if (string2 != null) {
            return new X509KeyManager(new ClientKeyStore(keyStore, string2, string3));
        }
        string2 = KeyManagerUtils.findAlias(keyStore);
        return new X509KeyManager(new ClientKeyStore(keyStore, string2, string3));
    }

    private static String findAlias(KeyStore keyStore) throws KeyStoreException {
        String string2;
        Enumeration<String> enumeration = keyStore.aliases();
        do {
            if (!enumeration.hasMoreElements()) throw new KeyStoreException("Cannot find a private key entry");
        } while (!keyStore.isKeyEntry(string2 = enumeration.nextElement()));
        return string2;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    private static KeyStore loadStore(String object, File object2, String string2) throws KeyStoreException, IOException, GeneralSecurityException {
        void var0_3;
        block4 : {
            object = KeyStore.getInstance((String)object);
            Object var3_6 = null;
            FileInputStream fileInputStream = new FileInputStream((File)object2);
            try {
                ((KeyStore)object).load(fileInputStream, string2.toCharArray());
            }
            catch (Throwable throwable) {
                object2 = fileInputStream;
                break block4;
            }
            Util.closeQuietly(fileInputStream);
            return object;
            catch (Throwable throwable) {
                object2 = var3_6;
            }
        }
        Util.closeQuietly((Closeable)object2);
        throw var0_3;
    }

    private static class ClientKeyStore {
        private final X509Certificate[] certChain;
        private final PrivateKey key;
        private final String keyAlias;

        ClientKeyStore(KeyStore arrcertificate, String arrx509Certificate, String string2) throws GeneralSecurityException {
            this.keyAlias = arrx509Certificate;
            this.key = (PrivateKey)arrcertificate.getKey((String)arrx509Certificate, string2.toCharArray());
            arrcertificate = arrcertificate.getCertificateChain(this.keyAlias);
            arrx509Certificate = new X509Certificate[arrcertificate.length];
            int n = 0;
            do {
                if (n >= arrcertificate.length) {
                    this.certChain = arrx509Certificate;
                    return;
                }
                arrx509Certificate[n] = (X509Certificate)arrcertificate[n];
                ++n;
            } while (true);
        }

        final String getAlias() {
            return this.keyAlias;
        }

        final X509Certificate[] getCertificateChain() {
            return this.certChain;
        }

        final PrivateKey getPrivateKey() {
            return this.key;
        }
    }

    private static class X509KeyManager
    extends X509ExtendedKeyManager {
        private final ClientKeyStore keyStore;

        X509KeyManager(ClientKeyStore clientKeyStore) {
            this.keyStore = clientKeyStore;
        }

        @Override
        public String chooseClientAlias(String[] arrstring, Principal[] arrprincipal, Socket socket) {
            return this.keyStore.getAlias();
        }

        @Override
        public String chooseServerAlias(String string2, Principal[] arrprincipal, Socket socket) {
            return null;
        }

        @Override
        public X509Certificate[] getCertificateChain(String string2) {
            return this.keyStore.getCertificateChain();
        }

        @Override
        public String[] getClientAliases(String string2, Principal[] arrprincipal) {
            return new String[]{this.keyStore.getAlias()};
        }

        @Override
        public PrivateKey getPrivateKey(String string2) {
            return this.keyStore.getPrivateKey();
        }

        @Override
        public String[] getServerAliases(String string2, Principal[] arrprincipal) {
            return null;
        }
    }

}

