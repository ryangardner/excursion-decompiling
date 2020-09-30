package org.apache.commons.net.io;

import java.io.IOException;

public class CopyStreamException extends IOException {
   private static final long serialVersionUID = -2602899129433221532L;
   private final long totalBytesTransferred;

   public CopyStreamException(String var1, long var2, IOException var4) {
      super(var1);
      this.initCause(var4);
      this.totalBytesTransferred = var2;
   }

   public IOException getIOException() {
      return (IOException)this.getCause();
   }

   public long getTotalBytesTransferred() {
      return this.totalBytesTransferred;
   }
}
