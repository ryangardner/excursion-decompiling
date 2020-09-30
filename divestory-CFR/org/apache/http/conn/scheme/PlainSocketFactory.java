/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn.scheme;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.HostNameResolver;
import org.apache.http.conn.scheme.SchemeSocketFactory;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class PlainSocketFactory
implements SocketFactory,
SchemeSocketFactory {
    private final HostNameResolver nameResolver;

    public PlainSocketFactory() {
        this.nameResolver = null;
    }

    @Deprecated
    public PlainSocketFactory(HostNameResolver hostNameResolver) {
        this.nameResolver = hostNameResolver;
    }

    public static PlainSocketFactory getSocketFactory() {
        return new PlainSocketFactory();
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

    @Override
    public Socket connectSocket(Socket socket, InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, HttpParams httpParams) throws IOException, ConnectTimeoutException {
        if (inetSocketAddress == null) throw new IllegalArgumentException("Remote address may not be null");
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        Socket socket2 = socket;
        if (socket == null) {
            socket2 = this.createSocket();
        }
        if (inetSocketAddress2 != null) {
            socket2.setReuseAddress(HttpConnectionParams.getSoReuseaddr(httpParams));
            socket2.bind(inetSocketAddress2);
        }
        int n = HttpConnectionParams.getConnectionTimeout(httpParams);
        int n2 = HttpConnectionParams.getSoTimeout(httpParams);
        try {
            socket2.setSoTimeout(n2);
            socket2.connect(inetSocketAddress, n);
            return socket2;
        }
        catch (SocketTimeoutException socketTimeoutException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Connect to ");
            stringBuilder.append(inetSocketAddress);
            stringBuilder.append(" timed out");
            throw new ConnectTimeoutException(stringBuilder.toString());
        }
    }

    @Override
    public Socket createSocket() {
        return new Socket();
    }

    @Override
    public Socket createSocket(HttpParams httpParams) {
        return new Socket();
    }

    @Override
    public final boolean isSecure(Socket socket) throws IllegalArgumentException {
        if (socket == null) throw new IllegalArgumentException("Socket may not be null.");
        if (socket.isClosed()) throw new IllegalArgumentException("Socket is closed.");
        return false;
    }
}

