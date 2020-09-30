package org.apache.http.entity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

public class BufferedHttpEntity extends HttpEntityWrapper {
   private final byte[] buffer;

   public BufferedHttpEntity(HttpEntity var1) throws IOException {
      super(var1);
      if (var1.isRepeatable() && var1.getContentLength() >= 0L) {
         this.buffer = null;
      } else {
         this.buffer = EntityUtils.toByteArray(var1);
      }

   }

   public InputStream getContent() throws IOException {
      return (InputStream)(this.buffer != null ? new ByteArrayInputStream(this.buffer) : this.wrappedEntity.getContent());
   }

   public long getContentLength() {
      byte[] var1 = this.buffer;
      return var1 != null ? (long)var1.length : this.wrappedEntity.getContentLength();
   }

   public boolean isChunked() {
      boolean var1;
      if (this.buffer == null && this.wrappedEntity.isChunked()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isRepeatable() {
      return true;
   }

   public boolean isStreaming() {
      boolean var1;
      if (this.buffer == null && this.wrappedEntity.isStreaming()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void writeTo(OutputStream var1) throws IOException {
      if (var1 != null) {
         byte[] var2 = this.buffer;
         if (var2 != null) {
            var1.write(var2);
         } else {
            this.wrappedEntity.writeTo(var1);
         }

      } else {
         throw new IllegalArgumentException("Output stream may not be null");
      }
   }
}
