package com.google.api.client.http;

import com.google.api.client.util.StreamingContent;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class GZipEncoding implements HttpEncoding {
   public void encode(StreamingContent var1, OutputStream var2) throws IOException {
      GZIPOutputStream var3 = new GZIPOutputStream(new BufferedOutputStream(var2) {
         public void close() throws IOException {
            try {
               this.flush();
            } catch (IOException var2) {
            }

         }
      });
      var1.writeTo(var3);
      var3.close();
   }

   public String getName() {
      return "gzip";
   }
}
