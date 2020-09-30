package org.apache.commons.net.tftp;

import java.net.DatagramPacket;
import java.net.InetAddress;

public final class TFTPReadRequestPacket extends TFTPRequestPacket {
   TFTPReadRequestPacket(DatagramPacket var1) throws TFTPPacketException {
      super(1, var1);
   }

   public TFTPReadRequestPacket(InetAddress var1, int var2, String var3, int var4) {
      super(var1, var2, 1, var3, var4);
   }
}
