package org.apache.commons.net;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.Charset;

public abstract class DatagramSocketClient {
   private static final DatagramSocketFactory __DEFAULT_SOCKET_FACTORY = new DefaultDatagramSocketFactory();
   protected boolean _isOpen_ = false;
   protected DatagramSocketFactory _socketFactory_;
   protected DatagramSocket _socket_ = null;
   protected int _timeout_ = 0;
   private Charset charset = Charset.defaultCharset();

   public DatagramSocketClient() {
      this._socketFactory_ = __DEFAULT_SOCKET_FACTORY;
   }

   public void close() {
      DatagramSocket var1 = this._socket_;
      if (var1 != null) {
         var1.close();
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
      DatagramSocket var1 = this._socketFactory_.createDatagramSocket();
      this._socket_ = var1;
      var1.setSoTimeout(this._timeout_);
      this._isOpen_ = true;
   }

   public void open(int var1) throws SocketException {
      DatagramSocket var2 = this._socketFactory_.createDatagramSocket(var1);
      this._socket_ = var2;
      var2.setSoTimeout(this._timeout_);
      this._isOpen_ = true;
   }

   public void open(int var1, InetAddress var2) throws SocketException {
      DatagramSocket var3 = this._socketFactory_.createDatagramSocket(var1, var2);
      this._socket_ = var3;
      var3.setSoTimeout(this._timeout_);
      this._isOpen_ = true;
   }

   public void setCharset(Charset var1) {
      this.charset = var1;
   }

   public void setDatagramSocketFactory(DatagramSocketFactory var1) {
      if (var1 == null) {
         this._socketFactory_ = __DEFAULT_SOCKET_FACTORY;
      } else {
         this._socketFactory_ = var1;
      }

   }

   public void setDefaultTimeout(int var1) {
      this._timeout_ = var1;
   }

   public void setSoTimeout(int var1) throws SocketException {
      this._socket_.setSoTimeout(var1);
   }
}
