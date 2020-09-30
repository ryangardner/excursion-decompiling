package org.apache.commons.net.tftp;

import java.net.DatagramPacket;
import java.net.InetAddress;

public final class TFTPAckPacket extends TFTPPacket {
   int _blockNumber;

   TFTPAckPacket(DatagramPacket var1) throws TFTPPacketException {
      super(4, var1.getAddress(), var1.getPort());
      byte[] var3 = var1.getData();
      if (this.getType() == var3[1]) {
         byte var2 = var3[2];
         this._blockNumber = var3[3] & 255 | (var2 & 255) << 8;
      } else {
         throw new TFTPPacketException("TFTP operator code does not match type.");
      }
   }

   public TFTPAckPacket(InetAddress var1, int var2, int var3) {
      super(4, var1, var2);
      this._blockNumber = var3;
   }

   DatagramPacket _newDatagram(DatagramPacket var1, byte[] var2) {
      var2[0] = (byte)0;
      var2[1] = (byte)((byte)this._type);
      int var3 = this._blockNumber;
      var2[2] = (byte)((byte)(('\uffff' & var3) >> 8));
      var2[3] = (byte)((byte)(var3 & 255));
      var1.setAddress(this._address);
      var1.setPort(this._port);
      var1.setData(var2);
      var1.setLength(4);
      return var1;
   }

   public int getBlockNumber() {
      return this._blockNumber;
   }

   public DatagramPacket newDatagram() {
      byte var1 = (byte)this._type;
      int var2 = this._blockNumber;
      byte var3 = (byte)(('\uffff' & var2) >> 8);
      byte var4 = (byte)(var2 & 255);
      InetAddress var5 = this._address;
      var2 = this._port;
      return new DatagramPacket(new byte[]{0, var1, var3, var4}, 4, var5, var2);
   }

   public void setBlockNumber(int var1) {
      this._blockNumber = var1;
   }
}
