/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SocketOutputStream
extends FilterOutputStream {
    private final Socket __socket;

    public SocketOutputStream(Socket socket, OutputStream outputStream2) {
        super(outputStream2);
        this.__socket = socket;
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.__socket.close();
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        this.out.write(arrby, n, n2);
    }
}

