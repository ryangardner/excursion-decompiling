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
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

@Deprecated
class SocketFactoryAdaptor
implements SocketFactory {
    private final SchemeSocketFactory factory;

    SocketFactoryAdaptor(SchemeSocketFactory schemeSocketFactory) {
        this.factory = schemeSocketFactory;
    }

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
        object = new InetSocketAddress(InetAddress.getByName((String)object), n);
        return this.factory.connectSocket(socket, (InetSocketAddress)object, (InetSocketAddress)serializable, httpParams);
    }

    @Override
    public Socket createSocket() throws IOException {
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        return this.factory.createSocket(basicHttpParams);
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (!(object instanceof SocketFactoryAdaptor)) return this.factory.equals(object);
        return this.factory.equals(((SocketFactoryAdaptor)object).factory);
    }

    public SchemeSocketFactory getFactory() {
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

