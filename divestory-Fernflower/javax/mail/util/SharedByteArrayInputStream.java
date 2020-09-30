package javax.mail.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.mail.internet.SharedInputStream;

public class SharedByteArrayInputStream extends ByteArrayInputStream implements SharedInputStream {
   protected int start = 0;

   public SharedByteArrayInputStream(byte[] var1) {
      super(var1);
   }

   public SharedByteArrayInputStream(byte[] var1, int var2, int var3) {
      super(var1, var2, var3);
      this.start = var2;
   }

   public long getPosition() {
      return (long)(this.pos - this.start);
   }

   public InputStream newStream(long var1, long var3) {
      if (var1 >= 0L) {
         long var5 = var3;
         if (var3 == -1L) {
            var5 = (long)(this.count - this.start);
         }

         return new SharedByteArrayInputStream(this.buf, this.start + (int)var1, (int)(var5 - var1));
      } else {
         throw new IllegalArgumentException("start < 0");
      }
   }
}
