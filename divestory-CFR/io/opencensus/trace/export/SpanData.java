/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.export;

import io.opencensus.common.Timestamp;
import io.opencensus.internal.Utils;
import io.opencensus.trace.Annotation;
import io.opencensus.trace.AttributeValue;
import io.opencensus.trace.BaseMessageEvent;
import io.opencensus.trace.Link;
import io.opencensus.trace.MessageEvent;
import io.opencensus.trace.NetworkEvent;
import io.opencensus.trace.Span;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.SpanId;
import io.opencensus.trace.Status;
import io.opencensus.trace.export.AutoValue_SpanData;
import io.opencensus.trace.export.AutoValue_SpanData_Attributes;
import io.opencensus.trace.export.AutoValue_SpanData_Links;
import io.opencensus.trace.export.AutoValue_SpanData_TimedEvent;
import io.opencensus.trace.export.AutoValue_SpanData_TimedEvents;
import io.opencensus.trace.internal.BaseMessageEventUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public abstract class SpanData {
    SpanData() {
    }

    @Deprecated
    public static SpanData create(SpanContext spanContext, @Nullable SpanId spanId, @Nullable Boolean bl, String string2, Timestamp timestamp, Attributes attributes, TimedEvents<Annotation> timedEvents, TimedEvents<? extends BaseMessageEvent> timedEvents2, Links links, @Nullable Integer n, @Nullable Status status, @Nullable Timestamp timestamp2) {
        return SpanData.create(spanContext, spanId, bl, string2, null, timestamp, attributes, timedEvents, timedEvents2, links, n, status, timestamp2);
    }

    public static SpanData create(SpanContext spanContext, @Nullable SpanId spanId, @Nullable Boolean bl, String string2, @Nullable Span.Kind kind, Timestamp timestamp, Attributes attributes, TimedEvents<Annotation> timedEvents, TimedEvents<? extends BaseMessageEvent> timedEvents2, Links links, @Nullable Integer n, @Nullable Status status, @Nullable Timestamp timestamp2) {
        Utils.checkNotNull(timedEvents2, "messageOrNetworkEvents");
        ArrayList arrayList = new ArrayList();
        Iterator<TimedEvent<? extends BaseMessageEvent>> iterator2 = timedEvents2.getEvents().iterator();
        while (iterator2.hasNext()) {
            TimedEvent<? extends BaseMessageEvent> timedEvent = iterator2.next();
            BaseMessageEvent baseMessageEvent = timedEvent.getEvent();
            if (baseMessageEvent instanceof MessageEvent) {
                arrayList.add(timedEvent);
                continue;
            }
            arrayList.add(TimedEvent.create(timedEvent.getTimestamp(), BaseMessageEventUtils.asMessageEvent(baseMessageEvent)));
        }
        return new AutoValue_SpanData(spanContext, spanId, bl, string2, kind, timestamp, attributes, timedEvents, TimedEvents.create(arrayList, timedEvents2.getDroppedEventsCount()), links, n, status, timestamp2);
    }

    public abstract TimedEvents<Annotation> getAnnotations();

    public abstract Attributes getAttributes();

    @Nullable
    public abstract Integer getChildSpanCount();

    public abstract SpanContext getContext();

    @Nullable
    public abstract Timestamp getEndTimestamp();

    @Nullable
    public abstract Boolean getHasRemoteParent();

    @Nullable
    public abstract Span.Kind getKind();

    public abstract Links getLinks();

    public abstract TimedEvents<MessageEvent> getMessageEvents();

    public abstract String getName();

    @Deprecated
    public TimedEvents<NetworkEvent> getNetworkEvents() {
        TimedEvents<MessageEvent> timedEvents = this.getMessageEvents();
        ArrayList<TimedEvent<T>> arrayList = new ArrayList<TimedEvent<T>>();
        Iterator<TimedEvent<MessageEvent>> iterator2 = timedEvents.getEvents().iterator();
        while (iterator2.hasNext()) {
            TimedEvent<MessageEvent> timedEvent = iterator2.next();
            arrayList.add(TimedEvent.create(timedEvent.getTimestamp(), BaseMessageEventUtils.asNetworkEvent(timedEvent.getEvent())));
        }
        return TimedEvents.create(arrayList, timedEvents.getDroppedEventsCount());
    }

    @Nullable
    public abstract SpanId getParentSpanId();

    public abstract Timestamp getStartTimestamp();

    @Nullable
    public abstract Status getStatus();

    public static abstract class Attributes {
        Attributes() {
        }

        public static Attributes create(Map<String, AttributeValue> map, int n) {
            return new AutoValue_SpanData_Attributes(Collections.unmodifiableMap(new HashMap<String, AttributeValue>(Utils.checkNotNull(map, "attributeMap"))), n);
        }

        public abstract Map<String, AttributeValue> getAttributeMap();

        public abstract int getDroppedAttributesCount();
    }

    public static abstract class Links {
        Links() {
        }

        public static Links create(List<Link> list, int n) {
            return new AutoValue_SpanData_Links(Collections.unmodifiableList(new ArrayList(Utils.checkNotNull(list, "links"))), n);
        }

        public abstract int getDroppedLinksCount();

        public abstract List<Link> getLinks();
    }

    public static abstract class TimedEvent<T> {
        TimedEvent() {
        }

        public static <T> TimedEvent<T> create(Timestamp timestamp, T t) {
            return new AutoValue_SpanData_TimedEvent<T>(timestamp, t);
        }

        public abstract T getEvent();

        public abstract Timestamp getTimestamp();
    }

    public static abstract class TimedEvents<T> {
        TimedEvents() {
        }

        public static <T> TimedEvents<T> create(List<TimedEvent<T>> list, int n) {
            return new AutoValue_SpanData_TimedEvents(Collections.unmodifiableList(new ArrayList(Utils.checkNotNull(list, "events"))), n);
        }

        public abstract int getDroppedEventsCount();

        public abstract List<TimedEvent<T>> getEvents();
    }

}

