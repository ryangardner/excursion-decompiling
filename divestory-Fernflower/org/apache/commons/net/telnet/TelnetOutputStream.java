package org.apache.commons.net.telnet;

import java.io.IOException;
import java.io.OutputStream;

final class TelnetOutputStream extends OutputStream {
   private final TelnetClient __client;
   private final boolean __convertCRtoCRLF = true;
   private boolean __lastWasCR = false;

   TelnetOutputStream(TelnetClient var1) {
      this.__client = var1;
   }

   public void close() throws IOException {
      this.__client._closeOutputStream();
   }

   public void flush() throws IOException {
      this.__client._flushOutputStream();
   }

   public void write(int var1) throws IOException {
      TelnetClient var2 = this.__client;
      synchronized(var2){}
      var1 &= 255;

      Throwable var10000;
      boolean var10001;
      label1089: {
         label1088: {
            label1087: {
               label1086: {
                  try {
                     if (!this.__client._requestedWont(0)) {
                        break label1087;
                     }

                     if (!this.__lastWasCR) {
                        break label1086;
                     }

                     this.__client._sendByte(10);
                  } catch (Throwable var135) {
                     var10000 = var135;
                     var10001 = false;
                     break label1089;
                  }

                  if (var1 == 10) {
                     try {
                        this.__lastWasCR = false;
                        return;
                     } catch (Throwable var126) {
                        var10000 = var126;
                        var10001 = false;
                        break label1089;
                     }
                  }
               }

               if (var1 != 10) {
                  if (var1 != 13) {
                     if (var1 != 255) {
                        try {
                           this.__client._sendByte(var1);
                           this.__lastWasCR = false;
                        } catch (Throwable var133) {
                           var10000 = var133;
                           var10001 = false;
                           break label1089;
                        }
                     } else {
                        try {
                           this.__client._sendByte(255);
                           this.__client._sendByte(255);
                           this.__lastWasCR = false;
                        } catch (Throwable var132) {
                           var10000 = var132;
                           var10001 = false;
                           break label1089;
                        }
                     }
                  } else {
                     try {
                        this.__client._sendByte(13);
                        this.__lastWasCR = true;
                     } catch (Throwable var131) {
                        var10000 = var131;
                        var10001 = false;
                        break label1089;
                     }
                  }
               } else {
                  try {
                     if (!this.__lastWasCR) {
                        this.__client._sendByte(13);
                     }
                  } catch (Throwable var134) {
                     var10000 = var134;
                     var10001 = false;
                     break label1089;
                  }

                  try {
                     this.__client._sendByte(var1);
                     this.__lastWasCR = false;
                  } catch (Throwable var130) {
                     var10000 = var130;
                     var10001 = false;
                     break label1089;
                  }
               }
               break label1088;
            }

            if (var1 == 255) {
               try {
                  this.__client._sendByte(var1);
                  this.__client._sendByte(255);
               } catch (Throwable var129) {
                  var10000 = var129;
                  var10001 = false;
                  break label1089;
               }
            } else {
               try {
                  this.__client._sendByte(var1);
               } catch (Throwable var128) {
                  var10000 = var128;
                  var10001 = false;
                  break label1089;
               }
            }
         }

         label1055:
         try {
            return;
         } catch (Throwable var127) {
            var10000 = var127;
            var10001 = false;
            break label1055;
         }
      }

      while(true) {
         Throwable var3 = var10000;

         try {
            throw var3;
         } catch (Throwable var125) {
            var10000 = var125;
            var10001 = false;
            continue;
         }
      }
   }

   public void write(byte[] var1) throws IOException {
      this.write(var1, 0, var1.length);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      TelnetClient var4 = this.__client;
      synchronized(var4){}

      while(true) {
         Throwable var10000;
         boolean var10001;
         if (var3 > 0) {
            label118: {
               try {
                  this.write(var1[var2]);
               } catch (Throwable var15) {
                  var10000 = var15;
                  var10001 = false;
                  break label118;
               }

               ++var2;
               --var3;
               continue;
            }
         } else {
            label120:
            try {
               return;
            } catch (Throwable var16) {
               var10000 = var16;
               var10001 = false;
               break label120;
            }
         }

         while(true) {
            Throwable var17 = var10000;

            try {
               throw var17;
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               continue;
            }
         }
      }
   }
}
