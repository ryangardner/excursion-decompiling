package javax.mail.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.DataSource;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.ParseException;

public class ByteArrayDataSource implements DataSource {
   private byte[] data;
   private int len = -1;
   private String name = "";
   private String type;

   public ByteArrayDataSource(InputStream var1, String var2) throws IOException {
      ByteArrayDataSource.DSByteArrayOutputStream var3 = new ByteArrayDataSource.DSByteArrayOutputStream();
      byte[] var4 = new byte[8192];

      while(true) {
         int var5 = var1.read(var4);
         if (var5 <= 0) {
            this.data = var3.getBuf();
            var5 = var3.getCount();
            this.len = var5;
            if (this.data.length - var5 > 262144) {
               byte[] var6 = var3.toByteArray();
               this.data = var6;
               this.len = var6.length;
            }

            this.type = var2;
            return;
         }

         var3.write(var4, 0, var5);
      }
   }

   public ByteArrayDataSource(String var1, String var2) throws IOException {
      String var3;
      try {
         ContentType var6 = new ContentType(var2);
         var3 = var6.getParameter("charset");
      } catch (ParseException var5) {
         var3 = null;
      }

      String var4 = var3;
      if (var3 == null) {
         var4 = MimeUtility.getDefaultJavaCharset();
      }

      this.data = var1.getBytes(var4);
      this.type = var2;
   }

   public ByteArrayDataSource(byte[] var1, String var2) {
      this.data = var1;
      this.type = var2;
   }

   public String getContentType() {
      return this.type;
   }

   public InputStream getInputStream() throws IOException {
      byte[] var1 = this.data;
      if (var1 != null) {
         if (this.len < 0) {
            this.len = var1.length;
         }

         return new SharedByteArrayInputStream(this.data, 0, this.len);
      } else {
         throw new IOException("no data");
      }
   }

   public String getName() {
      return this.name;
   }

   public OutputStream getOutputStream() throws IOException {
      throw new IOException("cannot do this");
   }

   public void setName(String var1) {
      this.name = var1;
   }

   static class DSByteArrayOutputStream extends ByteArrayOutputStream {
      public byte[] getBuf() {
         return this.buf;
      }

      public int getCount() {
         return this.count;
      }
   }
}
