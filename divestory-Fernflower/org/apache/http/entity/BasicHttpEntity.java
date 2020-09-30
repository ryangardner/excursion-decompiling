package org.apache.http.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BasicHttpEntity extends AbstractHttpEntity {
   private InputStream content;
   private long length = -1L;

   public void consumeContent() throws IOException {
      InputStream var1 = this.content;
      if (var1 != null) {
         var1.close();
      }

   }

   public InputStream getContent() throws IllegalStateException {
      InputStream var1 = this.content;
      if (var1 != null) {
         return var1;
      } else {
         throw new IllegalStateException("Content has not been provided");
      }
   }

   public long getContentLength() {
      return this.length;
   }

   public boolean isRepeatable() {
      return false;
   }

   public boolean isStreaming() {
      boolean var1;
      if (this.content != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void setContent(InputStream var1) {
      this.content = var1;
   }

   public void setContentLength(long var1) {
      this.length = var1;
   }

   public void writeTo(OutputStream var1) throws IOException {
      if (var1 != null) {
         InputStream var2 = this.getContent();

         Throwable var10000;
         label137: {
            boolean var10001;
            byte[] var3;
            try {
               var3 = new byte[2048];
            } catch (Throwable var16) {
               var10000 = var16;
               var10001 = false;
               break label137;
            }

            while(true) {
               int var4;
               try {
                  var4 = var2.read(var3);
               } catch (Throwable var15) {
                  var10000 = var15;
                  var10001 = false;
                  break;
               }

               if (var4 == -1) {
                  var2.close();
                  return;
               }

               try {
                  var1.write(var3, 0, var4);
               } catch (Throwable var14) {
                  var10000 = var14;
                  var10001 = false;
                  break;
               }
            }
         }

         Throwable var17 = var10000;
         var2.close();
         throw var17;
      } else {
         throw new IllegalArgumentException("Output stream may not be null");
      }
   }
}
