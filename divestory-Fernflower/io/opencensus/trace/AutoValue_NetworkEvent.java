package io.opencensus.trace;

import io.opencensus.common.Timestamp;
import javax.annotation.Nullable;

@Deprecated
final class AutoValue_NetworkEvent extends NetworkEvent {
   private final long compressedMessageSize;
   private final Timestamp kernelTimestamp;
   private final long messageId;
   private final NetworkEvent.Type type;
   private final long uncompressedMessageSize;

   private AutoValue_NetworkEvent(@Nullable Timestamp var1, NetworkEvent.Type var2, long var3, long var5, long var7) {
      this.kernelTimestamp = var1;
      this.type = var2;
      this.messageId = var3;
      this.uncompressedMessageSize = var5;
      this.compressedMessageSize = var7;
   }

   // $FF: synthetic method
   AutoValue_NetworkEvent(Timestamp var1, NetworkEvent.Type var2, long var3, long var5, long var7, Object var9) {
      this(var1, var2, var3, var5, var7);
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof NetworkEvent)) {
         return false;
      } else {
         label29: {
            NetworkEvent var3 = (NetworkEvent)var1;
            Timestamp var4 = this.kernelTimestamp;
            if (var4 == null) {
               if (var3.getKernelTimestamp() != null) {
                  break label29;
               }
            } else if (!var4.equals(var3.getKernelTimestamp())) {
               break label29;
            }

            if (this.type.equals(var3.getType()) && this.messageId == var3.getMessageId() && this.uncompressedMessageSize == var3.getUncompressedMessageSize() && this.compressedMessageSize == var3.getCompressedMessageSize()) {
               return var2;
            }
         }

         var2 = false;
         return var2;
      }
   }

   public long getCompressedMessageSize() {
      return this.compressedMessageSize;
   }

   @Nullable
   public Timestamp getKernelTimestamp() {
      return this.kernelTimestamp;
   }

   public long getMessageId() {
      return this.messageId;
   }

   public NetworkEvent.Type getType() {
      return this.type;
   }

   public long getUncompressedMessageSize() {
      return this.uncompressedMessageSize;
   }

   public int hashCode() {
      Timestamp var1 = this.kernelTimestamp;
      int var2;
      if (var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.hashCode();
      }

      long var3 = (long)(((var2 ^ 1000003) * 1000003 ^ this.type.hashCode()) * 1000003);
      long var5 = this.messageId;
      var5 = (long)((int)(var3 ^ var5 ^ var5 >>> 32) * 1000003);
      var3 = this.uncompressedMessageSize;
      var5 = (long)((int)(var5 ^ var3 ^ var3 >>> 32) * 1000003);
      var3 = this.compressedMessageSize;
      return (int)(var5 ^ var3 ^ var3 >>> 32);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("NetworkEvent{kernelTimestamp=");
      var1.append(this.kernelTimestamp);
      var1.append(", type=");
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

   static final class Builder extends NetworkEvent.Builder {
      private Long compressedMessageSize;
      private Timestamp kernelTimestamp;
      private Long messageId;
      private NetworkEvent.Type type;
      private Long uncompressedMessageSize;

      public NetworkEvent build() {
         NetworkEvent.Type var1 = this.type;
         String var2 = "";
         StringBuilder var3;
         if (var1 == null) {
            var3 = new StringBuilder();
            var3.append("");
            var3.append(" type");
            var2 = var3.toString();
         }

         String var4 = var2;
         if (this.messageId == null) {
            var3 = new StringBuilder();
            var3.append(var2);
            var3.append(" messageId");
            var4 = var3.toString();
         }

         var2 = var4;
         StringBuilder var5;
         if (this.uncompressedMessageSize == null) {
            var5 = new StringBuilder();
            var5.append(var4);
            var5.append(" uncompressedMessageSize");
            var2 = var5.toString();
         }

         var4 = var2;
         if (this.compressedMessageSize == null) {
            var3 = new StringBuilder();
            var3.append(var2);
            var3.append(" compressedMessageSize");
            var4 = var3.toString();
         }

         if (var4.isEmpty()) {
            return new AutoValue_NetworkEvent(this.kernelTimestamp, this.type, this.messageId, this.uncompressedMessageSize, this.compressedMessageSize);
         } else {
            var5 = new StringBuilder();
            var5.append("Missing required properties:");
            var5.append(var4);
            throw new IllegalStateException(var5.toString());
         }
      }

      public NetworkEvent.Builder setCompressedMessageSize(long var1) {
         this.compressedMessageSize = var1;
         return this;
      }

      public NetworkEvent.Builder setKernelTimestamp(@Nullable Timestamp var1) {
         this.kernelTimestamp = var1;
         return this;
      }

      NetworkEvent.Builder setMessageId(long var1) {
         this.messageId = var1;
         return this;
      }

      NetworkEvent.Builder setType(NetworkEvent.Type var1) {
         if (var1 != null) {
            this.type = var1;
            return this;
         } else {
            throw new NullPointerException("Null type");
         }
      }

      public NetworkEvent.Builder setUncompressedMessageSize(long var1) {
         this.uncompressedMessageSize = var1;
         return this;
      }
   }
}
