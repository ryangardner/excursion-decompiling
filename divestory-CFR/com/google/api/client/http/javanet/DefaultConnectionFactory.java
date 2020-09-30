/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http.javanet;

import com.google.api.client.http.javanet.ConnectionFactory;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class DefaultConnectionFactory
implements ConnectionFactory {
    private final Proxy proxy;

    public DefaultConnectionFactory() {
        this(null);
    }

    public DefaultConnectionFactory(Proxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public HttpURLConnection openConnection(URL object) throws IOException {
        Proxy proxy = this.proxy;
        if (proxy == null) {
            object = ((URL)object).openConnection();
            return (HttpURLConnection)object;
        }
        object = ((URL)object).openConnection(proxy);
        return (HttpURLConnection)object;
    }
}

