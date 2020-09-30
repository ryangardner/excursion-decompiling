package org.apache.commons.net.tftp;

import java.net.DatagramPacket;
import java.net.InetAddress;

public final class TFTPWriteRequestPacket extends TFTPRequestPacket {
   TFTPWriteRequestPacket(DatagramPacket var1) throws TFTPPacketException {
      super(2, var1);
   }

   public TFTPWriteRequestPacket(InetAddress var1, int var2, String var3, int var4) {
      super(var1, var2, 2, var3, var4);
   }
}
