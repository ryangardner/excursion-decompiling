/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.internal.Utils;
import io.opencensus.stats.AutoValue_BucketBoundaries;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BucketBoundaries {
    private static final Logger logger = Logger.getLogger(BucketBoundaries.class.getName());

    public static final BucketBoundaries create(List<Double> list) {
        Utils.checkNotNull(list, "bucketBoundaries");
        list = new ArrayList<Double>(list);
        if (list.size() <= 1) return new AutoValue_BucketBoundaries(Collections.unmodifiableList(BucketBoundaries.dropNegativeBucketBounds(list)));
        double d = list.get(0);
        int n = 1;
        while (n < list.size()) {
            double d2 = list.get(n);
            boolean bl = d < d2;
            Utils.checkArgument(bl, "Bucket boundaries not sorted.");
            ++n;
            d = d2;
        }
        return new AutoValue_BucketBoundaries(Collections.unmodifiableList(BucketBoundaries.dropNegativeBucketBounds(list)));
    }

    private static List<Double> dropNegativeBucketBounds(List<Double> list) {
        Comparable<Double> comparable;
        Object object = list.iterator();
        int n = 0;
        int n2 = 0;
        while (object.hasNext() && (Double)(comparable = object.next()) <= 0.0) {
            if ((Double)comparable == 0.0) {
                ++n2;
                continue;
            }
            ++n;
        }
        if (n <= 0) return list.subList(n + n2, list.size());
        object = logger;
        Level level = Level.WARNING;
        comparable = new StringBuilder();
        ((StringBuilder)comparable).append("Dropping ");
        ((StringBuilder)comparable).append(n);
        ((StringBuilder)comparable).append(" negative bucket boundaries, the values must be strictly > 0.");
        ((Logger)object).log(level, ((StringBuilder)comparable).toString());
        return list.subList(n + n2, list.size());
    }

    public abstract List<Double> getBoundaries();
}

