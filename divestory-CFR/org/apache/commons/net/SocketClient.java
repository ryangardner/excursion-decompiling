/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.charset.Charset;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import org.apache.commons.net.DefaultSocketFactory;
import org.apache.commons.net.ProtocolCommandListener;
import org.apache.commons.net.ProtocolCommandSupport;

public abstract class SocketClient {
    private static final int DEFAULT_CONNECT_TIMEOUT = 0;
    public static final String NETASCII_EOL = "\r\n";
    private static final ServerSocketFactory __DEFAULT_SERVER_SOCKET_FACTORY;
    private static final SocketFactory __DEFAULT_SOCKET_FACTORY;
    private ProtocolCommandSupport __commandSupport;
    protected int _defaultPort_ = 0;
    protected String _hostname_ = null;
    protected InputStream _input_ = null;
    protected OutputStream _output_ = null;
    protected ServerSocketFactory _serverSocketFactory_ = __DEFAULT_SERVER_SOCKET_FACTORY;
    protected SocketFactory _socketFactory_ = __DEFAULT_SOCKET_FACTORY;
    protected Socket _socket_ = null;
    protected int _timeout_ = 0;
    private Charset charset = Charset.defaultCharset();
    private Proxy connProxy;
    protected int connectTimeout = 0;
    private int receiveBufferSize = -1;
    private int sendBufferSize = -1;

    static {
        __DEFAULT_SOCKET_FACTORY = SocketFactory.getDefault();
        __DEFAULT_SERVER_SOCKET_FACTORY = ServerSocketFactory.getDefault();
    }

    private void closeQuietly(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    private void closeQuietly(Socket socket) {
        if (socket == null) return;
        try {
            socket.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    protected void _connectAction_() throws IOException {
        this._socket_.setSoTimeout(this._timeout_);
        this._input_ = this._socket_.getInputStream();
        this._output_ = this._socket_.getOutputStream();
    }

    public void addProtocolCommandListener(ProtocolCommandListener protocolCommandListener) {
        this.getCommandSupport().addProtocolCommandListener(protocolCommandListener);
    }

    public void connect(String string2) throws SocketException, IOException {
        this.connect(string2, this._defaultPort_);
        this._hostname_ = string2;
    }

    public void connect(String string2, int n) throws SocketException, IOException {
        this.connect(InetAddress.getByName(string2), n);
        this._hostname_ = string2;
    }

    public void connect(String string2, int n, InetAddress inetAddress, int n2) throws SocketException, IOException {
        this.connect(InetAddress.getByName(string2), n, inetAddress, n2);
        this._hostname_ = string2;
    }

    public void connect(InetAddress inetAddress) throws SocketException, IOException {
        this._hostname_ = null;
        this.connect(inetAddress, this._defaultPort_);
    }

    public void connect(InetAddress inetAddress, int n) throws SocketException, IOException {
        Socket socket;
        this._hostname_ = null;
        this._socket_ = socket = this._socketFactory_.createSocket();
        int n2 = this.receiveBufferSize;
        if (n2 != -1) {
            socket.setReceiveBufferSize(n2);
        }
        if ((n2 = this.sendBufferSize) != -1) {
            this._socket_.setSendBufferSize(n2);
        }
        this._socket_.connect(new InetSocketAddress(inetAddress, n), this.connectTimeout);
        this._connectAction_();
    }

    public void connect(InetAddress inetAddress, int n, InetAddress inetAddress2, int n2) throws SocketException, IOException {
        Socket socket;
        this._hostname_ = null;
        this._socket_ = socket = this._socketFactory_.createSocket();
        int n3 = this.receiveBufferSize;
        if (n3 != -1) {
            socket.setReceiveBufferSize(n3);
        }
        if ((n3 = this.sendBufferSize) != -1) {
            this._socket_.setSendBufferSize(n3);
        }
        this._socket_.bind(new InetSocketAddress(inetAddress2, n2));
        this._socket_.connect(new InetSocketAddress(inetAddress, n), this.connectTimeout);
        this._connectAction_();
    }

    protected void createCommandSupport() {
        this.__commandSupport = new ProtocolCommandSupport(this);
    }

    public void disconnect() throws IOException {
        this.closeQuietly(this._socket_);
        this.closeQuietly(this._input_);
        this.closeQuietly(this._output_);
        this._socket_ = null;
        this._hostname_ = null;
        this._input_ = null;
        this._output_ = null;
    }

    protected void fireCommandSent(String string2, String string3) {
        if (this.getCommandSupport().getListenerCount() <= 0) return;
        this.getCommandSupport().fireCommandSent(string2, string3);
    }

    protected void fireReplyReceived(int n, String string2) {
        if (this.getCommandSupport().getListenerCount() <= 0) return;
        this.getCommandSupport().fireReplyReceived(n, string2);
    }

    public Charset getCharset() {
        return this.charset;
    }

    public String getCharsetName() {
        return this.charset.name();
    }

    protected ProtocolCommandSupport getCommandSupport() {
        return this.__commandSupport;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public int getDefaultPort() {
        return this._defaultPort_;
    }

    public int getDefaultTimeout() {
        return this._timeout_;
    }

    public boolean getKeepAlive() throws SocketException {
        return this._socket_.getKeepAlive();
    }

    public InetAddress getLocalAddress() {
        return this._socket_.getLocalAddress();
    }

    public int getLocalPort() {
        return this._socket_.getLocalPort();
    }

    public Proxy getProxy() {
        return this.connProxy;
    }

    protected int getReceiveBufferSize() {
        return this.receiveBufferSize;
    }

    public InetAddress getRemoteAddress() {
        return this._socket_.getInetAddress();
    }

    public int getRemotePort() {
        return this._socket_.getPort();
    }

    protected int getSendBufferSize() {
        return this.sendBufferSize;
    }

    public ServerSocketFactory getServerSocketFactory() {
        return this._serverSocketFactory_;
    }

    public int getSoLinger() throws SocketException {
        return this._socket_.getSoLinger();
    }

    public int getSoTimeout() throws SocketException {
        return this._socket_.getSoTimeout();
    }

    public boolean getTcpNoDelay() throws SocketException {
        return this._socket_.getTcpNoDelay();
    }

    public boolean isAvailable() {
        if (!this.isConnected()) return false;
        try {
            if (this._socket_.getInetAddress() == null) {
                return false;
            }
            if (this._socket_.getPort() == 0) {
                return false;
            }
            if (this._socket_.getRemoteSocketAddress() == null) {
                return false;
            }
            if (this._socket_.isClosed()) {
                return false;
            }
            if (this._socket_.isInputShutdown()) {
                return false;
            }
            if (this._socket_.isOutputShutdown()) {
                return false;
            }
            this._socket_.getInputStream();
            this._socket_.getOutputStream();
            return true;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    public boolean isConnected() {
        Socket socket = this._socket_;
        if (socket != null) return socket.isConnected();
        return false;
    }

    public void removeProtocolCommandListener(ProtocolCommandListener protocolCommandListener) {
        this.getCommandSupport().removeProtocolCommandListener(protocolCommandListener);
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public void setConnectTimeout(int n) {
        this.connectTimeout = n;
    }

    public void setDefaultPort(int n) {
        this._defaultPort_ = n;
    }

    public void setDefaultTimeout(int n) {
        this._timeout_ = n;
    }

    public void setKeepAlive(boolean bl) throws SocketException {
        this._socket_.setKeepAlive(bl);
    }

    public void setProxy(Proxy proxy) {
        this.setSocketFactory(new DefaultSocketFactory(proxy));
        this.connProxy = proxy;
    }

    public void setReceiveBufferSize(int n) throws SocketException {
        this.receiveBufferSize = n;
    }

    public void setSendBufferSize(int n) throws SocketException {
        this.sendBufferSize = n;
    }

    public void setServerSocketFactory(ServerSocketFactory serverSocketFactory) {
        if (serverSocketFactory == null) {
            this._serverSocketFactory_ = __DEFAULT_SERVER_SOCKET_FACTORY;
            return;
        }
        this._serverSocketFactory_ = serverSocketFactory;
    }

    public void setSoLinger(boolean bl, int n) throws SocketException {
        this._socket_.setSoLinger(bl, n);
    }

    public void setSoTimeout(int n) throws SocketException {
        this._socket_.setSoTimeout(n);
    }

    public void setSocketFactory(SocketFactory socketFactory) {
        this._socketFactory_ = socketFactory == null ? __DEFAULT_SOCKET_FACTORY : socketFactory;
        this.connProxy = null;
    }

    public void setTcpNoDelay(boolean bl) throws SocketException {
        this._socket_.setTcpNoDelay(bl);
    }

    public boolean verifyRemote(Socket socket) {
        return socket.getInetAddress().equals(this.getRemoteAddress());
    }
}

