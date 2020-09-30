package org.apache.commons.net.tftp;

import java.net.DatagramPacket;
import java.net.InetAddress;

public final class TFTPDataPacket extends TFTPPacket {
   public static final int MAX_DATA_LENGTH = 512;
   public static final int MIN_DATA_LENGTH = 0;
   int _blockNumber;
   byte[] _data;
   int _length;
   int _offset;

   TFTPDataPacket(DatagramPacket var1) throws TFTPPacketException {
      super(3, var1.getAddress(), var1.getPort());
      this._data = var1.getData();
      this._offset = 4;
      int var2 = this.getType();
      byte[] var3 = this._data;
      if (var2 == var3[1]) {
         this._blockNumber = (var3[2] & 255) << 8 | var3[3] & 255;
         var2 = var1.getLength() - 4;
         this._length = var2;
         if (var2 > 512) {
            this._length = 512;
         }

      } else {
         throw new TFTPPacketException("TFTP operator code does not match type.");
      }
   }

   public TFTPDataPacket(InetAddress var1, int var2, int var3, byte[] var4) {
      this(var1, var2, var3, var4, 0, var4.length);
   }

   public TFTPDataPacket(InetAddress var1, int var2, int var3, byte[] var4, int var5, int var6) {
      super(3, var1, var2);
      this._blockNumber = var3;
      this._data = var4;
      this._offset = var5;
      if (var6 > 512) {
         this._length = 512;
      } else {
         this._length = var6;
      }

   }

   DatagramPacket _newDatagram(DatagramPacket var1, byte[] var2) {
      var2[0] = (byte)0;
      var2[1] = (byte)((byte)this._type);
      int var3 = this._blockNumber;
      var2[2] = (byte)((byte)(('\uffff' & var3) >> 8));
      var2[3] = (byte)((byte)(var3 & 255));
      byte[] var4 = this._data;
      if (var2 != var4) {
         System.arraycopy(var4, this._offset, var2, 4, this._length);
      }

      var1.setAddress(this._address);
      var1.setPort(this._port);
      var1.setData(var2);
      var1.setLength(this._length + 4);
      return var1;
   }

   public int getBlockNumber() {
      return this._blockNumber;
   }

   public byte[] getData() {
      return this._data;
   }

   public int getDataLength() {
      return this._length;
   }

   public int getDataOffset() {
      return this._offset;
   }

   public DatagramPacket newDatagram() {
      byte[] var1 = new byte[this._length + 4];
      var1[0] = (byte)0;
      var1[1] = (byte)((byte)this._type);
      int var2 = this._blockNumber;
      var1[2] = (byte)((byte)(('\uffff' & var2) >> 8));
      var1[3] = (byte)((byte)(var2 & 255));
      System.arraycopy(this._data, this._offset, var1, 4, this._length);
      return new DatagramPacket(var1, this._length + 4, this._address, this._port);
   }

   public void setBlockNumber(int var1) {
      this._blockNumber = var1;
   }

   public void setData(byte[] var1, int var2, int var3) {
      this._data = var1;
      this._offset = var2;
      this._length = var3;
      if (var3 > 512) {
         this._length = 512;
      } else {
         this._length = var3;
      }

   }
}
