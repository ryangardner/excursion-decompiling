/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.export;

import io.opencensus.trace.export.SpanData;
import java.util.List;

final class AutoValue_SpanData_TimedEvents<T>
extends SpanData.TimedEvents<T> {
    private final int droppedEventsCount;
    private final List<SpanData.TimedEvent<T>> events;

    AutoValue_SpanData_TimedEvents(List<SpanData.TimedEvent<T>> list, int n) {
        if (list == null) throw new NullPointerException("Null events");
        this.events = list;
        this.droppedEventsCount = n;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof SpanData.TimedEvents)) return false;
        if (!this.events.equals(((SpanData.TimedEvents)(object = (SpanData.TimedEvents)object)).getEvents())) return false;
        if (this.droppedEventsCount != ((SpanData.TimedEvents)object).getDroppedEventsCount()) return false;
        return bl;
    }

    @Override
    public int getDroppedEventsCount() {
        return this.droppedEventsCount;
    }

    @Override
    public List<SpanData.TimedEvent<T>> getEvents() {
        return this.events;
    }

    public int hashCode() {
        return (this.events.hashCode() ^ 1000003) * 1000003 ^ this.droppedEventsCount;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TimedEvents{events=");
        stringBuilder.append(this.events);
        stringBuilder.append(", droppedEventsCount=");
        stringBuilder.append(this.droppedEventsCount);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

