package org.apache.commons.net.tftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.apache.commons.net.io.FromNetASCIIOutputStream;
import org.apache.commons.net.io.ToNetASCIIInputStream;

public class TFTPClient extends TFTP {
   public static final int DEFAULT_MAX_TIMEOUTS = 5;
   private int __maxTimeouts = 5;

   public int getMaxTimeouts() {
      return this.__maxTimeouts;
   }

   public int receiveFile(String var1, int var2, OutputStream var3, String var4) throws UnknownHostException, IOException {
      return this.receiveFile(var1, var2, var3, (InetAddress)InetAddress.getByName(var4), 69);
   }

   public int receiveFile(String var1, int var2, OutputStream var3, String var4, int var5) throws UnknownHostException, IOException {
      return this.receiveFile(var1, var2, var3, InetAddress.getByName(var4), var5);
   }

   public int receiveFile(String var1, int var2, OutputStream var3, InetAddress var4) throws IOException {
      return this.receiveFile(var1, var2, var3, (InetAddress)var4, 69);
   }

   public int receiveFile(String var1, int var2, OutputStream var3, InetAddress var4, int var5) throws IOException {
      TFTPAckPacket var6 = new TFTPAckPacket(var4, var5, 0);
      this.beginBufferedOps();
      Object var7;
      if (var2 == 0) {
         var7 = new FromNetASCIIOutputStream(var3);
      } else {
         var7 = var3;
      }

      Object var21 = new TFTPReadRequestPacket(var4, var5, var1, var2);
      var2 = 0;
      int var8 = 1;
      int var9 = 0;
      int var10 = 0;
      var5 = 0;
      InetAddress var23 = var4;

      label84:
      do {
         this.bufferedSend((TFTPPacket)var21);
         int var11 = var9;
         var4 = var23;

         int var13;
         int var14;
         int var15;
         do {
            TFTPPacket var12;
            StringBuilder var22;
            try {
               var12 = this.bufferedReceive();
            } catch (SocketException var18) {
               if (1 < this.__maxTimeouts) {
                  var9 = var11;
                  var23 = var4;
                  continue label84;
               }

               this.endBufferedOps();
               throw new IOException("Connection timed out.");
            } catch (InterruptedIOException var19) {
               if (1 < this.__maxTimeouts) {
                  var23 = var4;
                  var9 = var11;
                  continue label84;
               }

               this.endBufferedOps();
               throw new IOException("Connection timed out.");
            } catch (TFTPPacketException var20) {
               this.endBufferedOps();
               var22 = new StringBuilder();
               var22.append("Bad packet: ");
               var22.append(var20.getMessage());
               throw new IOException(var22.toString());
            }

            var23 = var4;
            var9 = var11;
            if (var2 == 0) {
               var11 = var12.getPort();
               var6.setPort(var11);
               var23 = var4;
               var9 = var11;
               if (!var4.equals(var12.getAddress())) {
                  var23 = var12.getAddress();
                  var6.setAddress(var23);
                  ((TFTPPacket)var21).setAddress(var23);
                  var9 = var11;
               }
            }

            if (!var23.equals(var12.getAddress()) || var12.getPort() != var9) {
               this.bufferedSend(new TFTPErrorPacket(var12.getAddress(), var12.getPort(), 5, "Unexpected host or port."));
               continue label84;
            }

            var2 = var12.getType();
            if (var2 != 3) {
               if (var2 != 5) {
                  this.endBufferedOps();
                  throw new IOException("Received unexpected packet type.");
               }

               TFTPErrorPacket var25 = (TFTPErrorPacket)var12;
               this.endBufferedOps();
               var22 = new StringBuilder();
               var22.append("Error code ");
               var22.append(var25.getError());
               var22.append(" received: ");
               var22.append(var25.getMessage());
               throw new IOException(var22.toString());
            }

            TFTPDataPacket var24 = (TFTPDataPacket)var12;
            var13 = var24.getDataLength();
            var14 = var24.getBlockNumber();
            var15 = 65535;
            if (var14 == var8) {
               try {
                  ((OutputStream)var7).write(var24.getData(), var24.getDataOffset(), var13);
               } catch (IOException var17) {
                  this.bufferedSend(new TFTPErrorPacket(var23, var9, 3, "File write failed."));
                  this.endBufferedOps();
                  throw var17;
               }

               var2 = var8 + 1;
               var8 = var2;
               if (var2 > 65535) {
                  var8 = 0;
               }

               var6.setBlockNumber(var14);
               var10 += var13;
               var5 = var13;
               var21 = var6;
               var2 = var14;
               continue label84;
            }

            this.discardPackets();
            if (var8 != 0) {
               var15 = var8 - 1;
            }

            var4 = var23;
            var2 = var14;
            var11 = var9;
            var5 = var13;
         } while(var14 != var15);

         var2 = var14;
         var5 = var13;
      } while(var5 == 512);

      this.bufferedSend((TFTPPacket)var21);
      this.endBufferedOps();
      return var10;
   }

   public void sendFile(String var1, int var2, InputStream var3, String var4) throws UnknownHostException, IOException {
      this.sendFile(var1, var2, var3, (InetAddress)InetAddress.getByName(var4), 69);
   }

   public void sendFile(String var1, int var2, InputStream var3, String var4, int var5) throws UnknownHostException, IOException {
      this.sendFile(var1, var2, var3, InetAddress.getByName(var4), var5);
   }

   public void sendFile(String var1, int var2, InputStream var3, InetAddress var4) throws IOException {
      this.sendFile(var1, var2, var3, (InetAddress)var4, 69);
   }

   public void sendFile(String var1, int var2, InputStream var3, InetAddress var4, int var5) throws IOException {
      TFTPDataPacket var6 = new TFTPDataPacket(var4, var5, 0, this._sendBuffer, 4, 0);
      this.beginBufferedOps();
      Object var7;
      if (var2 == 0) {
         var7 = new ToNetASCIIInputStream(var3);
      } else {
         var7 = var3;
      }

      Object var23 = new TFTPWriteRequestPacket(var4, var5, var1, var2);
      boolean var8 = true;
      int var9 = 0;
      int var10 = 0;
      boolean var21 = false;
      int var11 = 0;
      InetAddress var20 = var4;

      label96:
      while(true) {
         this.bufferedSend((TFTPPacket)var23);
         var5 = var10;
         boolean var27 = var8;

         Object var12;
         boolean var13;
         int var14;
         boolean var15;
         int var16;
         while(true) {
            TFTPPacket var28;
            try {
               var28 = this.bufferedReceive();
            } catch (SocketException var17) {
               if (1 < this.__maxTimeouts) {
                  var16 = var11;
                  var15 = var21;
                  var14 = var9;
                  var13 = var27;
                  var4 = var20;
                  var12 = var23;
                  break;
               }

               this.endBufferedOps();
               throw new IOException("Connection timed out.");
            } catch (InterruptedIOException var18) {
               if (1 < this.__maxTimeouts) {
                  var12 = var23;
                  var4 = var20;
                  var13 = var27;
                  var14 = var9;
                  var15 = var21;
                  var16 = var11;
                  break;
               }

               this.endBufferedOps();
               throw new IOException("Connection timed out.");
            } catch (TFTPPacketException var19) {
               this.endBufferedOps();
               StringBuilder var24 = new StringBuilder();
               var24.append("Bad packet: ");
               var24.append(var19.getMessage());
               throw new IOException(var24.toString());
            }

            var4 = var20;
            var13 = var27;
            if (var27) {
               var5 = var28.getPort();
               var6.setPort(var5);
               var4 = var20;
               if (!var20.equals(var28.getAddress())) {
                  var4 = var28.getAddress();
                  var6.setAddress(var4);
                  ((TFTPPacket)var23).setAddress(var4);
               }

               var13 = false;
            }

            if (var4.equals(var28.getAddress()) && var28.getPort() == var5) {
               var10 = var28.getType();
               if (var10 != 4) {
                  if (var10 != 5) {
                     this.endBufferedOps();
                     throw new IOException("Received unexpected packet type.");
                  }

                  TFTPErrorPacket var25 = (TFTPErrorPacket)var28;
                  this.endBufferedOps();
                  StringBuilder var22 = new StringBuilder();
                  var22.append("Error code ");
                  var22.append(var25.getError());
                  var22.append(" received: ");
                  var22.append(var25.getMessage());
                  throw new IOException(var22.toString());
               }

               if (((TFTPAckPacket)var28).getBlockNumber() != var9) {
                  this.discardPackets();
                  var20 = var4;
                  var27 = var13;
                  continue;
               }

               ++var9;
               var11 = var9;
               if (var9 > 65535) {
                  var11 = 0;
               }

               if (var21) {
                  break label96;
               }

               var9 = 0;
               var10 = 512;

               for(int var26 = 4; var10 > 0; var9 += var14) {
                  var14 = ((InputStream)var7).read(this._sendBuffer, var26, var10);
                  if (var14 <= 0) {
                     break;
                  }

                  var26 += var14;
                  var10 -= var14;
               }

               if (var9 < 512) {
                  var21 = true;
               }

               var6.setBlockNumber(var11);
               var6.setData(this._sendBuffer, 4, var9);
               var23 = var6;
               var10 = var11;
               var11 = var9;
            } else {
               this.bufferedSend(new TFTPErrorPacket(var28.getAddress(), var28.getPort(), 5, "Unexpected host or port."));
               var10 = var9;
            }

            var12 = var23;
            var14 = var10;
            var15 = var21;
            var16 = var11;
            break;
         }

         var23 = var12;
         var20 = var4;
         var8 = var13;
         var9 = var14;
         var10 = var5;
         var21 = var15;
         var11 = var16;
         if (var16 <= 0) {
            var23 = var12;
            var20 = var4;
            var8 = var13;
            var9 = var14;
            var10 = var5;
            var21 = var15;
            var11 = var16;
            if (!var15) {
               break;
            }
         }
      }

      this.endBufferedOps();
   }

   public void setMaxTimeouts(int var1) {
      if (var1 < 1) {
         this.__maxTimeouts = 1;
      } else {
         this.__maxTimeouts = var1;
      }

   }
}
