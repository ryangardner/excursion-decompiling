/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import javax.net.SocketFactory;

public class DefaultSocketFactory
extends SocketFactory {
    private final Proxy connProxy;

    public DefaultSocketFactory() {
        this(null);
    }

    public DefaultSocketFactory(Proxy proxy) {
        this.connProxy = proxy;
    }

    public ServerSocket createServerSocket(int n) throws IOException {
        return new ServerSocket(n);
    }

    public ServerSocket createServerSocket(int n, int n2) throws IOException {
        return new ServerSocket(n, n2);
    }

    public ServerSocket createServerSocket(int n, int n2, InetAddress inetAddress) throws IOException {
        return new ServerSocket(n, n2, inetAddress);
    }

    @Override
    public Socket createSocket() throws IOException {
        if (this.connProxy == null) return new Socket();
        return new Socket(this.connProxy);
    }

    @Override
    public Socket createSocket(String string2, int n) throws UnknownHostException, IOException {
        if (this.connProxy == null) return new Socket(string2, n);
        Socket socket = new Socket(this.connProxy);
        socket.connect(new InetSocketAddress(string2, n));
        return socket;
    }

    @Override
    public Socket createSocket(String string2, int n, InetAddress inetAddress, int n2) throws UnknownHostException, IOException {
        if (this.connProxy == null) return new Socket(string2, n, inetAddress, n2);
        Socket socket = new Socket(this.connProxy);
        socket.bind(new InetSocketAddress(inetAddress, n2));
        socket.connect(new InetSocketAddress(string2, n));
        return socket;
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int n) throws IOException {
        if (this.connProxy == null) return new Socket(inetAddress, n);
        Socket socket = new Socket(this.connProxy);
        socket.connect(new InetSocketAddress(inetAddress, n));
        return socket;
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int n, InetAddress inetAddress2, int n2) throws IOException {
        if (this.connProxy == null) return new Socket(inetAddress, n, inetAddress2, n2);
        Socket socket = new Socket(this.connProxy);
        socket.bind(new InetSocketAddress(inetAddress2, n2));
        socket.connect(new InetSocketAddress(inetAddress, n));
        return socket;
    }
}

