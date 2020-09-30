package org.apache.commons.net.tftp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Locale;

public abstract class TFTPRequestPacket extends TFTPPacket {
   private static final byte[][] _modeBytes = new byte[][]{{110, 101, 116, 97, 115, 99, 105, 105, 0}, {111, 99, 116, 101, 116, 0}};
   static final String[] _modeStrings = new String[]{"netascii", "octet"};
   private final String _filename;
   private final int _mode;

   TFTPRequestPacket(int var1, DatagramPacket var2) throws TFTPPacketException {
      super(var1, var2.getAddress(), var2.getPort());
      byte[] var3 = var2.getData();
      if (this.getType() != var3[1]) {
         throw new TFTPPacketException("TFTP operator code does not match type.");
      } else {
         StringBuilder var4 = new StringBuilder();
         var1 = 2;

         int var5;
         for(var5 = var2.getLength(); var1 < var5 && var3[var1] != 0; ++var1) {
            var4.append((char)var3[var1]);
         }

         this._filename = var4.toString();
         if (var1 >= var5) {
            throw new TFTPPacketException("Bad filename and mode format.");
         } else {
            byte var6 = 0;
            var4.setLength(0);
            ++var1;

            while(var1 < var5 && var3[var1] != 0) {
               var4.append((char)var3[var1]);
               ++var1;
            }

            String var9 = var4.toString().toLowerCase(Locale.ENGLISH);
            int var7 = _modeStrings.length;
            var1 = 0;

            while(true) {
               var5 = var6;
               if (var1 >= var7) {
                  break;
               }

               if (var9.equals(_modeStrings[var1])) {
                  var5 = var1;
                  break;
               }

               ++var1;
            }

            this._mode = var5;
            if (var1 >= var7) {
               StringBuilder var8 = new StringBuilder();
               var8.append("Unrecognized TFTP transfer mode: ");
               var8.append(var9);
               throw new TFTPPacketException(var8.toString());
            }
         }
      }
   }

   TFTPRequestPacket(InetAddress var1, int var2, int var3, String var4, int var5) {
      super(var3, var1, var2);
      this._filename = var4;
      this._mode = var5;
   }

   final DatagramPacket _newDatagram(DatagramPacket var1, byte[] var2) {
      int var3 = this._filename.length();
      int var4 = _modeBytes[this._mode].length;
      var2[0] = (byte)0;
      var2[1] = (byte)((byte)this._type);
      System.arraycopy(this._filename.getBytes(), 0, var2, 2, var3);
      var2[var3 + 2] = (byte)0;
      System.arraycopy(_modeBytes[this._mode], 0, var2, var3 + 3, var4);
      var1.setAddress(this._address);
      var1.setPort(this._port);
      var1.setData(var2);
      var1.setLength(var3 + var4 + 3);
      return var1;
   }

   public final String getFilename() {
      return this._filename;
   }

   public final int getMode() {
      return this._mode;
   }

   public final DatagramPacket newDatagram() {
      int var1 = this._filename.length();
      int var2 = _modeBytes[this._mode].length;
      int var3 = var1 + var2 + 4;
      byte[] var4 = new byte[var3];
      var4[0] = (byte)0;
      var4[1] = (byte)((byte)this._type);
      System.arraycopy(this._filename.getBytes(), 0, var4, 2, var1);
      var4[var1 + 2] = (byte)0;
      System.arraycopy(_modeBytes[this._mode], 0, var4, var1 + 3, var2);
      return new DatagramPacket(var4, var3, this._address, this._port);
   }
}
