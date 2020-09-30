/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.export;

import io.opencensus.internal.Utils;
import io.opencensus.metrics.export.AutoValue_Summary;
import io.opencensus.metrics.export.AutoValue_Summary_Snapshot;
import io.opencensus.metrics.export.AutoValue_Summary_Snapshot_ValueAtPercentile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

public abstract class Summary {
    Summary() {
    }

    private static void checkCountAndSum(@Nullable Long l, @Nullable Double d) {
        boolean bl;
        block4 : {
            block3 : {
                boolean bl2 = false;
                bl = l == null || l >= 0L;
                Utils.checkArgument(bl, "count must be non-negative.");
                bl = d == null || d >= 0.0;
                Utils.checkArgument(bl, "sum must be non-negative.");
                if (l == null) return;
                if (l != 0L) return;
                if (d == null) break block3;
                bl = bl2;
                if (d != 0.0) break block4;
            }
            bl = true;
        }
        Utils.checkArgument(bl, "sum must be 0 if count is 0.");
    }

    public static Summary create(@Nullable Long l, @Nullable Double d, Snapshot snapshot) {
        Summary.checkCountAndSum(l, d);
        Utils.checkNotNull(snapshot, "snapshot");
        return new AutoValue_Summary(l, d, snapshot);
    }

    @Nullable
    public abstract Long getCount();

    public abstract Snapshot getSnapshot();

    @Nullable
    public abstract Double getSum();

    public static abstract class Snapshot {
        public static Snapshot create(@Nullable Long l, @Nullable Double d, List<ValueAtPercentile> list) {
            Summary.checkCountAndSum(l, d);
            Utils.checkListElementNotNull(Utils.checkNotNull(list, "valueAtPercentiles"), "valueAtPercentile");
            return new AutoValue_Summary_Snapshot(l, d, Collections.unmodifiableList(new ArrayList<ValueAtPercentile>(list)));
        }

        @Nullable
        public abstract Long getCount();

        @Nullable
        public abstract Double getSum();

        public abstract List<ValueAtPercentile> getValueAtPercentiles();

        public static abstract class ValueAtPercentile {
            public static ValueAtPercentile create(double d, double d2) {
                boolean bl = true;
                boolean bl2 = 0.0 < d && d <= 100.0;
                Utils.checkArgument(bl2, "percentile must be in the interval (0.0, 100.0]");
                bl2 = d2 >= 0.0 ? bl : false;
                Utils.checkArgument(bl2, "value must be non-negative");
                return new AutoValue_Summary_Snapshot_ValueAtPercentile(d, d2);
            }

            public abstract double getPercentile();

            public abstract double getValue();
        }

    }

}

