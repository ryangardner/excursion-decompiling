/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.internal.Utils;
import io.opencensus.trace.AutoValue_MessageEvent;
import io.opencensus.trace.BaseMessageEvent;

public abstract class MessageEvent
extends BaseMessageEvent {
    MessageEvent() {
    }

    public static Builder builder(Type type, long l) {
        return new AutoValue_MessageEvent.Builder().setType(Utils.checkNotNull(type, "type")).setMessageId(l).setUncompressedMessageSize(0L).setCompressedMessageSize(0L);
    }

    public abstract long getCompressedMessageSize();

    public abstract long getMessageId();

    public abstract Type getType();

    public abstract long getUncompressedMessageSize();

    public static abstract class Builder {
        Builder() {
        }

        public abstract MessageEvent build();

        public abstract Builder setCompressedMessageSize(long var1);

        abstract Builder setMessageId(long var1);

        abstract Builder setType(Type var1);

        public abstract Builder setUncompressedMessageSize(long var1);
    }

    public static final class Type
    extends Enum<Type> {
        private static final /* synthetic */ Type[] $VALUES;
        public static final /* enum */ Type RECEIVED;
        public static final /* enum */ Type SENT;

        static {
            Type type;
            SENT = new Type();
            RECEIVED = type = new Type();
            $VALUES = new Type[]{SENT, type};
        }

        public static Type valueOf(String string2) {
            return Enum.valueOf(Type.class, string2);
        }

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }
    }

}

