/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class FTPSServerSocketFactory
extends ServerSocketFactory {
    private final SSLContext context;

    public FTPSServerSocketFactory(SSLContext sSLContext) {
        this.context = sSLContext;
    }

    @Override
    public ServerSocket createServerSocket() throws IOException {
        return this.init(this.context.getServerSocketFactory().createServerSocket());
    }

    @Override
    public ServerSocket createServerSocket(int n) throws IOException {
        return this.init(this.context.getServerSocketFactory().createServerSocket(n));
    }

    @Override
    public ServerSocket createServerSocket(int n, int n2) throws IOException {
        return this.init(this.context.getServerSocketFactory().createServerSocket(n, n2));
    }

    @Override
    public ServerSocket createServerSocket(int n, int n2, InetAddress inetAddress) throws IOException {
        return this.init(this.context.getServerSocketFactory().createServerSocket(n, n2, inetAddress));
    }

    public ServerSocket init(ServerSocket serverSocket) {
        ((SSLServerSocket)serverSocket).setUseClientMode(true);
        return serverSocket;
    }
}

