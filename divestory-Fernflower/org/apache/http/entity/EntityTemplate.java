package org.apache.http.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class EntityTemplate extends AbstractHttpEntity {
   private final ContentProducer contentproducer;

   public EntityTemplate(ContentProducer var1) {
      if (var1 != null) {
         this.contentproducer = var1;
      } else {
         throw new IllegalArgumentException("Content producer may not be null");
      }
   }

   public InputStream getContent() {
      throw new UnsupportedOperationException("Entity template does not implement getContent()");
   }

   public long getContentLength() {
      return -1L;
   }

   public boolean isRepeatable() {
      return true;
   }

   public boolean isStreaming() {
      return false;
   }

   public void writeTo(OutputStream var1) throws IOException {
      if (var1 != null) {
         this.contentproducer.writeTo(var1);
      } else {
         throw new IllegalArgumentException("Output stream may not be null");
      }
   }
}
