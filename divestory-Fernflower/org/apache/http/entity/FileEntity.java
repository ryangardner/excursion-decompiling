package org.apache.http.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileEntity extends AbstractHttpEntity implements Cloneable {
   protected final File file;

   public FileEntity(File var1, String var2) {
      if (var1 != null) {
         this.file = var1;
         this.setContentType(var2);
      } else {
         throw new IllegalArgumentException("File may not be null");
      }
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public InputStream getContent() throws IOException {
      return new FileInputStream(this.file);
   }

   public long getContentLength() {
      return this.file.length();
   }

   public boolean isRepeatable() {
      return true;
   }

   public boolean isStreaming() {
      return false;
   }

   public void writeTo(OutputStream var1) throws IOException {
      if (var1 != null) {
         FileInputStream var2 = new FileInputStream(this.file);

         label189: {
            Throwable var10000;
            label188: {
               boolean var10001;
               byte[] var3;
               try {
                  var3 = new byte[4096];
               } catch (Throwable var24) {
                  var10000 = var24;
                  var10001 = false;
                  break label188;
               }

               while(true) {
                  int var4;
                  try {
                     var4 = var2.read(var3);
                  } catch (Throwable var22) {
                     var10000 = var22;
                     var10001 = false;
                     break;
                  }

                  if (var4 == -1) {
                     try {
                        var1.flush();
                        break label189;
                     } catch (Throwable var21) {
                        var10000 = var21;
                        var10001 = false;
                        break;
                     }
                  }

                  try {
                     var1.write(var3, 0, var4);
                  } catch (Throwable var23) {
                     var10000 = var23;
                     var10001 = false;
                     break;
                  }
               }
            }

            Throwable var25 = var10000;
            var2.close();
            throw var25;
         }

         var2.close();
      } else {
         throw new IllegalArgumentException("Output stream may not be null");
      }
   }
}
