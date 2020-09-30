package com.google.api.client.testing.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class TestableByteArrayInputStream extends ByteArrayInputStream {
   private boolean closed;

   public TestableByteArrayInputStream(byte[] var1) {
      super(var1);
   }

   public TestableByteArrayInputStream(byte[] var1, int var2, int var3) {
      super(var1);
   }

   public void close() throws IOException {
      this.closed = true;
   }

   public final byte[] getBuffer() {
      return this.buf;
   }

   public final boolean isClosed() {
      return this.closed;
   }
}
