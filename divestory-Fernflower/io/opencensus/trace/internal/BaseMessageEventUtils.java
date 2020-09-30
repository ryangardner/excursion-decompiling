package io.opencensus.trace.internal;

import io.opencensus.internal.Utils;
import io.opencensus.trace.BaseMessageEvent;
import io.opencensus.trace.MessageEvent;
import io.opencensus.trace.NetworkEvent;

public final class BaseMessageEventUtils {
   private BaseMessageEventUtils() {
   }

   public static MessageEvent asMessageEvent(BaseMessageEvent var0) {
      Utils.checkNotNull(var0, "event");
      if (var0 instanceof MessageEvent) {
         return (MessageEvent)var0;
      } else {
         NetworkEvent var1 = (NetworkEvent)var0;
         MessageEvent.Type var2;
         if (var1.getType() == NetworkEvent.Type.RECV) {
            var2 = MessageEvent.Type.RECEIVED;
         } else {
            var2 = MessageEvent.Type.SENT;
         }

         return MessageEvent.builder(var2, var1.getMessageId()).setUncompressedMessageSize(var1.getUncompressedMessageSize()).setCompressedMessageSize(var1.getCompressedMessageSize()).build();
      }
   }

   public static NetworkEvent asNetworkEvent(BaseMessageEvent var0) {
      Utils.checkNotNull(var0, "event");
      if (var0 instanceof NetworkEvent) {
         return (NetworkEvent)var0;
      } else {
         MessageEvent var1 = (MessageEvent)var0;
         NetworkEvent.Type var2;
         if (var1.getType() == MessageEvent.Type.RECEIVED) {
            var2 = NetworkEvent.Type.RECV;
         } else {
            var2 = NetworkEvent.Type.SENT;
         }

         return NetworkEvent.builder(var2, var1.getMessageId()).setUncompressedMessageSize(var1.getUncompressedMessageSize()).setCompressedMessageSize(var1.getCompressedMessageSize()).build();
      }
   }
}
