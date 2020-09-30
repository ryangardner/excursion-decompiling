/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http.javanet;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.javanet.ConnectionFactory;
import com.google.api.client.http.javanet.DefaultConnectionFactory;
import com.google.api.client.http.javanet.NetHttpRequest;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.SecurityUtils;
import com.google.api.client.util.SslUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Arrays;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public final class NetHttpTransport
extends HttpTransport {
    private static final String SHOULD_USE_PROXY_FLAG = "com.google.api.client.should_use_proxy";
    private static final String[] SUPPORTED_METHODS;
    private final ConnectionFactory connectionFactory;
    private final HostnameVerifier hostnameVerifier;
    private final SSLSocketFactory sslSocketFactory;

    static {
        Object[] arrobject = new String[]{"DELETE", "GET", "HEAD", "OPTIONS", "POST", "PUT", "TRACE"};
        SUPPORTED_METHODS = arrobject;
        Arrays.sort(arrobject);
    }

    public NetHttpTransport() {
        this((ConnectionFactory)null, null, null);
    }

    NetHttpTransport(ConnectionFactory connectionFactory, SSLSocketFactory sSLSocketFactory, HostnameVerifier hostnameVerifier) {
        this.connectionFactory = this.getConnectionFactory(connectionFactory);
        this.sslSocketFactory = sSLSocketFactory;
        this.hostnameVerifier = hostnameVerifier;
    }

    NetHttpTransport(Proxy proxy, SSLSocketFactory sSLSocketFactory, HostnameVerifier hostnameVerifier) {
        this(new DefaultConnectionFactory(proxy), sSLSocketFactory, hostnameVerifier);
    }

    private static Proxy defaultProxy() {
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(System.getProperty("https.proxyHost"), Integer.parseInt(System.getProperty("https.proxyPort"))));
    }

    private ConnectionFactory getConnectionFactory(ConnectionFactory connectionFactory) {
        ConnectionFactory connectionFactory2 = connectionFactory;
        if (connectionFactory != null) return connectionFactory2;
        if (System.getProperty(SHOULD_USE_PROXY_FLAG) == null) return new DefaultConnectionFactory();
        return new DefaultConnectionFactory(NetHttpTransport.defaultProxy());
    }

    @Override
    protected NetHttpRequest buildRequest(String object, String object2) throws IOException {
        Preconditions.checkArgument(this.supportsMethod((String)object), "HTTP method %s not supported", object);
        object2 = new URL((String)object2);
        object2 = this.connectionFactory.openConnection((URL)object2);
        ((HttpURLConnection)object2).setRequestMethod((String)object);
        if (!(object2 instanceof HttpsURLConnection)) return new NetHttpRequest((HttpURLConnection)object2);
        object = (HttpsURLConnection)object2;
        Object object3 = this.hostnameVerifier;
        if (object3 != null) {
            ((HttpsURLConnection)object).setHostnameVerifier((HostnameVerifier)object3);
        }
        if ((object3 = this.sslSocketFactory) == null) return new NetHttpRequest((HttpURLConnection)object2);
        ((HttpsURLConnection)object).setSSLSocketFactory((SSLSocketFactory)object3);
        return new NetHttpRequest((HttpURLConnection)object2);
    }

    @Override
    public boolean supportsMethod(String string2) {
        if (Arrays.binarySearch(SUPPORTED_METHODS, string2) < 0) return false;
        return true;
    }

    public static final class Builder {
        private ConnectionFactory connectionFactory;
        private HostnameVerifier hostnameVerifier;
        private Proxy proxy;
        private SSLSocketFactory sslSocketFactory;

        public NetHttpTransport build() {
            if (System.getProperty(NetHttpTransport.SHOULD_USE_PROXY_FLAG) != null) {
                this.setProxy(NetHttpTransport.defaultProxy());
            }
            if (this.proxy != null) return new NetHttpTransport(this.proxy, this.sslSocketFactory, this.hostnameVerifier);
            return new NetHttpTransport(this.connectionFactory, this.sslSocketFactory, this.hostnameVerifier);
        }

        public Builder doNotValidateCertificate() throws GeneralSecurityException {
            this.hostnameVerifier = SslUtils.trustAllHostnameVerifier();
            this.sslSocketFactory = SslUtils.trustAllSSLContext().getSocketFactory();
            return this;
        }

        public HostnameVerifier getHostnameVerifier() {
            return this.hostnameVerifier;
        }

        public SSLSocketFactory getSslSocketFactory() {
            return this.sslSocketFactory;
        }

        public Builder setConnectionFactory(ConnectionFactory connectionFactory) {
            this.connectionFactory = connectionFactory;
            return this;
        }

        public Builder setHostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.hostnameVerifier = hostnameVerifier;
            return this;
        }

        public Builder setProxy(Proxy proxy) {
            this.proxy = proxy;
            return this;
        }

        public Builder setSslSocketFactory(SSLSocketFactory sSLSocketFactory) {
            this.sslSocketFactory = sSLSocketFactory;
            return this;
        }

        public Builder trustCertificates(KeyStore keyStore) throws GeneralSecurityException {
            SSLContext sSLContext = SslUtils.getTlsSslContext();
            SslUtils.initSslContext(sSLContext, keyStore, SslUtils.getPkixTrustManagerFactory());
            return this.setSslSocketFactory(sSLContext.getSocketFactory());
        }

        public Builder trustCertificatesFromJavaKeyStore(InputStream inputStream2, String string2) throws GeneralSecurityException, IOException {
            KeyStore keyStore = SecurityUtils.getJavaKeyStore();
            SecurityUtils.loadKeyStore(keyStore, inputStream2, string2);
            return this.trustCertificates(keyStore);
        }

        public Builder trustCertificatesFromStream(InputStream inputStream2) throws GeneralSecurityException, IOException {
            KeyStore keyStore = SecurityUtils.getJavaKeyStore();
            keyStore.load(null, null);
            SecurityUtils.loadKeyStoreFromCertificates(keyStore, SecurityUtils.getX509CertificateFactory(), inputStream2);
            return this.trustCertificates(keyStore);
        }
    }

}

