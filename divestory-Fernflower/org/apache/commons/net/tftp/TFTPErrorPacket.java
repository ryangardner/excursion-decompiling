package org.apache.commons.net.tftp;

import java.net.DatagramPacket;
import java.net.InetAddress;

public final class TFTPErrorPacket extends TFTPPacket {
   public static final int ACCESS_VIOLATION = 2;
   public static final int FILE_EXISTS = 6;
   public static final int FILE_NOT_FOUND = 1;
   public static final int ILLEGAL_OPERATION = 4;
   public static final int NO_SUCH_USER = 7;
   public static final int OUT_OF_SPACE = 3;
   public static final int UNDEFINED = 0;
   public static final int UNKNOWN_TID = 5;
   int _error;
   String _message;

   TFTPErrorPacket(DatagramPacket var1) throws TFTPPacketException {
      super(5, var1.getAddress(), var1.getPort());
      byte[] var2 = var1.getData();
      int var3 = var1.getLength();
      if (this.getType() != var2[1]) {
         throw new TFTPPacketException("TFTP operator code does not match type.");
      } else {
         this._error = (var2[2] & 255) << 8 | var2[3] & 255;
         if (var3 < 5) {
            throw new TFTPPacketException("Bad error packet. No message.");
         } else {
            int var4 = 4;

            StringBuilder var5;
            for(var5 = new StringBuilder(); var4 < var3 && var2[var4] != 0; ++var4) {
               var5.append((char)var2[var4]);
            }

            this._message = var5.toString();
         }
      }
   }

   public TFTPErrorPacket(InetAddress var1, int var2, int var3, String var4) {
      super(5, var1, var2);
      this._error = var3;
      this._message = var4;
   }

   DatagramPacket _newDatagram(DatagramPacket var1, byte[] var2) {
      int var3 = this._message.length();
      var2[0] = (byte)0;
      var2[1] = (byte)((byte)this._type);
      int var4 = this._error;
      var2[2] = (byte)((byte)(('\uffff' & var4) >> 8));
      var2[3] = (byte)((byte)(var4 & 255));
      System.arraycopy(this._message.getBytes(), 0, var2, 4, var3);
      var3 += 4;
      var2[var3] = (byte)0;
      var1.setAddress(this._address);
      var1.setPort(this._port);
      var1.setData(var2);
      var1.setLength(var3);
      return var1;
   }

   public int getError() {
      return this._error;
   }

   public String getMessage() {
      return this._message;
   }

   public DatagramPacket newDatagram() {
      int var1 = this._message.length();
      int var2 = var1 + 5;
      byte[] var3 = new byte[var2];
      var3[0] = (byte)0;
      var3[1] = (byte)((byte)this._type);
      int var4 = this._error;
      var3[2] = (byte)((byte)(('\uffff' & var4) >> 8));
      var3[3] = (byte)((byte)(var4 & 255));
      System.arraycopy(this._message.getBytes(), 0, var3, 4, var1);
      var3[var1 + 4] = (byte)0;
      return new DatagramPacket(var3, var2, this._address, this._port);
   }
}
