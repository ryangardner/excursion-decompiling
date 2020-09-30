/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.Charset;
import org.apache.commons.net.DatagramSocketFactory;
import org.apache.commons.net.DefaultDatagramSocketFactory;

public abstract class DatagramSocketClient {
    private static final DatagramSocketFactory __DEFAULT_SOCKET_FACTORY = new DefaultDatagramSocketFactory();
    protected boolean _isOpen_ = false;
    protected DatagramSocketFactory _socketFactory_ = __DEFAULT_SOCKET_FACTORY;
    protected DatagramSocket _socket_ = null;
    protected int _timeout_ = 0;
    private Charset charset = Charset.defaultCharset();

    public void close() {
        DatagramSocket datagramSocket = this._socket_;
        if (datagramSocket != null) {
            datagramSocket.close();
        }
        this._socket_ = null;
        this._isOpen_ = false;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public String getCharsetName() {
        return this.charset.name();
    }

    public int getDefaultTimeout() {
        return this._timeout_;
    }

    public InetAddress getLocalAddress() {
        return this._socket_.getLocalAddress();
    }

    public int getLocalPort() {
        return this._socket_.getLocalPort();
    }

    public int getSoTimeout() throws SocketException {
        return this._socket_.getSoTimeout();
    }

    public boolean isOpen() {
        return this._isOpen_;
    }

    public void open() throws SocketException {
        DatagramSocket datagramSocket;
        this._socket_ = datagramSocket = this._socketFactory_.createDatagramSocket();
        datagramSocket.setSoTimeout(this._timeout_);
        this._isOpen_ = true;
    }

    public void open(int n) throws SocketException {
        DatagramSocket datagramSocket;
        this._socket_ = datagramSocket = this._socketFactory_.createDatagramSocket(n);
        datagramSocket.setSoTimeout(this._timeout_);
        this._isOpen_ = true;
    }

    public void open(int n, InetAddress object) throws SocketException {
        this._socket_ = object = this._socketFactory_.createDatagramSocket(n, (InetAddress)object);
        ((DatagramSocket)object).setSoTimeout(this._timeout_);
        this._isOpen_ = true;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public void setDatagramSocketFactory(DatagramSocketFactory datagramSocketFactory) {
        if (datagramSocketFactory == null) {
            this._socketFactory_ = __DEFAULT_SOCKET_FACTORY;
            return;
        }
        this._socketFactory_ = datagramSocketFactory;
    }

    public void setDefaultTimeout(int n) {
        this._timeout_ = n;
    }

    public void setSoTimeout(int n) throws SocketException {
        this._socket_.setSoTimeout(n);
    }
}

