/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http.apache;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.apache.ApacheHttpRequest;
import com.google.api.client.http.apache.HttpExtensionMethod;
import com.google.api.client.http.apache.SSLSocketFactoryExtension;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.SecurityUtils;
import com.google.api.client.util.SslUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProxySelector;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpHost;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.conn.ProxySelectorRoutePlanner;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

@Deprecated
public final class ApacheHttpTransport
extends HttpTransport {
    private final HttpClient httpClient;

    public ApacheHttpTransport() {
        this(ApacheHttpTransport.newDefaultHttpClient());
    }

    public ApacheHttpTransport(HttpClient object) {
        this.httpClient = object;
        HttpParams httpParams = object.getParams();
        object = httpParams;
        if (httpParams == null) {
            object = ApacheHttpTransport.newDefaultHttpClient().getParams();
        }
        HttpProtocolParams.setVersion((HttpParams)object, HttpVersion.HTTP_1_1);
        object.setBooleanParameter("http.protocol.handle-redirects", false);
    }

    public static DefaultHttpClient newDefaultHttpClient() {
        return ApacheHttpTransport.newDefaultHttpClient(SSLSocketFactory.getSocketFactory(), ApacheHttpTransport.newDefaultHttpParams(), ProxySelector.getDefault());
    }

    static DefaultHttpClient newDefaultHttpClient(SSLSocketFactory object, HttpParams httpParams, ProxySelector proxySelector) {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", (SocketFactory)object, 443));
        object = new DefaultHttpClient(new ThreadSafeClientConnManager(httpParams, schemeRegistry), httpParams);
        ((AbstractHttpClient)object).setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
        if (proxySelector == null) return object;
        ((AbstractHttpClient)object).setRoutePlanner(new ProxySelectorRoutePlanner(schemeRegistry, proxySelector));
        return object;
    }

    static HttpParams newDefaultHttpParams() {
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setStaleCheckingEnabled(basicHttpParams, false);
        HttpConnectionParams.setSocketBufferSize(basicHttpParams, 8192);
        ConnManagerParams.setMaxTotalConnections(basicHttpParams, 200);
        ConnManagerParams.setMaxConnectionsPerRoute(basicHttpParams, new ConnPerRouteBean(20));
        return basicHttpParams;
    }

    @Override
    protected ApacheHttpRequest buildRequest(String object, String string2) {
        if (((String)object).equals("DELETE")) {
            object = new HttpDelete(string2);
            return new ApacheHttpRequest(this.httpClient, (HttpRequestBase)object);
        }
        if (((String)object).equals("GET")) {
            object = new HttpGet(string2);
            return new ApacheHttpRequest(this.httpClient, (HttpRequestBase)object);
        }
        if (((String)object).equals("HEAD")) {
            object = new HttpHead(string2);
            return new ApacheHttpRequest(this.httpClient, (HttpRequestBase)object);
        }
        if (((String)object).equals("POST")) {
            object = new HttpPost(string2);
            return new ApacheHttpRequest(this.httpClient, (HttpRequestBase)object);
        }
        if (((String)object).equals("PUT")) {
            object = new HttpPut(string2);
            return new ApacheHttpRequest(this.httpClient, (HttpRequestBase)object);
        }
        if (((String)object).equals("TRACE")) {
            object = new HttpTrace(string2);
            return new ApacheHttpRequest(this.httpClient, (HttpRequestBase)object);
        }
        if (((String)object).equals("OPTIONS")) {
            object = new HttpOptions(string2);
            return new ApacheHttpRequest(this.httpClient, (HttpRequestBase)object);
        }
        object = new HttpExtensionMethod((String)object, string2);
        return new ApacheHttpRequest(this.httpClient, (HttpRequestBase)object);
    }

    public HttpClient getHttpClient() {
        return this.httpClient;
    }

    @Override
    public void shutdown() {
        this.httpClient.getConnectionManager().shutdown();
    }

    @Override
    public boolean supportsMethod(String string2) {
        return true;
    }

    public static final class Builder {
        private HttpParams params = ApacheHttpTransport.newDefaultHttpParams();
        private ProxySelector proxySelector = ProxySelector.getDefault();
        private SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();

        public ApacheHttpTransport build() {
            return new ApacheHttpTransport(ApacheHttpTransport.newDefaultHttpClient(this.socketFactory, this.params, this.proxySelector));
        }

        public Builder doNotValidateCertificate() throws GeneralSecurityException {
            SSLSocketFactoryExtension sSLSocketFactoryExtension = new SSLSocketFactoryExtension(SslUtils.trustAllSSLContext());
            this.socketFactory = sSLSocketFactoryExtension;
            sSLSocketFactoryExtension.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            return this;
        }

        public HttpParams getHttpParams() {
            return this.params;
        }

        public SSLSocketFactory getSSLSocketFactory() {
            return this.socketFactory;
        }

        public Builder setProxy(HttpHost httpHost) {
            ConnRouteParams.setDefaultProxy(this.params, httpHost);
            if (httpHost == null) return this;
            this.proxySelector = null;
            return this;
        }

        public Builder setProxySelector(ProxySelector proxySelector) {
            this.proxySelector = proxySelector;
            if (proxySelector == null) return this;
            ConnRouteParams.setDefaultProxy(this.params, null);
            return this;
        }

        public Builder setSocketFactory(SSLSocketFactory sSLSocketFactory) {
            this.socketFactory = Preconditions.checkNotNull(sSLSocketFactory);
            return this;
        }

        public Builder trustCertificates(KeyStore keyStore) throws GeneralSecurityException {
            SSLContext sSLContext = SslUtils.getTlsSslContext();
            SslUtils.initSslContext(sSLContext, keyStore, SslUtils.getPkixTrustManagerFactory());
            return this.setSocketFactory(new SSLSocketFactoryExtension(sSLContext));
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

