/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;

public class FTPSSocketFactory
extends SocketFactory {
    private final SSLContext context;

    public FTPSSocketFactory(SSLContext sSLContext) {
        this.context = sSLContext;
    }

    @Deprecated
    public ServerSocket createServerSocket(int n) throws IOException {
        return this.init(this.context.getServerSocketFactory().createServerSocket(n));
    }

    @Deprecated
    public ServerSocket createServerSocket(int n, int n2) throws IOException {
        return this.init(this.context.getServerSocketFactory().createServerSocket(n, n2));
    }

    @Deprecated
    public ServerSocket createServerSocket(int n, int n2, InetAddress inetAddress) throws IOException {
        return this.init(this.context.getServerSocketFactory().createServerSocket(n, n2, inetAddress));
    }

    @Override
    public Socket createSocket() throws IOException {
        return this.context.getSocketFactory().createSocket();
    }

    @Override
    public Socket createSocket(String string2, int n) throws UnknownHostException, IOException {
        return this.context.getSocketFactory().createSocket(string2, n);
    }

    @Override
    public Socket createSocket(String string2, int n, InetAddress inetAddress, int n2) throws UnknownHostException, IOException {
        return this.context.getSocketFactory().createSocket(string2, n, inetAddress, n2);
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int n) throws IOException {
        return this.context.getSocketFactory().createSocket(inetAddress, n);
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int n, InetAddress inetAddress2, int n2) throws IOException {
        return this.context.getSocketFactory().createSocket(inetAddress, n, inetAddress2, n2);
    }

    @Deprecated
    public ServerSocket init(ServerSocket serverSocket) throws IOException {
        ((SSLServerSocket)serverSocket).setUseClientMode(true);
        return serverSocket;
    }
}

