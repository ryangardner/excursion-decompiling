package io.opencensus.trace;

import io.opencensus.internal.Utils;

public abstract class MessageEvent extends BaseMessageEvent {
   MessageEvent() {
   }

   public static MessageEvent.Builder builder(MessageEvent.Type var0, long var1) {
      return (new AutoValue_MessageEvent.Builder()).setType((MessageEvent.Type)Utils.checkNotNull(var0, "type")).setMessageId(var1).setUncompressedMessageSize(0L).setCompressedMessageSize(0L);
   }

   public abstract long getCompressedMessageSize();

   public abstract long getMessageId();

   public abstract MessageEvent.Type getType();

   public abstract long getUncompressedMessageSize();

   public abstract static class Builder {
      Builder() {
      }

      public abstract MessageEvent build();

      public abstract MessageEvent.Builder setCompressedMessageSize(long var1);

      abstract MessageEvent.Builder setMessageId(long var1);

      abstract MessageEvent.Builder setType(MessageEvent.Type var1);

      public abstract MessageEvent.Builder setUncompressedMessageSize(long var1);
   }

   public static enum Type {
      RECEIVED,
      SENT;

      static {
         MessageEvent.Type var0 = new MessageEvent.Type("RECEIVED", 1);
         RECEIVED = var0;
      }
   }
}
