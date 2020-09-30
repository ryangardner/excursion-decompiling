package io.opencensus.trace;

final class AutoValue_MessageEvent extends MessageEvent {
   private final long compressedMessageSize;
   private final long messageId;
   private final MessageEvent.Type type;
   private final long uncompressedMessageSize;

   private AutoValue_MessageEvent(MessageEvent.Type var1, long var2, long var4, long var6) {
      this.type = var1;
      this.messageId = var2;
      this.uncompressedMessageSize = var4;
      this.compressedMessageSize = var6;
   }

   // $FF: synthetic method
   AutoValue_MessageEvent(MessageEvent.Type var1, long var2, long var4, long var6, Object var8) {
      this(var1, var2, var4, var6);
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof MessageEvent)) {
         return false;
      } else {
         MessageEvent var3 = (MessageEvent)var1;
         if (!this.type.equals(var3.getType()) || this.messageId != var3.getMessageId() || this.uncompressedMessageSize != var3.getUncompressedMessageSize() || this.compressedMessageSize != var3.getCompressedMessageSize()) {
            var2 = false;
         }

         return var2;
      }
   }

   public long getCompressedMessageSize() {
      return this.compressedMessageSize;
   }

   public long getMessageId() {
      return this.messageId;
   }

   public MessageEvent.Type getType() {
      return this.type;
   }

   public long getUncompressedMessageSize() {
      return this.uncompressedMessageSize;
   }

   public int hashCode() {
      long var1 = (long)((this.type.hashCode() ^ 1000003) * 1000003);
      long var3 = this.messageId;
      var1 = (long)((int)(var1 ^ var3 ^ var3 >>> 32) * 1000003);
      var3 = this.uncompressedMessageSize;
      var1 = (long)((int)(var1 ^ var3 ^ var3 >>> 32) * 1000003);
      var3 = this.compressedMessageSize;
      return (int)(var1 ^ var3 ^ var3 >>> 32);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("MessageEvent{type=");
      var1.append(this.type);
      var1.append(", messageId=");
      var1.append(this.messageId);
      var1.append(", uncompressedMessageSize=");
      var1.append(this.uncompressedMessageSize);
      var1.append(", compressedMessageSize=");
      var1.append(this.compressedMessageSize);
      var1.append("}");
      return var1.toString();
   }

   static final class Builder extends MessageEvent.Builder {
      private Long compressedMessageSize;
      private Long messageId;
      private MessageEvent.Type type;
      private Long uncompressedMessageSize;

      public MessageEvent build() {
         MessageEvent.Type var1 = this.type;
         String var2 = "";
         StringBuilder var5;
         if (var1 == null) {
            var5 = new StringBuilder();
            var5.append("");
            var5.append(" type");
            var2 = var5.toString();
         }

         String var3 = var2;
         StringBuilder var4;
         if (this.messageId == null) {
            var4 = new StringBuilder();
            var4.append(var2);
            var4.append(" messageId");
            var3 = var4.toString();
         }

         var2 = var3;
         if (this.uncompressedMessageSize == null) {
            var5 = new StringBuilder();
            var5.append(var3);
            var5.append(" uncompressedMessageSize");
            var2 = var5.toString();
         }

         var3 = var2;
         if (this.compressedMessageSize == null) {
            var4 = new StringBuilder();
            var4.append(var2);
            var4.append(" compressedMessageSize");
            var3 = var4.toString();
         }

         if (var3.isEmpty()) {
            return new AutoValue_MessageEvent(this.type, this.messageId, this.uncompressedMessageSize, this.compressedMessageSize);
         } else {
            var5 = new StringBuilder();
            var5.append("Missing required properties:");
            var5.append(var3);
            throw new IllegalStateException(var5.toString());
         }
      }

      public MessageEvent.Builder setCompressedMessageSize(long var1) {
         this.compressedMessageSize = var1;
         return this;
      }

      MessageEvent.Builder setMessageId(long var1) {
         this.messageId = var1;
         return this;
      }

      MessageEvent.Builder setType(MessageEvent.Type var1) {
         if (var1 != null) {
            this.type = var1;
            return this;
         } else {
            throw new NullPointerException("Null type");
         }
      }

      public MessageEvent.Builder setUncompressedMessageSize(long var1) {
         this.uncompressedMessageSize = var1;
         return this;
      }
   }
}
