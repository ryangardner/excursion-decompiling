package org.apache.commons.net.time;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Date;
import org.apache.commons.net.DatagramSocketClient;

public final class TimeUDPClient extends DatagramSocketClient {
   public static final int DEFAULT_PORT = 37;
   public static final long SECONDS_1900_TO_1970 = 2208988800L;
   private final byte[] __dummyData = new byte[1];
   private final byte[] __timeData = new byte[4];

   public Date getDate(InetAddress var1) throws IOException {
      return new Date((this.getTime(var1, 37) - 2208988800L) * 1000L);
   }

   public Date getDate(InetAddress var1, int var2) throws IOException {
      return new Date((this.getTime(var1, var2) - 2208988800L) * 1000L);
   }

   public long getTime(InetAddress var1) throws IOException {
      return this.getTime(var1, 37);
   }

   public long getTime(InetAddress var1, int var2) throws IOException {
      byte[] var3 = this.__dummyData;
      DatagramPacket var10 = new DatagramPacket(var3, var3.length, var1, var2);
      var3 = this.__timeData;
      DatagramPacket var12 = new DatagramPacket(var3, var3.length);
      this._socket_.send(var10);
      this._socket_.receive(var12);
      byte[] var11 = this.__timeData;
      long var4 = (long)((var11[0] & 255) << 24);
      long var6 = (long)((var11[1] & 255) << 16);
      long var8 = (long)((var11[2] & 255) << 8);
      return (long)(var11[3] & 255) & 4294967295L | var4 & 4294967295L | 0L | var6 & 4294967295L | var8 & 4294967295L;
   }
}
