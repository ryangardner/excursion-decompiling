package com.google.api.client.http;

import com.google.common.io.ByteStreams;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

final class ConsumingInputStream extends FilterInputStream {
   private boolean closed = false;

   ConsumingInputStream(InputStream var1) {
      super(var1);
   }

   public void close() throws IOException {
      if (!this.closed && this.in != null) {
         try {
            ByteStreams.exhaust(this);
            super.in.close();
         } finally {
            this.closed = true;
         }
      }

   }
}
