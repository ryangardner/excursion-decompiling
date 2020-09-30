/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http.apache;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;

final class SSLSocketFactoryExtension
extends org.apache.http.conn.ssl.SSLSocketFactory {
    private final SSLSocketFactory socketFactory;

    SSLSocketFactoryExtension(SSLContext sSLContext) throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        super((KeyStore)null);
        this.socketFactory = sSLContext.getSocketFactory();
    }

    @Override
    public Socket createSocket() throws IOException {
        return this.socketFactory.createSocket();
    }

    @Override
    public Socket createSocket(Socket socket, String string2, int n, boolean bl) throws IOException, UnknownHostException {
        socket = (SSLSocket)this.socketFactory.createSocket(socket, string2, n, bl);
        this.getHostnameVerifier().verify(string2, (SSLSocket)socket);
        return socket;
    }
}

