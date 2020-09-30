/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.io;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import org.apache.http.impl.io.AbstractSessionOutputBuffer;
import org.apache.http.params.HttpParams;

public class SocketOutputBuffer
extends AbstractSessionOutputBuffer {
    public SocketOutputBuffer(Socket socket, int n, HttpParams httpParams) throws IOException {
        if (socket == null) throw new IllegalArgumentException("Socket may not be null");
        int n2 = n;
        if (n < 0) {
            n2 = socket.getSendBufferSize();
        }
        n = n2;
        if (n2 < 1024) {
            n = 1024;
        }
        this.init(socket.getOutputStream(), n, httpParams);
    }
}

