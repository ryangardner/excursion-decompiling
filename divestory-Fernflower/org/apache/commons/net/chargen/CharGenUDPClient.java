package org.apache.commons.net.chargen;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import org.apache.commons.net.DatagramSocketClient;

public final class CharGenUDPClient extends DatagramSocketClient {
   public static final int CHARGEN_PORT = 19;
   public static final int DEFAULT_PORT = 19;
   public static final int NETSTAT_PORT = 15;
   public static final int QUOTE_OF_DAY_PORT = 17;
   public static final int SYSTAT_PORT = 11;
   private final byte[] __receiveData = new byte[512];
   private final DatagramPacket __receivePacket;
   private final DatagramPacket __sendPacket;

   public CharGenUDPClient() {
      byte[] var1 = this.__receiveData;
      this.__receivePacket = new DatagramPacket(var1, var1.length);
      this.__sendPacket = new DatagramPacket(new byte[0], 0);
   }

   public byte[] receive() throws IOException {
      this._socket_.receive(this.__receivePacket);
      int var1 = this.__receivePacket.getLength();
      byte[] var2 = new byte[var1];
      System.arraycopy(this.__receiveData, 0, var2, 0, var1);
      return var2;
   }

   public void send(InetAddress var1) throws IOException {
      this.send(var1, 19);
   }

   public void send(InetAddress var1, int var2) throws IOException {
      this.__sendPacket.setAddress(var1);
      this.__sendPacket.setPort(var2);
      this._socket_.send(this.__sendPacket);
   }
}
