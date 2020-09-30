package com.google.api.client.util;

import java.io.IOException;
import java.io.OutputStream;

final class ByteCountingOutputStream extends OutputStream {
   long count;

   public void write(int var1) throws IOException {
      ++this.count;
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      this.count += (long)var3;
   }
}
