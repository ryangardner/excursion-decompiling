/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn.ssl;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.HostNameResolver;
import org.apache.http.conn.scheme.LayeredSchemeSocketFactory;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.StrictHostnameVerifier;
import org.apache.http.conn.ssl.TrustManagerDecorator;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class SSLSocketFactory
implements LayeredSchemeSocketFactory,
LayeredSocketFactory {
    public static final X509HostnameVerifier ALLOW_ALL_HOSTNAME_VERIFIER = new AllowAllHostnameVerifier();
    public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = new BrowserCompatHostnameVerifier();
    public static final String SSL = "SSL";
    public static final String SSLV2 = "SSLv2";
    public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = new StrictHostnameVerifier();
    public static final String TLS = "TLS";
    private volatile X509HostnameVerifier hostnameVerifier;
    private final HostNameResolver nameResolver;
    private final javax.net.ssl.SSLSocketFactory socketfactory;

    private SSLSocketFactory() {
        this(SSLSocketFactory.createDefaultSSLContext());
    }

    @Deprecated
    public SSLSocketFactory(String string2, KeyStore keyStore, String string3, KeyStore keyStore2, SecureRandom secureRandom, HostNameResolver hostNameResolver) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(SSLSocketFactory.createSSLContext(string2, keyStore, string3, keyStore2, secureRandom, null), hostNameResolver);
    }

    public SSLSocketFactory(String string2, KeyStore keyStore, String string3, KeyStore keyStore2, SecureRandom secureRandom, TrustStrategy trustStrategy, X509HostnameVerifier x509HostnameVerifier) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(SSLSocketFactory.createSSLContext(string2, keyStore, string3, keyStore2, secureRandom, trustStrategy), x509HostnameVerifier);
    }

    public SSLSocketFactory(String string2, KeyStore keyStore, String string3, KeyStore keyStore2, SecureRandom secureRandom, X509HostnameVerifier x509HostnameVerifier) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(SSLSocketFactory.createSSLContext(string2, keyStore, string3, keyStore2, secureRandom, null), x509HostnameVerifier);
    }

    public SSLSocketFactory(KeyStore keyStore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(TLS, null, null, keyStore, null, null, BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(KeyStore keyStore, String string2) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(TLS, keyStore, string2, null, null, null, BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(KeyStore keyStore, String string2, KeyStore keyStore2) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(TLS, keyStore, string2, keyStore2, null, null, BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(SSLContext sSLContext) {
        this(sSLContext, BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    @Deprecated
    public SSLSocketFactory(SSLContext sSLContext, HostNameResolver hostNameResolver) {
        this.socketfactory = sSLContext.getSocketFactory();
        this.hostnameVerifier = BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
        this.nameResolver = hostNameResolver;
    }

    public SSLSocketFactory(SSLContext sSLContext, X509HostnameVerifier x509HostnameVerifier) {
        this.socketfactory = sSLContext.getSocketFactory();
        this.hostnameVerifier = x509HostnameVerifier;
        this.nameResolver = null;
    }

    public SSLSocketFactory(TrustStrategy trustStrategy) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(TLS, null, null, null, null, trustStrategy, BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(TrustStrategy trustStrategy, X509HostnameVerifier x509HostnameVerifier) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(TLS, null, null, null, null, trustStrategy, x509HostnameVerifier);
    }

    private static SSLContext createDefaultSSLContext() {
        try {
            return SSLSocketFactory.createSSLContext(TLS, null, null, null, null, null);
        }
        catch (Exception exception) {
            throw new IllegalStateException("Failure initializing default SSL context", exception);
        }
    }

    private static SSLContext createSSLContext(String object, KeyStore arrtrustManager, String object2, KeyStore keyStore, SecureRandom secureRandom, TrustStrategy trustStrategy) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, KeyManagementException {
        char[] arrc = object;
        if (object == null) {
            arrc = TLS;
        }
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        object = object2 != null ? ((String)object2).toCharArray() : null;
        keyManagerFactory.init((KeyStore)arrtrustManager, (char[])object);
        object = keyManagerFactory.getKeyManagers();
        arrtrustManager = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        arrtrustManager.init(keyStore);
        arrtrustManager = arrtrustManager.getTrustManagers();
        if (arrtrustManager != null && trustStrategy != null) {
            for (int i = 0; i < arrtrustManager.length; ++i) {
                object2 = arrtrustManager[i];
                if (!(object2 instanceof X509TrustManager)) continue;
                arrtrustManager[i] = new TrustManagerDecorator((X509TrustManager)object2, trustStrategy);
            }
        }
        object2 = SSLContext.getInstance((String)arrc);
        ((SSLContext)object2).init((KeyManager[])object, arrtrustManager, secureRandom);
        return object2;
    }

    public static SSLSocketFactory getSocketFactory() {
        return new SSLSocketFactory();
    }

    @Deprecated
    @Override
    public Socket connectSocket(Socket socket, String object, int n, InetAddress serializable, int n2, HttpParams httpParams) throws IOException, UnknownHostException, ConnectTimeoutException {
        if (serializable == null && n2 <= 0) {
            serializable = null;
        } else {
            int n3 = n2;
            if (n2 < 0) {
                n3 = 0;
            }
            serializable = new InetSocketAddress((InetAddress)serializable, n3);
        }
        HostNameResolver hostNameResolver = this.nameResolver;
        if (hostNameResolver != null) {
            object = hostNameResolver.resolve((String)object);
            return this.connectSocket(socket, new InetSocketAddress((InetAddress)object, n), (InetSocketAddress)serializable, httpParams);
        }
        object = InetAddress.getByName((String)object);
        return this.connectSocket(socket, new InetSocketAddress((InetAddress)object, n), (InetSocketAddress)serializable, httpParams);
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    @Override
    public Socket connectSocket(Socket socket, InetSocketAddress object, InetSocketAddress object2, HttpParams object3) throws IOException, UnknownHostException, ConnectTimeoutException {
        if (object == null) throw new IllegalArgumentException("Remote address may not be null");
        if (object3 == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        if (socket == null) {
            socket = new Socket();
        }
        if (object2 != null) {
            socket.setReuseAddress(HttpConnectionParams.getSoReuseaddr((HttpParams)object3));
            socket.bind((SocketAddress)object2);
        }
        int n = HttpConnectionParams.getConnectionTimeout((HttpParams)object3);
        int n2 = HttpConnectionParams.getSoTimeout((HttpParams)object3);
        socket.setSoTimeout(n2);
        socket.connect((SocketAddress)object, n);
        object2 = ((InetSocketAddress)object).toString();
        n2 = ((InetSocketAddress)object).getPort();
        object = new StringBuilder();
        ((StringBuilder)object).append(":");
        {
            catch (SocketTimeoutException socketTimeoutException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Connect to ");
                stringBuilder.append(object);
                stringBuilder.append(" timed out");
                throw new ConnectTimeoutException(stringBuilder.toString());
            }
        }
        ((StringBuilder)object).append(n2);
        object3 = ((StringBuilder)object).toString();
        object = object2;
        if (((String)object2).endsWith((String)object3)) {
            object = ((String)object2).substring(0, ((String)object2).length() - ((String)object3).length());
        }
        socket = socket instanceof SSLSocket ? (SSLSocket)socket : (SSLSocket)this.socketfactory.createSocket(socket, (String)object, n2, true);
        if (this.hostnameVerifier == null) return socket;
        try {
            this.hostnameVerifier.verify((String)object, (SSLSocket)socket);
            return socket;
        }
        catch (IOException iOException) {
            try {
                socket.close();
            }
            catch (Exception exception) {
                throw iOException;
            }
            throw iOException;
        }
    }

    @Override
    public Socket createLayeredSocket(Socket socket, String string2, int n, boolean bl) throws IOException, UnknownHostException {
        socket = (SSLSocket)this.socketfactory.createSocket(socket, string2, n, bl);
        if (this.hostnameVerifier == null) return socket;
        this.hostnameVerifier.verify(string2, (SSLSocket)socket);
        return socket;
    }

    @Deprecated
    @Override
    public Socket createSocket() throws IOException {
        return this.socketfactory.createSocket();
    }

    @Deprecated
    @Override
    public Socket createSocket(Socket socket, String string2, int n, boolean bl) throws IOException, UnknownHostException {
        return this.createLayeredSocket(socket, string2, n, bl);
    }

    @Override
    public Socket createSocket(HttpParams httpParams) throws IOException {
        return this.socketfactory.createSocket();
    }

    public X509HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    @Override
    public boolean isSecure(Socket socket) throws IllegalArgumentException {
        if (socket == null) throw new IllegalArgumentException("Socket may not be null");
        if (!(socket instanceof SSLSocket)) throw new IllegalArgumentException("Socket not created by this factory");
        if (socket.isClosed()) throw new IllegalArgumentException("Socket is closed");
        return true;
    }

    @Deprecated
    public void setHostnameVerifier(X509HostnameVerifier x509HostnameVerifier) {
        if (x509HostnameVerifier == null) throw new IllegalArgumentException("Hostname verifier may not be null");
        this.hostnameVerifier = x509HostnameVerifier;
    }
}

