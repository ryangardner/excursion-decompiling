/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.export;

import io.opencensus.common.Timestamp;
import io.opencensus.trace.export.SpanData;

final class AutoValue_SpanData_TimedEvent<T>
extends SpanData.TimedEvent<T> {
    private final T event;
    private final Timestamp timestamp;

    AutoValue_SpanData_TimedEvent(Timestamp timestamp, T t) {
        if (timestamp == null) throw new NullPointerException("Null timestamp");
        this.timestamp = timestamp;
        if (t == null) throw new NullPointerException("Null event");
        this.event = t;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof SpanData.TimedEvent)) return false;
        if (!this.timestamp.equals(((SpanData.TimedEvent)(object = (SpanData.TimedEvent)object)).getTimestamp())) return false;
        if (!this.event.equals(((SpanData.TimedEvent)object).getEvent())) return false;
        return bl;
    }

    @Override
    public T getEvent() {
        return this.event;
    }

    @Override
    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public int hashCode() {
        return (this.timestamp.hashCode() ^ 1000003) * 1000003 ^ this.event.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TimedEvent{timestamp=");
        stringBuilder.append(this.timestamp);
        stringBuilder.append(", event=");
        stringBuilder.append(this.event);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

