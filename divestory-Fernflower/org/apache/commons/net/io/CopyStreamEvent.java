package org.apache.commons.net.io;

import java.util.EventObject;

public class CopyStreamEvent extends EventObject {
   public static final long UNKNOWN_STREAM_SIZE = -1L;
   private static final long serialVersionUID = -964927635655051867L;
   private final int bytesTransferred;
   private final long streamSize;
   private final long totalBytesTransferred;

   public CopyStreamEvent(Object var1, long var2, int var4, long var5) {
      super(var1);
      this.bytesTransferred = var4;
      this.totalBytesTransferred = var2;
      this.streamSize = var5;
   }

   public int getBytesTransferred() {
      return this.bytesTransferred;
   }

   public long getStreamSize() {
      return this.streamSize;
   }

   public long getTotalBytesTransferred() {
      return this.totalBytesTransferred;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.getClass().getName());
      var1.append("[source=");
      var1.append(this.source);
      var1.append(", total=");
      var1.append(this.totalBytesTransferred);
      var1.append(", bytes=");
      var1.append(this.bytesTransferred);
      var1.append(", size=");
      var1.append(this.streamSize);
      var1.append("]");
      return var1.toString();
   }
}
