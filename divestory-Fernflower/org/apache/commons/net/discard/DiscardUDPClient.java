package org.apache.commons.net.discard;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import org.apache.commons.net.DatagramSocketClient;

public class DiscardUDPClient extends DatagramSocketClient {
   public static final int DEFAULT_PORT = 9;
   DatagramPacket _sendPacket = new DatagramPacket(new byte[0], 0);

   public void send(byte[] var1, int var2, InetAddress var3) throws IOException {
      this.send(var1, var2, var3, 9);
   }

   public void send(byte[] var1, int var2, InetAddress var3, int var4) throws IOException {
      this._sendPacket.setData(var1);
      this._sendPacket.setLength(var2);
      this._sendPacket.setAddress(var3);
      this._sendPacket.setPort(var4);
      this._socket_.send(this._sendPacket);
   }

   public void send(byte[] var1, InetAddress var2) throws IOException {
      this.send(var1, var1.length, var2, 9);
   }
}
