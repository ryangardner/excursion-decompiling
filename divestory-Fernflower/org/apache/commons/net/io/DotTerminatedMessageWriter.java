package org.apache.commons.net.io;

import java.io.IOException;
import java.io.Writer;

public final class DotTerminatedMessageWriter extends Writer {
   private static final int __LAST_WAS_CR_STATE = 1;
   private static final int __LAST_WAS_NL_STATE = 2;
   private static final int __NOTHING_SPECIAL_STATE = 0;
   private Writer __output;
   private int __state;

   public DotTerminatedMessageWriter(Writer var1) {
      super(var1);
      this.__output = var1;
      this.__state = 0;
   }

   public void close() throws IOException {
      Object var1 = this.lock;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label297: {
         try {
            if (this.__output == null) {
               return;
            }
         } catch (Throwable var32) {
            var10000 = var32;
            var10001 = false;
            break label297;
         }

         label287: {
            try {
               if (this.__state == 1) {
                  this.__output.write(10);
                  break label287;
               }
            } catch (Throwable var31) {
               var10000 = var31;
               var10001 = false;
               break label297;
            }

            try {
               if (this.__state != 2) {
                  this.__output.write("\r\n");
               }
            } catch (Throwable var30) {
               var10000 = var30;
               var10001 = false;
               break label297;
            }
         }

         label278:
         try {
            this.__output.write(".\r\n");
            this.__output.flush();
            this.__output = null;
            return;
         } catch (Throwable var29) {
            var10000 = var29;
            var10001 = false;
            break label278;
         }
      }

      while(true) {
         Throwable var2 = var10000;

         try {
            throw var2;
         } catch (Throwable var28) {
            var10000 = var28;
            var10001 = false;
            continue;
         }
      }
   }

   public void flush() throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void write(int var1) throws IOException {
      Object var2 = this.lock;
      synchronized(var2){}
      Throwable var10000;
      boolean var10001;
      if (var1 != 10) {
         if (var1 != 13) {
            label336: {
               if (var1 == 46) {
                  try {
                     if (this.__state == 2) {
                        this.__output.write(46);
                     }
                  } catch (Throwable var42) {
                     var10000 = var42;
                     var10001 = false;
                     break label336;
                  }
               }

               label332:
               try {
                  this.__state = 0;
                  this.__output.write(var1);
                  return;
               } catch (Throwable var41) {
                  var10000 = var41;
                  var10001 = false;
                  break label332;
               }
            }
         } else {
            label338:
            try {
               this.__state = 1;
               this.__output.write(13);
               return;
            } catch (Throwable var43) {
               var10000 = var43;
               var10001 = false;
               break label338;
            }
         }
      } else {
         label344: {
            try {
               if (this.__state != 1) {
                  this.__output.write(13);
               }
            } catch (Throwable var45) {
               var10000 = var45;
               var10001 = false;
               break label344;
            }

            label341:
            try {
               this.__output.write(10);
               this.__state = 2;
               return;
            } catch (Throwable var44) {
               var10000 = var44;
               var10001 = false;
               break label341;
            }
         }
      }

      while(true) {
         Throwable var3 = var10000;

         try {
            throw var3;
         } catch (Throwable var40) {
            var10000 = var40;
            var10001 = false;
            continue;
         }
      }
   }

   public void write(String var1) throws IOException {
      this.write(var1.toCharArray());
   }

   public void write(String var1, int var2, int var3) throws IOException {
      this.write(var1.toCharArray(), var2, var3);
   }

   public void write(char[] var1) throws IOException {
      this.write((char[])var1, 0, var1.length);
   }

   public void write(char[] var1, int var2, int var3) throws IOException {
      Object var4 = this.lock;
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
