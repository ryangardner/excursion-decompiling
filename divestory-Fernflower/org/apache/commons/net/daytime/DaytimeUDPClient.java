package org.apache.commons.net.daytime;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import org.apache.commons.net.DatagramSocketClient;

public final class DaytimeUDPClient extends DatagramSocketClient {
   public static final int DEFAULT_PORT = 13;
   private final byte[] __dummyData = new byte[1];
   private final byte[] __timeData = new byte[256];

   public String getTime(InetAddress var1) throws IOException {
      return this.getTime(var1, 13);
   }

   public String getTime(InetAddress var1, int var2) throws IOException {
      byte[] var3 = this.__dummyData;
      DatagramPacket var4 = new DatagramPacket(var3, var3.length, var1, var2);
      var3 = this.__timeData;
      DatagramPacket var5 = new DatagramPacket(var3, var3.length);
      this._socket_.send(var4);
      this._socket_.receive(var5);
      return new String(var5.getData(), 0, var5.getLength(), this.getCharsetName());
   }
}
