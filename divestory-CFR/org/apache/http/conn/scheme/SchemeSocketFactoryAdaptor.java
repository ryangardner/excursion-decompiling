/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn.scheme;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.SchemeSocketFactory;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.params.HttpParams;

@Deprecated
class SchemeSocketFactoryAdaptor
implements SchemeSocketFactory {
    private final SocketFactory factory;

    SchemeSocketFactoryAdaptor(SocketFactory socketFactory) {
        this.factory = socketFactory;
    }

    @Override
    public Socket connectSocket(Socket socket, InetSocketAddress serializable, InetSocketAddress inetSocketAddress, HttpParams httpParams) throws IOException, UnknownHostException, ConnectTimeoutException {
        int n;
        String string2 = serializable.getHostName();
        int n2 = serializable.getPort();
        if (inetSocketAddress != null) {
            serializable = inetSocketAddress.getAddress();
            n = inetSocketAddress.getPort();
            return this.factory.connectSocket(socket, string2, n2, (InetAddress)serializable, n, httpParams);
        }
        serializable = null;
        n = 0;
        return this.factory.connectSocket(socket, string2, n2, (InetAddress)serializable, n, httpParams);
    }

    @Override
    public Socket createSocket(HttpParams httpParams) throws IOException {
        return this.factory.createSocket();
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (!(object instanceof SchemeSocketFactoryAdaptor)) return this.factory.equals(object);
        return this.factory.equals(((SchemeSocketFactoryAdaptor)object).factory);
    }

    public SocketFactory getFactory() {
        return this.factory;
    }

    public int hashCode() {
        return this.factory.hashCode();
    }

    @Override
    public boolean isSecure(Socket socket) throws IllegalArgumentException {
        return this.factory.isSecure(socket);
    }
}

