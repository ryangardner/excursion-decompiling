package org.apache.http.client.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

public class DeflateDecompressingEntity extends DecompressingEntity {
   public DeflateDecompressingEntity(HttpEntity var1) {
      super(var1);
   }

   public Header getContentEncoding() {
      return null;
   }

   public long getContentLength() {
      return -1L;
   }

   InputStream getDecompressingInputStream(InputStream var1) throws IOException {
      byte[] var2 = new byte[6];
      PushbackInputStream var13 = new PushbackInputStream(var1, 6);
      int var3 = var13.read(var2);
      if (var3 == -1) {
         throw new IOException("Unable to read the response");
      } else {
         byte[] var4 = new byte[1];
         Inflater var5 = new Inflater();

         while(true) {
            boolean var10001;
            int var6;
            try {
               var6 = var5.inflate(var4);
            } catch (DataFormatException var12) {
               var10001 = false;
               break;
            }

            label57: {
               IOException var14;
               label56: {
                  if (var6 == 0) {
                     try {
                        if (var5.finished()) {
                           break label56;
                        }

                        if (!var5.needsDictionary()) {
                           break label57;
                        }
                     } catch (DataFormatException var11) {
                        var10001 = false;
                        break;
                     }
                  }

                  if (var6 != -1) {
                     try {
                        var13.unread(var2, 0, var3);
                        return new InflaterInputStream(var13);
                     } catch (DataFormatException var9) {
                        var10001 = false;
                        break;
                     }
                  } else {
                     try {
                        var14 = new IOException("Unable to read the response");
                        throw var14;
                     } catch (DataFormatException var10) {
                        var10001 = false;
                        break;
                     }
                  }
               }

               try {
                  var14 = new IOException("Unable to read the response");
                  throw var14;
               } catch (DataFormatException var8) {
                  var10001 = false;
                  break;
               }
            }

            try {
               if (var5.needsInput()) {
                  var5.setInput(var2);
               }
            } catch (DataFormatException var7) {
               var10001 = false;
               break;
            }
         }

         var13.unread(var2, 0, var3);
         return new InflaterInputStream(var13, new Inflater(true));
      }
   }
}
