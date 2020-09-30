/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.http.config.SocketConfig
 *  org.apache.http.config.SocketConfig$Builder
 *  org.apache.http.conn.HttpClientConnectionManager
 *  org.apache.http.conn.socket.LayeredConnectionSocketFactory
 *  org.apache.http.conn.ssl.SSLConnectionSocketFactory
 *  org.apache.http.impl.client.CloseableHttpClient
 *  org.apache.http.impl.client.HttpClientBuilder
 *  org.apache.http.impl.conn.PoolingHttpClientConnectionManager
 *  org.apache.http.impl.conn.SystemDefaultRoutePlanner
 */
package com.google.api.client.googleapis.apache;

import com.google.api.client.googleapis.GoogleUtils;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.util.SslUtils;
import java.io.IOException;
import java.net.ProxySelector;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import org.apache.http.client.HttpClient;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;

public final class GoogleApacheHttpTransport {
    private GoogleApacheHttpTransport() {
    }

    public static ApacheHttpTransport newTrustedTransport() throws GeneralSecurityException, IOException {
        SocketConfig socketConfig = SocketConfig.custom().setRcvBufSize(8192).setSndBufSize(8192).build();
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(-1L, TimeUnit.MILLISECONDS);
        poolingHttpClientConnectionManager.setValidateAfterInactivity(-1);
        KeyStore keyStore = GoogleUtils.getCertificateTrustStore();
        SSLContext sSLContext = SslUtils.getTlsSslContext();
        SslUtils.initSslContext(sSLContext, keyStore, SslUtils.getPkixTrustManagerFactory());
        sSLContext = new SSLConnectionSocketFactory(sSLContext);
        return new ApacheHttpTransport((HttpClient)HttpClientBuilder.create().useSystemProperties().setSSLSocketFactory((LayeredConnectionSocketFactory)sSLContext).setDefaultSocketConfig(socketConfig).setMaxConnTotal(200).setMaxConnPerRoute(20).setRoutePlanner((HttpRoutePlanner)new SystemDefaultRoutePlanner(ProxySelector.getDefault())).setConnectionManager((HttpClientConnectionManager)poolingHttpClientConnectionManager).disableRedirectHandling().disableAutomaticRetries().build());
    }
}

