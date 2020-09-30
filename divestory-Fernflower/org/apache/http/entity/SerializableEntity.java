package org.apache.http.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class SerializableEntity extends AbstractHttpEntity {
   private Serializable objRef;
   private byte[] objSer;

   public SerializableEntity(Serializable var1, boolean var2) throws IOException {
      if (var1 != null) {
         if (var2) {
            this.createBytes(var1);
         } else {
            this.objRef = var1;
         }

      } else {
         throw new IllegalArgumentException("Source object may not be null");
      }
   }

   private void createBytes(Serializable var1) throws IOException {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      ObjectOutputStream var3 = new ObjectOutputStream(var2);
      var3.writeObject(var1);
      var3.flush();
      this.objSer = var2.toByteArray();
   }

   public InputStream getContent() throws IOException, IllegalStateException {
      if (this.objSer == null) {
         this.createBytes(this.objRef);
      }

      return new ByteArrayInputStream(this.objSer);
   }

   public long getContentLength() {
      byte[] var1 = this.objSer;
      return var1 == null ? -1L : (long)var1.length;
   }

   public boolean isRepeatable() {
      return true;
   }

   public boolean isStreaming() {
      boolean var1;
      if (this.objSer == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void writeTo(OutputStream var1) throws IOException {
      if (var1 != null) {
         byte[] var2 = this.objSer;
         if (var2 == null) {
            ObjectOutputStream var3 = new ObjectOutputStream(var1);
            var3.writeObject(this.objRef);
            var3.flush();
         } else {
            var1.write(var2);
            var1.flush();
         }

      } else {
         throw new IllegalArgumentException("Output stream may not be null");
      }
   }
}
