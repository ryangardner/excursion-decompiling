package org.apache.commons.net.tftp;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import org.apache.commons.net.DatagramSocketClient;

public class TFTP extends DatagramSocketClient {
   public static final int ASCII_MODE = 0;
   public static final int BINARY_MODE = 1;
   public static final int DEFAULT_PORT = 69;
   public static final int DEFAULT_TIMEOUT = 5000;
   public static final int IMAGE_MODE = 1;
   public static final int NETASCII_MODE = 0;
   public static final int OCTET_MODE = 1;
   static final int PACKET_SIZE = 516;
   private byte[] __receiveBuffer;
   private DatagramPacket __receiveDatagram;
   private DatagramPacket __sendDatagram;
   byte[] _sendBuffer;

   public TFTP() {
      this.setDefaultTimeout(5000);
      this.__receiveBuffer = null;
      this.__receiveDatagram = null;
   }

   public static final String getModeName(int var0) {
      return TFTPRequestPacket._modeStrings[var0];
   }

   public final void beginBufferedOps() {
      this.__receiveBuffer = new byte[516];
      byte[] var1 = this.__receiveBuffer;
      this.__receiveDatagram = new DatagramPacket(var1, var1.length);
      this._sendBuffer = new byte[516];
      var1 = this._sendBuffer;
      this.__sendDatagram = new DatagramPacket(var1, var1.length);
   }

   public final TFTPPacket bufferedReceive() throws IOException, InterruptedIOException, SocketException, TFTPPacketException {
      this.__receiveDatagram.setData(this.__receiveBuffer);
      this.__receiveDatagram.setLength(this.__receiveBuffer.length);
      this._socket_.receive(this.__receiveDatagram);
      return TFTPPacket.newTFTPPacket(this.__receiveDatagram);
   }

   public final void bufferedSend(TFTPPacket var1) throws IOException {
      this._socket_.send(var1._newDatagram(this.__sendDatagram, this._sendBuffer));
   }

   public final void discardPackets() throws IOException {
      DatagramPacket var1 = new DatagramPacket(new byte[516], 516);
      int var2 = this.getSoTimeout();
      this.setSoTimeout(1);

      while(true) {
         try {
            this._socket_.receive(var1);
         } catch (InterruptedIOException | SocketException var3) {
            this.setSoTimeout(var2);
            return;
         }
      }
   }

   public final void endBufferedOps() {
      this.__receiveBuffer = null;
      this.__receiveDatagram = null;
      this._sendBuffer = null;
      this.__sendDatagram = null;
   }

   public final TFTPPacket receive() throws IOException, InterruptedIOException, SocketException, TFTPPacketException {
      DatagramPacket var1 = new DatagramPacket(new byte[516], 516);
      this._socket_.receive(var1);
      return TFTPPacket.newTFTPPacket(var1);
   }

   public final void send(TFTPPacket var1) throws IOException {
      this._socket_.send(var1.newDatagram());
   }
}
