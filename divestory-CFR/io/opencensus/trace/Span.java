/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.internal.Utils;
import io.opencensus.trace.Annotation;
import io.opencensus.trace.AttributeValue;
import io.opencensus.trace.EndSpanOptions;
import io.opencensus.trace.Link;
import io.opencensus.trace.MessageEvent;
import io.opencensus.trace.NetworkEvent;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.Status;
import io.opencensus.trace.TraceOptions;
import io.opencensus.trace.internal.BaseMessageEventUtils;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

public abstract class Span {
    private static final Set<Options> DEFAULT_OPTIONS;
    private static final Map<String, AttributeValue> EMPTY_ATTRIBUTES;
    private final SpanContext context;
    private final Set<Options> options;

    static {
        EMPTY_ATTRIBUTES = Collections.emptyMap();
        DEFAULT_OPTIONS = Collections.unmodifiableSet(EnumSet.noneOf(Options.class));
    }

    protected Span(SpanContext spanContext, @Nullable EnumSet<Options> set) {
        this.context = Utils.checkNotNull(spanContext, "context");
        set = set == null ? DEFAULT_OPTIONS : Collections.unmodifiableSet(EnumSet.copyOf(set));
        this.options = set;
        boolean bl = !spanContext.getTraceOptions().isSampled() || this.options.contains((Object)Options.RECORD_EVENTS);
        Utils.checkArgument(bl, "Span is sampled, but does not have RECORD_EVENTS set.");
    }

    public abstract void addAnnotation(Annotation var1);

    public final void addAnnotation(String string2) {
        Utils.checkNotNull(string2, "description");
        this.addAnnotation(string2, EMPTY_ATTRIBUTES);
    }

    public abstract void addAnnotation(String var1, Map<String, AttributeValue> var2);

    @Deprecated
    public void addAttributes(Map<String, AttributeValue> map) {
        this.putAttributes(map);
    }

    public abstract void addLink(Link var1);

    public void addMessageEvent(MessageEvent messageEvent) {
        Utils.checkNotNull(messageEvent, "messageEvent");
        this.addNetworkEvent(BaseMessageEventUtils.asNetworkEvent(messageEvent));
    }

    @Deprecated
    public void addNetworkEvent(NetworkEvent networkEvent) {
        this.addMessageEvent(BaseMessageEventUtils.asMessageEvent(networkEvent));
    }

    public final void end() {
        this.end(EndSpanOptions.DEFAULT);
    }

    public abstract void end(EndSpanOptions var1);

    public final SpanContext getContext() {
        return this.context;
    }

    public final Set<Options> getOptions() {
        return this.options;
    }

    public void putAttribute(String string2, AttributeValue attributeValue) {
        Utils.checkNotNull(string2, "key");
        Utils.checkNotNull(attributeValue, "value");
        this.putAttributes(Collections.singletonMap(string2, attributeValue));
    }

    public void putAttributes(Map<String, AttributeValue> map) {
        Utils.checkNotNull(map, "attributes");
        this.addAttributes(map);
    }

    public void setStatus(Status status) {
        Utils.checkNotNull(status, "status");
    }

    public static final class Kind
    extends Enum<Kind> {
        private static final /* synthetic */ Kind[] $VALUES;
        public static final /* enum */ Kind CLIENT;
        public static final /* enum */ Kind SERVER;

        static {
            Kind kind;
            SERVER = new Kind();
            CLIENT = kind = new Kind();
            $VALUES = new Kind[]{SERVER, kind};
        }

        public static Kind valueOf(String string2) {
            return Enum.valueOf(Kind.class, string2);
        }

        public static Kind[] values() {
            return (Kind[])$VALUES.clone();
        }
    }

    public static final class Options
    extends Enum<Options> {
        private static final /* synthetic */ Options[] $VALUES;
        public static final /* enum */ Options RECORD_EVENTS;

        static {
            Options options;
            RECORD_EVENTS = options = new Options();
            $VALUES = new Options[]{options};
        }

        public static Options valueOf(String string2) {
            return Enum.valueOf(Options.class, string2);
        }

        public static Options[] values() {
            return (Options[])$VALUES.clone();
        }
    }

}

