package io.opencensus.trace;

import io.opencensus.common.Timestamp;
import io.opencensus.internal.Utils;
import javax.annotation.Nullable;

@Deprecated
public abstract class NetworkEvent extends BaseMessageEvent {
   NetworkEvent() {
   }

   public static NetworkEvent.Builder builder(NetworkEvent.Type var0, long var1) {
      return (new AutoValue_NetworkEvent.Builder()).setType((NetworkEvent.Type)Utils.checkNotNull(var0, "type")).setMessageId(var1).setUncompressedMessageSize(0L).setCompressedMessageSize(0L);
   }

   public abstract long getCompressedMessageSize();

   @Nullable
   public abstract Timestamp getKernelTimestamp();

   public abstract long getMessageId();

   @Deprecated
   public long getMessageSize() {
      return this.getUncompressedMessageSize();
   }

   public abstract NetworkEvent.Type getType();

   public abstract long getUncompressedMessageSize();

   @Deprecated
   public abstract static class Builder {
      Builder() {
      }

      public abstract NetworkEvent build();

      public abstract NetworkEvent.Builder setCompressedMessageSize(long var1);

      public abstract NetworkEvent.Builder setKernelTimestamp(@Nullable Timestamp var1);

      abstract NetworkEvent.Builder setMessageId(long var1);

      @Deprecated
      public NetworkEvent.Builder setMessageSize(long var1) {
         return this.setUncompressedMessageSize(var1);
      }

      abstract NetworkEvent.Builder setType(NetworkEvent.Type var1);

      public abstract NetworkEvent.Builder setUncompressedMessageSize(long var1);
   }

   public static enum Type {
      RECV,
      SENT;

      static {
         NetworkEvent.Type var0 = new NetworkEvent.Type("RECV", 1);
         RECV = var0;
      }
   }
}
