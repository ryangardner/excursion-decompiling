/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.common.Timestamp;
import io.opencensus.internal.Utils;
import io.opencensus.trace.AutoValue_NetworkEvent;
import io.opencensus.trace.BaseMessageEvent;
import javax.annotation.Nullable;

@Deprecated
public abstract class NetworkEvent
extends BaseMessageEvent {
    NetworkEvent() {
    }

    public static Builder builder(Type type, long l) {
        return new AutoValue_NetworkEvent.Builder().setType(Utils.checkNotNull(type, "type")).setMessageId(l).setUncompressedMessageSize(0L).setCompressedMessageSize(0L);
    }

    public abstract long getCompressedMessageSize();

    @Nullable
    public abstract Timestamp getKernelTimestamp();

    public abstract long getMessageId();

    @Deprecated
    public long getMessageSize() {
        return this.getUncompressedMessageSize();
    }

    public abstract Type getType();

    public abstract long getUncompressedMessageSize();

    @Deprecated
    public static abstract class Builder {
        Builder() {
        }

        public abstract NetworkEvent build();

        public abstract Builder setCompressedMessageSize(long var1);

        public abstract Builder setKernelTimestamp(@Nullable Timestamp var1);

        abstract Builder setMessageId(long var1);

        @Deprecated
        public Builder setMessageSize(long l) {
            return this.setUncompressedMessageSize(l);
        }

        abstract Builder setType(Type var1);

        public abstract Builder setUncompressedMessageSize(long var1);
    }

    public static final class Type
    extends Enum<Type> {
        private static final /* synthetic */ Type[] $VALUES;
        public static final /* enum */ Type RECV;
        public static final /* enum */ Type SENT;

        static {
            Type type;
            SENT = new Type();
            RECV = type = new Type();
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

