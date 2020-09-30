package org.apache.commons.net.echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import org.apache.commons.net.discard.DiscardUDPClient;

public final class EchoUDPClient extends DiscardUDPClient {
   public static final int DEFAULT_PORT = 7;
   private final DatagramPacket __receivePacket = new DatagramPacket(new byte[0], 0);

   public int receive(byte[] var1) throws IOException {
      return this.receive(var1, var1.length);
   }

   public int receive(byte[] var1, int var2) throws IOException {
      this.__receivePacket.setData(var1);
      this.__receivePacket.setLength(var2);
      this._socket_.receive(this.__receivePacket);
      return this.__receivePacket.getLength();
   }

   public void send(byte[] var1, int var2, InetAddress var3) throws IOException {
      this.send(var1, var2, var3, 7);
   }

   public void send(byte[] var1, InetAddress var2) throws IOException {
      this.send(var1, var1.length, var2, 7);
   }
}
