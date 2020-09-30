/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.trace.MessageEvent;

final class AutoValue_MessageEvent
extends MessageEvent {
    private final long compressedMessageSize;
    private final long messageId;
    private final MessageEvent.Type type;
    private final long uncompressedMessageSize;

    private AutoValue_MessageEvent(MessageEvent.Type type, long l, long l2, long l3) {
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
        if (!(object instanceof MessageEvent)) return false;
        if (!this.type.equals((Object)((MessageEvent)(object = (MessageEvent)object)).getType())) return false;
        if (this.messageId != ((MessageEvent)object).getMessageId()) return false;
        if (this.uncompressedMessageSize != ((MessageEvent)object).getUncompressedMessageSize()) return false;
        if (this.compressedMessageSize != ((MessageEvent)object).getCompressedMessageSize()) return false;
        return bl;
    }

    @Override
    public long getCompressedMessageSize() {
        return this.compressedMessageSize;
    }

    @Override
    public long getMessageId() {
        return this.messageId;
    }

    @Override
    public MessageEvent.Type getType() {
        return this.type;
    }

    @Override
    public long getUncompressedMessageSize() {
        return this.uncompressedMessageSize;
    }

    public int hashCode() {
        long l = (this.type.hashCode() ^ 1000003) * 1000003;
        long l2 = this.messageId;
        l = (int)(l ^ (l2 ^ l2 >>> 32)) * 1000003;
        l2 = this.uncompressedMessageSize;
        l = (int)(l ^ (l2 ^ l2 >>> 32)) * 1000003;
        l2 = this.compressedMessageSize;
        return (int)(l ^ (l2 ^ l2 >>> 32));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MessageEvent{type=");
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
    extends MessageEvent.Builder {
        private Long compressedMessageSize;
        private Long messageId;
        private MessageEvent.Type type;
        private Long uncompressedMessageSize;

        Builder() {
        }

        @Override
        public MessageEvent build() {
            Object object = this.type;
            Object object2 = "";
            if (object == null) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("");
                ((StringBuilder)object2).append(" type");
                object2 = ((StringBuilder)object2).toString();
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
                return new AutoValue_MessageEvent(this.type, this.messageId, this.uncompressedMessageSize, this.compressedMessageSize);
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Missing required properties:");
            ((StringBuilder)object2).append((String)object);
            throw new IllegalStateException(((StringBuilder)object2).toString());
        }

        @Override
        public MessageEvent.Builder setCompressedMessageSize(long l) {
            this.compressedMessageSize = l;
            return this;
        }

        @Override
        MessageEvent.Builder setMessageId(long l) {
            this.messageId = l;
            return this;
        }

        @Override
        MessageEvent.Builder setType(MessageEvent.Type type) {
            if (type == null) throw new NullPointerException("Null type");
            this.type = type;
            return this;
        }

        @Override
        public MessageEvent.Builder setUncompressedMessageSize(long l) {
            this.uncompressedMessageSize = l;
            return this;
        }
    }

}

