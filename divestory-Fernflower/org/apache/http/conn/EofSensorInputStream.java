package org.apache.http.conn;

import java.io.IOException;
import java.io.InputStream;

public class EofSensorInputStream extends InputStream implements ConnectionReleaseTrigger {
   private final EofSensorWatcher eofWatcher;
   private boolean selfClosed;
   protected InputStream wrappedStream;

   public EofSensorInputStream(InputStream var1, EofSensorWatcher var2) {
      if (var1 != null) {
         this.wrappedStream = var1;
         this.selfClosed = false;
         this.eofWatcher = var2;
      } else {
         throw new IllegalArgumentException("Wrapped stream may not be null.");
      }
   }

   public void abortConnection() throws IOException {
      this.selfClosed = true;
      this.checkAbort();
   }

   public int available() throws IOException {
      int var1;
      if (this.isReadAllowed()) {
         try {
            var1 = this.wrappedStream.available();
         } catch (IOException var3) {
            this.checkAbort();
            throw var3;
         }
      } else {
         var1 = 0;
      }

      return var1;
   }

   protected void checkAbort() throws IOException {
      InputStream var1 = this.wrappedStream;
      if (var1 != null) {
         boolean var2 = true;

         label97: {
            Throwable var10000;
            label102: {
               boolean var10001;
               try {
                  if (this.eofWatcher != null) {
                     var2 = this.eofWatcher.streamAbort(var1);
                  }
               } catch (Throwable var8) {
                  var10000 = var8;
                  var10001 = false;
                  break label102;
               }

               if (!var2) {
                  break label97;
               }

               label92:
               try {
                  this.wrappedStream.close();
                  break label97;
               } catch (Throwable var7) {
                  var10000 = var7;
                  var10001 = false;
                  break label92;
               }
            }

            Throwable var9 = var10000;
            this.wrappedStream = null;
            throw var9;
         }

         this.wrappedStream = null;
      }

   }

   protected void checkClose() throws IOException {
      InputStream var1 = this.wrappedStream;
      if (var1 != null) {
         boolean var2 = true;

         label97: {
            Throwable var10000;
            label102: {
               boolean var10001;
               try {
                  if (this.eofWatcher != null) {
                     var2 = this.eofWatcher.streamClosed(var1);
                  }
               } catch (Throwable var8) {
                  var10000 = var8;
                  var10001 = false;
                  break label102;
               }

               if (!var2) {
                  break label97;
               }

               label92:
               try {
                  this.wrappedStream.close();
                  break label97;
               } catch (Throwable var7) {
                  var10000 = var7;
                  var10001 = false;
                  break label92;
               }
            }

            Throwable var9 = var10000;
            this.wrappedStream = null;
            throw var9;
         }

         this.wrappedStream = null;
      }

   }

   protected void checkEOF(int var1) throws IOException {
      InputStream var2 = this.wrappedStream;
      if (var2 != null && var1 < 0) {
         boolean var3 = true;

         label106: {
            Throwable var10000;
            label111: {
               boolean var10001;
               try {
                  if (this.eofWatcher != null) {
                     var3 = this.eofWatcher.eofDetected(var2);
                  }
               } catch (Throwable var9) {
                  var10000 = var9;
                  var10001 = false;
                  break label111;
               }

               if (!var3) {
                  break label106;
               }

               label101:
               try {
                  this.wrappedStream.close();
                  break label106;
               } catch (Throwable var8) {
                  var10000 = var8;
                  var10001 = false;
                  break label101;
               }
            }

            Throwable var10 = var10000;
            this.wrappedStream = null;
            throw var10;
         }

         this.wrappedStream = null;
      }

   }

   public void close() throws IOException {
      this.selfClosed = true;
      this.checkClose();
   }

   protected boolean isReadAllowed() throws IOException {
      if (!this.selfClosed) {
         boolean var1;
         if (this.wrappedStream != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      } else {
         throw new IOException("Attempted read on closed stream.");
      }
   }

   public int read() throws IOException {
      int var1;
      if (this.isReadAllowed()) {
         try {
            var1 = this.wrappedStream.read();
            this.checkEOF(var1);
         } catch (IOException var3) {
            this.checkAbort();
            throw var3;
         }
      } else {
         var1 = -1;
      }

      return var1;
   }

   public int read(byte[] var1) throws IOException {
      int var2;
      if (this.isReadAllowed()) {
         try {
            var2 = this.wrappedStream.read(var1);
            this.checkEOF(var2);
         } catch (IOException var3) {
            this.checkAbort();
            throw var3;
         }
      } else {
         var2 = -1;
      }

      return var2;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      if (this.isReadAllowed()) {
         try {
            var2 = this.wrappedStream.read(var1, var2, var3);
            this.checkEOF(var2);
         } catch (IOException var4) {
            this.checkAbort();
            throw var4;
         }
      } else {
         var2 = -1;
      }

      return var2;
   }

   public void releaseConnection() throws IOException {
      this.close();
   }
}
