package org.apache.http.client.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;

abstract class DecompressingEntity extends HttpEntityWrapper {
   private static final int BUFFER_SIZE = 2048;
   private InputStream content;

   public DecompressingEntity(HttpEntity var1) {
      super(var1);
   }

   public InputStream getContent() throws IOException {
      if (this.wrappedEntity.isStreaming()) {
         if (this.content == null) {
            this.content = this.getDecompressingInputStream(this.wrappedEntity.getContent());
         }

         return this.content;
      } else {
         return this.getDecompressingInputStream(this.wrappedEntity.getContent());
      }
   }

   abstract InputStream getDecompressingInputStream(InputStream var1) throws IOException;

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
