package com.google.api.client.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingByteArrayOutputStream extends ByteArrayOutputStream {
   private int bytesWritten;
   private boolean closed;
   private final Logger logger;
   private final Level loggingLevel;
   private final int maximumBytesToLog;

   public LoggingByteArrayOutputStream(Logger var1, Level var2, int var3) {
      this.logger = (Logger)Preconditions.checkNotNull(var1);
      this.loggingLevel = (Level)Preconditions.checkNotNull(var2);
      boolean var4;
      if (var3 >= 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4);
      this.maximumBytesToLog = var3;
   }

   private static void appendBytes(StringBuilder var0, int var1) {
      if (var1 == 1) {
         var0.append("1 byte");
      } else {
         var0.append(NumberFormat.getInstance().format((long)var1));
         var0.append(" bytes");
      }

   }

   public void close() throws IOException {
      synchronized(this){}

      try {
         if (!this.closed) {
            if (this.bytesWritten != 0) {
               StringBuilder var1 = new StringBuilder();
               var1.append("Total: ");
               appendBytes(var1, this.bytesWritten);
               if (this.count != 0 && this.count < this.bytesWritten) {
                  var1.append(" (logging first ");
                  appendBytes(var1, this.count);
                  var1.append(")");
               }

               this.logger.config(var1.toString());
               if (this.count != 0) {
                  this.logger.log(this.loggingLevel, this.toString("UTF-8").replaceAll("[\\x00-\\x09\\x0B\\x0C\\x0E-\\x1F\\x7F]", " "));
               }
            }

            this.closed = true;
         }
      } finally {
         ;
      }

   }

   public final int getBytesWritten() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.bytesWritten;
      } finally {
         ;
      }

      return var1;
   }

   public final int getMaximumBytesToLog() {
      return this.maximumBytesToLog;
   }

   public void write(int var1) {
      synchronized(this){}

      Throwable var10000;
      label92: {
         boolean var10001;
         boolean var2;
         label91: {
            label90: {
               try {
                  if (!this.closed) {
                     break label90;
                  }
               } catch (Throwable var9) {
                  var10000 = var9;
                  var10001 = false;
                  break label92;
               }

               var2 = false;
               break label91;
            }

            var2 = true;
         }

         label84:
         try {
            Preconditions.checkArgument(var2);
            ++this.bytesWritten;
            if (this.count < this.maximumBytesToLog) {
               super.write(var1);
            }

            return;
         } catch (Throwable var8) {
            var10000 = var8;
            var10001 = false;
            break label84;
         }
      }

      Throwable var3 = var10000;
      throw var3;
   }

   public void write(byte[] var1, int var2, int var3) {
      synchronized(this){}

      Throwable var10000;
      label240: {
         boolean var4;
         boolean var10001;
         label234: {
            label233: {
               try {
                  if (!this.closed) {
                     break label233;
                  }
               } catch (Throwable var26) {
                  var10000 = var26;
                  var10001 = false;
                  break label240;
               }

               var4 = false;
               break label234;
            }

            var4 = true;
         }

         int var5;
         try {
            Preconditions.checkArgument(var4);
            this.bytesWritten += var3;
            if (this.count >= this.maximumBytesToLog) {
               return;
            }

            var5 = this.count + var3;
         } catch (Throwable var25) {
            var10000 = var25;
            var10001 = false;
            break label240;
         }

         int var6 = var3;

         try {
            if (var5 > this.maximumBytesToLog) {
               var6 = var3 + (this.maximumBytesToLog - var5);
            }
         } catch (Throwable var24) {
            var10000 = var24;
            var10001 = false;
            break label240;
         }

         label220:
         try {
            super.write(var1, var2, var6);
            return;
         } catch (Throwable var23) {
            var10000 = var23;
            var10001 = false;
            break label220;
         }
      }

      Throwable var27 = var10000;
      throw var27;
   }
}
