package org.apache.http.entity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class StringEntity extends AbstractHttpEntity implements Cloneable {
   protected final byte[] content;

   public StringEntity(String var1) throws UnsupportedEncodingException {
      this(var1, (String)null);
   }

   public StringEntity(String var1, String var2) throws UnsupportedEncodingException {
      this(var1, (String)null, var2);
   }

   public StringEntity(String var1, String var2, String var3) throws UnsupportedEncodingException {
      if (var1 != null) {
         String var4 = var2;
         if (var2 == null) {
            var4 = "text/plain";
         }

         var2 = var3;
         if (var3 == null) {
            var2 = "ISO-8859-1";
         }

         this.content = var1.getBytes(var2);
         StringBuffer var5 = new StringBuffer();
         var5.append(var4);
         var5.append("; charset=");
         var5.append(var2);
         this.setContentType(var5.toString());
      } else {
         throw new IllegalArgumentException("Source string may not be null");
      }
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public InputStream getContent() throws IOException {
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
