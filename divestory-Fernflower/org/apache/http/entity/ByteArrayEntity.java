package org.apache.http.entity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ByteArrayEntity extends AbstractHttpEntity implements Cloneable {
   protected final byte[] content;

   public ByteArrayEntity(byte[] var1) {
      if (var1 != null) {
         this.content = var1;
      } else {
         throw new IllegalArgumentException("Source byte array may not be null");
      }
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public InputStream getContent() {
      return new ByteArrayInputStream(this.content);
   }

   public long getContentLength() {
      return (long)this.content.length;
   }

   public boolean isRepeatable() {
      return true;
   }

   public boolean isStreaming() {
      return false;
   }

   public void writeTo(OutputStream var1) throws IOException {
      if (var1 != null) {
         var1.write(this.content);
         var1.flush();
      } else {
         throw new IllegalArgumentException("Output stream may not be null");
      }
   }
}
