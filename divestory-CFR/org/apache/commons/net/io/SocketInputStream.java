/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class SocketInputStream
extends FilterInputStream {
    private final Socket __socket;

    public SocketInputStream(Socket socket, InputStream inputStream2) {
        super(inputStream2);
        this.__socket = socket;
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.__socket.close();
    }
}

