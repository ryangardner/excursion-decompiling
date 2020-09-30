/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.export;

import io.opencensus.internal.Utils;
import io.opencensus.trace.Status;
import io.opencensus.trace.export.AutoValue_SampledSpanStore_ErrorFilter;
import io.opencensus.trace.export.AutoValue_SampledSpanStore_LatencyFilter;
import io.opencensus.trace.export.AutoValue_SampledSpanStore_PerSpanNameSummary;
import io.opencensus.trace.export.AutoValue_SampledSpanStore_Summary;
import io.opencensus.trace.export.SpanData;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public abstract class SampledSpanStore {
    protected SampledSpanStore() {
    }

    static SampledSpanStore newNoopSampledSpanStore() {
        return new NoopSampledSpanStore();
    }

    public abstract Collection<SpanData> getErrorSampledSpans(ErrorFilter var1);

    public abstract Collection<SpanData> getLatencySampledSpans(LatencyFilter var1);

    public abstract Set<String> getRegisteredSpanNamesForCollection();

    public abstract Summary getSummary();

    @Deprecated
    public abstract void registerSpanNamesForCollection(Collection<String> var1);

    @Deprecated
    public abstract void unregisterSpanNamesForCollection(Collection<String> var1);

    public static abstract class ErrorFilter {
        ErrorFilter() {
        }

        public static ErrorFilter create(String string2, @Nullable Status.CanonicalCode canonicalCode, int n) {
            boolean bl;
            boolean bl2 = true;
            if (canonicalCode != null) {
                bl = canonicalCode != Status.CanonicalCode.OK;
                Utils.checkArgument(bl, "Invalid canonical code.");
            }
            bl = n >= 0 ? bl2 : false;
            Utils.checkArgument(bl, "Negative maxSpansToReturn.");
            return new AutoValue_SampledSpanStore_ErrorFilter(string2, canonicalCode, n);
        }

        @Nullable
        public abstract Status.CanonicalCode getCanonicalCode();

        public abstract int getMaxSpansToReturn();

        public abstract String getSpanName();
    }

    public static final class LatencyBucketBoundaries
    extends Enum<LatencyBucketBoundaries> {
        private static final /* synthetic */ LatencyBucketBoundaries[] $VALUES;
        public static final /* enum */ LatencyBucketBoundaries MICROSx100_MILLIx1;
        public static final /* enum */ LatencyBucketBoundaries MICROSx10_MICROSx100;
        public static final /* enum */ LatencyBucketBoundaries MILLIx100_SECONDx1;
        public static final /* enum */ LatencyBucketBoundaries MILLIx10_MILLIx100;
        public static final /* enum */ LatencyBucketBoundaries MILLIx1_MILLIx10;
        public static final /* enum */ LatencyBucketBoundaries SECONDx100_MAX;
        public static final /* enum */ LatencyBucketBoundaries SECONDx10_SECONDx100;
        public static final /* enum */ LatencyBucketBoundaries SECONDx1_SECONDx10;
        public static final /* enum */ LatencyBucketBoundaries ZERO_MICROSx10;
        private final long latencyLowerNs;
        private final long latencyUpperNs;

        static {
            LatencyBucketBoundaries latencyBucketBoundaries;
            ZERO_MICROSx10 = new LatencyBucketBoundaries(0L, TimeUnit.MICROSECONDS.toNanos(10L));
            MICROSx10_MICROSx100 = new LatencyBucketBoundaries(TimeUnit.MICROSECONDS.toNanos(10L), TimeUnit.MICROSECONDS.toNanos(100L));
            MICROSx100_MILLIx1 = new LatencyBucketBoundaries(TimeUnit.MICROSECONDS.toNanos(100L), TimeUnit.MILLISECONDS.toNanos(1L));
            MILLIx1_MILLIx10 = new LatencyBucketBoundaries(TimeUnit.MILLISECONDS.toNanos(1L), TimeUnit.MILLISECONDS.toNanos(10L));
            MILLIx10_MILLIx100 = new LatencyBucketBoundaries(TimeUnit.MILLISECONDS.toNanos(10L), TimeUnit.MILLISECONDS.toNanos(100L));
            MILLIx100_SECONDx1 = new LatencyBucketBoundaries(TimeUnit.MILLISECONDS.toNanos(100L), TimeUnit.SECONDS.toNanos(1L));
            SECONDx1_SECONDx10 = new LatencyBucketBoundaries(TimeUnit.SECONDS.toNanos(1L), TimeUnit.SECONDS.toNanos(10L));
            SECONDx10_SECONDx100 = new LatencyBucketBoundaries(TimeUnit.SECONDS.toNanos(10L), TimeUnit.SECONDS.toNanos(100L));
            SECONDx100_MAX = latencyBucketBoundaries = new LatencyBucketBoundaries(TimeUnit.SECONDS.toNanos(100L), Long.MAX_VALUE);
            $VALUES = new LatencyBucketBoundaries[]{ZERO_MICROSx10, MICROSx10_MICROSx100, MICROSx100_MILLIx1, MILLIx1_MILLIx10, MILLIx10_MILLIx100, MILLIx100_SECONDx1, SECONDx1_SECONDx10, SECONDx10_SECONDx100, latencyBucketBoundaries};
        }

        private LatencyBucketBoundaries(long l, long l2) {
            this.latencyLowerNs = l;
            this.latencyUpperNs = l2;
        }

        public static LatencyBucketBoundaries valueOf(String string2) {
            return Enum.valueOf(LatencyBucketBoundaries.class, string2);
        }

        public static LatencyBucketBoundaries[] values() {
            return (LatencyBucketBoundaries[])$VALUES.clone();
        }

        public long getLatencyLowerNs() {
            return this.latencyLowerNs;
        }

        public long getLatencyUpperNs() {
            return this.latencyUpperNs;
        }
    }

    public static abstract class LatencyFilter {
        LatencyFilter() {
        }

        public static LatencyFilter create(String string2, long l, long l2, int n) {
            boolean bl = true;
            boolean bl2 = n >= 0;
            Utils.checkArgument(bl2, "Negative maxSpansToReturn.");
            bl2 = l >= 0L;
            Utils.checkArgument(bl2, "Negative latencyLowerNs");
            bl2 = l2 >= 0L ? bl : false;
            Utils.checkArgument(bl2, "Negative latencyUpperNs");
            return new AutoValue_SampledSpanStore_LatencyFilter(string2, l, l2, n);
        }

        public abstract long getLatencyLowerNs();

        public abstract long getLatencyUpperNs();

        public abstract int getMaxSpansToReturn();

        public abstract String getSpanName();
    }

    private static final class NoopSampledSpanStore
    extends SampledSpanStore {
        private static final PerSpanNameSummary EMPTY_PER_SPAN_NAME_SUMMARY = PerSpanNameSummary.create(Collections.<LatencyBucketBoundaries, Integer>emptyMap(), Collections.<Status.CanonicalCode, Integer>emptyMap());
        private final Set<String> registeredSpanNames = new HashSet<String>();

        private NoopSampledSpanStore() {
        }

        @Override
        public Collection<SpanData> getErrorSampledSpans(ErrorFilter errorFilter) {
            Utils.checkNotNull(errorFilter, "errorFilter");
            return Collections.emptyList();
        }

        @Override
        public Collection<SpanData> getLatencySampledSpans(LatencyFilter latencyFilter) {
            Utils.checkNotNull(latencyFilter, "latencyFilter");
            return Collections.emptyList();
        }

        @Override
        public Set<String> getRegisteredSpanNamesForCollection() {
            Set<String> set = this.registeredSpanNames;
            synchronized (set) {
                Set<String> set2 = new Set<String>(this.registeredSpanNames);
                return Collections.unmodifiableSet(set2);
            }
        }

        /*
         * Enabled unnecessary exception pruning
         */
        @Override
        public Summary getSummary() {
            HashMap<String, PerSpanNameSummary> hashMap = new HashMap<String, PerSpanNameSummary>();
            Set<String> set = this.registeredSpanNames;
            synchronized (set) {
                Iterator<String> iterator2 = this.registeredSpanNames.iterator();
                while (iterator2.hasNext()) {
                    hashMap.put(iterator2.next(), EMPTY_PER_SPAN_NAME_SUMMARY);
                }
                return Summary.create(hashMap);
            }
        }

        @Override
        public void registerSpanNamesForCollection(Collection<String> collection) {
            Utils.checkNotNull(collection, "spanNames");
            Set<String> set = this.registeredSpanNames;
            synchronized (set) {
                this.registeredSpanNames.addAll(collection);
                return;
            }
        }

        @Override
        public void unregisterSpanNamesForCollection(Collection<String> collection) {
            Utils.checkNotNull(collection, "spanNames");
            Set<String> set = this.registeredSpanNames;
            synchronized (set) {
                this.registeredSpanNames.removeAll(collection);
                return;
            }
        }
    }

    public static abstract class PerSpanNameSummary {
        PerSpanNameSummary() {
        }

        public static PerSpanNameSummary create(Map<LatencyBucketBoundaries, Integer> map, Map<Status.CanonicalCode, Integer> map2) {
            return new AutoValue_SampledSpanStore_PerSpanNameSummary(Collections.unmodifiableMap(new HashMap<LatencyBucketBoundaries, Integer>(Utils.checkNotNull(map, "numbersOfLatencySampledSpans"))), Collections.unmodifiableMap(new HashMap<Status.CanonicalCode, Integer>(Utils.checkNotNull(map2, "numbersOfErrorSampledSpans"))));
        }

        public abstract Map<Status.CanonicalCode, Integer> getNumbersOfErrorSampledSpans();

        public abstract Map<LatencyBucketBoundaries, Integer> getNumbersOfLatencySampledSpans();
    }

    public static abstract class Summary {
        Summary() {
        }

        public static Summary create(Map<String, PerSpanNameSummary> map) {
            return new AutoValue_SampledSpanStore_Summary(Collections.unmodifiableMap(new HashMap<String, PerSpanNameSummary>(Utils.checkNotNull(map, "perSpanNameSummary"))));
        }

        public abstract Map<String, PerSpanNameSummary> getPerSpanNameSummary();
    }

}

