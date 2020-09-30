/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.common.Timestamp;
import io.opencensus.trace.NetworkEvent;
import javax.annotation.Nullable;

@Deprecated
final class AutoValue_NetworkEvent
extends NetworkEvent {
    private final long compressedMessageSize;
    private final Timestamp kernelTimestamp;
    private final long messageId;
    private final NetworkEvent.Type type;
    private final long uncompressedMessageSize;

    private AutoValue_NetworkEvent(@Nullable Timestamp timestamp, NetworkEvent.Type type, long l, long l2, long l3) {
        this.kernelTimestamp = timestamp;
        this.type = type;
        this.messageId = l;
        this.uncompressedMessageSize = l2;
        this.compressedMessageSize = l3;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof NetworkEvent)) return false;
        NetworkEvent networkEvent = (NetworkEvent)object;
        object = this.kernelTimestamp;
        if (object == null) {
            if (networkEvent.getKernelTimestamp() != null) return false;
        } else if (!object.equals(networkEvent.getKernelTimestamp())) return false;
        if (!this.type.equals((Object)networkEvent.getType())) return false;
        if (this.messageId != networkEvent.getMessageId()) return false;
        if (this.uncompressedMessageSize != networkEvent.getUncompressedMessageSize()) return false;
        if (this.compressedMessageSize != networkEvent.getCompressedMessageSize()) return false;
        return bl;
    }

    @Override
    public long getCompressedMessageSize() {
        return this.compressedMessageSize;
    }

    @Nullable
    @Override
    public Timestamp getKernelTimestamp() {
        return this.kernelTimestamp;
    }

    @Override
    public long getMessageId() {
        return this.messageId;
    }

    @Override
    public NetworkEvent.Type getType() {
        return this.type;
    }

    @Override
    public long getUncompressedMessageSize() {
        return this.uncompressedMessageSize;
    }

    public int hashCode() {
        Timestamp timestamp = this.kernelTimestamp;
        int n = timestamp == null ? 0 : timestamp.hashCode();
        long l = ((n ^ 1000003) * 1000003 ^ this.type.hashCode()) * 1000003;
        long l2 = this.messageId;
        l2 = (int)(l ^ (l2 ^ l2 >>> 32)) * 1000003;
        l = this.uncompressedMessageSize;
        l2 = (int)(l2 ^ (l ^ l >>> 32)) * 1000003;
        l = this.compressedMessageSize;
        return (int)(l2 ^ (l ^ l >>> 32));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("NetworkEvent{kernelTimestamp=");
        stringBuilder.append(this.kernelTimestamp);
        stringBuilder.append(", type=");
        stringBuilder.append((Object)this.type);
        stringBuilder.append(", messageId=");
        stringBuilder.append(this.messageId);
        stringBuilder.append(", uncompressedMessageSize=");
        stringBuilder.append(this.uncompressedMessageSize);
        stringBuilder.append(", compressedMessageSize=");
        stringBuilder.append(this.compressedMessageSize);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    static final class Builder
    extends NetworkEvent.Builder {
        private Long compressedMessageSize;
        private Timestamp kernelTimestamp;
        private Long messageId;
        private NetworkEvent.Type type;
        private Long uncompressedMessageSize;

        Builder() {
        }

        @Override
        public NetworkEvent build() {
            Object object = this.type;
            Object object2 = "";
            if (object == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("");
                ((StringBuilder)object).append(" type");
                object2 = ((StringBuilder)object).toString();
            }
            object = object2;
            if (this.messageId == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)object2);
                ((StringBuilder)object).append(" messageId");
                object = ((StringBuilder)object).toString();
            }
            object2 = object;
            if (this.uncompressedMessageSize == null) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append(" uncompressedMessageSize");
                object2 = ((StringBuilder)object2).toString();
            }
            object = object2;
            if (this.compressedMessageSize == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)object2);
                ((StringBuilder)object).append(" compressedMessageSize");
                object = ((StringBuilder)object).toString();
            }
            if (((String)object).isEmpty()) {
                return new AutoValue_NetworkEvent(this.kernelTimestamp, this.type, this.messageId, this.uncompressedMessageSize, this.compressedMessageSize);
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Missing required properties:");
            ((StringBuilder)object2).append((String)object);
            throw new IllegalStateException(((StringBuilder)object2).toString());
        }

        @Override
        public NetworkEvent.Builder setCompressedMessageSize(long l) {
            this.compressedMessageSize = l;
            return this;
        }

        @Override
        public NetworkEvent.Builder setKernelTimestamp(@Nullable Timestamp timestamp) {
            this.kernelTimestamp = timestamp;
            return this;
        }

        @Override
        NetworkEvent.Builder setMessageId(long l) {
            this.messageId = l;
            return this;
        }

        @Override
        NetworkEvent.Builder setType(NetworkEvent.Type type) {
            if (type == null) throw new NullPointerException("Null type");
            this.type = type;
            return this;
        }

        @Override
        public NetworkEvent.Builder setUncompressedMessageSize(long l) {
            this.uncompressedMessageSize = l;
            return this;
        }
    }

}

