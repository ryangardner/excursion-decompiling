package com.google.api.client.util;

import java.io.IOException;
import java.io.OutputStream;

public class ByteArrayStreamingContent implements StreamingContent {
   private final byte[] byteArray;
   private final int length;
   private final int offset;

   public ByteArrayStreamingContent(byte[] var1) {
      this(var1, 0, var1.length);
   }

   public ByteArrayStreamingContent(byte[] var1, int var2, int var3) {
      this.byteArray = (byte[])Preconditions.checkNotNull(var1);
      boolean var4;
      if (var2 >= 0 && var3 >= 0 && var2 + var3 <= var1.length) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4);
      this.offset = var2;
      this.length = var3;
   }

   public void writeTo(OutputStream var1) throws IOException {
      var1.write(this.byteArray, this.offset, this.length);
      var1.flush();
   }
}
