package org.apache.http.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class InputStreamEntity extends AbstractHttpEntity {
   private static final int BUFFER_SIZE = 2048;
   private final InputStream content;
   private final long length;

   public InputStreamEntity(InputStream var1, long var2) {
      if (var1 != null) {
         this.content = var1;
         this.length = var2;
      } else {
         throw new IllegalArgumentException("Source input stream may not be null");
      }
   }

   public void consumeContent() throws IOException {
      this.content.close();
   }

   public InputStream getContent() throws IOException {
      return this.content;
   }

   public long getContentLength() {
      return this.length;
   }

   public boolean isRepeatable() {
      return false;
   }

   public boolean isStreaming() {
      return true;
   }

   public void writeTo(OutputStream var1) throws IOException {
      if (var1 != null) {
         InputStream var2 = this.content;

         label434: {
            Throwable var10000;
            label433: {
               byte[] var3;
               int var4;
               boolean var10001;
               label440: {
                  try {
                     var3 = new byte[2048];
                     if (this.length < 0L) {
                        break label440;
                     }
                  } catch (Throwable var48) {
                     var10000 = var48;
                     var10001 = false;
                     break label433;
                  }

                  long var5;
                  try {
                     var5 = this.length;
                  } catch (Throwable var47) {
                     var10000 = var47;
                     var10001 = false;
                     break label433;
                  }

                  while(true) {
                     if (var5 <= 0L) {
                        break label434;
                     }

                     try {
                        var4 = var2.read(var3, 0, (int)Math.min(2048L, var5));
                     } catch (Throwable var46) {
                        var10000 = var46;
                        var10001 = false;
                        break label433;
                     }

                     if (var4 == -1) {
                        break label434;
                     }

                     try {
                        var1.write(var3, 0, var4);
                     } catch (Throwable var45) {
                        var10000 = var45;
                        var10001 = false;
                        break label433;
                     }

                     var5 -= (long)var4;
                  }
               }

               while(true) {
                  try {
                     var4 = var2.read(var3);
                  } catch (Throwable var44) {
                     var10000 = var44;
                     var10001 = false;
                     break;
                  }

                  if (var4 == -1) {
                     break label434;
                  }

                  try {
                     var1.write(var3, 0, var4);
                  } catch (Throwable var43) {
                     var10000 = var43;
                     var10001 = false;
                     break;
                  }
               }
            }

            Throwable var49 = var10000;
            var2.close();
            throw var49;
         }

         var2.close();
      } else {
         throw new IllegalArgumentException("Output stream may not be null");
      }
   }
}
