package org.apache.commons.net.tftp;

import java.net.DatagramPacket;
import java.net.InetAddress;

public abstract class TFTPPacket {
   public static final int ACKNOWLEDGEMENT = 4;
   public static final int DATA = 3;
   public static final int ERROR = 5;
   static final int MIN_PACKET_SIZE = 4;
   public static final int READ_REQUEST = 1;
   public static final int SEGMENT_SIZE = 512;
   public static final int WRITE_REQUEST = 2;
   InetAddress _address;
   int _port;
   int _type;

   TFTPPacket(int var1, InetAddress var2, int var3) {
      this._type = var1;
      this._address = var2;
      this._port = var3;
   }

   public static final TFTPPacket newTFTPPacket(DatagramPacket var0) throws TFTPPacketException {
      if (var0.getLength() >= 4) {
         byte var1 = var0.getData()[1];
         Object var2;
         if (var1 != 1) {
            if (var1 != 2) {
               if (var1 != 3) {
                  if (var1 != 4) {
                     if (var1 != 5) {
                        throw new TFTPPacketException("Bad packet.  Invalid TFTP operator code.");
                     }

                     var2 = new TFTPErrorPacket(var0);
                  } else {
                     var2 = new TFTPAckPacket(var0);
                  }
               } else {
                  var2 = new TFTPDataPacket(var0);
               }
            } else {
               var2 = new TFTPWriteRequestPacket(var0);
            }
         } else {
            var2 = new TFTPReadRequestPacket(var0);
         }

         return (TFTPPacket)var2;
      } else {
         throw new TFTPPacketException("Bad packet. Datagram data length is too short.");
      }
   }

   abstract DatagramPacket _newDatagram(DatagramPacket var1, byte[] var2);

   public final InetAddress getAddress() {
      return this._address;
   }

   public final int getPort() {
      return this._port;
   }

   public final int getType() {
      return this._type;
   }

   public abstract DatagramPacket newDatagram();

   public final void setAddress(InetAddress var1) {
      this._address = var1;
   }

   public final void setPort(int var1) {
      this._port = var1;
   }
}
