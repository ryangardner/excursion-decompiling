package org.apache.commons.net;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;

public abstract class SocketClient {
   private static final int DEFAULT_CONNECT_TIMEOUT = 0;
   public static final String NETASCII_EOL = "\r\n";
   private static final ServerSocketFactory __DEFAULT_SERVER_SOCKET_FACTORY = ServerSocketFactory.getDefault();
   private static final SocketFactory __DEFAULT_SOCKET_FACTORY = SocketFactory.getDefault();
   private ProtocolCommandSupport __commandSupport;
   protected int _defaultPort_ = 0;
   protected String _hostname_ = null;
   protected InputStream _input_ = null;
   protected OutputStream _output_ = null;
   protected ServerSocketFactory _serverSocketFactory_;
   protected SocketFactory _socketFactory_;
   protected Socket _socket_ = null;
   protected int _timeout_ = 0;
   private Charset charset = Charset.defaultCharset();
   private Proxy connProxy;
   protected int connectTimeout = 0;
   private int receiveBufferSize = -1;
   private int sendBufferSize = -1;

   public SocketClient() {
      this._socketFactory_ = __DEFAULT_SOCKET_FACTORY;
      this._serverSocketFactory_ = __DEFAULT_SERVER_SOCKET_FACTORY;
   }

   private void closeQuietly(Closeable var1) {
      if (var1 != null) {
         try {
            var1.close();
         } catch (IOException var2) {
         }
      }

   }

   private void closeQuietly(Socket var1) {
      if (var1 != null) {
         try {
            var1.close();
         } catch (IOException var2) {
         }
      }

   }

   protected void _connectAction_() throws IOException {
      this._socket_.setSoTimeout(this._timeout_);
      this._input_ = this._socket_.getInputStream();
      this._output_ = this._socket_.getOutputStream();
   }

   public void addProtocolCommandListener(ProtocolCommandListener var1) {
      this.getCommandSupport().addProtocolCommandListener(var1);
   }

   public void connect(String var1) throws SocketException, IOException {
      this.connect(var1, this._defaultPort_);
      this._hostname_ = var1;
   }

   public void connect(String var1, int var2) throws SocketException, IOException {
      this.connect(InetAddress.getByName(var1), var2);
      this._hostname_ = var1;
   }

   public void connect(String var1, int var2, InetAddress var3, int var4) throws SocketException, IOException {
      this.connect(InetAddress.getByName(var1), var2, var3, var4);
      this._hostname_ = var1;
   }

   public void connect(InetAddress var1) throws SocketException, IOException {
      this._hostname_ = null;
      this.connect(var1, this._defaultPort_);
   }

   public void connect(InetAddress var1, int var2) throws SocketException, IOException {
      this._hostname_ = null;
      Socket var3 = this._socketFactory_.createSocket();
      this._socket_ = var3;
      int var4 = this.receiveBufferSize;
      if (var4 != -1) {
         var3.setReceiveBufferSize(var4);
      }

      var4 = this.sendBufferSize;
      if (var4 != -1) {
         this._socket_.setSendBufferSize(var4);
      }

      this._socket_.connect(new InetSocketAddress(var1, var2), this.connectTimeout);
      this._connectAction_();
   }

   public void connect(InetAddress var1, int var2, InetAddress var3, int var4) throws SocketException, IOException {
      this._hostname_ = null;
      Socket var5 = this._socketFactory_.createSocket();
      this._socket_ = var5;
      int var6 = this.receiveBufferSize;
      if (var6 != -1) {
         var5.setReceiveBufferSize(var6);
      }

      var6 = this.sendBufferSize;
      if (var6 != -1) {
         this._socket_.setSendBufferSize(var6);
      }

      this._socket_.bind(new InetSocketAddress(var3, var4));
      this._socket_.connect(new InetSocketAddress(var1, var2), this.connectTimeout);
      this._connectAction_();
   }

   protected void createCommandSupport() {
      this.__commandSupport = new ProtocolCommandSupport(this);
   }

   public void disconnect() throws IOException {
      this.closeQuietly(this._socket_);
      this.closeQuietly((Closeable)this._input_);
      this.closeQuietly((Closeable)this._output_);
      this._socket_ = null;
      this._hostname_ = null;
      this._input_ = null;
      this._output_ = null;
   }

   protected void fireCommandSent(String var1, String var2) {
      if (this.getCommandSupport().getListenerCount() > 0) {
         this.getCommandSupport().fireCommandSent(var1, var2);
      }

   }

   protected void fireReplyReceived(int var1, String var2) {
      if (this.getCommandSupport().getListenerCount() > 0) {
         this.getCommandSupport().fireReplyReceived(var1, var2);
      }

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
      if (this.isConnected()) {
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
         } catch (IOException var2) {
         }
      }

      return false;
   }

   public boolean isConnected() {
      Socket var1 = this._socket_;
      return var1 == null ? false : var1.isConnected();
   }

   public void removeProtocolCommandListener(ProtocolCommandListener var1) {
      this.getCommandSupport().removeProtocolCommandListener(var1);
   }

   public void setCharset(Charset var1) {
      this.charset = var1;
   }

   public void setConnectTimeout(int var1) {
      this.connectTimeout = var1;
   }

   public void setDefaultPort(int var1) {
      this._defaultPort_ = var1;
   }

   public void setDefaultTimeout(int var1) {
      this._timeout_ = var1;
   }

   public void setKeepAlive(boolean var1) throws SocketException {
      this._socket_.setKeepAlive(var1);
   }

   public void setProxy(Proxy var1) {
      this.setSocketFactory(new DefaultSocketFactory(var1));
      this.connProxy = var1;
   }

   public void setReceiveBufferSize(int var1) throws SocketException {
      this.receiveBufferSize = var1;
   }

   public void setSendBufferSize(int var1) throws SocketException {
      this.sendBufferSize = var1;
   }

   public void setServerSocketFactory(ServerSocketFactory var1) {
      if (var1 == null) {
         this._serverSocketFactory_ = __DEFAULT_SERVER_SOCKET_FACTORY;
      } else {
         this._serverSocketFactory_ = var1;
      }

   }

   public void setSoLinger(boolean var1, int var2) throws SocketException {
      this._socket_.setSoLinger(var1, var2);
   }

   public void setSoTimeout(int var1) throws SocketException {
      this._socket_.setSoTimeout(var1);
   }

   public void setSocketFactory(SocketFactory var1) {
      if (var1 == null) {
         this._socketFactory_ = __DEFAULT_SOCKET_FACTORY;
      } else {
         this._socketFactory_ = var1;
      }

      this.connProxy = null;
   }

   public void setTcpNoDelay(boolean var1) throws SocketException {
      this._socket_.setTcpNoDelay(var1);
   }

   public boolean verifyRemote(Socket var1) {
      return var1.getInetAddress().equals(this.getRemoteAddress());
   }
}
