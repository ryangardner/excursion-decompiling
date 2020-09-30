/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.internal;

import io.opencensus.internal.Utils;
import io.opencensus.trace.BaseMessageEvent;
import io.opencensus.trace.MessageEvent;
import io.opencensus.trace.NetworkEvent;

public final class BaseMessageEventUtils {
    private BaseMessageEventUtils() {
    }

    public static MessageEvent asMessageEvent(BaseMessageEvent object) {
        Utils.checkNotNull(object, "event");
        if (object instanceof MessageEvent) {
            return (MessageEvent)object;
        }
        NetworkEvent networkEvent = (NetworkEvent)object;
        if (networkEvent.getType() == NetworkEvent.Type.RECV) {
            object = MessageEvent.Type.RECEIVED;
            return MessageEvent.builder((MessageEvent.Type)((Object)object), networkEvent.getMessageId()).setUncompressedMessageSize(networkEvent.getUncompressedMessageSize()).setCompressedMessageSize(networkEvent.getCompressedMessageSize()).build();
        }
        object = MessageEvent.Type.SENT;
        return MessageEvent.builder((MessageEvent.Type)((Object)object), networkEvent.getMessageId()).setUncompressedMessageSize(networkEvent.getUncompressedMessageSize()).setCompressedMessageSize(networkEvent.getCompressedMessageSize()).build();
    }

    public static NetworkEvent asNetworkEvent(BaseMessageEvent object) {
        Utils.checkNotNull(object, "event");
        if (object instanceof NetworkEvent) {
            return (NetworkEvent)object;
        }
        MessageEvent messageEvent = (MessageEvent)object;
        if (messageEvent.getType() == MessageEvent.Type.RECEIVED) {
            object = NetworkEvent.Type.RECV;
            return NetworkEvent.builder((NetworkEvent.Type)((Object)object), messageEvent.getMessageId()).setUncompressedMessageSize(messageEvent.getUncompressedMessageSize()).setCompressedMessageSize(messageEvent.getCompressedMessageSize()).build();
        }
        object = NetworkEvent.Type.SENT;
        return NetworkEvent.builder((NetworkEvent.Type)((Object)object), messageEvent.getMessageId()).setUncompressedMessageSize(messageEvent.getUncompressedMessageSize()).setCompressedMessageSize(messageEvent.getCompressedMessageSize()).build();
    }
}

