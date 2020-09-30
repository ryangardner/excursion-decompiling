/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

@Deprecated
public final class MultihomePlainSocketFactory
implements SocketFactory {
    private static final MultihomePlainSocketFactory DEFAULT_FACTORY = new MultihomePlainSocketFactory();

    private MultihomePlainSocketFactory() {
    }

    public static MultihomePlainSocketFactory getSocketFactory() {
        return DEFAULT_FACTORY;
    }

    @Override
    public Socket connectSocket(Socket arrinetAddress, String iterator2, int n, InetAddress inetAddress, int n2, HttpParams object) throws IOException {
        if (iterator2 == null) throw new IllegalArgumentException("Target host may not be null.");
        if (object == null) throw new IllegalArgumentException("Parameters may not be null.");
        Object object2 = arrinetAddress;
        if (arrinetAddress == null) {
            object2 = this.createSocket();
        }
        if (inetAddress != null || n2 > 0) {
            int n3 = n2;
            if (n2 < 0) {
                n3 = 0;
            }
            ((Socket)object2).bind(new InetSocketAddress(inetAddress, n3));
        }
        n2 = HttpConnectionParams.getConnectionTimeout((HttpParams)object);
        arrinetAddress = InetAddress.getAllByName((String)((Object)iterator2));
        iterator2 = new ArrayList(arrinetAddress.length);
        iterator2.addAll(Arrays.asList(arrinetAddress));
        Collections.shuffle(iterator2);
        arrinetAddress = null;
        iterator2 = iterator2.iterator();
        while (iterator2.hasNext()) {
            inetAddress = (InetAddress)iterator2.next();
            try {
                object = new InetSocketAddress(inetAddress, n);
                ((Socket)object2).connect((SocketAddress)object, n2);
                break;
            }
            catch (IOException iOException) {
                object2 = new Socket();
            }
            catch (SocketTimeoutException socketTimeoutException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Connect to ");
                stringBuilder.append(inetAddress);
                stringBuilder.append(" timed out");
                throw new ConnectTimeoutException(stringBuilder.toString());
            }
        }
        if (arrinetAddress != null) throw arrinetAddress;
        return object2;
    }

    @Override
    public Socket createSocket() {
        return new Socket();
    }

    @Override
    public final boolean isSecure(Socket socket) throws IllegalArgumentException {
        if (socket == null) throw new IllegalArgumentException("Socket may not be null.");
        if (socket.getClass() != Socket.class) throw new IllegalArgumentException("Socket not created by this factory.");
        if (socket.isClosed()) throw new IllegalArgumentException("Socket is closed.");
        return false;
    }
}

