package org.apache.commons.net.ntp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import org.apache.commons.net.DatagramSocketClient;

public final class NTPUDPClient extends DatagramSocketClient {
   public static final int DEFAULT_PORT = 123;
   private int _version = 3;

   public TimeInfo getTime(InetAddress var1) throws IOException {
      return this.getTime(var1, 123);
   }

   public TimeInfo getTime(InetAddress var1, int var2) throws IOException {
      if (!this.isOpen()) {
         this.open();
      }

      NtpV3Impl var3 = new NtpV3Impl();
      var3.setMode(3);
      var3.setVersion(this._version);
      DatagramPacket var4 = var3.getDatagramPacket();
      var4.setAddress(var1);
      var4.setPort(var2);
      NtpV3Impl var5 = new NtpV3Impl();
      DatagramPacket var6 = var5.getDatagramPacket();
      var3.setTransmitTime(TimeStamp.getCurrentTime());
      this._socket_.send(var4);
      this._socket_.receive(var6);
      return new TimeInfo(var5, System.currentTimeMillis(), false);
   }

   public int getVersion() {
      return this._version;
   }

   public void setVersion(int var1) {
      this._version = var1;
   }
}
