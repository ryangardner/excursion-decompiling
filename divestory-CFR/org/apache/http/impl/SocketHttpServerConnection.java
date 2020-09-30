/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import org.apache.http.HttpInetConnection;
import org.apache.http.impl.AbstractHttpServerConnection;
import org.apache.http.impl.io.SocketInputBuffer;
import org.apache.http.impl.io.SocketOutputBuffer;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class SocketHttpServerConnection
extends AbstractHttpServerConnection
implements HttpInetConnection {
    private volatile boolean open;
    private volatile Socket socket = null;

    protected void assertNotOpen() {
        if (this.open) throw new IllegalStateException("Connection is already open");
    }

    @Override
    protected void assertOpen() {
        if (!this.open) throw new IllegalStateException("Connection is not open");
    }

    protected void bind(Socket socket, HttpParams httpParams) throws IOException {
        if (socket == null) throw new IllegalArgumentException("Socket may not be null");
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        this.socket = socket;
        int n = HttpConnectionParams.getSocketBufferSize(httpParams);
        this.init(this.createHttpDataReceiver(socket, n, httpParams), this.createHttpDataTransmitter(socket, n, httpParams), httpParams);
        this.open = true;
    }

    @Override
    public void close() throws IOException {
        if (!this.open) {
            return;
        }
        this.open = false;
        this.open = false;
        Socket socket = this.socket;
        this.doFlush();
        try {
            try {
                socket.shutdownOutput();
            }
            catch (IOException iOException) {}
            socket.shutdownInput();
            return;
        }
        catch (IOException | UnsupportedOperationException exception) {
            return;
        }
        finally {
            socket.close();
        }
    }

    protected SessionInputBuffer createHttpDataReceiver(Socket socket, int n, HttpParams httpParams) throws IOException {
        return this.createSessionInputBuffer(socket, n, httpParams);
    }

    protected SessionOutputBuffer createHttpDataTransmitter(Socket socket, int n, HttpParams httpParams) throws IOException {
        return this.createSessionOutputBuffer(socket, n, httpParams);
    }

    protected SessionInputBuffer createSessionInputBuffer(Socket socket, int n, HttpParams httpParams) throws IOException {
        return new SocketInputBuffer(socket, n, httpParams);
    }

    protected SessionOutputBuffer createSessionOutputBuffer(Socket socket, int n, HttpParams httpParams) throws IOException {
        return new SocketOutputBuffer(socket, n, httpParams);
    }

    @Override
    public InetAddress getLocalAddress() {
        if (this.socket == null) return null;
        return this.socket.getLocalAddress();
    }

    @Override
    public int getLocalPort() {
        if (this.socket == null) return -1;
        return this.socket.getLocalPort();
    }

    @Override
    public InetAddress getRemoteAddress() {
        if (this.socket == null) return null;
        return this.socket.getInetAddress();
    }

    @Override
    public int getRemotePort() {
        if (this.socket == null) return -1;
        return this.socket.getPort();
    }

    protected Socket getSocket() {
        return this.socket;
    }

    @Override
    public int getSocketTimeout() {
        if (this.socket == null) return -1;
        try {
            return this.socket.getSoTimeout();
        }
        catch (SocketException socketException) {
            return -1;
        }
    }

    @Override
    public boolean isOpen() {
        return this.open;
    }

    @Override
    public void setSocketTimeout(int n) {
        this.assertOpen();
        if (this.socket == null) return;
        try {
            this.socket.setSoTimeout(n);
            return;
        }
        catch (SocketException socketException) {
            return;
        }
    }

    @Override
    public void shutdown() throws IOException {
        this.open = false;
        Socket socket = this.socket;
        if (socket == null) return;
        socket.close();
    }
}

