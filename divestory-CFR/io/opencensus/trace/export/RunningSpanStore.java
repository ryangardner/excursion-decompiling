/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.export;

import io.opencensus.internal.Utils;
import io.opencensus.trace.export.AutoValue_RunningSpanStore_Filter;
import io.opencensus.trace.export.AutoValue_RunningSpanStore_PerSpanNameSummary;
import io.opencensus.trace.export.AutoValue_RunningSpanStore_Summary;
import io.opencensus.trace.export.SpanData;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class RunningSpanStore {
    private static final RunningSpanStore NOOP_RUNNING_SPAN_STORE = new NoopRunningSpanStore();

    protected RunningSpanStore() {
    }

    static RunningSpanStore getNoopRunningSpanStore() {
        return NOOP_RUNNING_SPAN_STORE;
    }

    public abstract Collection<SpanData> getRunningSpans(Filter var1);

    public abstract Summary getSummary();

    public abstract void setMaxNumberOfSpans(int var1);

    public static abstract class Filter {
        Filter() {
        }

        public static Filter create(String string2, int n) {
            boolean bl = n >= 0;
            Utils.checkArgument(bl, "Negative maxSpansToReturn.");
            return new AutoValue_RunningSpanStore_Filter(string2, n);
        }

        public abstract int getMaxSpansToReturn();

        public abstract String getSpanName();
    }

    private static final class NoopRunningSpanStore
    extends RunningSpanStore {
        private static final Summary EMPTY_SUMMARY = Summary.create(Collections.<String, PerSpanNameSummary>emptyMap());

        private NoopRunningSpanStore() {
        }

        @Override
        public Collection<SpanData> getRunningSpans(Filter filter) {
            Utils.checkNotNull(filter, "filter");
            return Collections.emptyList();
        }

        @Override
        public Summary getSummary() {
            return EMPTY_SUMMARY;
        }

        @Override
        public void setMaxNumberOfSpans(int n) {
            boolean bl = n >= 0;
            Utils.checkArgument(bl, "Invalid negative maxNumberOfElements");
        }
    }

    public static abstract class PerSpanNameSummary {
        PerSpanNameSummary() {
        }

        public static PerSpanNameSummary create(int n) {
            boolean bl = n >= 0;
            Utils.checkArgument(bl, "Negative numRunningSpans.");
            return new AutoValue_RunningSpanStore_PerSpanNameSummary(n);
        }

        public abstract int getNumRunningSpans();
    }

    public static abstract class Summary {
        Summary() {
        }

        public static Summary create(Map<String, PerSpanNameSummary> map) {
            return new AutoValue_RunningSpanStore_Summary(Collections.unmodifiableMap(new HashMap<String, PerSpanNameSummary>(Utils.checkNotNull(map, "perSpanNameSummary"))));
        }

        public abstract Map<String, PerSpanNameSummary> getPerSpanNameSummary();
    }

}

