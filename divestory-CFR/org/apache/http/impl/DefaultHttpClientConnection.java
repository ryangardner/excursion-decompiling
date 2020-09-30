/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl;

import java.io.IOException;
import java.net.Socket;
import org.apache.http.impl.SocketHttpClientConnection;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class DefaultHttpClientConnection
extends SocketHttpClientConnection {
    @Override
    public void bind(Socket socket, HttpParams httpParams) throws IOException {
        if (socket == null) throw new IllegalArgumentException("Socket may not be null");
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        this.assertNotOpen();
        socket.setTcpNoDelay(HttpConnectionParams.getTcpNoDelay(httpParams));
        socket.setSoTimeout(HttpConnectionParams.getSoTimeout(httpParams));
        int n = HttpConnectionParams.getLinger(httpParams);
        if (n >= 0) {
            boolean bl = n > 0;
            socket.setSoLinger(bl, n);
        }
        super.bind(socket, httpParams);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        if (this.isOpen()) {
            stringBuffer.append(this.getRemotePort());
        } else {
            stringBuffer.append("closed");
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}

